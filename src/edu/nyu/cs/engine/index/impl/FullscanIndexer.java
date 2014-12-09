package edu.nyu.cs.engine.index.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

import edu.nyu.cs.engine.document.SearchDocument;
import edu.nyu.cs.engine.document.FullscanDocument;
import edu.nyu.cs.engine.index.SearchIndexer;
import edu.nyu.cs.engine.query.SearchQuery;
import edu.nyu.cs.engine.server.ServerOption;

/**
 * @author shenli
 * <p>
 * The {@code FullscanIndexer} based implementation of the {@link edu.nyu.cs.engine.index.SearchIndexer} 
 * interface. This implementation represents the simple fullscan indexer which provides availability of keeping 
 * search documents as well as terms information in order to access during search engine serving time.
 * <p>
 * Note: {@code FullscanIndexer} objects are mutable; their value could be changed after they are created. 
 * Thus, {@code FullscanIndexer} objects are not thread-safe. If multiple threads access a {@code FullscanIndexer} 
 * instance concurrently, and at least one of the threads modifies it structurally, it must be synchronized 
 * externally.
 */
public class FullscanIndexer extends SearchIndexer implements Serializable {
    private static final Logger LOGGER =  Logger.getLogger("edu.nyu.cs.engine.index.impl.FullscanIndexer");
    private static final long serialVersionUID = 8694287032090282208L;
    
    private Map<String, Integer> dictionary = new HashMap<>();
    private List<String> terms = new ArrayList<>();
    private Map<Integer, Integer> termFrequency = new HashMap<>();
    private Map<Integer, Integer> termFrequencyByDoc = new HashMap<>();
    private List<FullscanDocument> documents = new ArrayList<>();

    /**
     * Initializes a newly created {@code FullscanIndexer} object with no server option object. This 
     * constructor is provided for serialization usage.
     */
    public FullscanIndexer() {
        super();
    }
    
    /**
     * Initializes a newly created {@code FullscanIndexer} object so that it records specific server option 
     * arguments using in fullscan search indexing process.
     * <p>
     * @param serverOption the search engine server option
     */
    public FullscanIndexer(ServerOption serverOption) {
        super(serverOption);
    }
    
    /**
     * Returns an unmodifiable view of the tokens list and each token matches the indexes in this object. 
     * This method allows modules to provide users with "read-only" access to internal lists. Query operations 
     * on the returned list "read through" to the specified list, and attempts to modify the returned list, 
     * whether direct or via its iterator, result in an {@link java.lang.UnsupportedOperationException}.
     * <p>
     * @return an unmodifiable view of the specified list. If no tokens in document matched, returns an empty 
     * list
     */
    public List<String> getTermsByIndexes(List<Integer> indexes) {
        List<String> result = new ArrayList<>();
        for (int index : indexes) {
            result.add(terms.get(index));
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchDocument getDocument(int docId) {
        return (docId >= documents.size() || docId < 0) ? null : documents.get(docId);
    }
    
    /**
     * Always return null since the fullscan search indexer does not support to find the next document which 
     * match the search query.
     */
    @Override
    public SearchDocument nextDocument(SearchQuery query, int docId) {
        LOGGER.info("Not implement nextDocument logic");
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void construct() throws IOException {
        final String corpusFile = serverOption.getCorpusPrefix() + "/corpus.tsv";
        LOGGER.info("Construct search index from " + corpusFile);
        
        BufferedReader reader = new BufferedReader(
                new FileReader(corpusFile));
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                @SuppressWarnings("resource")
                Scanner scanner = new Scanner(line).useDelimiter("\t");
                final String title = scanner.next();
                final List<Integer> titleTokens = new ArrayList<>();
                readTerms(title, titleTokens);
                final List<Integer> bodyTokens = new ArrayList<>();
                readTerms(scanner.next(), bodyTokens);
                final int numberOfViews = Integer.parseInt(scanner.next());
                scanner.close();
                
                final FullscanDocument document = new FullscanDocument(
                        documents.size(), 
                        title, 
                        "", 
                        0.0f, 
                        numberOfViews,
                        this,
                        titleTokens,
                        bodyTokens);
                documents.add(document);
                ++numberOfDocs;
                
                final Set<Integer> uniqueTerms = new HashSet<>();
                updateIndexerStatistics(document.getTitleTokens(), uniqueTerms);
                updateIndexerStatistics(document.getBodyTokens(), uniqueTerms);
                for (Integer index : uniqueTerms) {
                    termFrequencyByDoc.put(index, termFrequencyByDoc.get(index) + 1);
                }
            }
        } finally {
            reader.close();
        }
        LOGGER.info(
                "Indexed " + Integer.toString(numberOfDocs) + " documents with " + Long.toString(totalTermFrequency) + " terms");
        
        final String indexFile = serverOption.getIndexPrefix() + "/corpus.idx";
        LOGGER.info("Save search index to " + indexFile);
        
        ObjectOutputStream writer = new ObjectOutputStream(
                new FileOutputStream(indexFile));
        writer.writeObject(this);
        writer.close();
    }
    
    /**
     * Read the content and collect each token into the specific {@code tokens} list. Besides, each token is 
     * updated for all arguments in the fullscan indexer.
     * <p>
     * @param content the string content
     * @param tokens the token list to be updated
     */
    private void readTerms(String content, List<Integer> tokens) {
        Scanner scanner = new Scanner(content);
        while (scanner.hasNext()) {
            final String token = scanner.next();
            int index = -1;
            if (dictionary.containsKey(token)) {
                index = dictionary.get(token);
            } else {
                index = terms.size();
                terms.add(token);
                dictionary.put(token, index);
                termFrequency.put(index, 0);
                termFrequencyByDoc.put(index, 0);
            }
            tokens.add(index);
        }
        scanner.close();
        return;
    }
    
    /**
     * Update the term frequencies in fullscan indexer based on the tokens list.
     * <p>
     * @param tokens the token list
     * @param uniqueTerms the word term set to be updated
     */
    private void updateIndexerStatistics(List<Integer> tokens, Set<Integer> uniqueTerms) {
        for (int index : tokens) {
            uniqueTerms.add(index);
            termFrequency.put(index, termFrequency.get(index) + 1);
            ++totalTermFrequency;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() throws IOException, ClassNotFoundException {
        final String indexFile = serverOption.getIndexPrefix() + "/corpus.idx";
        LOGGER.info("Load index file from " + indexFile);
        
        ObjectInputStream reader = new ObjectInputStream(
                new FileInputStream(indexFile));
        FullscanIndexer indexer = (FullscanIndexer) reader.readObject();
        this.documents = indexer.documents;
        this.numberOfDocs = documents.size();
        for (Integer frequency : indexer.termFrequency.values()) {
            this.totalTermFrequency += frequency;
        }
        this.dictionary = indexer.dictionary;
        this.terms = indexer.terms;
        this.termFrequency = indexer.termFrequency;
        this.termFrequencyByDoc = indexer.termFrequencyByDoc;
        reader.close();
        
        LOGGER.info(
                Integer.toString(numberOfDocs) + " documents loaded with " + Long.toString(totalTermFrequency) + " terms");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTermByIndex(int index) {
        return (index >= terms.size() || index < 0) ? null : terms.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIndexByTerm(String term) {
        return dictionary.containsKey(term) ? dictionary.get(term) : -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTermFrequency(String term) {
        return dictionary.containsKey(term) ? termFrequency.get(dictionary.get(term)) : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDocumentFrequencyByTerm(String term) {
        return dictionary.containsKey(term) ? termFrequencyByDoc.get(dictionary.get(term)) : 0;
    }

    /**
     * Always return 0 since the fullscan search indexer does not support to calculate the term numbers of 
     * each document.
     */
    @Override
    public int getTermFrequencyByDocument(String term, String url) {
        LOGGER.info("Not implement getTermFrequencyByDocument logic");
        return 0;
    }

}

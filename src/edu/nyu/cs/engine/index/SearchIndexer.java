package edu.nyu.cs.engine.index;

import java.io.IOException;

import edu.nyu.cs.engine.document.BasicDocument;
import edu.nyu.cs.engine.query.SearchQuery;
import edu.nyu.cs.engine.server.ServerOption;

/**
 * @author shenli
 * <p>
 * The {@code SearchIndexer} abstract class represents a skeletal implementation of the search indexer 
 * interface, to minimize the effort required to implement this abstract class.
 * <p>
 * To implement a specific indexer type, the programmer needs to extend this abstract class and provides
 * concrete implementation for all abstract methods in this class so that certain search indexer could run
 * basic operations successfully.
 */
public abstract class SearchIndexer {
    protected final ServerOption serverOption;
    protected int numberOfDocs = 0;
    protected long totalTermFrequency = 0;
    
    /**
     * Initializes a newly created {@code SearchIndexer} object so that it records basic arguments using in 
     * search indexing process.
     * <p>
     * @param serverOption the search engine server option
     */
    public SearchIndexer(ServerOption serverOption) {
        this.serverOption = serverOption;
    }
    
    /**
     * Returns the number of documents in the corpus.
     * <p>
     * @return the number of documents in the corpus
     */
    public final int getNumberOfDocs() {
        return numberOfDocs;
    }
    
    /**
     * Returns the number of term occurrences in the corpus. For example, if a term appears 10 times, it will 
     * be counted 10 times.
     * <p>
     * @return the number of term occurrences in the corpus
     */
    public final long getTotalTermFrequency() {
        return totalTermFrequency;
    }

    /**
     * Returns the {@link edu.nyu.cs.engine.document.BasicDocument} object or its subclass instance based on 
     * the document id.
     * <p>
     * @param docId the document id
     * @return the {@link edu.nyu.cs.engine.document.BasicDocument} object or its subclass instance based on 
     * the document id
     */
    public abstract BasicDocument getDocument(int docId);
    
    /**
     * Returns the {@link edu.nyu.cs.engine.document.BasicDocument} object or its subclass instance which is 
     * the next {@link edu.nyu.cs.engine.document.BasicDocument} object after specific {@code docId} satisfying 
     * the search query. If no such document exists, return {@code null}.
     * <p>
     * @param query the search query
     * @param docId the document id
     * @return the {@link edu.nyu.cs.engine.document.BasicDocument} object or its subclass instance which is 
     * the next {@link edu.nyu.cs.engine.document.BasicDocument} object after specific {@code docId} satisfying 
     * the search query
     */
    public abstract BasicDocument nextDocument(SearchQuery query, int docId);
    
    /**
     * Called when the {@link edu.nyu.cs.engine.server.SearchEngineServer} object is in 
     * {@link edu.nyu.cs.engine.server.ServerMode#INDEX} mode. The search indexes are constructed from the
     * provided corpus location which defined in server configuration file.
     * <p>
     * The indexing also includes document processing as:<br/>
     * A. Remove the non-visible web page content. For example, the content in the {@literal <script>} tag;<br/>
     * B. All tokens are stemmed based on the Porter's algorithm. For more details, please see 
     * <a href="http://en.wikipedia.org/wiki/Stemming">http://en.wikipedia.org/wiki/Stemming</a>;<br/>
     * C. Remove the stop-words and dynamically determine whether to drop the processing.
     * <p>
     * The introduced search indexes are resided under index location which described in server configuration
     * file and no other related data stored.
     * <p>
     * @throws IOException if an I/O error occurs
     */
    public abstract void construct() throws IOException;
    
    /**
     * Called exactly once when the {@link edu.nyu.cs.engine.server.SearchEngineServer} object is in
     * {@link edu.nyu.cs.engine.server.ServerMode#SERVER} mode. The search indexer are loaded from the provided
     * index location which defined in server configuration file and to be ready for serving users' HTTP search
     * requests.
     * <p>
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be located
     */
    public abstract void load() throws IOException, ClassNotFoundException;
    
    /**
     * Returns the string representation term by its index.
     * <p>
     * @param index the term index
     * @return the string representation term by its index
     */
    public abstract String getTermByIndex(int index);
    
    /**
     * Returns the term index based on the string representation term.
     * <p>
     * @param term the string representation term
     * @return the term index based on the string representation term
     */
    public abstract int getIndexByTerm(String term);
    
    /**
     * Returns the number of times that specific {@code term} appeared in the entire corpus.
     * <p>
     * @param term the string representation term
     * @return the number of times that specific {@code term} appeared in the entire corpus
     */
    public abstract int getTermFrequency(String term);
    
    /**
     * Returns the number of documents in which {@code term} appears over entire corpus.
     * <p>
     * @param term the string representation term
     * @return the number of documents in which {@code term} appears over entire corpus
     */
    public abstract int getDocumentFrequencyByTerm(String term);
    
    /**
     * Returns the number of times that {@code term} appeared in the specific document {@code url}.
     * <p>
     * @param term the string representation term
     * @param url the document url
     * @return the number of times that {@code term} appeared in the specific document {@code url}
     */
    public abstract int getTermFrequencyByDocument(String term, String url);
    
}

package edu.nyu.cs.engine.document;

import java.util.Collections;
import java.util.List;

import edu.nyu.cs.engine.index.impl.FullscanIndexer;

/**
 * @author shenli
 * <p>
 * The {@code FullscanDocument} based implementation of the {@link edu.nyu.cs.engine.document.SearchDocument} 
 * interface. This implementation represents search document along with title and body tokens' index list which  
 * all indexes come from the {@link edu.nyu.cs.engine.index.impl.FullscanIndexer} object. 
 * <p>
 * Note: {@code FullscanDocument} objects are mutable; their value could be changed after they are created. 
 * Thus, {@code FullscanDocument} objects are not thread-safe. If multiple threads access a {@code FullscanDocument} 
 * instance concurrently, and at least one of the threads modifies it structurally, it must be synchronized 
 * externally.
 */
public class FullscanDocument extends SearchDocument {
    private static final long serialVersionUID = 1819751558060838909L;
    
    private final FullscanIndexer indexer;
    private final List<Integer> titleTokens;
    private final List<Integer> bodyTokens;
    
    /**
     * Initializes a newly created {@code FullscanDocument} object with given values and default document url, 
     * page rank as well as number of views value so that it records search document information used in the 
     * fullscan indexer.
     * <p>
     * @param id the document id
     * @param title the document title
     * @param indexer the fullscan indexer
     * @param titleTokens the list of index points to the document title tokens
     * @param bodyTokens the list of index points to the document body tokens
     */
    public FullscanDocument(
            int id, 
            String title,
            FullscanIndexer indexer,
            List<Integer> titleTokens,
            List<Integer> bodyTokens) {
        super(id, title);
        this.indexer = indexer;
        this.titleTokens = titleTokens;
        this.bodyTokens = bodyTokens;
    }
    
    /**
     * Initializes a newly created {@code FullscanDocument} object with given values so that it records search 
     * document information used in the fullscan indexer.
     * <p>
     * @param id the document id
     * @param title the document title
     * @param url the document url
     * @param pageRank the document page rank
     * @param numberOfViews the document number of views
     * @param indexer the fullscan indexer
     * @param titleTokens the list of index points to the document title tokens
     * @param bodyTokens the list of index points to the document body tokens
     */
    public FullscanDocument(
            int id,
            String title,
            String url,
            float pageRank,
            int numberOfViews,
            FullscanIndexer indexer,
            List<Integer> titleTokens,
            List<Integer> bodyTokens) {
        super(id, title, url, pageRank, numberOfViews);
        this.indexer = indexer;
        this.titleTokens = titleTokens;
        this.bodyTokens = bodyTokens;
    }

    /**
     * Returns an unmodifiable view of the title tokens index list. This method allows modules to provide users 
     * with "read-only" access to internal lists. Query operations on the returned list "read through" to the 
     * specified list, and attempts to modify the returned list, whether direct or via its iterator, result 
     * in an {@link java.lang.UnsupportedOperationException}.
     * <p>
     * @return an unmodifiable view of the specified list. If no tokens in document title have been collected, 
     * returns an empty list
     */
    public List<Integer> getTitleTokens() {
        return Collections.unmodifiableList(titleTokens);
    }

    /**
     * Returns an unmodifiable view of the body tokens index list. This method allows modules to provide users 
     * with "read-only" access to internal lists. Query operations on the returned list "read through" to the 
     * specified list, and attempts to modify the returned list, whether direct or via its iterator, result 
     * in an {@link java.lang.UnsupportedOperationException}.
     * <p>
     * @return an unmodifiable view of the specified list. If no tokens in document body have been collected, 
     * returns an empty list
     */
    public List<Integer> getBodyTokens() {
        return Collections.unmodifiableList(bodyTokens);
    }
    
    /**
     * Returns an unmodifiable view of the title tokens list and each token comes from the pointed index in the
     * {@link edu.nyu.cs.engine.index.impl.FullscanIndexer} object. This method allows modules to provide users 
     * with "read-only" access to internal lists. Query operations on the returned list "read through" to the 
     * specified list, and attempts to modify the returned list, whether direct or via its iterator, result 
     * in an {@link java.lang.UnsupportedOperationException}.
     * <p>
     * @return an unmodifiable view of the specified list. If no tokens in document title have been collected, 
     * returns an empty list
     */
    public List<String> getInvertedTitleTokens() {
        return Collections.unmodifiableList(indexer.getTermsByIndexes(titleTokens));
    }
    
    /**
     * Returns an unmodifiable view of the body tokens list and each token comes from the pointed index in the
     * {@link edu.nyu.cs.engine.index.impl.FullscanIndexer} object. This method allows modules to provide users 
     * with "read-only" access to internal lists. Query operations on the returned list "read through" to the 
     * specified list, and attempts to modify the returned list, whether direct or via its iterator, result 
     * in an {@link java.lang.UnsupportedOperationException}.
     * <p>
     * @return an unmodifiable view of the specified list. If no tokens in document body have been collected, 
     * returns an empty list
     */
    public List<String> getInvertedBodyTokens() {
        return Collections.unmodifiableList(indexer.getTermsByIndexes(bodyTokens));
    }
    
}

package edu.nyu.cs.engine.index.utils;

/**
 * @author shenli
 * <p>
 * The {@code IndexerType} enum represents the search indexer type.
 */
public enum IndexerType {
    /**
     * The indexes contain all appearance terms with scanning the while corpus.
     */
    FULLSCAN,
    
    /**
     * The indexes contain the inverted map that all terms with the documents they appear in.
     */
    INVERTED_DOCONLY,
    
    /**
     * The indexes contain the inverted map that all terms with their occurrences in documents.
     */
    INVERTED_OCCURRENCE,
    
    /**
     * The indexer contain the inverted map that all terms with their occurrences in documents but with the 
     * resulting postings lists compressed.
     */
    INVERTED_COMPRESSED;
    
}

package edu.nyu.cs.engine.index.utils;

import edu.nyu.cs.engine.index.SearchIndexer;
import edu.nyu.cs.engine.server.ServerOption;

/**
 * @author shenli
 * <p>
 * Factory object that can vend search indexer based on the type of indexer.
 * <p>
 * NOTE: The factory object is thread-safe.
 */
public class SearchIndexerFactory {
    
    /**
     * Suppress default constructor for non-instantiable
     */
    private SearchIndexerFactory() {
        
    }
    
    /**
     * Returns a search indexer object based on the indexer type described in the {@code option}.
     * <p>
     * @param option the server option
     * @return a suitable search indexer object
     * @throws IllegalArgumentException if indexer type does not exist
     */
    public static SearchIndexer getSearchIndexer(ServerOption option) {
        IndexerType indexerType = option.getIndexerType();
        switch (indexerType) {
            case FULLSCAN:              // TODO create full scan search indexer object
            case INVERTED_DOCONLY:      // TODO create inverted doc-only search indexer object
            case INVERTED_OCCURRENCE:   // TODO create inverted occurrence search indexer object
            case INVERTED_COMPRESSED:   // TODO create inverted compressed search indexer object
        }
        throw new IllegalArgumentException("No such search indexer type: " + indexerType);
    }

}

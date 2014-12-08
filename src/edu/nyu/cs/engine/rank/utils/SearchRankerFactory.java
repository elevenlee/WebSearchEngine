package edu.nyu.cs.engine.rank.utils;

import edu.nyu.cs.engine.index.SearchIndexer;
import edu.nyu.cs.engine.rank.SearchRanker;
import edu.nyu.cs.engine.rank.impl.CosineRanker;
import edu.nyu.cs.engine.rank.impl.FullscanRanker;
import edu.nyu.cs.engine.rank.impl.LinearRanker;
import edu.nyu.cs.engine.rank.impl.NumberViewsRanker;
import edu.nyu.cs.engine.rank.impl.PhraseRanker;
import edu.nyu.cs.engine.rank.impl.QueryLikelihoodRanker;

/**
 * @author shenli
 * <p>
 * Factory object that can vend search ranker based on the type of ranker.
 * <p>
 * NOTE: The factory object is thread-safe.
 */
public class SearchRankerFactory {
    
    /**
     * Suppress default constructor for non-instantiable
     */
    private SearchRankerFactory() {
        
    }

    /**
     * Returns a search ranker object based on the ranker type described in the {@code rankerType}.
     * <p>
     * @param rankerType the ranker type
     * @param searchIndexer the search indexer object
     * @return a suitable search ranker object
     * @throws IllegalArgumentException if ranker type does not exist
     */
    public static SearchRanker getSearchRanker(
            RankerType rankerType, SearchIndexer searchIndexer) {
        switch (rankerType) {
            case FULLSCAN:          return new FullscanRanker(searchIndexer);
            case COSINE:            return new CosineRanker(searchIndexer);
            case QUERYLIKELIHOOD:   return new QueryLikelihoodRanker(searchIndexer);
            case PHRASE:            return new PhraseRanker(searchIndexer);
            case NUMBERVIEWS:       return new NumberViewsRanker(searchIndexer);
            case LINEAR:            return new LinearRanker(searchIndexer);
        }
        throw new IllegalArgumentException("No such search ranker type: " + rankerType);
    }
    
}

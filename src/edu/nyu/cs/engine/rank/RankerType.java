package edu.nyu.cs.engine.rank;

/**
 * @author shenli
 * <p>
 * The {@code RankerType} enum represents the search ranker type.
 */
public enum RankerType {
    /**
     * Fullscan model
     */
    FULLSCAN,
    
    /**
     * Cosine similarity vector space model
     */
    COSINE,
    
    /**
     * Query likelihood language model
     */
    QUERYLIKELIHOOD,
    
    /**
     * Phrase query bigrams model
     */
    PHRASE,
    
    /**
     * Number of views model
     */
    NUMBERVIEWS,
    
    /**
     * Linear ranking model which coefficient Cosine, Query likelihood, Phrase as well as Number of views models
     */
    LINEAR;
    
}
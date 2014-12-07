package edu.nyu.cs.engine.rank.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import edu.nyu.cs.engine.index.SearchIndexer;
import edu.nyu.cs.engine.rank.impl.CosineRanker;
import edu.nyu.cs.engine.rank.impl.FullscanRanker;
import edu.nyu.cs.engine.rank.impl.LinearRanker;
import edu.nyu.cs.engine.rank.impl.NumberViewsRanker;
import edu.nyu.cs.engine.rank.impl.PhraseRanker;
import edu.nyu.cs.engine.rank.impl.QueryLikelihoodRanker;

public class SearchRankerFactoryTest {
    private SearchIndexer searchIndexer;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        searchIndexer = Mockito.mock(SearchIndexer.class);
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.rank.utils.SearchRankerFactory#getSearchRanker(edu.nyu.cs.engine.rank.utils.RankerType, edu.nyu.cs.engine.index.SearchIndexer)}.
     */
    @Test
    public void testGetSearchRankerWithFullscanRanker() {
        assertEquals(FullscanRanker.class, SearchRankerFactory.getSearchRanker(
                RankerType.FULLSCAN, searchIndexer).getClass());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.rank.utils.SearchRankerFactory#getSearchRanker(edu.nyu.cs.engine.rank.utils.RankerType, edu.nyu.cs.engine.index.SearchIndexer)}.
     */
    @Test
    public void testGetSearchRankerWithCosineRanker() {
        assertEquals(CosineRanker.class, SearchRankerFactory.getSearchRanker(
                RankerType.COSINE, searchIndexer).getClass());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.rank.utils.SearchRankerFactory#getSearchRanker(edu.nyu.cs.engine.rank.utils.RankerType, edu.nyu.cs.engine.index.SearchIndexer)}.
     */
    @Test
    public void testGetSearchRankerWithQueryLikelihoodRanker() {
        assertEquals(QueryLikelihoodRanker.class, SearchRankerFactory.getSearchRanker(
                RankerType.QUERYLIKELIHOOD, searchIndexer).getClass());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.rank.utils.SearchRankerFactory#getSearchRanker(edu.nyu.cs.engine.rank.utils.RankerType, edu.nyu.cs.engine.index.SearchIndexer)}.
     */
    @Test
    public void testGetSearchRankerWithPhraseRanker() {
        assertEquals(PhraseRanker.class, SearchRankerFactory.getSearchRanker(
                RankerType.PHRASE, searchIndexer).getClass());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.rank.utils.SearchRankerFactory#getSearchRanker(edu.nyu.cs.engine.rank.utils.RankerType, edu.nyu.cs.engine.index.SearchIndexer)}.
     */
    @Test
    public void testGetSearchRankerWithNumberViewsRanker() {
        assertEquals(NumberViewsRanker.class, SearchRankerFactory.getSearchRanker(
                RankerType.NUMBERVIEWS, searchIndexer).getClass());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.rank.utils.SearchRankerFactory#getSearchRanker(edu.nyu.cs.engine.rank.utils.RankerType, edu.nyu.cs.engine.index.SearchIndexer)}.
     */
    @Test
    public void testGetSearchRankerWithLinearRanker() {
        assertEquals(LinearRanker.class, SearchRankerFactory.getSearchRanker(
                RankerType.LINEAR, searchIndexer).getClass());
    }

}

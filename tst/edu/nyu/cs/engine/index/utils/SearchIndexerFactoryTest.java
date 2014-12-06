package edu.nyu.cs.engine.index.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import edu.nyu.cs.engine.index.impl.FullscanIndexer;
import edu.nyu.cs.engine.index.impl.InvertedCompressedIndexer;
import edu.nyu.cs.engine.index.impl.InvertedDocOnlyIndexer;
import edu.nyu.cs.engine.index.impl.InvertedOccurrenceIndexer;
import edu.nyu.cs.engine.server.ServerOption;

public class SearchIndexerFactoryTest {
    private ServerOption serverOption;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        serverOption = Mockito.mock(ServerOption.class);
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.index.utils.SearchIndexerFactory#getSearchIndexer(edu.nyu.cs.engine.server.ServerOption)}.
     */
    @Test
    public void testGetSearchIndexerWithFullscanIndexer() {
        Mockito.when(serverOption.getIndexerType()).thenReturn(IndexerType.FULLSCAN);
        assertEquals(FullscanIndexer.class, SearchIndexerFactory.getSearchIndexer(serverOption).getClass());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.index.utils.SearchIndexerFactory#getSearchIndexer(edu.nyu.cs.engine.server.ServerOption)}.
     */
    @Test
    public void testGetSearchIndexerWithInvertedDocOnlyIndexer() {
        Mockito.when(serverOption.getIndexerType()).thenReturn(IndexerType.INVERTED_DOCONLY);
        assertEquals(InvertedDocOnlyIndexer.class, SearchIndexerFactory.getSearchIndexer(serverOption).getClass());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.index.utils.SearchIndexerFactory#getSearchIndexer(edu.nyu.cs.engine.server.ServerOption)}.
     */
    @Test
    public void testGetSearchIndexerWithInvertedOccurrenceIndexer() {
        Mockito.when(serverOption.getIndexerType()).thenReturn(IndexerType.INVERTED_OCCURRENCE);
        assertEquals(InvertedOccurrenceIndexer.class, SearchIndexerFactory.getSearchIndexer(serverOption).getClass());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.index.utils.SearchIndexerFactory#getSearchIndexer(edu.nyu.cs.engine.server.ServerOption)}.
     */
    @Test
    public void testGetSearchIndexerWithInvertedCompressedIndexer() {
        Mockito.when(serverOption.getIndexerType()).thenReturn(IndexerType.INVERTED_COMPRESSED);
        assertEquals(InvertedCompressedIndexer.class, SearchIndexerFactory.getSearchIndexer(serverOption).getClass());
    }

}

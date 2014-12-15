package edu.nyu.cs.engine.server;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.engine.exception.IllegalSearchEngineConfigurationException;
import edu.nyu.cs.engine.index.utils.IndexerType;

public class ServerOptionTest {
    private ServerOption serverOption;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.serverOption = ServerOption.newInstance("test-files/confs/engine.conf");
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.server.ServerOption#newInstance(java.lang.String)}.
     */
    @Test
    public void testNewInstance() {
        assertEquals("test-files/data/simple/corpus.tsv", serverOption.getCorpusPath());
        assertEquals("test-files/data/index/corpus.idx", serverOption.getIndexPath());
        assertEquals(IndexerType.FULLSCAN, serverOption.getIndexerType());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.server.ServerOption#newInstance(java.lang.String)}.
     */
    @Test(expected=IOException.class)
    public void testNewInstanceWithNonexistConfigurationFile() throws IOException {
        ServerOption.newInstance("test-files/confs/nonexist.conf");
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.server.ServerOption#newInstance(java.lang.String)}.
     */
    @Test(expected=IllegalSearchEngineConfigurationException.class)
    public void testNewInstanceWithNoCorpusConfiguration() {
        try {
            ServerOption.newInstance("test-files/confs/no-corpus.conf");
        } catch (IOException e) {
            
        }
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.server.ServerOption#newInstance(java.lang.String)}.
     */
    @Test(expected=IllegalSearchEngineConfigurationException.class)
    public void testNewInstanceWithNoIndexConfiguration() {
        try {
            ServerOption.newInstance("test-files/confs/no-index.conf");
        } catch (IOException e) {
            
        }
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.server.ServerOption#newInstance(java.lang.String)}.
     */
    @Test(expected=IllegalSearchEngineConfigurationException.class)
    public void testNewInstanceWithNoIndexerTypeConfiguration() {
        try {
            ServerOption.newInstance("test-files/confs/no-indexer-type.conf");
        } catch (IOException e) {
            
        }
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.server.ServerOption#newInstance(java.lang.String)}.
     */
    @Test(expected=IllegalSearchEngineConfigurationException.class)
    public void testNewInstanceWithNotValidConfiguration() {
        try {
            ServerOption.newInstance("test-files/confs/not-valid.conf");
        } catch (IOException e) {
            
        }
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.server.ServerOption#newInstance(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testNewInstanceWithNotSuchIndexerType() {
        try {
            ServerOption.newInstance("test-files/confs/nonexist-indexer-type.conf");
        } catch (IOException e) {
            
        }
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.server.ServerOption#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithThisObject() {
        assertTrue(serverOption.equals(serverOption));
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.server.ServerOption#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithOtherTypeObject() {
        assertFalse(serverOption.equals(new String("test")));
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.server.ServerOption#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithNotEqualObject() throws IOException {
        assertFalse(serverOption.equals(ServerOption.newInstance("test-files/confs/other-engine.conf")));
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.server.ServerOption#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals(
                "ServerOption={corpus_path: test-files/data/simple/corpus.tsv, index_path: test-files/data/index/corpus.idx, indexer_type: fullscan}",
                serverOption.toString());
    }

}

package edu.nyu.cs.engine.query;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.engine.rank.RankerType;

public class QueryParameterTest {
    private QueryParameter queryParameter;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.queryParameter = QueryParameter.newInstance("query=test sample&ranker=fullscan&format=html&numResults=50");
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.query.QueryParameter#getQuery()}.
     */
    @Test
    public void testGetQuery() {
        assertEquals("test sample", queryParameter.getQuery());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.query.QueryParameter#getRankerType()}.
     */
    @Test
    public void testGetRankerType() {
        assertEquals(RankerType.FULLSCAN, queryParameter.getRankerType());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.query.QueryParameter#getFormat()}.
     */
    @Test
    public void testGetFormat() {
        assertEquals(QueryParameter.Format.HTML, queryParameter.getFormat());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.query.QueryParameter#getNumberOfResults()}.
     */
    @Test
    public void testGetNumberOfResults() {
        assertEquals(50, queryParameter.getNumberOfResults());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.query.QueryParameter#newInstance(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testNewInstanceWithNonexistRankerType() {
        try {
            QueryParameter.newInstance("query=test sample&ranker=nonexist&format=html&numResults=50");
        } catch (UnsupportedEncodingException e) {
            
        }
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.query.QueryParameter#newInstance(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testNewInstanceWithNonexistFormatType() {
        try {
            QueryParameter.newInstance("query=test sample&ranker=fullscan&format=json&numResults=50");
        } catch (UnsupportedEncodingException e) {
            
        }
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.query.QueryParameter#newInstance(java.lang.String)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testNewInstanceWithNegativeNumResults() {
        try {
            QueryParameter.newInstance("query=test sample&ranker=fullscan&format=html&numResults=-10");
        } catch (UnsupportedEncodingException e) {
            
        }
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.query.QueryParameter#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithThisObject() {
        assertTrue(queryParameter.equals(queryParameter));
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.query.QueryParameter#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithOtherTypeObject() {
        assertFalse(queryParameter.equals(new String("test")));
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.query.QueryParameter#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithEquallyObject() throws UnsupportedEncodingException {
        QueryParameter qp = QueryParameter.newInstance("format=html&ranker=fullscan&query=test sample&numResults=50");
        assertTrue(queryParameter.equals(qp));
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.query.QueryParameter#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithNotEqualObject() throws UnsupportedEncodingException {
        QueryParameter qp1 = QueryParameter.newInstance("format=html&ranker=fullscan&query=other test sample&numResults=50");
        assertFalse(queryParameter.equals(qp1));
        
        QueryParameter qp2 = QueryParameter.newInstance("format=html&ranker=linear&query=test sample&numResults=50");
        assertFalse(queryParameter.equals(qp2));
        
        QueryParameter qp3 = QueryParameter.newInstance("format=text&ranker=fullscan&query=test sample&numResults=50");
        assertFalse(queryParameter.equals(qp3));
        
        QueryParameter qp4 = QueryParameter.newInstance("format=html&ranker=fullscan&query=test sample&numResults=10");
        assertFalse(queryParameter.equals(qp4));
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.query.QueryParameter#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals(
                "QueryParameter={query: test sample, rankerType: fullscan, format: html, numberOfResults: 50}",
                queryParameter.toString());
    }

}

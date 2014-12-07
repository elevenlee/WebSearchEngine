package edu.nyu.cs.engine.document;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BasicDocumentTest {
    private BasicDocument basicDocument;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        basicDocument = new BasicDocument.BasicDocumentBuilder(1)
                            .title("test doc")
                            .url("http://localhost/test.html")
                            .pageRank(4.5f)
                            .numberOfViews(125).build();
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.BasicDocument#getId()}.
     */
    @Test
    public void testGetId() {
        assertEquals(1, basicDocument.getId());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.BasicDocument#getTitle()}.
     */
    @Test
    public void testGetTitle() {
        assertEquals("test doc", basicDocument.getTitle());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.BasicDocument#getUrl()}.
     */
    @Test
    public void testGetUrl() {
        assertEquals("http://localhost/test.html", basicDocument.getUrl());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.BasicDocument#getPageRank()}.
     */
    @Test
    public void testGetPageRank() {
        assertTrue(Float.compare(4.5f, basicDocument.getPageRank()) == 0);
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.BasicDocument#getNumberOfViews()}.
     */
    @Test
    public void testGetNumberOfViews() {
        assertEquals(125, basicDocument.getNumberOfViews());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.BasicDocument#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithThisObject() {
        assertTrue(basicDocument.equals(basicDocument));
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.document.BasicDocument#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithOtherTypeObject() {
        assertFalse(basicDocument.equals(new String("test doc")));
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.document.BasicDocument#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithEquallyObject() {
        BasicDocument bd = new BasicDocument.BasicDocumentBuilder(1)
                                .url("http://localhost/test.html")
                                .numberOfViews(125)
                                .title("test doc")
                                .pageRank(4.5f).build();
        assertTrue(basicDocument.equals(bd));
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.document.BasicDocument#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithNotEqualObject() {
        BasicDocument bd = new BasicDocument.BasicDocumentBuilder(1).build();
        assertFalse(basicDocument.equals(bd));
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.BasicDocument#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals(
                "BasicDocument={id: 1, title: test doc, url: http://localhost/test.html, page rank: 4.5, number of views: 125}",
                basicDocument.toString());
    }

}

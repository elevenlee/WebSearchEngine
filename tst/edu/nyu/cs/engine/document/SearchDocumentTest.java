package edu.nyu.cs.engine.document;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SearchDocumentTest {
    private SearchDocument searchDocument;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        searchDocument = new SearchDocument(
                1, "test doc", "http://localhost/test.html", 4.5f, 125);
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.SearchDocument#getId()}.
     */
    @Test
    public void testGetId() {
        assertEquals(1, searchDocument.getId());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.SearchDocument#getTitle()}.
     */
    @Test
    public void testGetTitle() {
        assertEquals("test doc", searchDocument.getTitle());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.SearchDocument#getUrl()}.
     */
    @Test
    public void testGetUrl() {
        assertEquals("http://localhost/test.html", searchDocument.getUrl());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.document.SearchDocument#setUrl()}.
     */
    @Test
    public void testSetUrl() {
        String url = "http://localhost/newurl.html";
        searchDocument.setUrl(url);
        assertEquals(url, searchDocument.getUrl());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.SearchDocument#getPageRank()}.
     */
    @Test
    public void testGetPageRank() {
        assertTrue(Float.compare(4.5f, searchDocument.getPageRank()) == 0);
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.document.SearchDocument#setPageRank()}.
     */
    @Test
    public void testSetPageRank() {
        float pageRank = 10.3f;
        searchDocument.setPageRank(pageRank);
        assertTrue(Float.compare(pageRank, searchDocument.getPageRank()) == 0);
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.SearchDocument#getNumberOfViews()}.
     */
    @Test
    public void testGetNumberOfViews() {
        assertEquals(125, searchDocument.getNumberOfViews());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.document.SearchDocument#setNumberOfViews()}.
     */
    @Test
    public void testSetNumberOfViews() {
        int numberOfViews = 500;
        searchDocument.setNumberOfViews(numberOfViews);
        assertEquals(numberOfViews, searchDocument.getNumberOfViews());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.SearchDocument#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithThisObject() {
        assertTrue(searchDocument.equals(searchDocument));
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.document.SearchDocument#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithOtherTypeObject() {
        assertFalse(searchDocument.equals(new String("test doc")));
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.document.SearchDocument#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithEquallyObject() {
        SearchDocument bd = new SearchDocument(
                1, "test doc", "http://localhost/test.html", 4.5f, 125);
        assertTrue(searchDocument.equals(bd));
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.document.SearchDocument#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithNotEqualObject() {
        SearchDocument bd = new SearchDocument(1, "test doc");
        assertFalse(searchDocument.equals(bd));
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.SearchDocument#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals(
                "BasicDocument={id: 1, title: test doc, url: http://localhost/test.html, page rank: 4.500, number of views: 125}",
                searchDocument.toString());
    }

}

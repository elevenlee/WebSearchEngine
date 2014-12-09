package edu.nyu.cs.engine.document;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ScoredDocumentTest {
    private ScoredDocument scoredDocument;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.scoredDocument = new ScoredDocument(
                new SearchDocument(
                        1, "test doc", "http://localhost/test.html", 1.1f, 100),
                123.456);
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.ScoredDocument#getDocument()}.
     */
    @Test
    public void testGetDocument() {
        SearchDocument sd = new SearchDocument(
                1, "test doc", "http://localhost/test.html", 1.1f, 100);
        assertEquals(sd, scoredDocument.getDocument());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.ScoredDocument#getScore()}.
     */
    @Test
    public void testGetScore() {
        assertTrue(Double.compare(123.456, scoredDocument.getScore()) == 0);
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.ScoredDocument#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithThisObject() {
        assertTrue(scoredDocument.equals(scoredDocument));
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.document.ScoredDocument#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithOtherTypeObject() {
        assertFalse(scoredDocument.equals(new String("test doc")));
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.document.ScoredDocument#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithEqualObject() {
        ScoredDocument sd = new ScoredDocument(
                new SearchDocument(1, "same score"),
                123.4560);
        assertTrue(scoredDocument.equals(sd));
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.document.ScoredDocument#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectWithNotEqualObject() {
        ScoredDocument sd = new ScoredDocument(
                new SearchDocument(
                        1, "test doc", "http://localhost/test.html", 1.1f, 100),
                1.01);
        assertFalse(scoredDocument.equals(sd));
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.document.ScoredDocument#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals(
                "ScoredDocument={document: BasicDocument={id: 1, title: test doc, url: http://localhost/test.html, page rank: 1.100, number of views: 100}, score: 123.456}", 
                scoredDocument.toString());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.document.ScoredDocument#compareTo(edu.nyu.cs.engine.document.ScoredDocument)}.
     */
    @Test
    public void testCompareToWithGreaterObject() {
        ScoredDocument sd = new ScoredDocument(
                new SearchDocument(1, "greater than test doc"),
                999.9999);
        assertTrue(scoredDocument.compareTo(sd) < 0);
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.document.ScoredDocument#compareTo(edu.nyu.cs.engine.document.ScoredDocument)}.
     */
    @Test
    public void testCompareToWithEquallyObject() {
        ScoredDocument sd = new ScoredDocument(
                new SearchDocument(1, "same score doc"),
                123.456000);
        assertTrue(scoredDocument.compareTo(sd) == 0);
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.document.ScoredDocument#compareTo(edu.nyu.cs.engine.document.ScoredDocument)}.
     */
    @Test
    public void testCompareToWithLessObject() {
        ScoredDocument sd = new ScoredDocument(
                new SearchDocument(1, "less score"),
                10.003);
        assertTrue(scoredDocument.compareTo(sd) > 0);
    }

}

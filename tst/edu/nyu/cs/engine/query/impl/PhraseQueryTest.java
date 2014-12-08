package edu.nyu.cs.engine.query.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PhraseQueryTest {
    private PhraseQuery phraseQuery;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.phraseQuery = new PhraseQuery("foo and \"bar or\" baz");
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.query.impl.PhraseQuery#getQuery()}.
     */
    @Test
    public void testGetQuery() {
        assertEquals("foo and \"bar or\" baz", phraseQuery.getQuery());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.query.impl.PhraseQuery#processQuery()}.
     */
    @Test
    public void testProcessQuery() {
        List<List<String>> phrases = Arrays.asList(
                Arrays.asList("foo"),
                Arrays.asList("and"),
                Arrays.asList("bar", "or"),
                Arrays.asList("baz"));
        phraseQuery.processQuery();
        assertEquals(phrases, phraseQuery.getTokens());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.query.impl.PhraseQuery#processQuery()}.
     */
    @Test
    public void testProcessQueryWithNullQuery() {
        PhraseQuery pq = new PhraseQuery(null);
        pq.processQuery();
        assertEquals(Collections.emptyList(), pq.getTokens());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.query.impl.PhraseQuery#processQuery()}.
     */
    @Test
    public void testProcessQueryWithEmptyQuery() {
        PhraseQuery pq = new PhraseQuery("");
        pq.processQuery();
        assertEquals(Collections.emptyList(), pq.getTokens());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.query.impl.PhraseQuery#processQuery()}.
     */
    @Test
    public void testProcessQueryWithMultiplePhraseQuery() {
        PhraseQuery pq = new PhraseQuery("\"foo and bar\" or baz there \"has\" several \"phrases in\" this \"test case\"");
        List<List<String>> phrases = Arrays.asList(
                Arrays.asList("foo", "and", "bar"),
                Arrays.asList("or"),
                Arrays.asList("baz"),
                Arrays.asList("there"),
                Arrays.asList("has"),
                Arrays.asList("several"),
                Arrays.asList("phrases", "in"),
                Arrays.asList("this"),
                Arrays.asList("test", "case"));
        pq.processQuery();
        assertEquals(phrases, pq.getTokens());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.query.impl.PhraseQuery#processQuery()}.
     */
    @Test
    public void testProcessQueryWithMultipleWhitespaceQuery() {
        PhraseQuery pq = new PhraseQuery("   foo and  \" bar    or \"     baz  ");
        List<List<String>> phrases = Arrays.asList(
                Arrays.asList("foo"),
                Arrays.asList("and"),
                Arrays.asList("bar", "or"),
                Arrays.asList("baz"));
        pq.processQuery();
        assertEquals(phrases, pq.getTokens());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.query.impl.PhraseQuery#processQuery()}.
     */
    @Test
    public void testProcessQueryWithEmptyPhraseQuery() {
        PhraseQuery pq = new PhraseQuery("\"foo and\" \"\" bar or \"baz\"");
        List<List<String>> phrases = Arrays.asList(
                Arrays.asList("foo", "and"),
                Arrays.asList("bar"),
                Arrays.asList("or"),
                Arrays.asList("baz"));
        pq.processQuery();
        assertEquals(phrases, pq.getTokens());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.query.impl.PhraseQuery#toString()}.
     */
    @Test
    public void testToString() {
        phraseQuery.processQuery();
        assertEquals(
                "PhraseQuery={query: foo and \"bar or\" baz, phrases: [[foo], [and], [bar, or], [baz]]}",
                phraseQuery.toString());
    }

}

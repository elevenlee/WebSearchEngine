package edu.nyu.cs.engine.query.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class WordQueryTest {
    private WordQuery wordQuery;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        wordQuery = new WordQuery("foo and bar or baz");
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.query.impl.WordQuery#getQuery()}.
     */
    @Test
    public void testGetQuery() {
        assertEquals("foo and bar or baz", wordQuery.getQuery());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.query.impl.WordQuery#processQuery()}.
     */
    @Test
    public void testProcessQuery() {
        wordQuery.processQuery();
        List<String> tokens = Arrays.asList("foo", "and", "bar", "or", "baz");
        assertEquals(tokens, wordQuery.getTokens());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.query.impl.WordQuery#processQuery()}.
     */
    @Test
    public void testProcessQueryWithNullQuery() {
        WordQuery wq = new WordQuery(null);
        wq.processQuery();
        assertEquals(Collections.emptyList(), wordQuery.getTokens());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.query.impl.WordQuery#processQuery()}.
     */
    @Test
    public void testProcessQueryWithEmptyQuery() {
        WordQuery wq = new WordQuery("");
        wq.processQuery();
        assertEquals(Collections.emptyList(), wordQuery.getTokens());
    }
    
    /**
     * Test method for {@link edu.nyu.cs.engine.query.impl.WordQuery#processQuery()}.
     */
    @Test
    public void testProcessQueryWithMultipleWhitespaceQuery() {
        WordQuery wq = new WordQuery("  foo and bar   or       baz   ");
        wq.processQuery();
        List<String> tokens = Arrays.asList("foo", "and", "bar", "or", "baz");
        assertEquals(tokens, wq.getTokens());
    }

    /**
     * Test method for {@link edu.nyu.cs.engine.query.impl.WordQuery#toString()}.
     */
    @Test
    public void testToString() {
        wordQuery.processQuery();
        assertEquals(
                "WordQuery={query: foo and bar or baz, tokens: [foo, and, bar, or, baz]}", wordQuery.toString());
    }

}

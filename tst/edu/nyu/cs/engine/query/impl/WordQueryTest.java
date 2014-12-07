package edu.nyu.cs.engine.query.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
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
     * Test method for {@link edu.nyu.cs.engine.query.impl.WordQuery#toString()}.
     */
    @Test
    public void testToString() {
        wordQuery.processQuery();
        assertEquals(
                "WordQuery={query: foo and bar or baz, tokens: [foo, and, bar, or, baz]}", wordQuery.toString());
    }

}

package edu.nyu.cs.engine.query.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import edu.nyu.cs.engine.query.SearchQuery;

/**
 * @author shenli
 * <p>
 * The {@code WordQuery} based implementation of the {@link edu.nyu.cs.engine.query.SearchQuery} interface.
 * This implementation represents single word tokens list which collected based on the search query of 
 * splitting around the whitespace.
 * <p>
 * The search query "foo and bar", for example, yields the following word token results: 
 * {@literal {"foo", "and", "bar"}}.
 * <p>
 * Note: {@code WordQuery} objects are mutable; their value could be changed after they are created. Thus, 
 * {@code WordQuery} objects are not thread-safe. If multiple threads access a {@code WordQuery} instance 
 * concurrently, and at least one of the threads modifies it structurally, it must be synchronized externally.
 */
public class WordQuery implements SearchQuery {
    private final String query;
    private final List<String> tokens = new ArrayList<>();
    
    public WordQuery(String query) {
        this.query = query;
    }
    
    /**
     * Returns the search query.
     * <p>
     * @return the search query
     */
    public String getQuery() {
        return query;
    }

    /**
     * Returns an unmodifiable view of the word token list. This method allows modules to provide users with 
     * "read-only" access to internal lists. Query operations on the returned list "read through" to the 
     * specified list, and attempts to modify the returned list, whether direct or via its iterator, result 
     * in an {@link java.lang.UnsupportedOperationException}.
     * <p>
     * @return an unmodifiable view of the specified list. If no word token have been collected, returns an 
     * empty list
     */
    public List<String> getTokens() {
        return Collections.unmodifiableList(tokens);
    }

    /**
     * Separate the search query by whitespace and collect word tokens into the list.
     */
    @Override
    public void processQuery() {
        if (query == null) {
            return;
        }
        Scanner scanner = new Scanner(query);
        while (scanner.hasNext()) {
            tokens.add(scanner.next());
        }
        scanner.close();
    }
    
    /**
     * Returns the string representation of this {@code WordQuery} object. The string consists of raw 
     * search query, and word tokens collected by the raw query.
     * <p>
     * @return string comprising the raw query as well as word tokens
     */
    @Override
    public String toString() {
        return String.format(
                "WordQuery={query: %s, tokens: %s}", query, tokens);
    }

}

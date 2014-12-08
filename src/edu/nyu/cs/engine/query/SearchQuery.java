package edu.nyu.cs.engine.query;

import java.util.List;

/**
 * @author shenli
 * <p>
 * The {@code SearchQuery} interface represents a skeletal implementation of the search query(s) interface, 
 * to minimize the effort required to implement this interface.
 * <p>
 * To implement a specific search query, the programmer needs to extend this abstract class and provides
 * concrete implementation for all abstract methods in this interface so that certain search query(s) could 
 * run basic {@code processQuery}, {@code getQuery} as well as {@code getTokens} operations successfully.
 */
public interface SearchQuery<T> {
    
    /**
     * Returns the raw search query.
     * <p>
     * @return the raw search query
     */
    public String getQuery();
    
    /**
     * Returns an unmodifiable view of the token list. This method allows modules to provide users with 
     * "read-only" access to internal lists. Query operations on the returned list "read through" to the 
     * specified list, and attempts to modify the returned list, whether direct or via its iterator, result 
     * in an {@link java.lang.UnsupportedOperationException}.
     * <p>
     * @return an unmodifiable view of the specified list. If no token have been collected, returns an empty 
     * list
     */
    public List<T> getTokens();

    /**
     * Process the search query into customize format.
     */
    public void processQuery();
    
}

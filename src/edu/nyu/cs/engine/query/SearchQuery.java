package edu.nyu.cs.engine.query;

/**
 * @author shenli
 * <p>
 * The {@code SearchQuery} interface represents a skeletal implementation of the search query(s) interface, 
 * to minimize the effort required to implement this interface.
 * <p>
 * To implement a specific search query, the programmer needs to extend this abstract class and provides
 * concrete implementation for all abstract methods in this interface so that certain search query(s) could 
 * run basic {@code processQuery} operations successfully.
 */
public interface SearchQuery {

    /**
     * Process the search query into customize format.
     */
    public void processQuery();
    
}

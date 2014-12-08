package edu.nyu.cs.engine.rank;

import java.util.List;

import edu.nyu.cs.engine.document.ScoredDocument;
import edu.nyu.cs.engine.query.SearchQuery;

/**
 * @author shenli
 * <p>
 * The {@code SearchRanker} abstract class represents a skeletal implementation of the search ranker interface, 
 * to minimize the effort required to implement this abstract class.
 * <p>
 * To implement a specific ranker type, the programmer needs to extend this abstract class and provides 
 * concrete implementation for all abstract methods in this class so that certain search ranker could run 
 * basic {@code runQuery} operations successfully.
 */
public abstract class SearchRanker {

    /**
     * Returns an unmodifiable view of the scored documents list collected by the specific search rank model. 
     * This method allows modules to provide users with "read-only" access to internal lists. Query operations 
     * on the returned list "read through" to the specified list, and attempts to modify the returned list, 
     * whether direct or via its iterator, result in an {@link java.lang.UnsupportedOperationException}.
     * <p>
     * @param query the search query
     * @param numberOfResults the number of results to be returned
     * @return an unmodifiable view of the specified list. If no scored document have been returned, returns 
     * an empty list
     */
    public abstract List<ScoredDocument> runQuery(SearchQuery query, int numberOfResults);
    
}

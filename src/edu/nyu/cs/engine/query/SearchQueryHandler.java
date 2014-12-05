package edu.nyu.cs.engine.query;

import java.io.IOException;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import edu.nyu.cs.engine.index.SearchIndexer;

/**
 * @author shenli
 * <p>
 * The {@code SearchQueryHandler} based implementation of the {@link com.sun.net.httpserver.HttpHandler} 
 * interface. This implementation represents a HTTP search query handler which is invoked to process HTTP 
 * exchanges. Each exchange takes a HTTP query at path {@code localhost:<port_number>/search} through CGI 
 * arguments and give the searching result which based on the indexes back to users.
 * <p>
 * Note: {@code SearchQueryHandler} objects are immutable; their value could not be changed after they are
 * created. Thus, {@code SearchQueryHandler} objects are thread-safe.
 */
public final class SearchQueryHandler implements HttpHandler {
    private static final Logger LOGGER = Logger.getLogger("edu.nyu.cs.engine.query.SearchQueryHandler");
    
    private final SearchIndexer indexer;
    
    /**
     * Initializes a newly created {@code SearchQueryHandler} object so that it records HTTP search query 
     * handler which is processed based on the given search indexes.
     * <p>
     * @param indexer the search indexer
     */
    public SearchQueryHandler(SearchIndexer indexer) {
        this.indexer = indexer;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // TODO Auto-generated method stub
        
    }

}

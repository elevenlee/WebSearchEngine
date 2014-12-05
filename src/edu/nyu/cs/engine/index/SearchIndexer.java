package edu.nyu.cs.engine.index;

import java.io.IOException;

/**
 * @author shenli
 * <p>
 * The {@code SearchIndexer} abstract class represents a skeletal implementation of the search indexer 
 * interface, to minimize the effort required to implement this abstract class.
 * <p>
 * To implement a specific indexer type, the programmer needs to extend this abstract class and provides
 * concrete implementation for all abstract methods in this class so that certain search indexer could run
 * basic {@code construct} as well as {@code load} operations successfully.
 */
public abstract class SearchIndexer {

    /**
     * Called when the {@link edu.nyu.cs.engine.server.SearchEngineServer} object is in 
     * {@link edu.nyu.cs.engine.server.ServerMode#INDEX} mode. The search indexes are constructed from the
     * provided corpus location which defined in server configuration file.
     * <p>
     * The indexing also includes document processing as:<br/>
     * A. Remove the non-visible web page content. For example, the content in the {@literal <script>} tag;<br/>
     * B. All tokens are stemmed based on the Porter's algorithm. For more details, please see 
     * <a href="http://en.wikipedia.org/wiki/Stemming">http://en.wikipedia.org/wiki/Stemming</a>;<br/>
     * C. Remove the stop-words and dynamically determine whether to drop the processing.
     * <p>
     * The introduced search indexes are resided under index location which described in server configuration
     * file and no other related data stored.
     * <p>
     * @throws IOException if an I/O error occurs
     */
    public abstract void construct() throws IOException;
    
    /**
     * Called exactly once when the {@link edu.nyu.cs.engine.server.SearchEngineServer} object is in
     * {@link edu.nyu.cs.engine.server.ServerMode#SERVER} mode. The search indexer are loaded from the provided
     * index location which defined in server configuration file and to be ready for serving users' HTTP search
     * requests.
     * <p>
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be located
     */
    public abstract void load() throws IOException, ClassNotFoundException;
    
}

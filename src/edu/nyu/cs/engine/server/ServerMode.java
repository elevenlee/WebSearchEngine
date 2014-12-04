package edu.nyu.cs.engine.server;

/**
 * @author shenli
 * <p>
 * The {@code ServerMode} enum represents the mode of server.
 */
enum ServerMode {
    /**
     * Indexing corpus mode.
     */
    INDEX,
    /**
     * Serving HTTP search request mode.
     */
    SERVER;
}

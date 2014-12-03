package edu.nyu.cs.search.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpServer;

/**
 * @author shenli
 * <p>
 * The {@code WebSearchServer} class represents a simple HTTP server that takes a HTTP query at path
 * {@code localhost:25814/search} through CGI arguments and give the same query back to users.
 */
public class WebSearchServer {
    private static final Logger LOGGER = Logger.getLogger("edu.nyu.cs.search.server.WebSearchServer");
    
    private static final int PORT = 25814;

    /**
     * The main entry that launch HTTP server.
     */
    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(PORT);
        HttpServer httpServer = HttpServer.create(address, -1);
        httpServer.createContext("/", new WebSearchEngineServerHandler());
        httpServer.setExecutor(Executors.newCachedThreadPool());
        httpServer.start();
        
        LOGGER.info("Listening on port: " + PORT);
    }

}

package edu.nyu.cs.search.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpServer;

public class WebSearchServer {
    private static final Logger LOGGER = Logger.getLogger("edu.nyu.cs.search.server.WebSearchServer");
    private static final int PORT = 25814;

    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(PORT);
        HttpServer httpServer = HttpServer.create(address, -1);
        httpServer.createContext("/", new WebSearchEngineServerHandler());
        httpServer.setExecutor(Executors.newCachedThreadPool());
        httpServer.start();
        
        LOGGER.info("Listening on port: " + PORT);
    }

}

package edu.nyu.cs.search.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

public class WebSearchServer {
    private static final int PORT = 25814;

    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(PORT);
        HttpServer httpServer = HttpServer.create(address, -1);
        httpServer.createContext("/", new WebSearchEngineServerHandler());
        httpServer.setExecutor(Executors.newCachedThreadPool());
        httpServer.start();
        
        System.out.println("Listening on port: " + PORT);
    }

}

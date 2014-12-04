package edu.nyu.cs.engine.server;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author shenli
 * <p>
 * The {@code SearchEngineServer} class represents a search engine server that provides availability of 
 * indexing corpus as well as serving HTTP search query at path {@code localhost:<port_number>/search} based 
 * on the built indexes and then give the search query result back to users.
 * <p>
 * The search engine server configuration values are resided under {@code conf/engine.conf} file by default. 
 * To start indexing, it needs provide at least 2 parameters {@code --mode=index --options=<config_file_name>} 
 * and the indexer type is defined in the server configuration file. To start serving users' HTTP request, it
 * needs provide at least 3 parameters {@code --mode=serve --port=<port_number> --options=<config_file_name>}}.
 * and additional server options are also provided in the server configuration file.
 */
public class SearchEngineServer {
    private static final Logger LOGGER = Logger.getLogger("edu.nyu.cs.engine.server.SearchEngineServer");
    
    private static ServerMode mode;
    private static ServerOption option;
    private static int port;
    
    /**
     * Returns true if all command line parameters are valid and false otherwise.
     * <p>
     * @param args the command line arguments
     * @return true if all command line parameters are valid and false otherwise
     * @throws IOException If an I/O error occurs
     */
    private static boolean parseCommandLine(String[] args) throws IOException {
        for (String arg : args) {
            String[] keyValues = arg.split("=", 2);
            String key = keyValues[0].trim();
            String value = keyValues[1].trim();
            if ("--mode".equals(key)) {
                mode = ServerMode.valueOf(value.toUpperCase());
            } else if ("--port".equals(key)) {
                port = Integer.parseInt(value);
            } else if ("--options".equals(key)) {
                option = ServerOption.newInstance(value);
            }
        }
        if (mode == null) {
            LOGGER.info("Could not find server mode, must provide a valid mode: index or server.");
            return false;
        }
        if (mode == ServerMode.SERVER && port <= 0) {
            LOGGER.info("Could not find port number, must provide a valid port number.");
            return false;
        }
        if (option == null) {
            LOGGER.info("Could not find server configuration options, must provide options file path.");
            return false;
        }
        return true;
    }

    /**
     * The main entry that launch search engine server.
     * <p>
     * @throws IOException If an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            LOGGER.info("Could not find required parameters.");
            LOGGER.info("To start indexing, parameters at least --mode=index --options=<config_file_name>");
            LOGGER.info("To start serving, parameters at least --mode=serve --port=<port_number> --options=<config_file_name>");
            System.exit(1);
        }
        if (!parseCommandLine(args)) {
            System.exit(1);
        }
        switch (mode) {
            case INDEX: // TODO implement indexing process
            case SERVER: // TODO implement server which takes HTTP search requests
        }
        throw new IllegalArgumentException("No such mode: " + mode);
    }

}

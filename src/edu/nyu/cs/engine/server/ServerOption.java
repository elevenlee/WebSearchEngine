package edu.nyu.cs.engine.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author shenli
 * <p>
 * The {@code ServerOption} class represents configuration of search engine server. The options configuration 
 * are defined under {@code conf/engine.conf} file by default. It describes the corpus location, indexer type 
 * as well as index location which generated during server index mode. Besides, user could provide additional 
 * options in the same server configuration file. Each options must have a key-value map, separated by ":".
 */
public class ServerOption {
    private static final Logger LOGGER = Logger.getLogger("edu.nyu.cs.engine.server.ServerOption");
    
    private final String corpusPrefix;
    private final String indexPrefix;
    private final String indexerType;
    private volatile int hashCode;
    
    /**
     * Initializes a newly created {@code ServerOption} object so that it records search engine server
     * configuration options which at least includes corpus location, index location as well as indexer type.
     * <p>
     * @param corpusPrefix the corpus prefix
     * @param indexPrefix the index prefix
     * @param indexerType the indexer type
     */
    private ServerOption(String corpusPrefix, String indexPrefix, String indexerType) {
        this.corpusPrefix = corpusPrefix;
        this.indexPrefix = indexPrefix;
        this.indexerType = indexerType;
    }

    /**
     * Returns the corpus prefix.
     * <p>
     * @return corpus prefix
     */
    public String getCorpusPrefix() {
        return corpusPrefix;
    }

    /**
     * Returns the index prefix.
     * <p>
     * @return index prefix
     */
    public String getIndexPrefix() {
        return indexPrefix;
    }

    /**
     * Returns the indexer type.
     * <p>
     * @return indexer type
     */
    public String getIndexerType() {
        return indexerType;
    }
    
    /**
     * Creates a new instance of the {@code ServerOption} object so that it records search engine server
     * configuration options which are defined in search engine server configuration file.
     * <p>
     * @param optionsFilePath the server configuration file path
     * @return a newly allocated instance of the {@code ServerOption} object
     * @throws IOException if an I/O error occurs
     */
    static ServerOption newInstance(String optionsFilePath) throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader(optionsFilePath));
        final Map<String, String> options = new HashMap<>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            line  = line.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            String[] keyValues = line.split(":", 2);
            if (keyValues.length < 2) {
                LOGGER.info("Incorrect option key value pattern: " + keyValues);
                continue;
            }
            options.put(keyValues[0].trim(), keyValues[1].trim());
        }
        reader.close();
        
        String corpusPrefix = options.get("corpus_prefix");
        if (corpusPrefix == null) {
            LOGGER.info("corpus_prefix option miss in server configuration file " + optionsFilePath);
        }
        String indexPrefix = options.get("index_prefix");
        if (indexPrefix == null) {
            LOGGER.info("index_prefix option miss in server configuration file " + optionsFilePath);
        }
        String indexerType = options.get("indexer_type");
        if (indexerType == null) {
            LOGGER.info("indexer_type option miss in server configuration file " + optionsFilePath);
        }
        return new ServerOption(corpusPrefix, indexPrefix, indexerType);
    }
    
    /**
     * Compares the specified object with this {@code ServerOption} for equality. Returns true if and only if 
     * the specified object is also a {@code ServerOption} object, both objects have the same corpus prefix,
     * index prefix as well as indexer type.
     * <p>
     * This implementation first checks if the specified object is this {@code ServerOption}. If so, it returns 
     * true; if not, it checks if the specified object is a {@code ServerOption} object. If not, it returns 
     * false; if so, it iterates over both {@code ServerOption} objects, comparing corresponding fields. If 
     * any comparison returns false, this method returns false. Otherwise it returns true when the iterations 
     * complete. 
     * <p>
     * @param o the object to be compared for equality with this {@code ServerOption} object
     * @return true if the specified object is equal to this {@code ServerOption} object
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ServerOption)) {
            return false;
        }
        ServerOption so = (ServerOption) o;
        return corpusPrefix.equals(so.corpusPrefix)
                && indexPrefix.equals(so.indexPrefix)
                && indexerType.equals(so.indexerType);
    }
    
    /**
     * Returns the hash code value for this {@code ServerOption} object.
     * <p>
     * @return the hash code value for this {@code ServerOption} object
     */
    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            final int prime = 31;
            result = 17;
            result = result * prime + corpusPrefix.hashCode();
            result = result * prime + indexPrefix.hashCode();
            result = result * prime + indexerType.hashCode();
            hashCode = result;
        }
        return result;
    }
    
    /**
     * Returns the string representation of this {@code ServerOption} object. The string consists of corpus
     * prefix, index prefix as well as indexer type in order.
     * <p>
     * @return string comprising the corpus prefix, index prefix and indexer type
     */
    @Override
    public String toString() {
        return String.format(
                "corpus_prefix: %s, index_prefix: %s, indexer_type: %s", 
                corpusPrefix, indexPrefix, indexerType);
    }
    
}

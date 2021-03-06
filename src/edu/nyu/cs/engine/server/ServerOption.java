package edu.nyu.cs.engine.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import edu.nyu.cs.engine.exception.IllegalSearchEngineConfigurationException;
import edu.nyu.cs.engine.index.utils.IndexerType;

/**
 * @author shenli
 * <p>
 * The {@code ServerOption} class represents configuration of search engine server. The options configuration 
 * are defined under {@code conf/engine.conf} file by default. It describes the corpus location, indexer type 
 * as well as index location which generated during server index mode. Besides, user could provide additional 
 * options in the same server configuration file. Each options must have a key-value map, separated by ":".
 * <p>
 * {@code ServerOption} could not be created via the constructors in this class. Objects could be obtained 
 * using the {@link edu.nyu.cs.engine.server.ServerOption#newInstance(java.lang.String)} method in this class.
 */
public class ServerOption {
    private static final Logger LOGGER = Logger.getLogger("edu.nyu.cs.engine.server.ServerOption");
    
    private final String corpusPath;
    private final String indexPath;
    private final IndexerType indexerType;
    private volatile int hashCode;
    
    /**
     * Initializes a newly created {@code ServerOption} object so that it records search engine server
     * configuration options which at least includes corpus location, index location as well as indexer type.
     * <p>
     * @param corpusPath the corpus path
     * @param indexPath the index path
     * @param indexerType the indexer type
     */
    private ServerOption(String corpusPath, String indexPath, IndexerType indexerType) {
        this.corpusPath = corpusPath;
        this.indexPath = indexPath;
        this.indexerType = indexerType;
    }

    /**
     * Returns the corpus path.
     * <p>
     * @return corpus path
     */
    public String getCorpusPath() {
        return corpusPath;
    }

    /**
     * Returns the index path.
     * <p>
     * @return index path
     */
    public String getIndexPath() {
        return indexPath;
    }

    /**
     * Returns the indexer type.
     * <p>
     * @return indexer type
     */
    public IndexerType getIndexerType() {
        return indexerType;
    }
    
    /**
     * Creates a new instance of the {@code ServerOption} object so that it records search engine server
     * configuration options which are defined in search engine server configuration file.
     * <p>
     * @param optionsFilePath the server configuration file path
     * @return a newly allocated instance of the {@code ServerOption} object
     * @throws IOException if an I/O error occurs
     * @throws IllegalSearchEngineConfigurationException if server configuration file miss necessary options
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
                LOGGER.info("Incorrect option key value pattern: " + line);
                continue;
            }
            options.put(keyValues[0].trim(), keyValues[1].trim());
        }
        reader.close();
        
        String corpusPath = options.get("corpus_path");
        if (corpusPath == null) {
            throw new IllegalSearchEngineConfigurationException(
                    "corpus_path option miss in server configuration file " + optionsFilePath);
        }
        String indexPath = options.get("index_path");
        if (indexPath == null) {
            throw new IllegalSearchEngineConfigurationException(
                    "index_path option miss in server configuration file " + optionsFilePath);
        }
        String indexerType = options.get("indexer_type");
        if (indexerType == null) {
            throw new IllegalSearchEngineConfigurationException(
                    "indexer_type option miss in server configuration file " + optionsFilePath);
        }
        return new ServerOption(
                corpusPath, indexPath, IndexerType.valueOf(indexerType.toUpperCase()));
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
        return corpusPath.equals(so.corpusPath)
                && indexPath.equals(so.indexPath)
                && indexerType == so.indexerType;
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
            result = result * prime + corpusPath.hashCode();
            result = result * prime + indexPath.hashCode();
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
                "ServerOption={corpus_path: %s, index_path: %s, indexer_type: %s}", 
                corpusPath, indexPath, indexerType.name().toLowerCase());
    }
    
}

package edu.nyu.cs.engine.exception;

/**
 * @author shenli
 * <p>
 * Thrown to indicate a serious search engine server configuration error.
 */
public class IllegalSearchEngineConfigurationException extends RuntimeException {
    private static final long serialVersionUID = -630318041637349970L;

    /**
     * Create a new {@code IllegalSearchEngineConfigurationException} with the {@code String} specified as an 
     * error message.
     * <p>
     * @param message the error message for the exception
     */
    public IllegalSearchEngineConfigurationException(String message) {
        super(message);
    }
}

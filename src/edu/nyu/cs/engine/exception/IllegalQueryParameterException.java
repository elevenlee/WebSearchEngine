package edu.nyu.cs.engine.exception;

/**
 * @author shenli
 * <p>
 * Thrown to indicate an illegal arguments in HTTP search requests.
 */
public class IllegalQueryParameterException extends RuntimeException {
    private static final long serialVersionUID = -8413783163384962429L;

    /**
     * Create a new {@code IllegalQueryParameterException} with the query parameter {@code key}, {@code value} 
     * as well as illegal {@code reason} specified as an error message.
     * <p>
     * @param key the illegal argument key
     * @param value the illegal argument value
     * @param reason the invalid parameter reason
     */
    public IllegalQueryParameterException(String key, String value, String reason) {
        super("Invalid HTTP search request [" + key + "=" + value + "]: " + reason);
    }
    
}

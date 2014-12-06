package edu.nyu.cs.engine.query;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Logger;

import edu.nyu.cs.engine.rank.RankerType;

/**
 * @author shenli
 * <p>
 * Encapsulates arguments for a HTTP search query request through the URL. The parameters are the raw search 
 * query, the ranker algorithm type, the format of search results and the number of search results to be
 * returned.
 * <p>
 * {@code QueryParameter} could not be created via the constructors in this class. Objects could be obtained 
 * using the {@link edu.nyu.cs.engine.query.QueryParameter#newInstance(java.lang.String)} method in this class.
 */
final class QueryParameter {
    private static final Logger LOGGER = Logger.getLogger("edu.nyu.cs.engine.query.QueryParameter");
    
    private static final String ENCODE = "UTF-8";

    private final String query;
    private final RankerType rankerType;
    private final Format format;
    private final int numberOfResults;
    private volatile int hashCode;
    
    /**
     * @author shenli
     * <p>
     * The {@code Format} enum represents the format of search results.
     */
    enum Format {
        /**
         * The HTML format.
         */
        HTML,
        
        /**
         * The plain text format.
         */
        TEXT;
    }
    
    /**
     * Initializes a newly created {@code QueryParameter} object so that it records CGI arguments of a single 
     * HTTP search query request via URL.
     * <p>
     * @param query the raw search query
     * @param rankerType the ranker algorithm type 
     * @param format the format of search results
     * @param numberOfResults the number of search results to be returned
     */
    private QueryParameter(String query, RankerType rankerType, Format format, int numberOfResults) {
        this.query = query;
        this.rankerType = rankerType;
        this.format = format;
        this.numberOfResults = numberOfResults;
    }
    
    /**
     * Returns the raw search query.
     * <p>
     * @return raw search query
     */
    String getQuery() {
        return query;
    }
    
    /**
     * Returns the rank algorithm type.
     * <p>
     * @return rank algorithm type
     */
    RankerType getRankerType() {
        return rankerType;
    }
    
    /**
     * Returns the search results format.
     * <p>
     * @return search results format
     */
    Format getFormat() {
        return format;
    }
    
    /**
     * Returns the number of search results.
     * <p>
     * @return number of search results
     */
    int getNumberOfResults() {
        return numberOfResults;
    }

    /**
     * Creates a new instance of the {@code QueryParameter} object so that it records CGI arguments of a 
     * single HTTP search query request via URL. If any required parameters not in HTTP requests, this method 
     * would provide missing parameters to the default values.
     * <p>
     * @param uri the uri contains HTTP request arguments
     * @return a newly allocated instance of the {@code QueryParameter} object
     * @throws UnsupportedEncodingException if the named encoding is not supported
     * @throws IllegalArugmentException if number of results less than or equals to 0, or greater than maximum
     * value of {@link java.lang.Integer}
     */
    static QueryParameter newInstance(String uri) throws UnsupportedEncodingException {
        final String[] parameters = uri.split("&");
        // Default values
        String query = "";
        RankerType rankerType = RankerType.FULLSCAN;
        Format format = Format.HTML;
        int numberOfResults = 10;
        for (String param : parameters) {
            final String[] keyValue = param.split("=", 2);
            if (keyValue.length < 2) {
                LOGGER.info("Incorrect parameter key value pattern: " + param);
                continue;
            }
            final String key = URLDecoder.decode(keyValue[0], ENCODE).trim();
            final String value = URLDecoder.decode(keyValue[1], ENCODE).trim();
            if ("query".equals(key)) {
                query = value;
            } else if ("ranker".equals(key)) {
                rankerType = RankerType.valueOf(value.toUpperCase());
            } else if ("format".equals(key)) {
                format = Format.valueOf(value.toUpperCase());
            } else if ("numResults".equals(key)) {
                numberOfResults = Integer.parseInt(value);
            }
        }
        if (numberOfResults <= 0 || numberOfResults > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Invalid results to return number: " + numberOfResults);
        }
        return new QueryParameter(query, rankerType, format, numberOfResults);
    }
    
    /**
     * Compares the specified object with this {@code QueryParameter} for equality. Returns true if and only 
     * if the specified object is also a {@code QueryParameter} object, both objects have the same raw search 
     * query, ranker algorithm type, search results format as well as number of results to be returned.
     * <p>
     * This implementation first checks if the specified object is this {@code QueryParameter}. If so, it 
     * returns true; if not, it checks if the specified object is a {@code QueryParameter} object. If not, it 
     * returns false; if so, it iterates over both {@code QueryParameter} objects, comparing corresponding 
     * fields. If any comparison returns false, this method returns false. Otherwise it returns true when the 
     * iterations complete. 
     * <p>
     * @param o the object to be compared for equality with this {@code QueryParameter} object
     * @return true if the specified object is equal to this {@code QueryParameter} object
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof QueryParameter)) {
            return false;
        }
        QueryParameter qp = (QueryParameter) o;
        return query.equals(qp.query)
                && rankerType == qp.rankerType
                && format == qp.format
                && numberOfResults == qp.numberOfResults;
    }
    
    /**
     * Returns the hash code value for this {@code QueryParameter} object.
     * <p>
     * @return the hash code value for this {@code QueryParameter} object
     */
    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            final int prime = 31;
            result = 17;
            result = result * prime + query.hashCode();
            result = result * prime + rankerType.hashCode();
            result = result * prime + format.hashCode();
            result = result * prime + numberOfResults;
            hashCode = result;
        }
        return result;
    }
    
    /**
     * Returns the string representation of this {@code QueryParameter} object. The string consists of raw 
     * search query, ranker algorithm type, search results format as well as the number of results to be
     * returned.
     * <p>
     * @return string comprising the arguments for a HTTP search query request through the URL
     */
    @Override
    public String toString() {
        return String.format(
                "QueryParameter={query: %s, rankerType: %s, format: %s, numberOfResults: %s}", 
                query, rankerType.name().toLowerCase(), format.name().toLowerCase(), numberOfResults);
    }
    
}

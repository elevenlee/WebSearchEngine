package edu.nyu.cs.search.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * @author shenli
 * <p>
 * The {@code WebSearchEngineServerHandler} based implementation of the {@link com.sun.net.httpserver.HttpHandler}
 * interface. This implementation represents a simple handler which is invoked to process HTTP exchanges. Each
 * exchange takes a HTTP query at path {@code localhost:25814/search} through CGI arguments and give the same query
 * back to users.
 */
public class WebSearchEngineServerHandler implements HttpHandler {
    private static final Logger LOGGER = Logger.getLogger("edu.nyu.cs.search.server.WebSearchEngineServerHandler");
    
    private static final String GET = "GET";
    private static final String QUERY = "query";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ENCODE = "UTF-8";

    /**
     * Handle the given request and generate an appropriate response which exactly same as request. 
     * See {@link com.sun.net.httpserver.HttpExchange} for a description of the steps involved in handling 
     * an exchange.
     * <p>
     * @param exchange he exchange containing the request from the client and used to send the response
     * @throws NullPointerException if exchange is {@code null}
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!GET.equalsIgnoreCase(exchange.getRequestMethod())) {
            return;
        }
        Headers requestHeaders = exchange.getRequestHeaders();
        LOGGER.info("Incoming request: ");
        for (String key : requestHeaders.keySet()) {
            LOGGER.info(key + ":" + requestHeaders.get(key) + "; ");
        }
        
        URI uri = exchange.getRequestURI();
        String parameter = getParameter(QUERY, uri.getRawQuery());
        LOGGER.info("URI: " + exchange.getRemoteAddress() + uri);
        LOGGER.info("Query Parameter: [" + QUERY + "=" + parameter + "]");
        
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set(CONTENT_TYPE, "text/plain");
        exchange.sendResponseHeaders(200, 0);
        
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(parameter.getBytes());
        responseBody.close();
    }

    /**
     * Returns the string representation of the request key value in the CGI query.
     * <p>
     * @param key the key name of query
     * @param query the CGI query content
     * @return the string representation of the request key value in the CGI query
     */
    private String getParameter(String key, String query) {
        Object value = getQueryMaps(query).get(key);
        if (value == null) {
            return null;
        } else if (value instanceof List<?>) {
            @SuppressWarnings("unchecked")
            Object firstValue = ((List<Object>) value).get(0);
            return firstValue.toString();
        }
        return value.toString();
    }
    
    /**
     * Returns the map containing all keys whose associated correspondent request value in CGI query.
     * <p>
     * @param query the CGI query content
     * @return the map containing all keys whose associated correspondent request value in CGI query
     */
    private Map<String, Object> getQueryMaps(String query) {
        if (query == null) {
            return Collections.emptyMap();
        }
        Map<String, Object> parameters = new HashMap<>();
        String pairs[] = query.split("&");
        String key = null;
        String value = null;
        for (String pair : pairs) {
            String param[] = pair.split("=");
            if (param.length > 0) {
                try {
                    key = URLDecoder.decode(param[0], ENCODE);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("URLDecoder error", e);
                }
            }
            if (param.length > 1) {
                try {
                    value = URLDecoder.decode(param[1], ENCODE);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("URLDecoder error", e);
                }
            }
            Object o = parameters.get(key);
            if (o == null) {
                parameters.put(key, value);
            } else if (o instanceof List<?>) {
                @SuppressWarnings("unchecked")
                List<Object> values = (List<Object>) o;
                values.add(values);
            } else if (o instanceof String) {
                List<Object> values = new ArrayList<>();
                values.add(o);
                values.add(value);
                parameters.put(key, values);
            }
        }
        return Collections.unmodifiableMap(parameters);
    }
}

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

public class WebSearchEngineServerHandler implements HttpHandler {
    private static final Logger LOGGER = Logger.getLogger("edu.nyu.cs.search.server.WebSearchEngineServerHandler");
    
    private static final String GET = "GET";
    private static final String QUERY = "query";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ENCODE = "UTF-8";

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

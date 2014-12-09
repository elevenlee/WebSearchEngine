package edu.nyu.cs.engine.query;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import edu.nyu.cs.engine.document.ScoredDocument;
import edu.nyu.cs.engine.exception.IllegalQueryParameterException;
import edu.nyu.cs.engine.index.SearchIndexer;
import edu.nyu.cs.engine.query.impl.WordQuery;
import edu.nyu.cs.engine.rank.SearchRanker;
import edu.nyu.cs.engine.rank.utils.SearchRankerFactory;

/**
 * @author shenli
 * <p>
 * The {@code SearchQueryHandler} based implementation of the {@link com.sun.net.httpserver.HttpHandler} 
 * interface. This implementation represents a HTTP search query handler which is invoked to process HTTP 
 * exchanges. Each exchange takes a HTTP query at path {@code localhost:<port_number>/search} through CGI 
 * arguments and give the searching result which based on the indexes back to users.
 * <p>
 * Note: {@code SearchQueryHandler} objects are immutable; their value could not be changed after they are
 * created. Thus, {@code SearchQueryHandler} objects are thread-safe.
 */
public final class SearchQueryHandler implements HttpHandler {
    private static final Logger LOGGER = Logger.getLogger("edu.nyu.cs.engine.query.SearchQueryHandler");
    
    private final SearchIndexer indexer;
    
    /**
     * Initializes a newly created {@code SearchQueryHandler} object so that it records HTTP search query 
     * handler which is processed based on the given search indexes.
     * <p>
     * @param indexer the search indexer
     */
    public SearchQueryHandler(SearchIndexer indexer) {
        this.indexer = indexer;
    }

    /**
     * Handle the given request and generate an appropriate response which generated based on the search 
     * indexes. See {@link com.sun.net.httpserver.HttpExchange} for a description of the steps involved in 
     * handling an exchange.
     * <p>
     * @param exchange the exchange containing the request from the client and used to send the response
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        if (!"GET".equalsIgnoreCase(requestMethod)) {
            LOGGER.info(" Ignore HTTP request " + requestMethod);
            return;
        }
        Headers requestHeaders = exchange.getRequestHeaders();
        LOGGER.info("Incoming request: ");
        for (String key : requestHeaders.keySet()) {
            LOGGER.info(key + ":" + requestHeaders.get(key) + "; ");
        }
        
        // Construct HTTP response headers
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/html");
        exchange.sendResponseHeaders(200, 0);
        
        LOGGER.info("Handle URI: " + exchange.getRemoteAddress() + exchange.getRequestURI());
        String uriPath = exchange.getRequestURI().getPath();
        if (uriPath == null) {
            response(exchange, "Could not find URI path");
            return;
        } else if (!uriPath.equals("/search")) {
            response(exchange, "Could not handle URI " + uriPath);
            return;
        }
        String uriQuery = exchange.getRequestURI().getQuery();
        if (uriQuery == null) {
            response(exchange, "Could not find URI query arguments");
            return;
        }
        
        LOGGER.info("Handle URI query: " + uriQuery);
        QueryParameter queryParameter = null;
        try {
            queryParameter = QueryParameter.newInstance(uriQuery);
        } catch (IllegalQueryParameterException e) {
            response(exchange, e.getMessage());
            return;
        } catch (UnsupportedEncodingException e) {
            response(exchange, e.getMessage());
            return;
        }
        
        SearchRanker searchRanker = 
                SearchRankerFactory.getSearchRanker(queryParameter.getRankerType(), indexer);
        SearchQuery query = new WordQuery(queryParameter.getQuery());
        
        List<ScoredDocument> scoredDocuments = 
                searchRanker.runQuery(query, queryParameter.getNumberOfResults());
        switch (queryParameter.getFormat()) {
            case HTML: response(exchange, getSearchResultsInHTMLFormat(scoredDocuments)); break;
            case TEXT: response(exchange, getSearchResultsInTextFormat(scoredDocuments)); break;
        }
        LOGGER.info("Complete search query: " + queryParameter.getQuery());
    }
    
    /**
     * Generates HTTP response message.
     * <p>
     * @param exchange the exchange used to send response
     * @param message the response message
     * @throws IOException if an I/O error occurs
     */
    private static void response(HttpExchange exchange, String message) throws IOException {
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(message.getBytes());
        responseBody.close();
    }
    
    /**
     * Returns the string representation of each scored documentation in collection {@code scoredDocuments} 
     * with HTML syntax. The string consists of the document id, title as well as score of each document and 
     * also includes necessary HTML components.
     * <p>
     * @param scoredDocuments the collection of scored documents for which to be returned
     * @return string comprising each scored documentation with HTML components
     */
    private static String getSearchResultsInHTMLFormat(Collection<ScoredDocument> scoredDocuments) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>")
          .append(" <html>")
          .append("     <head>")
          .append("         <title>Search Result</title>")
          .append("     </head>")
          .append("     <body>")
          .append("         <table border='1' cellpadding='6' cellspacing='1'>")
          .append("             <tr>")
          .append("                 <th style=\"background-color:lightgreen\">Document Id</th>")
          .append("                 <th style=\"background-color:lightgreen\">Title</th>")
          .append("                 <th style=\"background-color:lightgreen\">Score</th>")
          .append("             </tr>");
        for (ScoredDocument sd : scoredDocuments) {
            sb.append("         <tr>")
              .append("             <td>" + sd.getDocument().getId() + "</td>")
              .append("             <td>" + sd.getDocument().getTitle() + "</td>")
              .append("             <td>" + sd.getScore() + "</td>")
              .append("         </tr>");
        }
        sb.append("         </table>")
          .append("     </body>")
          .append(" </html>");
        return sb.toString();
    }
    
    /**
     * Returns the string representation of each scored documentation in collection {@code scoredDocuments} 
     * with plain text syntax. The string consists of the document id, title as well as score of each document 
     * that each field is separated by a tab.
     * <p>
     * @param scoredDocuments the collection of scored documents for which to be returned
     * @return string comprising each scored documentation
     */
    private static String getSearchResultsInTextFormat(Collection<ScoredDocument> scoredDocuments) {
        StringBuilder sb = new StringBuilder();
        for (ScoredDocument sd : scoredDocuments) {
            sb.append(sd.getDocument().getId())
              .append('\t')
              .append(sd.getDocument().getTitle())
              .append('\t')
              .append(sd.getScore())
              .append('\n');
        }
        return sb.toString();
    }

}

package edu.nyu.cs.engine.rank.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.nyu.cs.engine.document.FullscanDocument;
import edu.nyu.cs.engine.document.ScoredDocument;
import edu.nyu.cs.engine.index.SearchIndexer;
import edu.nyu.cs.engine.query.SearchQuery;
import edu.nyu.cs.engine.query.impl.WordQuery;
import edu.nyu.cs.engine.rank.SearchRanker;

/**
 * @author shenli
 * <p>
 * The {@code FullscanRanker} based implementation of the {@link edu.nyu.cs.engine.rank.SearchRanker} interface. 
 * This implementation represents a simple fullscan search rank model. It only counts the all documents' title 
 * against each token in the search query and score the document as 1.0 if any search token exist in the document 
 * title.
 * <p>
 * {@code FullscanRanker} are constant; their value could not be changed after they are created. Because 
 * {@code FullscanRanker} objects are immutable they could be shared.
 */
public class FullscanRanker extends SearchRanker {

    /**
     * Initializes a newly created {@code FullscanRanker} object with given {@link edu.nyu.cs.engine.index.SearchIndexer} 
     * object so that it records simple fullscan search rank model.
     * <p>
     * @param searchIndexer the search indexer
     */
    public FullscanRanker(SearchIndexer searchIndexer) {
        super(searchIndexer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ScoredDocument> runQuery(SearchQuery query, int numberOfResults) {
        query.processQuery();
        List<ScoredDocument> scoredDocuments = new ArrayList<>();
        for (int i = 0; i < searchIndexer.getNumberOfDocs(); i++) {
            scoredDocuments.add(score(query, i));
        }
        Collections.sort(scoredDocuments, Collections.reverseOrder());
        List<ScoredDocument> results = new ArrayList<>();
        for (int i = 0; i < scoredDocuments.size() && i < numberOfResults; i++) {
            results.add(scoredDocuments.get(i));
        }
        return Collections.unmodifiableList(results);
    }
    
    /**
     * Return the scored document of given {@code docId} and score it based on the simple fullscan rank model. 
     * It only counts document's title against tokens in the give {@code query} and score it as 1.0 if any 
     * token exist in the document title and 0.0 otherwise.
     * <p>
     * @param query the search query
     * @param docId the document id
     * @return the scored document of give {@code docId} and score it based on the simple fullscan rank model
     */
    private ScoredDocument score(SearchQuery query, int docId) {
        FullscanDocument document = (FullscanDocument) searchIndexer.getDocument(docId);
        double score = 0.0;
        for (String docToken : document.getInvertedTitleTokens()) {
            for (String queryToken : ((WordQuery) query).getTokens()) {
                if (docToken.equals(queryToken)) {
                    score = 1.0;
                    break;
                }
            }
            if (Double.compare(score, 0.0) > 0) {
                break;
            }
        }
        return new ScoredDocument(document, score);
    }

}

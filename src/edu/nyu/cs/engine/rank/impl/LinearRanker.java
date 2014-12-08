package edu.nyu.cs.engine.rank.impl;

import java.util.List;

import edu.nyu.cs.engine.document.ScoredDocument;
import edu.nyu.cs.engine.index.SearchIndexer;
import edu.nyu.cs.engine.query.SearchQuery;
import edu.nyu.cs.engine.rank.SearchRanker;

/**
 * @author shenli
 * <p>
 */
public class LinearRanker extends SearchRanker {

    public LinearRanker(SearchIndexer searchIndexer) {
        super(searchIndexer);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<ScoredDocument> runQuery(SearchQuery query, int numberOfResults) {
        // TODO Auto-generated method stub
        return null;
    }

}

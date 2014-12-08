package edu.nyu.cs.engine.index.impl;

import java.io.IOException;

import edu.nyu.cs.engine.document.BasicDocument;
import edu.nyu.cs.engine.index.SearchIndexer;
import edu.nyu.cs.engine.query.SearchQuery;
import edu.nyu.cs.engine.server.ServerOption;

/**
 * @author shenli
 *
 */
public class InvertedCompressedIndexer extends SearchIndexer {

    public InvertedCompressedIndexer(ServerOption serverOption) {
        super(serverOption);
        // TODO Auto-generated constructor stub
    }

    @Override
    public BasicDocument getDocument(int docId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BasicDocument nextDocument(SearchQuery query, int docId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void construct() throws IOException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void load() throws IOException, ClassNotFoundException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getTermByIndex(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getIndexByTerm(String term) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getTermFrequency(String term) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getDocumentFrequencyByTerm(String term) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getTermFrequencyByDocument(String term, String url) {
        // TODO Auto-generated method stub
        return 0;
    }

}

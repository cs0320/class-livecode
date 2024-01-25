package edu.brown.cs32.livecode.live.caching;

import java.util.List;

public class SearcherProxy implements Searcher {
    private final Searcher wrappedSearcher;

    SearcherProxy(Searcher realSearcher) {
        this.wrappedSearcher = realSearcher;
    }

    @Override
    public List<String> searchLines(String target) {
        return this.wrappedSearcher.searchLines(target);
    }
}

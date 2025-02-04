package edu.brown.cs32.livecode.search;

import java.util.HashMap;
import java.util.Map;

/**
 * A revision of the Searcher class that caches results using the proxy pattern.
 */
public class CachingSearcher implements Searchable {

    /** This is the object that the CachingSearcher proxies. */
    private final Searchable wrapped;
    /** The cache state. */
    private final Map<String, Boolean> cache = new HashMap<>();

    /**
     * Creates a Searchable object that wraps another, adding a local cache of
     * prior results.
     * @param toWrap the Searchable object to add a cache for.
     */
    CachingSearcher(Searchable toWrap) {
        this.wrapped = toWrap;
    }

    /**
     * Search within the datasource for the content. If we have results for this
     * content already, return the cached result immediately. Otherwise, query the
     * wrapped searcher object and save the results.
     *
     * EXERCISE: this is a very simple cache. What scenarios might it handle poorly?
     *
     * @param content The target value to search for
     * @return true if and only if the content is present in the data
     */
    @Override
    public boolean search(String content) {
        if(cache.containsKey(content)) {
            return cache.get(content);
        }
        boolean result = this.wrapped.search(content);
        cache.put(content, result);
        return result;
    }
}

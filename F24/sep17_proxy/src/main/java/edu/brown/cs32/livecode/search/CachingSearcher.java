package edu.brown.cs32.livecode.search;

import java.util.HashMap;
import java.util.Map;

public class CachingSearcher implements Searchable {

    private final Searchable wrapped;
    private final Map<String, Boolean> cache = new HashMap<>();
    CachingSearcher(Searchable toWrap) {
        this.wrapped = toWrap;
    }

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

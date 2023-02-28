package edu.brown.cs32.livecode.caching;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CachedFileSearcher implements Searcher {
    private final Searcher wrappedSearcher;
    private final LoadingCache<String, List<String>> cache;
    public CachedFileSearcher(Searcher toWrap) {
        this.wrappedSearcher = toWrap;

        // Look at the docs -- there are lots of builder parameters you can use
        //   including ones that affect garbage-collection (not needed for Server).
        this.cache = CacheBuilder.newBuilder()
                // How many entries maximum in the cache?
                .maximumSize(10)
                // How long should entries remain in the cache?
                .expireAfterWrite(1, TimeUnit.MINUTES)
                // Keep statistical info around for profiling purposes
                .recordStats()
                .build(
                        new CacheLoader<String, List<String>>() {
                            @Override
                            public List<String> load(String key)  {
                                // If this isn't yet present in the cache, load it:
                                return wrappedSearcher.searchLines(key);
                            }
                        });
    }

    @Override
    public List<String> searchLines(String target) {
        // "get" is designed for concurrent situations
        // For today, use getUnchecked:
        List<String> result = cache.getUnchecked(target);
        System.out.println(cache.stats());
        return result;
    }

    /*
    public List<String> searchLines(String target) {
        // Pass through: call the wrapped object
        return this.wrappedSearcher.searchLines(target);
    }
     */
}

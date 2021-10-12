package edu.brown.cs32.fall21.Oct05;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Oct05Caching {

    public static void main(String[] args) {
        Pair2.demo();

        demoCache();
    }

    private static void demoCache() {
        // Strategy pattern! YOU provide to the cache library a function
        //   that does the work of producing the value for a new key.
        //  This is an "anonymous class" (which extends CacheLoader
        //   and overrides the load(k) method.
        CacheLoader<Integer, Pair2<Integer, String>> loader =
                new CacheLoader<Integer, Pair2<Integer, String>>() {
                    @Override
                    public Pair2<Integer, String> load(Integer k) throws Exception {
                        // This will be called for a key that isn't in the cache
                        // so do a (possibly expensive) task here, like loading
                        // from a DB or making an API call
                        return new Pair2<>(k, k.toString());
                    }

                };
        // create the cache. note:
        //   - using a factory pattern here: not calling constructor directly
        //   - providing the loader dependency to the factory
        //  There are lots of kinds of caches, and lots of configurable behavior!
        LoadingCache<Integer, Pair2<Integer, String>> cache =
                CacheBuilder.newBuilder().build(loader);

        // Why have a Builder class? To support chained configuration. Let's
        //  overwrite the cache we just created with a limited-size one:
        cache = CacheBuilder.newBuilder().maximumSize(100).build(loader);
        // Better, let's evict elements that aren't accessed after a second
        cache = CacheBuilder.newBuilder().maximumSize(100)
                .expireAfterAccess(1, TimeUnit.SECONDS).build(loader);

        // We can now rely on the loader to compute the correct value:
        System.out.println(cache.size());
        System.out.println(cache.getIfPresent(100)); // part of Cache
        System.out.println(cache.size());
        System.out.println(cache.getUnchecked(100)); // part of LoadingCache
        System.out.println(cache.size());
        // But we could also just insert both key and value directly:
        cache.put(200, new Pair2<>(200, "200")); // part of Cache
        System.out.println(cache.size());

        // See the documentation for (many) more configuration options.
        // If you want your cache to *NOT* be counted for garbage-collection,
        //    check out weakKeys() etc.

    }
}

/**
 * Wrapper for a pair of values. The values do not need to be the same type.
 * Any two pairs having the same value types should be comparable, first by
 * the first ("left") element, and then by the second ("right") element in case
 * of equal left elements.
 * <p>
 * We enforce the comparable requirement by implementing the Comparable interface,
 * which is itself generic. The declaration says that a Pair<A,B> must provide a method:
 * compareTo, which accepts one argument with type Pair<A, B>.
 *
 * @param <A> Type of the first ("left") value
 * @param <B> Type of the second ("right") value
 */
class Pair2<A extends Comparable<A>,
        B extends Comparable<B>>
        implements Comparable<Pair2<A, B>> {
    private final A left;
    private final B right;

    public Pair2(A a, B b) {
        left = a;
        right = b;
    }
    public A getLeft() {
        return left;
    }

    public B getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "(" + left + " . " + right + ")";
    }

    @Override
    public int compareTo(Pair2<A, B> other) {
        int leftCompare = this.left.compareTo(other.left);
        if (leftCompare != 0) return leftCompare;
        int rightCompare = this.right.compareTo(other.right);
        return rightCompare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair2)) return false; // we'll come back to this later
        Pair2<?, ?> otherPair = (Pair2<?, ?>) o; // don't care about the exact types
        return Objects.equals(left, otherPair.left) &&
                Objects.equals(right, otherPair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }


    /**
     * This method exists to make sure we can actually create and use pairs.
     * Normally, such a class wouldn't have a demo() method, but we want to
     * more easily run the in-class exercise.
     */
    public static void demo() {
        List<Pair<Integer, String>> lst =
                Arrays.asList(
                        new Pair<>(4, "potato"),
                        new Pair<>(2, "apple"),
                        new Pair<>(7, "pizza"));

        Collections.sort(lst);
        System.out.println(lst);
        // Requirement: get this to run.
    }
}


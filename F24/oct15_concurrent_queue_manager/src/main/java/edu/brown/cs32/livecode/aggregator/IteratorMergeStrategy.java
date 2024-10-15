package edu.brown.cs32.livecode.aggregator;

import java.util.Iterator;

/**
 * A strategy for merging iterators. It doesn't matter what they iterate over,
 * but the type system is likely to need to connect what nextIterator returns to
 * some context, hence we parameterize.
 */
public interface IteratorMergeStrategy<T> {
    Iterator<T> nextIterator();
}

package edu.brown.cs32.livecode.aggregator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomMergeStrategy<T> implements IteratorMergeStrategy<T> {
    private final List<Iterator<T>> iterators;

    public RandomMergeStrategy(Collection<Iterator<T>> iterators) {
        this.iterators = new ArrayList<>(iterators);
    }

    @Override
    public Iterator<T> nextIterator() {
        // We must be cautious. If we randomly select an iterator that's empty, others may be ignored.
        // So we cannot do this:
        // int idx = ThreadLocalRandom.current().nextInt(this.iterators.size());
        // Instead, filter on the non-empty iterators and select from _those_:
        List<Iterator<T>> nonEmpty = iterators.stream().filter(it -> it.hasNext()).toList();
        int idx = ThreadLocalRandom.current().nextInt(nonEmpty.size());
        return nonEmpty.get(idx);
    }
}

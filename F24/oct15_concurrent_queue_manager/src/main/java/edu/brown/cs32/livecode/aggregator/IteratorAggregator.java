package edu.brown.cs32.livecode.aggregator;

import java.util.*;

/**
 * Example of how to write an aggregator that combines multiple queues.
 * This is related to the proxy pattern; we call it an aggregator
 * because it turns 2 objects that implement an interface into one.
 * @param <T> the type of the object to produce
 */
public class IteratorAggregator<T> implements Iterator<T> {
    private final List<Iterator<T>> queues = new ArrayList<>();
    private final IteratorMergeStrategy<T> strat;

    /**
     * Create an iterator that combines a number of other iterators. Notice that we make a defensive
     * copy of the collection, but keep the identity of the iterators themselves and the strategy
     * we're given. This means the strategy can keep its own state and refer to iterator objects
     * by reference.
     *
     * Another design note: we could probably have worked with a Comparator here. But then it
     * would be possible to provide some inconsistent notion of comparison, and leave it to
     * _us_ to decide how to address that. Instead, we ask the caller to provide us a consistent
     * notion of "next iterator". And if the next iterator they provide us is empty, we won't
     * look at any others.
     *
     * @param startQueues the collection of iterators to draw from
     */
    public IteratorAggregator(Collection<Iterator<T>> startQueues, IteratorMergeStrategy<T> strat) {
        if(startQueues == null) throw new IllegalArgumentException("IteratorAggregator given null collection.");
        queues.addAll(startQueues);
        this.strat = strat;
    }

    @Override
    public boolean hasNext() {
        // True IFF _any_ of the given iterators has a next element at this point
        // We could do this with a for loop, but here's an example of more modern Java:
        return queues.stream().anyMatch(it -> it.hasNext());
        // We could even do this, which is what the suggestion in IntelliJ is:
        // return queues.stream().anyMatch(Iterator::hasNext);
    }

    @Override
    public T next() {
        // What's the right strategy for merging iterators? The way this is written, _we_ don't
        // need to decide. Our caller is in a better position to say which approach is best.
        // (In practice, we'd provide more than 1 basic strategy to pick from, just so most
        // wouldn't need to write their own.)
        Iterator<T> nextIterator = this.strat.nextIterator();
        if(nextIterator.hasNext()) return nextIterator.next();

        // We only reach this point if we have nothing to return.
        // Note: IntelliJ's method-generation defaulted to "return false"; that would BREAK THE CONTRACT!
        // Instead, throw a communicative exception.
        throw new NoSuchElementException();
    }
}
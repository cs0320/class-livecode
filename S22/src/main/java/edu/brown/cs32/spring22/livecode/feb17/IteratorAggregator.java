package edu.brown.cs32.spring22.livecode.feb17;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class IteratorAggregator<T> implements Iterator<T> {
    List<Iterator<T>> queues = new ArrayList<>();

    IteratorAggregator(List<Iterator<T>> startQueues) {
        queues.addAll(startQueues);
    }

    @Override
    public boolean hasNext() {
        // "Lambda can be replaced with method reference):
        // return queues.stream().anyMatch(Iterator::hasNext);
        return queues.stream().anyMatch(it -> it.hasNext());
    }

    @Override
    public T next() {
        // We have a design decision to make...
        return null;
    }
}
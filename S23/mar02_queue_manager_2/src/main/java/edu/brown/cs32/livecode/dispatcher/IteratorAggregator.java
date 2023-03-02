package edu.brown.cs32.livecode.dispatcher;

import java.util.*;

public class IteratorAggregator<T> implements Iterator<T> {
    List<Iterator<T>> queues = new ArrayList<>();

    IteratorAggregator(Collection<Iterator<T>> startQueues) {
        queues.addAll(startQueues);
        System.out.println("Merged "+startQueues.size()+" queues.");
    }

    @Override
    public boolean hasNext() {
        // "Lambda can be replaced with method reference):
        // return queues.stream().anyMatch(Iterator::hasNext);
        return queues.stream().anyMatch(it -> it.hasNext());
    }

    @Override
    public T next() {
        // Let's proceed according to iterator priority
        // Generalizing might be a fun use of the strategy or decorator pattern(s).
        for(Iterator<T> it : queues) {
            if(it.hasNext()) return it.next();
        }
        System.out.println(queues.stream().anyMatch(it -> it.hasNext()));
        // Note: IntelliJ defaulted to "return false"; that would BREAK THE CONTRACT!
        throw new NoSuchElementException();
    }
}
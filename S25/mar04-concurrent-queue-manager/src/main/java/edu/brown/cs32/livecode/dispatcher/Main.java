package edu.brown.cs32.livecode.dispatcher;

import edu.brown.cs32.livecode.aggregator.IteratorAggregator;
import edu.brown.cs32.livecode.aggregator.RandomMergeStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * This is an extended version of the prior queue-manager example.
 * Here we focus on concurrency, not on defensive programming.
 */
public class Main {

    public static void main(String[] args) {
        List<Iterator<Student>> queues = new ArrayList<>();

        // Two convenient ways to create lists for testing:
        queues.add(Arrays.asList(new Student[]{
                new Student("Nim"),
                new Student("Alice"),
                new Student("Bob")}).iterator());
        queues.add(List.of(
                new Student("Charli"),
                new Student("Boatswain"),
                new Student("Bucky")).iterator());

        /* Notice we now have TWO iterators to work with. In reality, this
           might represent students queuing from a webapp and students added
           manually to the queue. We'll have to handle this... */

        // queueManyStudents(queues); // for 2nd stage of demo

        // Another pattern example: aggregate multiple queues into one.
        Iterator<Student> aggregatedQueue = new IteratorAggregator<>(queues, new RandomMergeStrategy<>(queues));
        HoursDispatcher dispatcher =
                new HoursDispatcher(aggregatedQueue);

        // Random Spring '23 TA names, for demo purposes:
        dispatcher.addTA(new TA("Erica"), 110);
        dispatcher.addTA(new TA("Orion"), 50);

        // What if we had many more TAs?
        // addManyTAs(dispatcher); // for 2nd stage of demo

        // Start the dispatcher. This call never terminates! We cannot add new TAs, etc.
        // at this point. (Next time, contrast TypeScript behavior.)
        dispatcher.dispatch();
    }

    /**
     * used in the demo: add many TAs
     */
    private static void addManyTAs(HoursDispatcher dispatcher) {
        for(int index=0;index<100;index++) {
            dispatcher.addTA(new TA("TA"+index), 10);
        }
    }

    /**
     * Used in demo: queue up many students with very short questions
     */
    private static void queueManyStudents(List<Iterator<Student>> queues) {
        List<Student> newQueue = new ArrayList<>();
        for(int index=0;index<10000;index++) {
           newQueue.add(new Student("Student"+index, 10));
        }
        queues.add(newQueue.iterator());
    }
}

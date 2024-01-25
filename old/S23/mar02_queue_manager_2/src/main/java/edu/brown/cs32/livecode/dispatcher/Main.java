package edu.brown.cs32.livecode.dispatcher;

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

        //queueManyStudents(queues);

        // Another pattern example (for reference, optional reading)
        Iterator<Student> aggregatedQueue = new IteratorAggregator<>(queues);
        HoursDispatcher dispatcher =
                new HoursDispatcher(aggregatedQueue, "Concurrency");

        // Random Spring '23 edu.brown.cs32.livecode.threads.TA names, for demo purposes
        dispatcher.addTA(new TA("Erica"), 110);
        dispatcher.addTA(new TA("Orion"), 50);

        // What if we had many more TAs?
        //addManyTAs(dispatcher);

        dispatcher.dispatch(); // like starting the REPL, *NEVER TERMINATES*

    }

    private static void addManyTAs(HoursDispatcher dispatcher) {
        for(int index=0;index<100;index++) {
            dispatcher.addTA(new TA("TA"+index), 10);
        }
    }

    private static void queueManyStudents(List<Iterator<Student>> queues) {
        List<Student> newQueue = new ArrayList<>();
        for(int index=0;index<10000;index++) {
           newQueue.add(new Student("Student"+index, 10));
        }
        queues.add(newQueue.iterator());
    }
}

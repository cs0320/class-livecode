package edu.brown.cs32.spring22.Mar01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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

        Iterator<Student> aggregatedQueue = new IteratorAggregator<>(queues);
        HoursDispatcher dispatcher = new HoursDispatcher(aggregatedQueue, "Concurrency");
        dispatcher.addTA(new TA("Galen"), 120); // arbitrary TA name
        dispatcher.addTA(new TA("Jenny"), 120); // arbitrary TA name
        dispatcher.dispatch(); // like starting the REPL, never terminates

    }
}

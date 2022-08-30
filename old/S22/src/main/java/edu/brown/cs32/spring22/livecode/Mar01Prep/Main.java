package main.java.edu.brown.cs32.spring22.Mar01Prep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //demo1();
        demo2();
    }

    static void demo2() {
        List<Student> oneQueue = new ArrayList<>();
        // Not always guaranteed to manifest the bug...
        final int numStudents = 300000;
        final int numTas = 100000;
        for(int i = 1;i<=numStudents; i++)
            oneQueue.add(new Student("Student"+i));
        HoursDispatcher dispatcher = new HoursDispatcher(oneQueue.iterator(), "Concurrency 2");
        for(int i = 1;i<=numTas;i++)
            dispatcher.addTA(new TA("TA"+i), 120);
        dispatcher.dispatch();

    }

    static void demo1() {
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

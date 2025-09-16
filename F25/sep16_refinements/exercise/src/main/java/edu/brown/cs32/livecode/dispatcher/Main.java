package edu.brown.cs32.livecode.dispatcher;

import edu.brown.cs32.livecode.records.Student;
import edu.brown.cs32.livecode.records.TA;

import java.util.*;

/**
 * Show the (incomplete) dispatcher example
 */
public class Main {

    public static void main(String[] args) {
        // Not all Lists implement Queue. LinkedList does, ArrayList does not.
        Deque<Student> queue = new LinkedList<>(Arrays.asList(
                new Student("Nim", "project1", 1000),
                new Student("Alice", "project1", 1000),
                new Student("Bob", "project1", 1000),
                new Student("Charli", "project2", 1000),
                new Student("Boatswain", "project2", 1000),
                new Student("Bucky", "project3", 1000)));
        HoursDispatcher dispatcher = new HoursDispatcher(queue);

        // Random Spring '23 TA names, for demo purposes:
        dispatcher.addTA(new TA("Erica", "project1", queue), 110);
        dispatcher.addTA(new TA("Orion", "project2", queue), 50);

        dispatcher.dispatch();
    }
}

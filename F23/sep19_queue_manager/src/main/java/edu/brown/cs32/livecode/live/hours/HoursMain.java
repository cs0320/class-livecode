package edu.brown.cs32.livecode.live.hours;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Demo main for the TA-queue example.
 */
public class HoursMain {
    public static void main(String[] args) {
        // Let's start with an empty queue. We'll add students, etc. at a later date.
        Iterator<Student> studentIterator = new ArrayList<Student>().iterator();
        HoursDispatcherBasic disp = new HoursDispatcherBasic(studentIterator,
                "Let's talk about patterns!");

        // At this point we'd add students etc.
    }
}

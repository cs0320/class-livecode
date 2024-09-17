package edu.brown.cs32.livecode.queue;

import edu.brown.cs32.livecode.records.Student;
import edu.brown.cs32.livecode.records.TA;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HoursDispatcher {
    Iterator<Student> queue;
    Map<TA, Integer> minutesLeft = new HashMap<>();

    /** We won't use the iterator yet, I'm just including it for verisimilitude. */
    HoursDispatcher(Iterator<Student> signups) {
        this.queue = signups;
    }

    void addTA(TA ta, int minutes) {
        minutesLeft.put(ta, minutes);
    }
}

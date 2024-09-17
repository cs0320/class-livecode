package edu.brown.cs32.livecode.queue;

import edu.brown.cs32.livecode.records.Student;
import edu.brown.cs32.livecode.records.TA;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HoursDispatcher {
    private final Iterator<Student> queue;
    private final Map<TA, Integer> minutesLeft = new HashMap<>();

    /** We won't use the iterator yet, I'm just including it for verisimilitude. */
    HoursDispatcher(Iterator<Student> signups) {
        this.queue = signups;
    }

    void addTA(TA ta, int minutes) {
        if(minutes < 0) {
            // throw something
        }
        this.minutesLeft.put(ta, minutes);
    }

    private Map<TA, Integer> canonical_minutes;
    Map<TA, Integer> getMinutesLeft() {
        if(this.canonical_minutes == null) {
            this.canonical_minutes = Collections.unmodifiableMap(this.minutesLeft);
        }
        return this.canonical_minutes;
    }
}

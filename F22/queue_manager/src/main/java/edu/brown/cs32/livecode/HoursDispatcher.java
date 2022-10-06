package edu.brown.cs32.livecode;

import java.util.*;

public class HoursDispatcher {
    Iterator<Student> queue;
    Map<TA, Integer> minutesLeft;
    String statusMessage;

    HoursDispatcher(Iterator<Student> signups, String statusMessage) {
        this.queue = signups;
        this.statusMessage = statusMessage;
        this.minutesLeft = new HashMap<>();
    }

    /**
     * Add a TA to the pool, with "minutes" time remaining. If the TA is already
     * in the pool, add that amount of time to their time remaining.
     * @param ta
     * @param minutes
     * @throws IllegalArgumentException if the time remaining is invalid
     */
    void addTA(TA ta, int minutes) throws IllegalArgumentException {
        if(minutes <= 0) {
            throw new IllegalArgumentException("minutes must be greater than 0");
        }
        if(!minutesLeft.containsKey(ta))
            minutesLeft.put(ta, minutes);
        else
            minutesLeft.put(ta, minutes + minutesLeft.get(ta));
    }

}

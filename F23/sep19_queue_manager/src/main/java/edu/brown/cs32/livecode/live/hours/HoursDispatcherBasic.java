package edu.brown.cs32.livecode.live.hours;

import java.util.*;

/**
 * Basic version of the hours dispatcher. Accepts an
 * Iterator of Students and follows that ordering as it assigns TAs.
 */
public class HoursDispatcherBasic implements Dispatcher {
    Iterator<Student> queue;
    Map<TA, Integer> minutesLeft;
    String statusMessage;

    HoursDispatcherBasic(Iterator<Student> signups, String statusMessage) {
        this.queue = signups;
        this.statusMessage = statusMessage;
        this.minutesLeft = new HashMap<>();
    }

    /**
     * @return the current minutes that each active TA has left
     */
    public Map<TA, Integer> getMinutesLeft() {
        return minutesLeft;
    }

    /**
     * Add a TA to the pool, with "minutes" time remaining. If the TA is already
     * in the pool, add that amount of time to their time remaining.
     * @param ta the TA who is joining the queue
     * @param minutes the number of minutes this TA will hold hours
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

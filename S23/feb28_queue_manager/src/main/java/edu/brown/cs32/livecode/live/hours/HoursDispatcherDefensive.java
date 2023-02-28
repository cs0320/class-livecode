package edu.brown.cs32.livecode.live.hours;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * HoursDispatcherBasic, except with:
 *   some better field modifiers (like final)
 *   defensive copy on return value of getMinutesLeft()
 */
public class HoursDispatcherDefensive implements Dispatcher {
    final private Iterator<Student> queue;
    final private Map<TA, Integer> minutesLeft;

    String statusMessage;

    HoursDispatcherDefensive(Iterator<Student> signups, String statusMessage) {
        this.queue = signups;
        this.statusMessage = statusMessage;
        this.minutesLeft = new HashMap<>();
    }

    /**
     * @return the current minutes that each active TA has left
     */
    public Map<TA, Integer> getMinutesLeft() {
        return new HashMap<>(minutesLeft);
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

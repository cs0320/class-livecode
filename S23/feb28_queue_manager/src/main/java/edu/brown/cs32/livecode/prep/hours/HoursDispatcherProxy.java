package edu.brown.cs32.livecode.prep.hours;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * HoursDispatcherDefensive with:
 *   only one canonical defensive map to return
 *   that's a pass-through unmodifiable proxy (via Java library!)
 */
public class HoursDispatcherProxy implements Dispatcher {
    private final Iterator<Student> queue;
    private final Map<TA, Integer> minutesLeft;

    // Critique "defensive copy": what could go wrong?
    //   (any potential issues remaining? any features we removed that _good_ user may have relied on?)

    // PROXY with an unmodifiable view:
    private Map<TA, Integer> public_minutes_left = null;
    public Map<TA, Integer> getMinutesLeft() {
        if(public_minutes_left == null) {
            public_minutes_left = Collections.unmodifiableMap(minutesLeft);
        }
        return public_minutes_left;
    }

    String statusMessage;

    HoursDispatcherProxy(Iterator<Student> signups, String statusMessage) {
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

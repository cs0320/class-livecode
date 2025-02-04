package edu.brown.cs32.livecode.queue;

import edu.brown.cs32.livecode.records.TA;

import java.util.HashMap;
import java.util.Map;

/**
 * Starter class for the defensive programming and proxies class livecode
 */
public class HoursDispatcher {
    /** A map that says who is on duty, and how many more minutes they are on duty for.
     *  Note that it's a "final" field, so we can never replace its value with a different
     *  Map object. */
    private final Map<TA, Integer> minutesLeft = new HashMap<>();

    /** If this were a real project, we'd be passing in some sort of provider of student signups,
      * perhaps an iterator or generator object. But I'm leaving it out so that we can focus
      * exclusively on minutesLeft. */
    HoursDispatcher() {}

    /**
     * Add a TA to the on-duty hours list.
     * @param ta The TA who is now on duty.
     * @param minutes The number of minutes this TA will be on duty for.
     */
    void addTA(TA ta, int minutes) {
        this.minutesLeft.put(ta, minutes);
    }

    /**
     * Gets an object that represents the current on-duty state.
     * @return A map from TA objects to the number of minutes they have left on duty.
     */
    Map<TA, Integer> getMinutesLeft() {
        return this.minutesLeft;
    }
}

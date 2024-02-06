package edu.brown.cs32.livecode.hours;

import java.util.HashMap;
import java.util.Map;

/**
 * Basic class, starting point for example
 *
 */
public class HoursDispatcherBasic implements Dispatcher {
    Map<TA, Integer> minutesLeft;

    HoursDispatcherBasic() {
        this.minutesLeft = new HashMap<>();
    }

    /**
     * @return the current minutes that each active TA has left
     */
    public Map<TA, Integer> getMinutesLeft() {
        return minutesLeft;
    }

}

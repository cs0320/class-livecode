package edu.brown.cs32.livecode.hours;

import java.util.HashMap;
import java.util.Map;

/**
 * HoursDispatcherBasic, except with:
 *   some better field modifiers (like final)
 *   defensive copy on return value of getMinutesLeft()
 */
public class HoursDispatcherDefensive implements Dispatcher {
    final private Map<TA, Integer> minutesLeft;

    HoursDispatcherDefensive() {
        this.minutesLeft = new HashMap<>();
    }

    /**
     * @return the current minutes that each active TA has left
     */
    public Map<TA, Integer> getMinutesLeft() {
        return new HashMap<>(minutesLeft);
    }

}

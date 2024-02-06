package edu.brown.cs32.livecode.hours;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * HoursDispatcherDefensive with:
 *   only one canonical defensive map to return
 *   that's a pass-through unmodifiable proxy (via Java library!)
 *
 */
public class HoursDispatcherProxyViaUnmodifiable implements Dispatcher {
    private final Map<TA, Integer> minutesLeft;

    // Critique "defensive copy": what could go wrong?
    //   (any potential issues remaining? any features we removed that _good_ user may have relied on?)

    // PROXY with an unmodifiable view:
    private Map<TA, Integer> public_minutes_left = null;
    public Map<TA, Integer> getMinutesLeft() {
        if(public_minutes_left == null) {
            // We could have written this ourselves
            public_minutes_left = Collections.unmodifiableMap(minutesLeft);
        }
        return public_minutes_left;
    }

    String statusMessage;

    HoursDispatcherProxyViaUnmodifiable() {
        this.minutesLeft = new HashMap<>();
    }


}

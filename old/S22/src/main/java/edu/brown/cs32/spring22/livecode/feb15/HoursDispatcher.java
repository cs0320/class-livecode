package edu.brown.cs32.spring22.livecode.feb15;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class HoursDispatcher {
    private Iterator<Student> queue;
    private String statusMessage;
    private Map<TA, Integer> public_view_minutesLeft;
    public Map<TA, Integer> getMinutesLeft() {
        // Defensive copy
        if(public_view_minutesLeft == null)
            public_view_minutesLeft = new HashMap<>(minutesLeft);
        return public_view_minutesLeft;
    }

    private final Map<TA, Integer> minutesLeft;
    HoursDispatcher(Iterator<Student> signups, String statusMessage) {
        this.queue = signups;
        this.statusMessage = statusMessage;
        this.minutesLeft = new HashMap<>();
    }

    /**
     *
     * @param ta
     * @param minutes
     */
    void addTA(TA ta, int minutes) throws IllegalArgumentException {
        if(minutes <= 0 ) {
            // return;
            throw new IllegalArgumentException("minutes cannot be less than 1");
        }
        minutesLeft.put(ta, minutes);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HoursDispatcher that = (HoursDispatcher) o;
        return Objects.equals(queue, that.queue) && Objects.equals(statusMessage, that.statusMessage) && Objects.equals(minutesLeft, that.minutesLeft);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queue, statusMessage, minutesLeft);
    }
}

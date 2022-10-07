package edu.brown.cs32.livecode;

import java.util.*;

public class HoursDispatcher {
    private final Iterator<Student> queue;
    private final Map<TA, Integer> minutesLeft;

    // Critique "defensive copy": what could go wrong?
    //   (any attacker potential remaining? any features we removed that _good_ user may have relied on?)

    // Option: PROXY with an unmodifiable view:
    private Map<TA, Integer> public_minutes_left = null;
    public Map<TA, Integer> getMinutesLeft() {
        if(public_minutes_left == null) {
            public_minutes_left = Collections.unmodifiableMap(minutesLeft);
        }
        return public_minutes_left;
    }

    /**
     * Returns a view of all the currently on duty TAs by name
     */
    public Collection<String> getOnDutyView() {
        return new OnDutyTAView();
    }


    // Adapter pattern: proxy that changes the interface to caller
    //  Here, we narrow the interface provided. We didn't have to use
    //  a nested class; that just makes it easier. If we used a normal
    //  class, probably we'd pass minutesLeft to the constructor, just like
    //  Collections.unmodifiableMap does.
    class OnDutyTAView implements Collection<String> {

        @Override
        public int size() {
            return minutesLeft.size();
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<String> iterator() {
            return minutesLeft.keySet().stream().map(ta -> ta.name).iterator();
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(String s) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }
    }






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

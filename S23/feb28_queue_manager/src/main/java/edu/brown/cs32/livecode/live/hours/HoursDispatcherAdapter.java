package edu.brown.cs32.livecode.live.hours;

import java.util.*;

/**
 * HoursDispatcherProxy, except
 *   changes the interface! No longer offer a direct hours map
 *   instead, give a view object that hides the real TA object references and time.
 */
public class HoursDispatcherAdapter implements Dispatcher, LimitedDispatcher {
    private final Iterator<Student> queue;
    private final Map<TA, Integer> minutesLeft;

    /**
     * Returns a view of all the currently on duty TAs by name
     */
    @Override
    public Collection<String> getOnDutyView() {
        return new OnDutyTAView();
    }

    @Override
    public Map<TA, Integer> getMinutesLeft() {
        throw new UnsupportedOperationException("use getOnDutyView instead");
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

    HoursDispatcherAdapter(Iterator<Student> signups, String statusMessage) {
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

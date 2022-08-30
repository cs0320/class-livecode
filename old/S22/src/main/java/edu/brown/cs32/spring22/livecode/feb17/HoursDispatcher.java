package edu.brown.cs32.spring22.livecode.feb17;

import java.util.*;

class CustomBadArgumentException extends Exception {
    String argname;
    Object value;
    String message;

    CustomBadArgumentException(String argname, Object value, String message) {
        this.argname = argname;
        this.value = value;
        this.message = message;
    }
}

public class HoursDispatcher {
    private final Iterator<Student> queue;
    private final Map<TA, Integer> minutesLeft;
    private final String statusMessage;

    // WHAT IF I HAVE MULTIPLE ITERATORS???
    //   I don't want to change this class.
    //   I want a proxy-ish class that combines them.
    HoursDispatcher(Iterator<Student> signups, String statusMessage) {
        this.queue = signups;
        this.statusMessage = statusMessage;
        this.minutesLeft = new HashMap<>();
    }

    void addTA(TA ta, int minutes) throws CustomBadArgumentException {
        if(minutes <= 0) {
            throw new CustomBadArgumentException("minutes", minutes, "must be greater than 0");
        }
        minutesLeft.put(ta, minutes);
    }


    private Map<TA, Integer> view_minutesLeft;
    public Map<TA, Integer> getMinutesLeft() {
        // Canonical defensive copy
        if(view_minutesLeft == null)
            view_minutesLeft = Collections.unmodifiableMap(minutesLeft);
        return view_minutesLeft;
    }

    class OnDutyTAsView implements Collection<String> {

        @Override
        public int size() {
            return minutesLeft.size();
        }

        @Override
        public boolean isEmpty() {
            return minutesLeft.isEmpty();
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
            throw new UnsupportedOperationException();
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

}

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HoursDispatcher {
    private final Iterator<Student> queue;
    private final Map<TA, Integer> minutesLeft = new HashMap<>();

    private final String statusMessage;

    // Counter
    static int studentsHelped = 0;

    HoursDispatcher(Iterator<Student> signups, String statusMessage) {
        this.queue = signups;
        this.statusMessage = statusMessage;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    void addTA(TA ta, int minutes) {
        if(minutes <= 0) {
            throw new IllegalArgumentException("minutes must be non-negative");
        }
        minutesLeft.put(ta, minutes);
    }

    private Map<TA, Integer> public_view_minutesLeft;
    public Map<TA, Integer> getMinutesLeft() {
        // Canonical defensive copy
        if(public_view_minutesLeft == null)
            public_view_minutesLeft = new HashMap<>(minutesLeft);
        return public_view_minutesLeft;
    }

    public void dispatch()  {
        System.out.println(Utils.timestamp()+" Dispatcher: Welcome to TA hours! Today we're discussing "+this.getStatusMessage());
        while(true) {
            if(queue.hasNext()) {
                // Who to help them?
                TA helper = null;
                for(TA ta : this.minutesLeft.keySet()) {
                    if (ta.isFree()) {
                        helper = ta;
                        break;
                    }
                }
                if(helper == null) continue;

                // Who to see next?
                Student nextStudent = queue.next();

                // NOTE 1: is there a risk of bad behavior here?
                // NOTE 2: or: stream, filter, findFirst

                // Help the student
                System.out.println(Utils.timestamp()+" Dispatcher: Hi "+nextStudent+"; you'll be seen by "+helper);
                try {
                    helper.seeStudent(nextStudent);
                } catch (TABusyException e) {
                    System.out.println("Unexpected behavior: Student dropped from queue!");
                }
            } else {
                try {
                    // nobody is waiting; go to sleep for a bit
                    System.out.println(Utils.timestamp()+" Dispatcher ("+studentsHelped+"): Nobody waiting in queue, will check again in three seconds.");
                    Thread.sleep(3000);
                } catch(InterruptedException e) {
                    System.out.println("Dispatcher terminated.");
                }
            }
        }
    }

    /**
     * Inner class that exposes a protected abstraction: let the caller see TA names and times, but
     * not access the underlying TA objects. Attempts to modify the collection will produce an
     * UnsupportedOperatorException.
     *
     */
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
            return minutesLeft.containsKey(o);
        }

        @Override
        public Iterator<String> iterator() {
            // FP approach (concise); can also use an anonymous class
            // IntelliJ wants me to use a "TA::getName" method reference; I prefer this way personally
            return minutesLeft.keySet().stream().map(ta -> ta.getName()).iterator();
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(String s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends String> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
    }

}
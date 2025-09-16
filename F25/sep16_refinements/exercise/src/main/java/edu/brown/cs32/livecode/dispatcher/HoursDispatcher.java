package edu.brown.cs32.livecode.dispatcher;

import edu.brown.cs32.livecode.dispatcher.utils.TABusyException;
import edu.brown.cs32.livecode.dispatcher.utils.Utils;
import edu.brown.cs32.livecode.records.Student;
import edu.brown.cs32.livecode.records.TA;

import java.util.*;

/**
 * A (very incomplete) dispatcher class for TA hours.
 */
public class HoursDispatcher {
    /** Single queue that the dispatcher will get students from */
    private final Deque<Student> queue;
    /** TAs on duty */
    private final Map<TA, Integer> minutesLeft = new HashMap<>();
    /** How many students have been helped by *any* dispatcher instance. */
    static int studentsHelped = 0;

    /**
     * @param signups an Iterator that provides Student objects
     */
    HoursDispatcher(Deque<Student> signups) {
        this.queue = signups;
    }

    /**
     * Add an on-duty TA
     * @param ta the TA who is arriving for hours
     * @param minutes the number of minutes that this TA will stay
     */
    void addTA(TA ta, int minutes) {
        minutesLeft.put(ta, minutes);
    }

    /**
     * Running this method starts the dispatcher.
     */
    public void dispatch()  {
        System.out.println(Utils.timestamp()+
                " Dispatcher: Welcome to edu.brown.cs32.livecode hours!");

        // Loop forever: new students may arrive over time
        while(true) {
            // Is there a student waiting right now?
            if(queue.peek() != null) {
                // Who will help this student?
                // TODO: later, prioritize free TAs with a specialty matching the student's question.
                Optional<TA> maybeHelper = this.minutesLeft.keySet().stream().filter(TA::isFree).findFirst();
                if(maybeHelper.isEmpty()) {
                   Utils.tryWait(); // nobody is free; wait a short time
                   continue;
                }
                TA helper = maybeHelper.orElseThrow();

                // Who to see next?
                Student nextStudent = queue.poll();

                // Help the student
                System.out.println(Utils.timestamp()+" Dispatcher: Hi "+nextStudent+"; you'll be seen by "+helper);
                try {
                    helper.seeStudent(nextStudent);
                } catch (TABusyException e) {
                    // We hope this won't happen, but if this class had a bug in it, or a different
                    // thread were possibly assigning students, or if we had implemented a TA timeout,
                    // this would be possible...
                    System.out.println("Unexpected behavior: TA became busy while assigning student! Re-adding student to queue.");
                    queue.addFirst(nextStudent); // a Deque lets us add at the front or the back

                }
            } else {
                System.out.println(Utils.timestamp()+" Dispatcher ("+studentsHelped+" helped so far): Nobody waiting in queue, will check again shortly.");
                Utils.tryWait();
            }
        }
    }

    public static void helpedAStudent() {
        studentsHelped++;
    }

}
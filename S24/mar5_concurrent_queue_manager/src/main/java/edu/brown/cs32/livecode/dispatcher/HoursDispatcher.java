package edu.brown.cs32.livecode.dispatcher;

import edu.brown.cs32.livecode.dispatcher.utils.TABusyException;
import edu.brown.cs32.livecode.dispatcher.utils.Utils;

import java.util.*;

/**
 * Revised version of the prior dispatcher example. This focuses on concurrency,
 * *NOT* on defensive programming, and so I simplified out some of the proxy
 * example code.
 */
public class HoursDispatcher {
    private final Iterator<Student> queue;
    private final Map<TA, Integer> minutesLeft = new HashMap<>();

    private final String statusMessage;

    /**
     * How many students have been helped by any dispatcher, on any queue?
     */
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
        System.out.println(Utils.timestamp()+" Dispatcher: Welcome to edu.brown.cs32.livecode.threads.TA hours! Today we're discussing "+this.getStatusMessage());
        //noinspection InfiniteLoopStatement
        while(true) {
            if(queue.hasNext()) {
                // Who will help this student?
                TA helper = null;
                for(TA ta : this.minutesLeft.keySet()) {
                    if (ta.isFree()) {
                        helper = ta;
                        break;
                    }
                }
                if(helper == null) continue; // no available TA

                // Who to see next?
                //  (Question: why set this here, rather than before the edu.brown.cs32.livecode.threads.TA loop?)
                Student nextStudent = queue.next();

                // Help the student
                System.out.println(Utils.timestamp()+" Dispatcher: Hi "+nextStudent+"; you'll be seen by "+helper);
                try {
                    helper.seeStudent(nextStudent);
                } catch (TABusyException e) {
                    System.out.println("Unexpected behavior: edu.brown.cs32.livecode.threads.TA timed out while assigning student!");
                    // TODO Return student to queue
                    // ...
                }
            } else {
                try {
                    // nobody is waiting; go to sleep for a bit
                    System.out.println(Utils.timestamp()+" Dispatcher ("+studentsHelped+" helped so far): Nobody waiting in queue, will check again in three seconds.");
                    Thread.sleep(3000);
                } catch(InterruptedException e) {
                    System.out.println("Dispatcher terminated.");
                }
            }
        }
    }

}
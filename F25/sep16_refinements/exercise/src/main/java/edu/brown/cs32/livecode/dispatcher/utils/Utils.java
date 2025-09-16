package edu.brown.cs32.livecode.dispatcher.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    private Utils() {} // no constructor
    final static private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("hh:mm:ss:SSS");
    public static String timestamp() {
        return fmt.format(LocalTime.now());
    }

    /** The number of milliseconds to wait if no progress can be made. */
    static final int SLEEP_DELAY_MS = 3000;
    /** Exit code: the application thread has unexpectedly terminated. */
    static final int ERR_THREAD_TERMINATED = 1;

    /** The application waits before trying again. */
    public static void tryWait() {
        try {
            Thread.sleep(SLEEP_DELAY_MS);
        } catch(InterruptedException e) {
            // The application thread has been interrupted. Stop the app.
            System.out.println("Dispatcher terminated. Stopping.");
            System.exit(ERR_THREAD_TERMINATED); // Exit the application with error status
        }
    }

}

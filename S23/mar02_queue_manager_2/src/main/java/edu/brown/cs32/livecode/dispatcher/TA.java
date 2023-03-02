package edu.brown.cs32.livecode.dispatcher;

import edu.brown.cs32.livecode.dispatcher.utils.TABusyException;
import edu.brown.cs32.livecode.dispatcher.utils.Utils;

/**
 * A edu.brown.cs32.livecode.threads.TA is now a _runnable_, which means we can create a thread for each.
 *
 * Question: what would you do if you needed to create a thread for a class
 * that didn't implement Runnable? (Hint: consider what you can do with a proxy.)
 */
public class TA implements Runnable {
    final private String name;

    TA(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    private Student helping = null;
    public void seeStudent(Student student) throws TABusyException {
        if(this.helping != null)
            throw new TABusyException();
        this.helping = student;

        // *** Only uncomment one of these at a time ***

        // Version that won't allow multiple TAs to see students in parallel
        run();

        // Version that *WILL* allow multiple TAs to see students in parallel
        //new Thread(this).start();
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isFree() {
        return helping == null;
    }

    @Override
    public void run() {
        System.out.println(Utils.timestamp()+" "+name+ " says: Hello "+helping+"!");
        try {
            // Help the student, however long they need
            Thread.sleep(helping.getProblemTimeMilliseconds());
        } catch (InterruptedException e) {
            // Actually shouldn't happen...
            System.out.println(this.name+" was interrupted; had to stop helping.");
        }
        System.out.println(name+ " says: Goodbye "+helping+", I hope that helped!!");
        HoursDispatcher.studentsHelped += 1;
        helping = null;
    }
}

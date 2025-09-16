package edu.brown.cs32.livecode.records;

import edu.brown.cs32.livecode.dispatcher.HoursDispatcher;
import edu.brown.cs32.livecode.dispatcher.utils.TABusyException;
import edu.brown.cs32.livecode.dispatcher.utils.Utils;

import java.util.Deque;

/** A TA record, with some functionality. Because we need to have internal
 *  state (at least, designed like this) we have to use a class, not a record.
 */
public class TA {
    final private String name;
    final private String specialty;
    final private Deque<Student> replaceIn;

    public TA(String name, String specialty, Deque<Student> replaceIn) {
        this.name = name;
        this.specialty = specialty;
        this.replaceIn = replaceIn;
    }
    public String getName() {
        return name;
    }

    private Student helping = null;
    public void seeStudent(Student student) throws TABusyException {
        if(this.helping != null)
            throw new TABusyException();
        this.helping = student;

        System.out.println(Utils.timestamp()+" "+name+ " says: Hello "+helping+"!");
        try {
            // Help the student, however long they need
            Thread.sleep(helping.problem_needs_ms());
        } catch (InterruptedException e) {
            // Shouldn't happen, but in case it does due to OS issues etc.:
            System.out.println(this.name+" was interrupted; had to stop helping.");
            this.replaceIn.addFirst(helping);
        }

        System.out.println(name+ " says: Goodbye "+helping+", I hope that helped!!");
        HoursDispatcher.helpedAStudent();
        helping = null;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isFree() {
        return helping == null;
    }

}

package edu.brown.cs32.spring22.Mar01Prep;

import static edu.brown.cs32.spring22.Mar01Prep.Utils.timestamp;

public class TA implements Runnable {
    final private String name;

    private Student helping = null;

    TA(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void seeStudent(Student student) throws TABusyException {
        if(helping != null)
            throw new TABusyException();
        helping = student;
        new Thread(this).start(); // NOT the same as .run()
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
        // help the student (simulate by pausing for 1.5 seconds -- we're VERY EFFICIENT)
        //System.out.println(timestamp()+" "+name+ " says: Hello "+helping+"!");
        //System.out.println(timestamp()+" "+name+ " says: Goodbye "+helping+", I hope that helped!!");
        HoursDispatcher.studentsSeen += 1;
        helping = null;
    }
}

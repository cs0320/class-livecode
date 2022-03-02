package main.java.edu.brown.cs32.spring22.Mar01;

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
        if(helping != null)
            throw new TABusyException();
        helping = student;
        new Thread(this).start();
        // immediately return!
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
        System.out.println(name+ " says: Hello "+helping+"!");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("Oops -- terminated!"); // not a great message
            System.exit(1);
        }
        System.out.println(name+ " says: Goodbye "+helping+", I hope that helped!!");
        // Why might this increment operation be concerning
        //   inside a thread?
        //HoursDispatcher.studentsHelped += 1;
        HoursDispatcher.studentsHelped.getAndIncrement();
        //  ^ Could be a concern with += 1, too
        //  or with <var> = <var> + 1
        helping = null;
    }
}

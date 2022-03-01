package edu.brown.cs32.spring22.Mar01;

public class TA {
    final private String name;

    private boolean free = true;

    TA(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void seeStudent(Student student) throws TABusyException {
        if(!free)
            throw new TABusyException();

        // help the student (simulate by pausing for 1.5 seconds -- we're VERY EFFICIENT)
        System.out.println(name+ " says: Hello "+student+"!");
        free = false;
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("Oops -- terminated!"); // not a great message
            System.exit(1);
        }
        System.out.println(name+ " says: Goodbye "+student+", I hope that helped!!");
        free = true;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isFree() {
        return free;
    }

}

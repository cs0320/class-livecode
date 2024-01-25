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
        System.out.println(name+ " says: Hello "+helping+"!");
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

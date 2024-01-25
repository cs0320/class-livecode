public class ThreadProblemsDemo {
    static long counter = 0;

    public static void main(String[] args) {
        for(int ii = 0; ii<2;ii++) {
            Runnable worker = new WorkerThread();
            Thread workerThread = new Thread(worker);
            System.out.println("Starting worker "+ii);
            workerThread.start();
        }
        // NOTE x2
        System.out.println(ThreadProblemsDemo.counter);
    }
}

class WorkerThread implements Runnable {

    @Override
    public void run() {
        while(ThreadProblemsDemo.counter < 100000000) {
            ThreadProblemsDemo.counter++;
        }
    }
}

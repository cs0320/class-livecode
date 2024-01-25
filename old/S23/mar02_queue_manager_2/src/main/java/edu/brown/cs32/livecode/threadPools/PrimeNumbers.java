package edu.brown.cs32.livecode.threadPools;
import java.util.*;
import java.util.concurrent.*;

/**
 * A piece of code that **someone else** has produced. We asked for how many
 * prime numbers there are between 2 and 1 million, as well as how many times
 * their algorithm uses the % operator.
 *
 * Your job is to review this code...
 */

public class PrimeNumbers {
    private static final int THREAD_COUNT = 2;

    public static void main(String[] args) throws InterruptedException {
        int n = 1000000; // maximum number to check
        boolean[] isPrime = new boolean[n+1]; // initialize boolean array
        Arrays.fill(isPrime, true); // assume all numbers are prime

        // create thread pool
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        // calculate prime numbers in parallel
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) {
                executor.submit(new CheckPrimeTask(isPrime, i));
            }
        }

        // wait for all tasks to complete
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        // create list of prime numbers
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }

        System.out.println("Count of prime numbers between 2 and 1,000,000:");
        System.out.println(primes.size());

        // count the number of times the remainder operator is used
        int remainderCount = CheckPrimeTask.getRemainderCount();

        System.out.println("Number of times the remainder operator (%) is used: " + remainderCount);
    }

    // task to check if a number is prime
    private static class CheckPrimeTask implements Runnable {
        private static volatile int remainderCount = 0;
        private boolean[] isPrime;
        private int num;

        public CheckPrimeTask(boolean[] isPrime, int num) {
            this.isPrime = isPrime;
            this.num = num;
        }

        public void run() {
            int divisorCount = 0;
            for (int i = 2; i*i <= num; i++) {
                divisorCount++;
                if (num % i == 0) {
                    remainderCount++;
                    isPrime[num] = false; // mark non-prime numbers
                    return;
                }
            }
            remainderCount += (divisorCount - 1); // subtract 1 for the first divisor (1)
        }

        public static int getRemainderCount() {
            return remainderCount;
        }
    }
}

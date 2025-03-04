package edu.brown.cs32.livecode.threadPools;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/*
  Another threads example, this time focused on using parallelism to improve
  performance. I'm adapting this from an example from Fall 2021, so there may
  be some dissonance vs. the examples from this semester.

  Here's one way to take advantage of parallelism in Java: task pools

  A toy example: searching for a needle in a haystack.
  Here, a "needle" is an integer in a set, and the "haystack"
  is a very large range of possible integers. This general
  approach can be useful for a lot of things (e.g., primality
  testing).

  There are many parameters to work with here: can threads can "stuck"
  and need assistance to continue? How should we increment the counter? Etc.
*/

class Haystack {
    private final Collection<Integer> needles = new HashSet<>();
    public static final int MAXKEY = 100000000; // 100 million
    private final Collection<HunterTask> unstuck = new HashSet<>();

    Haystack() {
        for(int i=0;i<=MAXKEY;i++) {
            if(i%10000 == 0) needles.add(i);
        }
    }
    Haystack(Collection<Integer> needles) {
        this.needles.addAll(needles);
    }

    public boolean query(int i) {
        return needles.contains(i);
    }

    public void stuck(HunterTask h) {
        unstuck.remove(h);
    }
    public void unstuck(HunterTask h) {
        unstuck.add(h);
    }
    public boolean isUnstuck(HunterTask h) {
        return unstuck.contains(h);
    }
    public boolean someoneUnstuck() {
        return unstuck.size() > 0;
    }
    public void hello(HunterTask h) {
        unstuck.add(h);
    }
    public void goodbye (HunterTask h) {
        unstuck.remove(h);
    }
}


public class ThreadPoolDemo {

    public static void main(String[] args) {
        Haystack haystack = new Haystack();
        searchHaystackPool(haystack, 4, 100000);
    }

    private static void searchHaystackPool(Haystack haystack, int nThreads, int increment) {
        ExecutorService service = Executors.newFixedThreadPool(nThreads);

        // Q: what would happen if Haystack.MAXKEY = Integer.MAX_VALUE?
        for (int ii = 0; ii <= Haystack.MAXKEY; ii += increment) {
            service.execute(new HunterTask(haystack, ii, Math.min(ii + increment - 1, Haystack.MAXKEY)));
        }
        service.shutdown(); // done dispatching work
        // work has started at this point
        try {
            // wait until all tasks are completed
            boolean notimeout = service.awaitTermination(100, TimeUnit.HOURS);
            System.out.println("Number of needles found: "+HunterTask.getNeedlesFound().size());
            System.out.println("Number of needles found was also: "+HunterTask.getNeedlesCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class HunterTask implements Runnable {
    final int min;
    final int max;
    final Haystack haystack;

    private static AtomicInteger needlesCount = new AtomicInteger(0);
    private static Collection<Integer> needlesFound = new HashSet<>();

    public static int getNeedlesCount() {
        return needlesCount.get();
    }
    public static Collection<Integer> getNeedlesFound() {
        return needlesFound;
    }

    HunterTask(Haystack haystack, int min, int max) {
        this.min = min;
        this.max = max;
        this.haystack = haystack; // defensive copy would be unhelpful
    }

    public void run() {
        System.out.println("New worker running starting at: "+min);
        haystack.hello(this);
        int i = min;
        while(i<=max) {
            if(haystack.isUnstuck(this)) {
                if (false) { //(Math.random() < 0.001) {
                    // arrrrgh! I'm stalled until someone can help me.
                    haystack.stuck(this);
                } else {
                    if (haystack.query(i)) {
                        //needlesCount++;
                        //needlesCount = needlesCount + 1; // not "atomic"
                        needlesCount.getAndAdd(1); // ATOMIC

                        // VITAL: "synchronized"
                        // Only one thread can enter this section at once
                        // The "needlesFound" object here is used to disambiguate
                        // See Bloch "Synchronize Access to Mutable Data"
                        // ***************************
                        synchronized (needlesFound) {
                            needlesFound.add(i);
                        }
                    }
                    i++; // checked this spot
                }
            } else {
                if(haystack.someoneUnstuck()) {
                    // they will help me out
                    haystack.unstuck(this);
                }
            }
        }
        haystack.goodbye(this);
    }
}

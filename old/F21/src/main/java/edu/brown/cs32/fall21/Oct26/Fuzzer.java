package edu.brown.cs32.fall21.Oct26;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A *DEMO* of the fuzz-testing technique. You don't need to
 * do it this way; I just made a separate class to keep the code
 * isolated. Don't worry about making a "generic fuzzer"; just
 * try to make one for testing a specific program you've written:
 * kd-tree nearest-neighbor, list-based nearest neighbor, Bloom filter,
 * something from another class...
 */
public class Fuzzer {

    // Convenient way to generate random doubles within a range
    private final ThreadLocalRandom tlr = ThreadLocalRandom.current();

    public static void demoFuzz(int trials) {
        // My design got a little tangled here :-(
        // This might make sense as a factory method, if Fuzzer
        // took configuration parameters (but it doesn't).
        Fuzzer f = new Fuzzer();

        for(int i=0;i<trials;i++) {
            try {
                System.out.println("Trial: " + i);
                LinearBasedNearest lb = f.fuzzTestBuild(f.generate(1000));
                double query = f.tlr.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE);
                int num = f.tlr.nextInt(100); // between 0 (inclusive) and bound
                System.out.println("  query on " + query + "; seeking " + num + " values.");
                f.fuzzTestQuery(lb, query, num);
            } catch(Exception e) {
                // Normally catching "Exception" is a really bad idea
                // Here it makes sense, kind of.
                // I say "kind of" because programs produce exceptions
                //   normally; you'd really like to catch *unexpected*
                //   ones, like NullPointerException.
                // You'd also want to log the generated input that produced the exception.
                System.out.println("logging: ...");
            }
        }
    }

    private Collection<Double> generate(int length) {
        Set<Double> points = new HashSet<>();
        for(int i=0;i<length;i++) {
            // Math.random() return a random double on [0,1]
            // This is a newer way that's a bit easier:
            points.add(tlr.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE));
        }
        System.out.println("generated: "+points);
        return points;
    }

    private LinearBasedNearest fuzzTestBuild(Collection<Double> inputCollection) {
        return new LinearBasedNearest(inputCollection);
    }

    private void fuzzTestQuery(LinearBasedNearest lb, double query, int num) {
        lb.nearest(query, num);
    }


}

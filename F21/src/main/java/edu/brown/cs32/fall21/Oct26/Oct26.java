package edu.brown.cs32.fall21.Oct26;

import java.util.HashSet;
import java.util.Set;

public class Oct26 {
    public static void main(String[] args) {
        //demo();
        Fuzzer.demoFuzz(10);
    }

    private static void demo() {
        Set<Double> points = new HashSet<>();
        for(double value = -100; value <=100; value++) {
            points.add(value);
        }
        LinearBasedNearest lb = new LinearBasedNearest(points);
        System.out.println(lb.nearest(0, 5));
    }
}

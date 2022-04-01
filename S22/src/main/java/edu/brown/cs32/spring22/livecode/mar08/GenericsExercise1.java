package edu.brown.cs32.spring22.livecode.mar08;

import java.util.Collection;
import java.util.HashSet;

public class GenericsExercise1 {
    public static void main(String[] args) {
        Collection<Number> someNums = new HashSet<>();
        someNums.add(1);    // setup (works)
        someNums.add(1.5);  // setup (works)
        someNums.add(null); // setup (works)

        // Bounded wildcard: any subtype of Number
        Collection<? extends Number> someNums2 = new HashSet<>();
        // Which of these will be OK?
        //someNums2.add(1);       // ?
        //someNums2.add(1.5);     // ?
        someNums2.add(null);    // ?
        someNums2 = someNums;   // ?

        System.out.println(someNums2);
    }
}

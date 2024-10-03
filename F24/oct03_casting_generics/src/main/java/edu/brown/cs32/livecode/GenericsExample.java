package edu.brown.cs32.livecode;

import java.util.Collection;
import java.util.HashSet;

public class GenericsExample {
    public static void main(String[] args) {
        demo();
    }

    static Number sumAll(Collection<Number> numbers) {
        int sum = 0;
        for(Number num : numbers) {
            sum += num.intValue(); // "may involve rounding or truncation"
        }
        return sum;
    }

    static void demo() {
        // Part 1

        Collection<Number> someNums = new HashSet<>();
        //Number aNumber = sumAll(someNums);    // ?
        //Integer anInteger = sumAll(someNums); // ?
        //Object anObject = sumAll(someNums);   // ?


        // Part 2

        //Collection<Number> someNums = new HashSet<>();
        Collection<Integer> someInts = new HashSet<>();
        Collection<Object> someObjects = new HashSet<>();

        //sumAll(someNums);     // ?
        //sumAll(someInts);     // ?
        //sumAll(someObjects);  // ?


        // Part 2.5 (Why would Java do this?)









        // Part 3

        //sumAll_improved(someNums);     // ?
       // sumAll_improved(someInts);     // ?
       // sumAll_improved(someObjects);  // ?

    }









    static Number sumAll_improved(Collection<? extends Number> numbers) {
        int sum = 0;
        for(Number num : numbers) {
            sum += num.intValue(); // "may involve rounding or truncation"
        }
        return sum;
    }

}
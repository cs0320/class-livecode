package edu.brown.cs32.spring22.Mar01;

import java.util.Collection;
import java.util.HashSet;

public class GenericsExercise {

    public static void main(String[] args) {
        Collection<Number> someNums = new HashSet<>();
        //Number aNumber = sumAll(someNums);    // ?
        //Integer anInteger = sumAll(someNums); // ?
        //Object anObject = sumAll(someNums);   // ?

        ////////////////////////////////////////////////////////////

        Collection<Integer> someInts = new HashSet<>();
        Collection<Object> someObjects = new HashSet<>();

        //sumAll(someNums);     // ?
        //sumAll(someInts);     // ?
        //sumAll(someObjects);  // ?

        ////////////////////////////////////////////////////////////

        // Problematic:

        // <fill: a line>
        someNums.add(Math.PI); // adding a Number to a collection of Numbers
        for(int i : someInts) {
            System.out.println(Integer.numberOfLeadingZeros(i));
        }


        ////////////////////////////////////////////////////////////





        // Last example, very important:
        
        someNums.add(1);    // setup (works)
        someNums.add(1.5);  // setup (works)


        Collection< ? extends Number> someNums2 = new HashSet<>();  // ?
        //someNums2.add(1);       // ?
        //someNums2.add(1.5);     // ?
        //someNums2.add(null);    // ?
        //someNums2 = someNums;   // ?
    }

    static Number sumAll(Collection<Number> numbers) {
        int sum = 0;
        for(Number num : numbers) {
            sum += num.intValue(); // "may involve rounding or truncation"
        }
        return sum;
    }
}

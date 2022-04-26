package edu.brown.cs32.fall21.Sep30;

import java.util.*;

public class Sep30 {

    static Number sumAll(Collection<Number> numbers) {
        int sum = 0;
        for(Number num : numbers) {
            sum += num.intValue();
        }
        return sum;
    }

    public static void main(String[] args) {
        // (Object > Number > Integer)
        Collection<Number> someNums = new HashSet<>();

        // Q: what should be able to work to store the return type in?
        // Number aNumber = sumAll(someNums);    // ?
        // Integer anInteger = sumAll(someNums); // ?
        // Object anObject = sumAll(someNums);   // ?


        // Q: what should be able to work as an argument to sumAll1?
        List<Integer> someInts = new ArrayList<>();
        Collection<Object> someObjects = new HashSet<>();
        sumAll(someNums);     // ?
        //sumAll(someInts);     // ?
        //sumAll(someObjects);  // ?

//        List<Number> someNums2 = someInts; // <-- forbidden
//        someNums2.add(Math.PI);
//        int pi2 = someInts.get(0);
//        // ...

        someNums.add(2);
        someNums.add(2.5);

        Collection<? extends Number> c = new HashSet<>();
        //c.add(Integer.valueOf(1));
        //c.add(Double.valueOf(1.5));
        c = someNums;
        c.add(null);
    }
    
}

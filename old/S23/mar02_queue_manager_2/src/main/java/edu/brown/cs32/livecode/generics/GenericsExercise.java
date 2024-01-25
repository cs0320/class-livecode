package edu.brown.cs32.livecode.generics;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * This class contains a demo script of exercises involving generics.
 * Use it to understand the Liskov Substitution Principle, and how
 * wildcards work in Java generics. (The LSP is used in many other places,
 * not just in Java!)
 */
public class GenericsExercise {

    /**
     * Sum the given numbers.
     *   (Question: is there anything dangerous about how I'm rounding here?)
     * @param numbers numbers to add together
     * @return the sum of the given numbers (possibly subject to rounding)
     */
    static Number sumAll(Collection<Number> numbers) {
        int sum = 0;
        for(Number num : numbers) {
            sum += num.intValue(); // "may involve rounding or truncation"
        }
        return sum;
    }


    public static void main(String[] args) {

        ////////////////////////////////////////////////////////////
        // Exercise 1 and 2
        ////////////////////////////////////////////////////////////

        Collection<Number> someNums = new HashSet<>();
        Collection<Integer> someInts = new HashSet<>();
        Collection<Object> someObjects = new HashSet<>();

        // Will these compile?
//        sumAll(someNums);     // ?
//        sumAll(someInts);     // ?
//        sumAll(someObjects);  // ?

        // Will these compile?
//        Collection<Number> someNums = new HashSet<>();
//        Number aNumber = sumAll(someNums);    // ?
//        Integer anInteger = sumAll(someNums); // ?
//        Object anObject = sumAll(someNums);   // ?

        // QUESTION: why do you think Java forbids what it forbids?
        //   what is the type system protecting you from?









        ////////////////////////////////////////////////////////////
        // Explanation of what can go wrong
        ////////////////////////////////////////////////////////////


        // if Collection<Integer> was a subtype of Collection<Number>
        // we'd be able to run this line:
        //someNums = someInts;
        // and then do this:
        someNums.add(Math.PI); // adding a Number to a collection of *Numbers*
        for(int i : someInts) {
            // But it would be the same collection, and thus we could work with
            //   it as if it contained only Integers:
            System.out.println(Integer.numberOfLeadingZeros(i));
            // ^ but this would cause an exception! Math.PI is not an Integer!
        }





        ////////////////////////////////////////////////////////////
        // Exercise 3: wildcards
        ////////////////////////////////////////////////////////////

        someNums.add(1);    // setup (works)
        someNums.add(1.5);  // setup (works)


        // This is a collection of SOME TYPE that extends Number.
        // Vitally, this is one type only. It just doesn't need to be Number.
        Collection< ? extends Number> someNums2 = new HashSet<>();
        //someNums2.add(1);       // ?
        //someNums2.add(1.5);     // ?
        //someNums2.add(null);    // ?
        //someNums2 = someNums;   // ?










        ////////////////////////////////////////////////////////////
        // So how are wildcards useful AT ALL?
        ////////////////////////////////////////////////////////////

        // The problem was that Java's type system won't say "I see that you're
        // "only adding Integers (or Doubles) to the collection." This is
        // reasonable! The collection must be of _some_ single type. And you
        // might write more code that adds different values...

        // The power is in the flexibility wildcards grant the _caller_.
        Collection<Integer> someInts2 = List.of(1, 2, 3);
        Collection<Double> someDoubles2 = List.of(4.4, 5.5, 6.6);
        doSomethingWithNumbers(someInts2);
        doSomethingWithNumbers(someDoubles2);

    }

    // NOTE: this would not work if the method took a Collection<Number>
    public static void doSomethingWithNumbers(Collection<? extends Number> nums) {
        System.out.println("These are numbers, and I can work with them as numbers: "+nums);
        // ...but I have no guarantees what kind they are, beyond that.
    }

}

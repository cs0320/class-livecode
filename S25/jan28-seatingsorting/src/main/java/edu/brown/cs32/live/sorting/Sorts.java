package edu.brown.cs32.live.sorting;

import java.util.Comparator;
import java.util.List;

/**
 * Helper class, not intended to be instantiated, that contains helper methods.
 */
public class Sorts {
    private Sorts() {}

    /**
     * Runs an inefficient but easy-to-write-with-for-loops sorting algorithm on the input.
     * The given list will be modified in place, thus the original ordering will be lost.
     *
     * NOTE: "static" methods can be called without instantiating the class. Great for helper methods!
     *
     * NOTE: the type parameter before the return type: this is how you declare a method-level
     * type parameter. (The *class* is not generic, but the *static method* is.)
     *
     * THERE IS A BUG IN THIS IMPLEMENTATION! See the TestSorts.java file.
     *
     * @param <T> the type of object this list contains
     */
    public static <T> void bubbleSort(List<T> lst, Comparator<T> howToCompare) {

        // Flashback to Tim's 1990's CS intro!
        // ...is this correct? Hmm...

        for(int ii=0;ii<lst.size();ii++) {
            for(int jj=ii+1; jj<lst.size();jj++) {
                if(howToCompare.compare(lst.get(ii), lst.get(jj)) < 0) {
                    lst.set(ii, lst.get(jj));
                    lst.set(jj, lst.get(ii));
                }
            }
        }
    }

}

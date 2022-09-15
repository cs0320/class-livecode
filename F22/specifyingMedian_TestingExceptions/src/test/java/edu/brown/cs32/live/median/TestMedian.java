package edu.brown.cs32.live.median;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/*
   JUnit 4 -> 5 reminder

   Should we test median on the empty list? Look at the Javadoc.

 */

import java.util.Comparator;
import java.util.List;

/**
 * Yes, in practice we'd want more tests than just these 3.
 */
public class TestMedian {

    @Test
    public void testMedianEmpty() {
        // We didn't return null; how can we check for an exception?
        // Need to pass a _function_ as the second argument.
        assertThrows(
                IllegalArgumentException.class,
                () -> Utilities.median5(null, null, null));
        // This WILL NOT WORK! The exception will be thrown before assertThrows has a chance to set up.
//        assertThrows(
//                IllegalArgumentException.class,
//                Utilities.median5(null, null, null));

        // The () -> ... is a modern Java version of defining a new class extending Executable
        // or the more verbose, but inline...
//        assertThrows(
//                IllegalArgumentException.class,
//                new Executable() {
//                    @Override
//                    public void execute() {
//                        Utilities.median5(null, null, null);
//                    }
//                });
        // That took so much work and space to write. Let's just use () -> ... from now on.

        List<Integer> emptyIntList = List.of();
        assertThrows(
                IllegalArgumentException.class,
                () -> Utilities.median5(emptyIntList,
                        Comparator.naturalOrder(),
                        (lst) -> (lst.get(0) + lst.get(1)) / 2));

    }

    @Test
    public void testMedianEven() {
        // NOTE: median5 works with a generic type T
        //  But *here*, where we create a concrete list,
        //  we know what type T is for this test
        List<Integer> input = List.of(1, 7, 14, 2);
        // Don't try List<T> input = ...

        // NOTE: Generics can cause some annoyance in testing.
        //   E.g., here, JUnit4 will say it isn't sure whether to apply
        //   assertEquals(long, long) or assertEquals(Object, Object)
        //   Both /could/ work. Fixes:
        //      * update to JUnit 5
        //      * use Integer.valueOf((2+7)/2) to inform the type system.
        assertEquals((2+7)/2, Utilities.median5(input,
                        Comparator.naturalOrder(),
                        (lst) -> (lst.get(0) + lst.get(1)) / 2));
    }

    @Test
    public void testMedianOdd() {
        List<Integer> input = List.of(1, 7, 14);
        assertEquals(7, Utilities.median5(input,
                Comparator.naturalOrder(),
                (lst) -> (lst.get(0) + lst.get(1)) / 2));
    }

}

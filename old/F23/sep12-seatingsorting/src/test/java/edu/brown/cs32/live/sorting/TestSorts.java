package edu.brown.cs32.live.sorting;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the bubble-sort implementation in the Sorts class.
 * Note that we are *NOT* testing how bubble sort works with the diplomatic ordering.
 * Instead, we'll just use the natural ordering that exists on naturally comparable objects.
 */
public class TestSorts {

    @Test
    public void testBubbleEmpty() {
        List<Integer> lst = new ArrayList<>(List.of());
        // NOTE: Comparator.naturalOrder() saves us from having to write a comparator strategy for integers.
        // If you mouse over it, you'll see a fairly complex generic type. We'll cover that in a future class.
        Sorts.bubbleSort(lst, Comparator.naturalOrder());
        assertEquals(0, lst.size());
    }

    // NOTE: Run "with coverage" to see line-coverage info.
    // Are there any pieces of the code that I haven't yet exercised?
    // (We're looking for at least 50% line coverage for this sprint, but
    //  only for *your* classes, not the kd-tree stuff.)
}






////////////////////////////
// *** SPOILERS BELOW *** //
////////////////////////////






/*
    @Test
    public void testBubbleFiveDifferentReverse() {
        List<Integer> lst = new ArrayList<>(List.of(5,4,3,2,1));
        // NOTE: Comparator.naturalOrder() saves us from having to write a comparator strategy for integers.
        // If you mouse over it, you'll see a fairly complex generic type. We'll cover that in a future class.
        Sorts.bubbleSort(lst, Comparator.naturalOrder());
        assertEquals(List.of(1,2,3,4,5), lst);
    }

    @Test
    public void testBubbleFiveMixedOneDuplicate() {
        List<Integer> lst = new ArrayList<>(List.of(5,1,3,2,3));
        // NOTE: Comparator.naturalOrder() saves us from having to write a comparator strategy for integers.
        // If you mouse over it, you'll see a fairly complex generic type. We'll cover that in a future class.
        Sorts.bubbleSort(lst, Comparator.naturalOrder());
        assertEquals(List.of(1,2,3,3,5), lst);
    }

*/
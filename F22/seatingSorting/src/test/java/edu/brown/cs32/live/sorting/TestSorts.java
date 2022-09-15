package edu.brown.cs32.live.sorting;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test the bubble-sort implementation in the Sorts class.
 * Note that we are *NOT* testing how bubble sort works with the diplomatic ordering.
 * Instead, we'll just use the natural ordering that exists on naturally comparable objects.
 */
public class TestSorts {

    // NOTE: this is JUnit 4. That's kinda out of date; we could consider using JUnit 5.
    // Let's look at our Maven configuration in pom.xml for this project.

    @Test
    public void testBubbleEmpty() {
        List<Integer> lst = new ArrayList<>(List.of());
        // NOTE: Comparator.naturalOrder() saves us from having to write a comparator strategy for integers.
        // If you mouse over it, you'll see a fairly complex generic type. We'll cover that in a future class.
        Sorts.bubbleSort(lst, Comparator.naturalOrder());
        assertEquals(0, lst.size());
    }

    @Test
    public void testAnother() {
        List<Integer> lst = new ArrayList<>(List.of(1,3,2,5,2));
        // NOTE: Comparator.naturalOrder() saves us from having to write a comparator strategy for integers.
        // If you mouse over it, you'll see a fairly complex generic type. We'll cover that in a future class.
        Sorts.bubbleSort(lst, Comparator.naturalOrder());
        assertEquals(List.of(1,2,2,3,5), lst);
    }


    // NOTE: Run "with coverage" to see line-coverage info.
    // Are there any pieces of the code that I haven't yet exercised?
    // (We're looking for at least 50% line coverage for this sprint, but
    //  only for *your* classes, not the kd-tree stuff.)
}

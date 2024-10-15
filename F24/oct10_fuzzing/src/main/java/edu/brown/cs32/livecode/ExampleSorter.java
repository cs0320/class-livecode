package edu.brown.cs32.livecode;

import java.util.List;

public abstract class ExampleSorter {
    /** This class is not intended to be instantiated. */
    private ExampleSorter() {}

    /**
     * Runs an implementation of bubble sort on the input list. The input list
     * will be modified in place, and sorted in ascending order by the natural
     * ordering of its element type (which must implement Comparable).
     *
     * @param items the list to sort in place
     * @param <T>   the type of elements in the list
     */
    public static <T extends Comparable<T>> void bubbleSort(List<T> items) {
        for (int idx1 = 0; idx1 < items.size(); idx1++) {
            for (int idx2 = idx1 + 1; idx2 < items.size(); idx2++) {
                if (items.get(idx2).compareTo(items.get(idx1)) < 0) {
                    T swap = items.get(idx2);
                    items.set(idx2, items.get(idx1));
                    items.set(idx1, swap);
                }
            }
        }
    }
}

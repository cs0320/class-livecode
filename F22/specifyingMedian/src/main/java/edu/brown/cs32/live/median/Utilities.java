package edu.brown.cs32.live.median;

import java.util.Collection;

/**
 * A utility class containing only static methods. Not intended to be instantiated.
 */
public class Utilities {
    private Utilities() {}

    /**
     * Compute the median of a collection of elements.
     * NOTE: you may have written a method like this before, but taken a *list* as input. But why do we need a list?
     * Lists impose an ordering that doesn't matter to median calculation. Sets wouldn't allow duplicates.
     * In Java, a Collection is a generalization of List and Set. We just want a bag of elements, so Collection works.
     * NOTE: recall that we say <T> before the return type to tell Java this *method* is generic, even if its class is not.
     * @param elements the elements to find the median of
     * @param <T> the type of the elements we're finding the median of
     * @return the median of the collection
     */
    public static <T> T median(Collection<T> elements) {
        // How can we make this work?
        return null;
    }
}

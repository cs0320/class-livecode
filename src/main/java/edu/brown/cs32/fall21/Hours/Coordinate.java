package edu.brown.cs32.fall21.Hours;

import java.util.Collections;
import java.util.List;

/**
 * Mock of a generic coordinate class that supports variable dimension
 * Coordinate type must be comparable vs. itself.
 *
 * @param <T> The type of the coordinates
 */
class Coordinate<T extends Comparable<T>> {
    private final List<T> args;
    Coordinate(List<T> args) {
        // Convenient way to make a defensive copy
        //  (either from input or for output!)
        // provided that the result doesn't need modification
        // BTW this uses the proxy pattern to wrap the
        // input list to protect it :-) If curious, Google for
        //  the source code of "class UnmodifiableList" in Java.
        this.args = Collections.unmodifiableList(args);
    }

    /**
     * Extract the dim-th component from this coordinate
     * @param dim The desired coordinate index
     * @return The component at index dim
     */
    T getArg(int dim) {
        return args.get(dim);
    }

    @Override
    public String toString() {
        return args.toString();
    }

}

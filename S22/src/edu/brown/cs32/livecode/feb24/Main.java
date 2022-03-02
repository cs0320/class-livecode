package edu.brown.cs32.livecode.feb24;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Wrapper for a pair of values. The values do not need to
 * be the same type. Any two pairs having the same value
 * types should be comparable, first by the first ("left")
 * element, and then by the second ("right") element in case
 * of equal left elements.
 *
 * In a real setting, this class would be in its own file.
 *
 * @param <A> Type of the first ("left") value
 * @param <B> Type of the second ("right") value
 */
class Pair<A extends Comparable<A>,
           B extends Comparable<B>> implements Comparable<Pair<A,B>> {
    private final A left;
    private final B right;

    public Pair(A a, B b) {
        left = a;
        right = b;
    }

    public A getLeft() {
        return left;
    }

    public B getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "(" + left + " . " + right + ")";
    }

    @Override
    public int compareTo(Pair<A, B> other) {
        int leftCompare = this.left.compareTo(other.left);
        if(leftCompare != 0) return leftCompare;
        int rightCompare = this.right.compareTo(other.right);
        return rightCompare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false; // can't instanceof generic
        Pair<?, ?> otherPair = (Pair<?, ?>) o; // don't care about the exact types
        return Objects.equals(left, otherPair.left) &&
                Objects.equals(right, otherPair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }


    /**
     * This method exists to make sure we can actually create and use pairs.
     * Normally, such a class wouldn't have a demo() method, but we want to
     * more easily run the in-class exercise.
     */
    public static void demo() {
        List<Pair<Integer, String>> lst =
                Arrays.asList(
                        new Pair<>(4, "potato"),
                        new Pair<>(2, "apple"),
                        new Pair<>(7, "pizza"));

        Collections.sort(lst);
        System.out.println(lst);
        // Requirement: get this to run.
    }
}



public class Main {
    public static void main(String[] args) {
        Pair.demo();
    }
}


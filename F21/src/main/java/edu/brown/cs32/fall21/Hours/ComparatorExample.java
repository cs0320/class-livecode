package edu.brown.cs32.fall21.Hours;

import java.util.*;

/**
 * 2 ways of building a generic comparator for KD Tree nodes
 * (Mock example built to show a few SPECIFIC ideas concisely!
 *    Read and understand; don't just cut+paste or issues may occur.)
 */
public class ComparatorExample {
    public static void main(String[] args) {
        // Convenient way to manufacture test lists: Arrays.asList
        Coordinate<Integer> coordinate1 = new Coordinate<>(Arrays.asList(1, -2, 3));
        Coordinate<Integer> coordinate2 = new Coordinate<>(Arrays.asList(4, -5, 6));
        Coordinate<Integer> coordinate3 = new Coordinate<>(Arrays.asList(-1, 2, -3));
        Coordinate<Integer> coordinate4 = new Coordinate<>(Arrays.asList(-2, 4, -6));
        // ^ Note that at this point, in main, we *know* that we're
        // creating coordinates with Integers, and so we can write
        // Node<Integer>. But elsewhere, we use a type variable.
        List<Coordinate<Integer>> coordinates = new ArrayList<>();
        coordinates.add(coordinate1); coordinates.add(coordinate2);
        coordinates.add(coordinate3); coordinates.add(coordinate4);
        System.out.println(coordinates);

        // Way 1: Comparator.comparing factory method
        coordinates.sort(buildComparator(0));
        System.out.println(coordinates);
        coordinates.sort(buildComparator(1));
        System.out.println(coordinates);

        // Way 2: Our own Comparator class
        coordinates.sort(new CoordinateComparator<>(0));
        System.out.println(coordinates);
        coordinates.sort(new CoordinateComparator<>(1));
        System.out.println(coordinates);

    }

    // Static factory method
    // Make this builder generic over T, even though this class isn't
    // with the method-level type variable T:
    static <T extends Comparable<T>> Comparator<Coordinate<T>> buildComparator(int dim) {
        // Comparator.comparing(...) builds a Comparator;
        // it takes 2 arguments:
        //   (1) a key *extractor* (here, get the n^th component)
        //   (2) a key *comparator* (here, use the defined compareTo)
        // Alternatively, we could write our own class that implements
        //   the Comparator interface and use an instance of that class here.
        // See below for an example of that working
        return Comparator.comparing(
                        n -> n.getArg(dim),
                        (n1, n2) -> { return n1.compareTo(n2);} );
        // ^^^ Could also use Comparator.naturalOrder as second argument here
        // IntelliJ is suggesting a few alternatives, but I kept this because
        // it's a bit more direct as an example.

        // If you don't like this, note the other option being shown in main()
    }
}
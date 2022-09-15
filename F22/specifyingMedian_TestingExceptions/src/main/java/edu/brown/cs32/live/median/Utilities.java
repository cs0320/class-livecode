package edu.brown.cs32.live.median;

import java.util.*;
import java.util.function.Function;

/**
 * A utility class containing only static methods. Not intended to be instantiated.
 */
public class Utilities {
    private Utilities() {
    }

    /**
     * Compute the median of a collection of elements.
     * NOTE: you may have written a method like this before, but taken a *list* as input. But why do
     * we need a list? Lists impose an ordering that doesn't matter to median calculation. Sets
     * wouldn't allow duplicates. In Java, a Collection is a generalization of List and Set. We just
     * want a bag of elements, so Collection works. NOTE: recall that we say <T> before the return
     * type to tell Java this *method* is generic, even if its class is not.
     *
     * @param elements the elements to find the median of
     * @param <T>      the type of the elements we're finding the median of
     * @return the median of the collection
     */
    public static <T> T median(Collection<T> elements) {
        // How can we make this work?
        // NOTE: I'm using this example for specific teaching purposes. We're going to get deeper
        //   than a normal real-world median method probably gets, but we'll learn things on the way.
        return null;
    }

    ///////////////////////////////////////////////////////
    // Spoilers below: different versions
    ///////////////////////////////////////////////////////


//    public static <T> T median1(Collection<T> elements) {
//        T result;
//        // ???
//        return T;
//    }



//    public static <T> T median2(Collection<T> elements) {
//        T result;
//        List<T> asList = new ArrayList<>(elements);
//        Collections.sort(asList);
//        if(asList.size() % 2 == 1) {
//            // odd number of elements: take the middle element
//            result = asList.get(asList.size()/2);
//        } else {
//            // even number of elements: take the average of middle 2 elements
//            // TODO: is this always a safe operation? (hidden 3rd problem!)
//            result = (asList.get(asList.size()/2) + asList.get(asList.size()/2 + 1)) / 2;
//        }
//        return result;
//    }



    ///////////////////////////////////////////////////////
    // Spoilers below: let's deal with one problem at a time
    ///////////////////////////////////////////////////////

    /**
     * Compute the median of the given collection
     * @param elements the collection to find the median of
     * @param <T> the type of elements in the collection
     * @return the median element
     * @throws IllegalArgumentException if given null or an empty collection
     *
     * NOTE: IllegalArgumentException is "unchecked". The type system won't /make/ you declare it.
     * It indicates a failure of preconditions that have (hopefully) been documented. The caller
     * _can_ catch it and recover using a normal try/catch. This is a deep topic, and there's a lot
     * of internet debate. Ideally, more to say in the future class.
     *
     * NOTE: consider how we might /test/ this behavior, and whether we should.
     * (Note our context is /different/ here from prior courses. We have real developers as users.)
     */
//    public static <T> T median3(Collection<T> elements) throws IllegalArgumentException {
//        if(elements == null)
//            throw new IllegalArgumentException("median expected a collection, given null");
//        if(elements.size() < 1)
//            throw new IllegalArgumentException("median expected a nonempty collection");
//        T result;
//        List<T> asList = new ArrayList<>(elements);
//        Collections.sort(asList);
//        if(asList.size() % 2 == 1) {
//            // odd number of elements: take the middle element
//            result = asList.get(asList.size()/2);
//        } else {
//            // even number of elements: take the average of middle 2 elements
//            result = (asList.get(asList.size()/2) + asList.get(asList.size()/2 + 1)) / 2;
//        }
//        return result;
//    }

    /*
        Problem: we have no way to know how to compare elements of type T.
        Two solutions: require a comparator, or require T implement Comparable.
          I like a comparator here! It's more flexible, and user can always pass the natural comp.
     */
//    public static <T> T median4(Collection<T> elements, Comparator<T> comp) throws IllegalArgumentException {
//        if(elements == null)
//            throw new IllegalArgumentException("median expected a collection, given null");
//        if(elements.size() < 1)
//            throw new IllegalArgumentException("median expected a nonempty collection");
//        T result;
//        List<T> asList = new ArrayList<>(elements);
//        Collections.sort(asList, comp); // just compare the way the caller says to compare
//        if(asList.size() % 2 == 1) {
//            // odd number of elements: take the middle element
//            result = asList.get(asList.size()/2);
//        } else {
//            // even number of elements: take the average of middle 2 elements
//            result = (asList.get(asList.size()/2) + asList.get(asList.size()/2 + 1)) / 2;
//        }
//        return result;
//    }

    /*
    Problem: we have no way of knowing how to _combine_ "middle elements"
    This is more complex. Lots of options. Traditionally, we'd have numbers and + would "just work".

      * Might want to say "And T is some kind of Number"
          ...except this doesn't quite work, because the compiler needs more info.
          ..."T is some kind of Integer" is way too specific, and STILL doesn't work.
          ...and aren't we trying to be _generic_ here? What if the elements are records?
      * change the type signature to return a collection of median candidates; let the caller decide
          ...better, but might be considered unnatural in a math library! All fixes might seem
             unnatural, so ideally we'd have some wrappers that just "work" for ints, doubles, etc.
      * require a strategy that takes the collection of candidates and returns the median
          ...this is the one I pick here, give me an ElementSelector of your choice...
    */

    // You might want to create a new interface here, but it's even easier:
    interface ElementSelector<T> {
        T select(Collection<T> elements);
    }

    public static <T> T median5(Collection<T> elements, Comparator<T> comp,
                                Function<List<T>, T> selector) throws IllegalArgumentException {
        if(elements == null)
            throw new IllegalArgumentException("median expected a collection, given null");
        if(elements.size() < 1)
            throw new IllegalArgumentException("median expected a nonempty collection");
        T result;
        List<T> asList = new ArrayList<>(elements);
        asList.sort(comp); // just compare the way the caller says to compare
        if(asList.size() % 2 == 1) {
            // odd number of elements: take the middle element
            result = asList.get(asList.size()/2);
        } else {
            // even number of elements: take the average of middle 2 elements
            result = selector.apply(List.of(asList.get(asList.size()/2),
                                            asList.get(asList.size()/2 - 1)));
        }
        return result;
    }
}

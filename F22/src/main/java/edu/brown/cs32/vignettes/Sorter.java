package edu.brown.cs32.vignettes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class to demonstrate dependency injection in class and in TA camp minilecture
 * To sort a list, a caller can either:
 *   (1) call the static "sorted" method directly with a comparator and list to sort; or
 *   (2) create a Sorter instance, passing a comparator to its constructor, and
 *       then call the Sorter's "sort" method with only a list to sort.
 * Note that, via OOP, we've effectively turned a 2-argument method into a 1-argument method.
 */
public class Sorter<T> {
    private final Comparator<T> comp;

    /**
     * Create a Sorter object that's prepared to sort any given list of "T" elements
     * using the provided comparator.
     * @param comp the comparator this instance of Sorter should use
     */
    Sorter(Comparator<T> comp) {
        this.comp = comp;
    }

    /**
     * Sort the given list according to the comparator that this object received
     * in its constructor.
     *
     * The resulting list is guaranteed to be stable (equal elements are not re-ordered).
     * Moreover, unlike Collections.sort, this method returns a *new list*
     * containing the results, rather than modifying the input list.
     *
     * DEMO NOTES:
     *    - This method doesn't take a comparator. Why should it? That strategy
     *      was already provided when the object was created. (Sometimes, especially
     *      in a testing context, this idea will be called "dependency injection".)
     *
     * @param lst the list to sort
     * @return a new list instance containing the same elements as lst, but sorted according to comp
     */
    List<T> sort(List<T> lst) {
        List<T> result = new ArrayList<>(lst);
        Collections.sort(result, comp); // modifies the list argument!
        return result;
    }


    /**
     * Sort a list according to the provided comparator. The resulting
     * list is guaranteed to be stable (equal elements are not re-ordered).
     * Moreover, unlike Collections.sort, this method returns a *new list*
     * containing the results, rather than modifying the input list.
     *
     * DEMO NOTES:
     *    - This is a static method, and so not tied to any instance of Sorter.
     *      As a consequence, although we could use "T" instead of "S" for the
     *      type variable, it would be *A DIFFERENT T*. We try to avoid ambiguity.
     *    - the "S" type variable right after "static" in the method header is how you introduce
     *      a method-level type variable in Java. We need to do this, since "T" from
     *      the class isn't accessible to a static method.
     * @param comp the comparator to use
     * @param lst the list to sort
     * @param <S> the type of elements of the given list
     * @return a new list instance containing the same elements as lst, but sorted according to comp
     */
    public static <S> List<S> sorted(Comparator<S> comp, List<S> lst) {
        return new Sorter<S>(comp).sort(lst);
    }
}

package edu.brown.cs32.spring22.livecode.mar08;

import java.util.*;

/**
 * BTW:
 * Example of per-method generic type variable
 */
class GenericsExercise2 {

    // Example:
    public static <X extends Comparable<X>> void
    filterLowerThan(Collection<X> a, X value) {
        a.removeIf(b -> b.compareTo(value) < 0);
    }
}


// type variables don't need to be one letter
class Stack<E> {
    List<E> actual = new ArrayList<>();

    public void push(E element) {
        actual.add(0, element);
    }
    public E pop() {
        E element = actual.get(0);
        actual.remove(0);
        return element;
    }

    /////////////////////////////////////////
    // The "PECS" Rule (I'll explain this...)
    /////////////////////////////////////////

    // What's wrong with this method's type?
    // elements is a PRODUCER of instances of E
    public void pushAll(Set<? extends E> elements) {
        for(E e : elements)
            push(e);
    }
    // What's wrong with this method's type?
    // destination is a CONSUMER of instances of E
    public void popAll(Collection<? super E> destination) {
        for(E e : actual) {
            destination.add(e);
            // bug that doesn't matter: forgot to remove
        }
    }

    public static void main(String[] args) {
        Set<Number> nums = new HashSet<>();
        Set<Integer> ints = new HashSet<>();
        Stack<Number> stack = new Stack<>();
        stack.pushAll(nums);
        stack.pushAll(ints);
        stack.popAll(nums);
        //stack.popAll(ints);


    }
}

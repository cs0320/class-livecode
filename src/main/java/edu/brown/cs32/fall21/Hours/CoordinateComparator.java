package edu.brown.cs32.fall21.Hours;

import java.util.Comparator;

/**
 * Example home-made Comparator class. Instantiate with the dimension
 * you want to compare by. Note the generic types: T is the type of the
 * individual components of a coordinate, but this class *implements*
 * a Comparator over *Coordinates* that use T, not T itself. Generics
 * can be pretty flexible!
 * @param <T>
 */
class CoordinateComparator<T extends Comparable<T>> implements Comparator<Coordinate<T>> {
    private final int dim;

    CoordinateComparator(int dim) {
        this.dim = dim;
    }

    @Override
    public int compare(Coordinate<T> o1, Coordinate<T> o2) {
        return o1.getArg(dim).compareTo(o2.getArg(dim));
    }
}

package edu.brown.cs32.points;

/**
 * A point in 2-dimensional space.
 */
public class Point {
    final double x;
    final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() { return x; }
    public double y() { return y; }
}

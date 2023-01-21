package edu.brown.cs32.whyDefineEquals;

/**
 * A point in 2-dimensional space
 * (with the equals method defined)
 */
public class PointWithEquals {
    final double x;
    final double y;

    public PointWithEquals(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() { return x; }
    public double y() { return y; }

    // This was auto-generated by IntelliJ
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointWithEquals that = (PointWithEquals) o;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0;
    }
}

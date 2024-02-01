import org.junit.jupiter.api.Test;
import edu.brown.cs32.points.Point;
import edu.brown.cs32.points.PointWithBoth;
import edu.brown.cs32.points.PointWithEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Ignore IntelliJ's warning highlights. The point is to understand
 * why we get those warnings!
 */
public class TestDefiningEqualityMatters_PREP {

    @Test
    public void equalityMattersForLists() {
        // For objects, we might have two (effectively) identical objects that aren't ==:
        Point originA = new Point(0, 0);
        Point originB = new Point(0, 0);
        assertFalse(originA == originB);

        // We can write code that checks all their fields (if they're accessible directly or via getters):
        assertTrue(originA.x() == originB.x() && originA.y() == originB.y());
        // This becomes a huge bother when there are many fields, and it relies on their values being accessible.

        // Worse, data structures rely on equality to decide (among other things) membership.
        List<Point> points = List.of(originA);
        // They are different objects! originB *isn't there*.
        assertFalse(points.contains(originB));

        // Collections don't actually (usually) use == though; they use the .equal() methods of their contents.
        // We didn't redefine .equals() in the Point class, though...

        PointWithEquals originC = new PointWithEquals(0, 0);
        PointWithEquals originD = new PointWithEquals(0, 0);

        // Now that we've defined .equals() properly...
        assertTrue(originD.equals(originC));
        List<PointWithEquals> betterPoints = List.of(originC);
        assertTrue(betterPoints.contains(originD));
    }

    @Test
    public void equalityMattersForSets() {
        // Let's try that example again, except with a HashSet:
        Set<PointWithEquals> betterPoints = new HashSet<>();
        PointWithEquals originC = new PointWithEquals(0, 0);
        PointWithEquals originD = new PointWithEquals(0, 0);
        betterPoints.add(originC);
        // ...Why is this returning false, when the *List* in the previous example returned true?
        assertFalse(betterPoints.contains(originD));
        // because checking for membership in a HashSet happens in two stages:
        //  (1) what do we have in the set with the same hash value as the object we're searching for?
        //  (2) are any of them .equals() to the object we're searching for?
        // and we didn't redefine hashCode() for our Point class, either.
        // In fact. if we add both, the set will report it contains 2 different elements:
        betterPoints.add(originD);
        assertTrue(betterPoints.size() == 2);

        // This will work properly:
        Set<PointWithBoth> evenBetterPoints = new HashSet<>();
        PointWithBoth originE = new PointWithBoth(0, 0);
        PointWithBoth originF = new PointWithBoth(0, 0);
        evenBetterPoints.add(originE);
        assertTrue(evenBetterPoints.contains(originF));

    }
}

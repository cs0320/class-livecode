package edu.brown.cs32.fall21.Oct26;

import java.util.*;

/**
 *   Demo of list-based nearest neighbor for 1-dimensional
 *   coordinates (the point here is the testing, not the
 *   multi-dimensional aspect).
 */
public class LinearBasedNearest {
    private final Collection<Double> contents;

    LinearBasedNearest(Collection<Double> contents) {
        this.contents = Collections.unmodifiableCollection(contents);
    }

    private Double dist(double x1, double x2) {
        return Math.abs(x1 - x2); // return double
    }

    public Collection<Double> nearest(double target, int num) {
        List<Double> best = new ArrayList<>(num);

        Comparator<Double> distanceToTargetComparator =
                Comparator.comparing((x) -> dist(x, target));

        for(double candidate : contents) {
            if(best.size() < num) {
                best.add(candidate);
                best.sort(Comparator.naturalOrder());
            } else {
                double toReplace = Collections.max(best, distanceToTargetComparator);
                if(dist(toReplace, target) > dist(candidate, target)) {
                    best.set(best.indexOf(toReplace), candidate);
                    best.sort(Comparator.naturalOrder());
                }
            }
        }
        return best;
    }

}

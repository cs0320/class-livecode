package edu.brown.cs32.fall21.Nov02;

import edu.brown.cs.student.coordinates.Coordinate;
import edu.brown.cs.student.coordinates.KdTree;
import edu.brown.cs.student.coordinates.KeyDistance;
import edu.brown.cs.student.node.Node;
import edu.brown.cs.student.searchAlgorithms.KdTreeSearch;
import edu.brown.cs.student.searchAlgorithms.ListNaiveSearch;
import edu.brown.cs.student.stars.Star;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Nov02 {

    // Convenient way to generate random doubles within a range
    private static final ThreadLocalRandom tlr = ThreadLocalRandom.current();
    private static final int NUM_TRIALS = 1000;

    public static void main(String[] args) {
        // FUZZ TEST

        for(int trial=0; trial<NUM_TRIALS;trial++) {
            // FUZZ TEST: create kd tree using this constructor
            List<Coordinate<Integer>> constellation = generateRandomConstellation();
            KdTree<Integer> t = new KdTree<Integer>(3, constellation);

            // FUZZ TEST: kd-tree nearest-neighbor and radius
            Star target = generateRandomStar(-1);
            KdTreeSearch<Integer> search1 = new KdTreeSearch<>();
            List<Coordinate<Integer>> kdnn = search1.getNearestNeighborsResult(5, target, t.getRoot(), false);
            List<Coordinate<Integer>> kdr = search1.getRadiusSearchResult(1.0, target, t.getRoot(), false);

            List<KeyDistance<Integer>> dists = new ArrayList<>();
            for(int i = 0 ; i<constellation.size();i++) {
                Coordinate<Integer> s = constellation.get(i);
                // Dims are 1 based in this implementation
                double dist = Math.sqrt(
                        Math.pow(s.getCoordinateVal(1), 2) +
                        Math.pow(s.getCoordinateVal(2), 2) +
                        Math.pow(s.getCoordinateVal(3), 2));
                dists.add(new KeyDistance<>(i, dist));
            }
            // FUZZ TEST: naive list-based nearest neighbor
            ListNaiveSearch<Integer> search2 = new ListNaiveSearch<>(dists);
            List<Integer> naivenn = search2.getNaiveNearestNeighbors(5);
            List<Integer> naiver = search2.getNaiveRadiusSearchResult(5);

            if(trial % 100 == 0) {
                System.out.println("visibility: trial "+trial+"; generated: "+t);
            }
        }
    }

    private static List<Coordinate<Integer>> generateRandomConstellation() {
        List<Coordinate<Integer>> contents = new ArrayList<>();
        for(int i=0;i<100;i++) {
            Star star = generateRandomStar(i);
            contents.add(star);
        }
        return contents;
    }

    private static Star generateRandomStar(int i) {
        double rx = tlr.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE);
        double ry = tlr.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE);
        double rz = tlr.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE);
        // QUESTION: should we generate a random name? random id?
        return new Star(i, "Name "+i, rx, ry ,rz);
    }

}

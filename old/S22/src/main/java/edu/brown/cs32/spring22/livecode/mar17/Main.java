package edu.brown.cs32.spring22.livecode.mar17;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    // Convenient way to generate random doubles within a range
    private static final ThreadLocalRandom tlr = ThreadLocalRandom.current();
    private static final int NUM_TRIALS = 10000;

    public static void main(String[] args) {
        for (int trial = 0; trial < NUM_TRIALS; trial++) {
            // FUZZ TEST: random list of 5 stars
            List<Star> constellation = generateRandomConstellation(5);

            // FUZZ TEST: naive list-based nearest neighbor; get 3 nearest
            ListNaiveStarSearch search = new ListNaiveStarSearch(constellation);
            Star target = generateRandomStar(-1);
            int k = 3;
            List<Star> results = search.getNaiveNearestNeighbors(k, target);

            /////////////////////////////////////////////////////////////////////
            // PBT //////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////

            // property 1: number of stars returned == number of stars requested
            if (results.size() != 3) {
                System.err.println("ERROR: number of nodes returned != number of nodes requested");
                System.err.println("       returned: " + results.size() + "; requested: " + k);
                System.exit(1);
            }

            // property 2: all stars returned are in the original set of stars
            for (Star s : results) {
                if (!constellation.contains(s)) {
                    System.err.println("ERROR: some star returned is not in the original set of stars");
                    System.err.println("       star: " + s + "; set: " + constellation);
                    System.exit(1);
                }
            }

            // property 3: for each star in the returned list, there is no star
            //             later in the list that is closer than that star
            for (int i = 0; i < results.size(); i++) {
                Star currentStar = results.get(i);
                for (int j = i + 1; j < results.size(); j++) {
                    Star laterStar = results.get(j);
                    if (laterStar.distance(target) < currentStar.distance(target)) {
                        System.err.println("ERROR: some star returned has a closer star later in the list");
                        System.err.println("       star: " + currentStar + "; later star: " + laterStar);
                        System.exit(1);
                    }
                }
            }


            if (trial % 100 == 0) {
                System.out.println("visibility: trial " + trial);
            }
        }
    }

    private static List<Star> generateRandomConstellation(int size) {
        List<Star> constellation = new ArrayList<>();
        if (size < 0) {
            return constellation;
        }
        for (int i = 0; i < size; i++) {
            Star star = generateRandomStar(i);
            constellation.add(star);
        }
        return constellation;
    }

    private static Star generateRandomStar(int i) {
        double rx = tlr.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE);
        double ry = tlr.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE);
        double rz = tlr.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE);
        return new Star(i, "Name " + i, rx, ry, rz);
    }

}
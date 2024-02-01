package edu.brown.cs32.demo;

import edu.brown.cs32.points.Point;
import edu.brown.cs32.points.PointWithEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Demo playground for exploring equality. Start simple and build from there.
 */
public class Main {
    public static void main(String[] args) {
        String s1 = "hi";
        String s2 = "hi";
        print("s1 == s2: ", s1 == s2);
        print("s1.equals(s2): ", s1.equals(s2));

        String h = "h";
        String i = "i";
        String hi = "hi";
        print("h+i == hi", h+i == hi);

        int five = 5;
        int six = 6;
        int elevenA = five + six;
        int elevenB = 11;
        print("elevenA == elevenB", elevenA == elevenB);
        Integer fiveI = Integer.valueOf(-5000);
        Integer sixI = -6000;
        Integer elevenAI = fiveI + sixI;
        Integer elevenBI = -11000;
        print("elevenAI == elevenBI", elevenAI == elevenBI);
        //print("elevenA == elevenAI", elevenA == elevenAI);

        PointWithEquals origin1 = new PointWithEquals(0,0);
        PointWithEquals origin2 = new PointWithEquals(0,0);
        print("origin1 == origin2", origin1 == origin2);
        print("origin1.equal(origin2)", origin1.equals(origin2));

        origin1.equals(origin1)

        origin1.equals(origin2)
        origin2.equals(origin1)

        List<PointWithEquals> points = List.of(origin1);

        print("origin2 in (origin1)", points.contains(origin2));

        Set<PointWithEquals> setOfPoints = new HashSet<>();
        setOfPoints.add(origin1);
        print("origin2 in (origin1) (Set)", setOfPoints.contains(origin2));
    }

    private static void print(String description, boolean result) {
        System.out.println(description+": "+result);
    }
}

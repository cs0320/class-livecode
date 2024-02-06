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
    static String classHi = "hi";
    public static void main(String[] args) {
        String s1 = "hi";
        //String s2 = classHi;
        String s2 = "h"+"i";
        print("s1 == s2: ", s1 == s2);
        print("s1.equals(s2): ", s1.equals(s2));

        String h = "h";
        String i = "i";
        String hi = h + i;
        print("s1 == hi: ", s1 == hi);
        print("s1.equals(hi): ", s1.equals(hi));

        /*int five= 5;
        int six = 6;
        int elevenA = five+six;
        int elevenB = 11;
        print("elevenA==elevenB: ", elevenA==elevenB);
*/

        Integer five= 1005;
        Integer six = 6;
        Integer elevenB = Integer.valueOf(1011);
        Integer elevenA = five+six;

        print("elevenA==elevenB: ", elevenA==elevenB);
        print("elevenA.equals(elevenB): ",
                elevenA.equals(elevenB));

        /*Point originA = new Point(0,0);
        Point originB = new Point(0,0);
        print("originA.equals(originB): ",
                originA.equals(originB));
*/
        PointWithEquals originA = new PointWithEquals(0,0);
        PointWithEquals originB = new PointWithEquals(0,0);
        print("originA.equals(originB): ",
                originA.equals(originB));

        Set<PointWithEquals> points = new HashSet<>();
        points.add(originA);
        print("points contains originB?",
                points.contains(originB));
    }

    private static void print(String description, boolean result) {
        System.out.println(description+": "+result);
    }
}

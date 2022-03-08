package edu.brown.cs32.livecode.mar08;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StaticMethodDemo {
    public static void hi() {
        System.out.println("hi");
    }
    public static void main(String[] args) {
        StaticMethodDemo m1 = new StaticMethodDemo();
        StaticMethodDemo m2 = new StaticMethodDemo();
        hi(); // don't do this (Tim's preference)
        StaticMethodDemo.hi(); // safe from overriding
        m1.hi(); // don't do this
        m2.hi(); // don't do this

        // useful in factories, e.g.:
        Map<Integer, Integer> aMap = new HashMap<>();
        Map<Integer, Integer> aMap2 = Collections.unmodifiableMap(aMap);

        // useful in utility methods, e.g.
        String s = Arrays.toString(new String[]{"abc", "def"});
        System.out.println(s);
    }
}

package edu.brown.cs32.demo;

/**
 * Demo playground for exploring equality. Start simple and build from there.
 */
public class Main {
    public static void main(String[] args) {
        String s1 = "hi";
        String s2 = "hi";
        print("s1 == s2: ", s1 == s2);
    }

    private static void print(String description, boolean result) {
        System.out.println(description+": "+result);
    }
}

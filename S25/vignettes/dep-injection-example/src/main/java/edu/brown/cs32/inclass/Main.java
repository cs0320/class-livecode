package edu.brown.cs32.inclass;

/**
 * Vignette: dependency injection and proxies
 *
 * How do you know that you aren't a brain in a jar?
 *
 * More directly applicable: how do you manage parallel development of
 * 2 different parts of the same overall application?
 */
public class Main {
    public static void main(String[] args) {
        Environment env = new RealWorld();
        /* This is "dependency injection": the object receives an important
        persistent dependency. In this case, it's the environment through which
        they interact with their world.

        Here, Nim interacts with the *REAL* world.
         */
        Person nim = new Person("Nim", env);
        nim.react();
    }
}
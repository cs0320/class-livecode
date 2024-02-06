package edu.brown.cs32.inclass;

/**
 * Vignette: dependency injection and proxies
 *
 * How do you know that you aren't a brain in a jar?
 */
public class Main {
    public static void main(String[] args) {
        Environment env = new RealWorld();
        /* This is "dependency injection": the object receives an important
        persistent dependency. In this case, it's the environment through which
        they interact with their world.

        Here, Nim interacts with the real world.
         */
        Person nim = new Person("Nim", env);
        nim.react();
    }
}
package edu.brown.cs32.livecode;

/**
 * Challenge!
 */
public class Main {
    public static void main(String[] args) {
        // Java, like most languages, has a "ternary operator":
        double value = Math.random();
        System.out.println(value > 0.5 ? "Hello" : "Goodbye");
        // C ? T : F   will evaluate to T if C is true, otherwise F.

        // We'll be using this a lot once we get to TypeScript, because it makes it
        // convenient to describe conditional rendering in a webapp.

        // Suppose I said: "I want you to write me a static helper method that does
        // *exactly* the same thing as the ternary operator." You might write it
        // something like the static helper method I've defined below.

        // But it doesn't work. Or, to be more accurate, it often will do the same thing
        // that the ternary operator does, but won't always. Why? What goes wrong?
        // (You may assume that the problem isn't about datatypes. I've just
        //  made it double-specific for simplicity.)


    }

    // This won't always be correct. Why not?
    static double ternary(boolean condition, double ifTrue, double ifFalse) {
        return condition ? ifTrue : ifFalse;
    }
}
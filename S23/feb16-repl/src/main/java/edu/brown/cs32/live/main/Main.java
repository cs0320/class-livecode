package edu.brown.cs32.live.main;

import edu.brown.cs32.live.repl.REPL;

/**
 * The entry point for the livecode demo. This sets up state and
 * then starts the REPL.
 *
 * The-developer user of the REPL wrote this. In our pretend context,
 * that would be someone using our library to write their own text
 * adventure game...
 */
public class Main {
    public static void main(String[] args) {
        REPL repl = new REPL();
        repl.run();
    }
}

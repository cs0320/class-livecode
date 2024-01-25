package edu.brown.cs32.live.main;

import edu.brown.cs32.live.repl.REPL;

import java.util.ArrayList;
import java.util.List;

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
        // This command has no state
        repl.registerCommand("subtract", new Subtractor());
        // These commands should _share_ state
        List<Integer> sharedCounter = new ArrayList<>(List.of(0));
        repl.registerCommand("next", new Next(sharedCounter));
        repl.registerCommand("prev", new Prev(sharedCounter));

        repl.run();
    }
}

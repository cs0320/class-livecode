package edu.brown.cs32.live.main;

import repl.CommandFunction;
import repl.REPL;

/**
 * The entry point for the livecode demo. This sets up state and
 * then starts the REPL.
 *
 * "YOU" wrote this: the developer user of the REPL
 */
public class Main {
    public static void main(String[] args) {
        REPL repl = new REPL();
        CommandFunction subFunction = new Subtractor();
        repl.registerCommand("subtract", subFunction);
        repl.start(); // blocked....  but stay tuned for concurrency
    }
}

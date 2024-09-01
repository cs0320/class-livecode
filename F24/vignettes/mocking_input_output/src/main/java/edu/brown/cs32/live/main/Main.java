package edu.brown.cs32.live.main;

import edu.brown.cs32.live.repl.CommandProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * The entry point for our program. It creates a command-line app and starts it listening for commands.
 *
 * Notice that we called the CommandProcessor constructor with _no arguments_ here.
 */
public class Main {
    public static void main(String[] args) {
        CommandProcessor proc = new CommandProcessor();
        proc.run();
    }
}

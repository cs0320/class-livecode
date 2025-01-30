package edu.brown.cs32.live.repl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This is the starting point for the demo only! The `CommandProcessor` class is an improvement on this one.
 */
public class BasicCommandProcessor {
    public void run() {
        // This is a "try with resources"; the resource will automatically
        // be closed if necessary. Prefer this over finally blocks.
        // See: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while ((input = br.readLine()) != null) {
                if (input.equalsIgnoreCase("EXIT")) {
                    return;
                } else if (input.equalsIgnoreCase("HI")) {
                    System.out.println("Hi!");
                } else if (input.equalsIgnoreCase("GREETINGS")) {
                    System.out.println("Delightful to meet you, I'm sure.");
                } else {
                    System.err.println("ERROR: Invalid command.");
                    // Keep running, though!
                }
            }
        } catch (IOException ex) {
            System.err.println("ERROR: Error reading input.");
            System.exit(1); // exit with error status
        }
    }

    /**
     * This is the entry point for the command-line application.
     */
    public static void main(String[] args) {
        BasicCommandProcessor proc = new BasicCommandProcessor();
        proc.run();
    }

}

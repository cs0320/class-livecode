package edu.brown.cs32.live.repl;

import java.io.*;

/**
 * An example class that implements a command prompt. When it is "run" (by calling its run method) it will
 * loop forever, listening for input. When it receives input, it parses it and executes the appropriate command.
 */
public class ImprovedCommandProcessor {
    private final InputStream in;
    private final PrintStream out;
    private final PrintStream err;

    /**
     * Create a CommandProcessor that listens for input, sends output, and sends errors to the the given streams.
     * @param in the input stream to use
     * @param out the output stream to use
     * @param err the error stream to use
     */
    public ImprovedCommandProcessor(InputStream in, PrintStream out, PrintStream err) {
        this.in = in;
        this.out = out;
        this.err = err;
    }

    /**
     * Create a CommandProcessor using the standard System-defined input, output, and error streams.
     */
    public ImprovedCommandProcessor() {
        this(System.in, System.out, System.err);
    }

    /**
     * Start listening for input, and don't stop until you see "exit".
     */
    public void run() {
        // This is a "try with resources"; the resource will automatically
        // be closed if necessary. Prefer this over finally blocks.
        // See: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.in))) {
            String input;
            while ((input = br.readLine()) != null) {
                if (input.equalsIgnoreCase("EXIT")) {
                    // DEMO FROM CLASS: for debugging not test
                    //System.out.println("IN EXIT");
                    //////
                    return;
                } else if (input.equalsIgnoreCase("HI")) {
                    this.out.println("Hi!");
                } else if (input.equalsIgnoreCase("GREETINGS")) {
                    this.out.println("Delightful to meet you, I'm sure.");
                } else {
                    this.err.println("ERROR: Invalid command.");
                    // Keep running, though!
                }
            }
        } catch (IOException ex) {
            this.err.println("ERROR: Error reading input.");
            System.exit(1); // exit with error status
        }
    }

    /**
     * This is the entry point for the command-line application.
     */
    public static void main(String[] args) {
        ImprovedCommandProcessor proc = new ImprovedCommandProcessor();
        proc.run();
    }
}

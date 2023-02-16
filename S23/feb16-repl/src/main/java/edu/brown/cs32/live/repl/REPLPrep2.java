package edu.brown.cs32.live.repl;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class REPLPrep2 implements Runnable {
    private final Map<String, CommandFunction> registered = new HashMap<>();
    private final InputStream in;
    private final PrintStream out;
    private final PrintStream err;

    public REPLPrep2(InputStream in, PrintStream out, PrintStream err) {
        this.in = in;
        this.out = out;
        this.err = err;
    }
    public REPLPrep2() {
        this(System.in, System.out, System.err);
    }

    /**
     *
     * @param name
     * @param func
     * @return true if and only if an old command was overwritten
     */
    public boolean registerCommand(String name, CommandFunction func) {
        boolean result = registered.containsKey(name);
        registered.put(name, func);
        return result;
    }

    @Override
    public void run() {
        System.out.println("DEBUG: REPL STARTED");
        // This is a "try with resources"; the resource will automatically
        // be closed if necessary. Prefer this over finally blocks.
        // See: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String input;
            while ((input = br.readLine()) != null) {
                List<String> parsedInput = parseInput(input);
                System.out.println("DEBUG: "+parsedInput);
                try {
                    if(registered.containsKey(parsedInput.get(0))) {
                        CommandFunction func = registered.get(parsedInput.get(0));
                        this.out.println(func.run(parsedInput));
                        // ^ make sure to be clear whether this includes the
                        //  command name in the docs :-)
                    }

                    // All but exit here could be converted
                    if (input.equalsIgnoreCase("EXIT")) {
                        return; // exit the REPL
                    } else if(parsedInput.get(0).equalsIgnoreCase("HI")) {
                        runHi(parsedInput);
                    } else if(parsedInput.get(0).equalsIgnoreCase("ADD")) {
                        runAdd(parsedInput);
                    } else {
                        // handle this gracefully; report to the user and continue
                        this.err.println("ERROR: Invalid command.");
                    }
                } catch (IllegalArgumentException ex) {
                    this.err.println("ERROR: Invalid input for command: "+ex.getMessage());
                    // continue, it was just a mistake in one command
                }
            }
        } catch (IOException ex) {
            this.err.println("ERROR: Error reading input.");
            System.exit(1); // exit with error status
        }
    }

    // these could be made functions, too
    private void runHi(List<String> parsedInput) {
        if(parsedInput.size() != 2) {
            throw new IllegalArgumentException("'hi' command requires one argument");
        }
        this.out.println("Hi, "+parsedInput.get(1));
    }

    private void runAdd(List<String> parsedInput) {
        if(parsedInput.size() != 3) {
            throw new IllegalArgumentException("'add' command requires two arguments");
        }
        try {
            int arg1 = Integer.parseInt(parsedInput.get(1));
            int arg2 = Integer.parseInt(parsedInput.get(2));
            int result = arg1 + arg2;
            this.out.println(result);
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("'add' command needs integer arguments");
        }
    }


    /**
     * Parses user input via a regular expression.
     * NOTE: this may be a useful reference example, but don't focus here
     * during class---it's not the main point.
     *
     * @param input String of user input to be parsed.
     * @return list containing the input split input at whitespaces except
     * within quotes or apostrophes.
     */
    public static List<String> parseInput(String input) {
        List<String> parsedInput = new ArrayList<>();
        // regex parsing adapted from: https://stackoverflow.com/q/366202/
        Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"");
        Matcher regexMatcher = regex.matcher(input);
        while (regexMatcher.find()) {
            if (regexMatcher.group(1) != null) {
                // Add double-quoted string with the quotes
                parsedInput.add("\"" + regexMatcher.group(1) + "\"");
            } else {
                // Add unquoted word
                parsedInput.add(regexMatcher.group());
            }
        }
        return parsedInput;
    }
}

package edu.brown.cs32.live.repl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Example "naive" REPL. This first version uses an if-else statement
 * to decide which command to run, and all commands are hard coded.
 * Error handling is also rather messy and ad-hoc.
 *
 *  **THIS FILE SHOULD NOT BE CHANGED; IT SAVES THE BEGINNING STATE OF LIVECODE**
 */
public class REPLStarter {
    public REPLStarter() {}

     /*
      EXERCISE 2: how can we make this REPL more extensible?
     */

    /*
      EXERCISE 3: how can we test this REPL?
     */

    public void run() {
        // This is a "try with resources"; the resource will automatically
        // be closed if necessary. Prefer this over finally blocks.
        // See: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while ((input = br.readLine()) != null) {
                List<String> parsedInput = parseInput(input);

                if (input.equalsIgnoreCase("EXIT")) {
                    return; // exit the REPL
                } else if (parsedInput.get(0).equalsIgnoreCase("HI")) {
                    runHi(parsedInput);
                } else if (parsedInput.get(0).equalsIgnoreCase("ADD")) {
                    runAdd(parsedInput);
                } else {
                    // handle this gracefully; report to the user and continue
                    System.err.println("ERROR: Invalid command name.");
                }
            }

        /*
          EXERCISE 1: how can we improve this error handling?
         */
        } catch (Exception ex) {
            System.err.println("ERROR handling command!");
            System.exit(1); // exit with error status
        }
    }

    private void runHi(List<String> parsedInput) {
        if(parsedInput.size() != 2) {
            throw new IllegalArgumentException("'hi' command requires one argument");
        }
        System.out.println("Hi, "+parsedInput.get(1));
    }

    private void runAdd(List<String> parsedInput) {
        if(parsedInput.size() != 3) {
            throw new IllegalArgumentException("'add' command requires two arguments");
        }
        try {
            int arg1 = Integer.parseInt(parsedInput.get(1));
            int arg2 = Integer.parseInt(parsedInput.get(2));
            int result = arg1 + arg2;
            System.out.println(result);
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

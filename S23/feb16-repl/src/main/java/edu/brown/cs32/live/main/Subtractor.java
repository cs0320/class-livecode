package edu.brown.cs32.live.main;

import edu.brown.cs32.live.repl.CommandFunction;

import java.util.List;

public class Subtractor implements CommandFunction {

    @Override
    public String run(List<String> args) {
        try {
            int num1 = Integer.parseInt(args.get(1));
            int num2 = Integer.parseInt(args.get(2));
            return Integer.toString(num1-num2);
        } catch(NumberFormatException e) {
            // YOU as developer-user can decide whether to print an error
            //  or something else (like throwing an exception)
            //   - make a new REPLFunctionError exception and throw it
            System.err.println("Bad input: ...");
            return ""; // we should do better than this

        }
    }
}

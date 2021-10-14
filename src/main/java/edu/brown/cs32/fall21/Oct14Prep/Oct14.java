package edu.brown.cs32.fall21.Oct14Prep;

import java.util.HashSet;
import java.util.Set;

public class Oct14 {
    public static void main(String[] args) {
        // Avoid confusion: which package is this?
        System.out.println("Starting "+Oct14.class.getPackageName());

        Set<StockService> svcs = new HashSet<>();
        svcs.add(new StockService("NYSE"));
        svcs.add(new StockService("Nasdaq"));
        svcs.add(new StockService("Shanghai"));
        svcs.add(new StockService("London"));

        REPL repl = new REPL(svcs);
        try {
            repl.start("Use `subscribe <stockname>` or `serve`");
        } catch (BadCommandException e) {
            System.out.println(e);
            System.exit( 1); // repl failed; stop
        }
    }
}

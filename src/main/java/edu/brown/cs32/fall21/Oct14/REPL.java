package edu.brown.cs32.fall21.Oct14;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class BadCommandException extends Exception {
    final String command;
    final String[] args;

    BadCommandException(String command, String[] args) {
        this.command = command;
        this.args = args.clone();
    }

    @Override
    public String toString() {
        return "Bad command: "+command+"; args array: "+ Arrays.toString(args);
    }
}

class REPLStockCallback implements StockCallback {
    private final String filename;
    private final Map<Integer, Integer> counter;

    @Override
    public void notifyCustomer(int stockID, double newPrice) {
        // This causes problems in IntelliJ's console
        // and, anyway, it's kinda spammy and annoying.
        //System.out.println("The price of Stk" + stockID + " changed to $" + newPrice);
        // Instead, we'll just write out to a file.
        FileWriter writer = null;
        try {
            writer = new FileWriter(filename, true); // append mode
            writer.write("Update "+stockID+": "+newPrice+" (thread="+Thread.currentThread().getId()+")\n");
            writer.close();
        } catch (IOException e) {
            // This isn't great -- abstraction breaking!
            // ??? What to do instead?
            e.printStackTrace();
        }

        // Count one more update for this stock
        if(!counter.containsKey(stockID))
            counter.put(stockID, 0);
        counter.put(stockID, counter.get(stockID) + 1);
    }

    REPLStockCallback(String filename, Map<Integer, Integer> counter) {
        this.filename = filename;
        this.counter = counter;
    }
}
public class REPL {
    private final StockService svc;

    REPL(StockService svc) {
        this.svc = svc;
    }

    public void start(String startMessage) throws BadCommandException {
        System.out.println(startMessage + "(thread = "+Thread.currentThread().getId()+")");

        // This shouldn't be in the REPL, but proving a point
        Map<Integer, Integer> updateCounter = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input;

            // Keep trying to get a command...
            while ((input = br.readLine()) != null) {
                input = input.trim();
                System.out.println("REPL input:"+input);
                String[] args = input.split(" ");
                for (int i = 0; i < args.length; i++) {
                    args[0] = args[0].trim();
                }
                if ("subscribe".equals(args[0])) {
                    try {
                        svc.subscribe(Integer.parseInt(args[1]),
                                      new REPLStockCallback("stocks.txt", updateCounter));
                    } catch(NumberFormatException e) {
                        throw new BadCommandException(args[0], args);
                    }
                } else if ("serve".equals(args[0])) {
                    Thread t = new Thread(svc);
                    t.start();
                } else if("count".equals(args[0])) {
                    System.out.println(updateCounter);
                }
                else {
                    throw new BadCommandException(args[0], args);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not create REPL.");
            e.printStackTrace();
        }
    }
}

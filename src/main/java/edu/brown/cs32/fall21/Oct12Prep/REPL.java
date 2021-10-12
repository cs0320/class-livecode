package edu.brown.cs32.fall21.Oct12Prep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

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
    @Override
    public void notifyCustomer(int stockID, double newPrice) {
        System.out.println("The price of Stk" + stockID + " changed to $" + newPrice);
    }
}
public class REPL {
    private final StockService svc;

    REPL(StockService svc) {
        this.svc = svc;
    }

    public void start(String startMessage) throws BadCommandException {
        System.out.println(startMessage);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input;

            // Keep trying to get a command...
            while ((input = br.readLine()) != null) {
                input = input.trim();
                System.out.println("repl input:"+input);
                String[] args = input.split(" ");
                for (int i = 0; i < args.length; i++) {
                    args[0] = args[0].trim();
                }
                if ("subscribe".equals(args[0])) {
                    try {
                        System.out.println("REPL subscribe");
                        svc.subscribe(Integer.parseInt(args[1]), new REPLStockCallback());
                    } catch(NumberFormatException e) {
                        throw new BadCommandException(args[0], args);
                    }
                } else if ("serve".equals(args[0])) {
                    Thread t = new Thread(svc);
                    t.start();
                    System.out.println("Server started! New subscription possible...");
                } else {
                    throw new BadCommandException(args[0], args);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not create REPL.");
            e.printStackTrace();
        }
    }
}

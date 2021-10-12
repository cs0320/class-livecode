package edu.brown.cs32.fall21.Oct12Prep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    public void start(String startMessage) {
        System.out.println(startMessage);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input;

            // Keep trying to get a command...
            while ((input = br.readLine()) != null) {
                input = input.trim();
                String[] args = input.split(" ");
                for (int i = 0; i < args.length; i++) {
                    args[0] = args[0].trim();
                }
                if ("subscribe".equals(args[0])) {
                    try {
                        svc.subscribe(Integer.parseInt(args[1]), new REPLStockCallback());
                    } catch(NumberFormatException e) {
                        // ???: What should we do here?
                        throw e;
                    }
                } else if ("serve".equals(args[0])) {
                    svc.serve();
                }
            }
        } catch (IOException e) {
            System.out.println("Could not create REPL.");
            e.printStackTrace();
        }
    }
}

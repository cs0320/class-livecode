package edu.brown.cs32.fall21.Oct12Prep;

import java.text.DecimalFormat;
import java.util.*;

// Package-public is OK for this context (1 class meeting per package)
interface StockCallback {
    void notifyCustomer(int stockID, double newPrice);
}

public class StockService implements Runnable {
    Map<Integer, List<StockCallback>> subscriptions = new HashMap<>();

    void subscribe(int stockID, StockCallback cb) {
        System.out.println("StockService subscribe");
        if(!subscriptions.containsKey(stockID))
            subscriptions.put(stockID, new ArrayList<>());
        subscriptions.get(stockID).add(cb);
    }

    void serve() {
        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
            int stock = (new Double(Math.random() * 10)).intValue();
            DecimalFormat dec2 = new DecimalFormat("###.##");
            double price = Double.parseDouble(dec2.format(Math.random() * 100));

            if(subscriptions.containsKey(stock)) {
                for(StockCallback cb : subscriptions.get(stock)) {
                    cb.notifyCustomer(stock, price);
                }
            }
        }
    }

    @Override
    public void run() {
        serve();
    }
}

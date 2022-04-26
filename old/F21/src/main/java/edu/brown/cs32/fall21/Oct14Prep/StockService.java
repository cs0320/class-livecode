package edu.brown.cs32.fall21.Oct14Prep;

import java.text.DecimalFormat;
import java.util.*;

// Package-public is OK for this context (1 class meeting per package)
interface StockCallback {
    void notifyCustomer(int stockID, double newPrice);
}

public class StockService implements Runnable {
    private final Map<Integer, List<StockCallback>> subscriptions = new HashMap<>();
    private final String exchangeName;
    // for billing, etc.
    final private Map<StockCallback, Integer> updateCounters = new HashMap<>();

    StockService(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    void subscribe(int stockID, StockCallback cb) {
        if(!subscriptions.containsKey(stockID))
            subscriptions.put(stockID, new ArrayList<>());
        subscriptions.get(stockID).add(cb);

        // be ready to charge the customer
        if(!updateCounters.containsKey(cb))
            updateCounters.put(cb, 0);
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
                    updateCounters.put(cb, updateCounters.get(cb) + 1);
                }
            }
        }
    }

    @Override
    public void run() {
        System.out.println("Service for "+exchangeName+" starting (thread = "+Thread.currentThread().getId()+")");
        serve();
    }

    public String status() {
        return exchangeName+" (thread = "+Thread.currentThread().getId()+")"+
                " logged "+updateCounters+" updates";
    }
}

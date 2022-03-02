package edu.brown.cs32.fall21.Oct12;

import java.text.DecimalFormat;
import java.util.*;

// Package-public is OK for this context (1 class meeting per package)
interface StockCallback {
    void notifyCustomer(int stockID, double newPrice);
}

public class StockService {
    Map<Integer, List<StockCallback>> subscriptions = new HashMap<>();

    void subscribe(int stockID, StockCallback cb) {
        if(!subscriptions.containsKey(stockID))
            subscriptions.put(stockID, new ArrayList<>());
        subscriptions.get(stockID).add(cb);
    }

    void serve() {
        while(true) {
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
}

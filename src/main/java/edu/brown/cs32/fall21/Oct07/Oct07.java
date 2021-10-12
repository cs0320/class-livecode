package edu.brown.cs32.fall21.Oct07;

import java.text.DecimalFormat;
import java.util.*;

public class Oct07 {

    public static void main(String args[]) {
        StockService svc = new StockService();
        InvestmentApp app = new InvestmentApp();
        app.setup(svc); // dependency injection (if in constructor)
        svc.serve();
    }
}

class InvestmentApp {
    void setup(StockService svc) {
        System.out.println("$$$ LET'S MONEY $$$");
        svc.subscribe(1, new InvestmentAppCallback());
    }
}

class InvestmentAppCallback implements StockCallback {
    @Override
    public void notifyCustomer(int stockID, double oldPrice, double newPrice) {
        System.out.println("The price of Stk"+stockID+" changed to $"+newPrice);
    }
}

interface StockCallback {
    void notifyCustomer(int stockID, double oldPrice, double newPrice);
}

class StockService {
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
                    cb.notifyCustomer(stock, 0, price);
                }
            }
        }
    }
}

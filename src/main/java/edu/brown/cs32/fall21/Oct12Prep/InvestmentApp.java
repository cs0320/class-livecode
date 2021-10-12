package edu.brown.cs32.fall21.Oct12Prep;

/**
 * We will ignore this class today; I'm keeping it around
 * for reference only!
 */

class InvestmentAppCallback implements StockCallback {
    @Override
    public void notifyCustomer(int stockID, double newPrice) {
        System.out.println("The price of Stk" + stockID + " changed to $" + newPrice);
    }
}


public class InvestmentApp {
    void setup(StockService svc) {
        System.out.println("$$$ LET'S MONEY $$$");
        svc.subscribe(1, new InvestmentAppCallback());
    }
}


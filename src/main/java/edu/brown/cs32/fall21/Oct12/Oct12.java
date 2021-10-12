package edu.brown.cs32.fall21.Oct12;

public class Oct12 {
    public static void main(String[] args) {
        StockService svc = new StockService();
        InvestmentApp app = new InvestmentApp();
        app.setup(svc); // dependency injection (if in constructor)
        svc.serve();
    }
}

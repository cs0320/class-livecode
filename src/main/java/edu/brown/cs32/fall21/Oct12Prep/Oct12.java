package edu.brown.cs32.fall21.Oct12Prep;

public class Oct12 {
    public static void main(String[] args) {
        StockService svc = new StockService();
        REPL repl = new REPL(svc);
        repl.start("Use `subscribe <stockname>` or `serve`");
    }
}

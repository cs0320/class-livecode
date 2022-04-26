package edu.brown.cs32.fall21.Oct12Prep;

public class Oct12 {
    public static void main(String[] args) {
        StockService svc = new StockService();
        REPL repl = new REPL(svc);
        try {
            repl.start("Use `subscribe <stockname>` or `serve`");
        } catch (BadCommandException e) {
            System.out.println(e.toString());
            System.exit( 1); // repl failed; stop
        }
    }
}

package edu.brown.cs32.spring22.livecode.mar08;

class NeverLoaded {
    static {
        System.out.println("NeverLoaded Class is loading (executes only once)...");
    }
    private NeverLoaded() {}
}

public class StaticBlockDemo {
    static {
        System.out.println("Class is loading (executes only once)...");
    }
    public static void main(String[] args) {
        System.out.println("Main is invoked...");
    }
}

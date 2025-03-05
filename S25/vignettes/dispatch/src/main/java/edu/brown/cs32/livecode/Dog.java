package edu.brown.cs32.livecode;

public class Dog extends Animal {
    private final boolean fuzzy;
    public Dog(String name, boolean fuzzy) {
        super(name);
        this.fuzzy = fuzzy;
    }
    boolean fuzzy() { return this.fuzzy; }

    String speak() { return "woof!"; }
}

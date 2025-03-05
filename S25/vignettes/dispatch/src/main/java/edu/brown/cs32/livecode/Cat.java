package edu.brown.cs32.livecode;

public class Cat extends Animal {
    private final boolean likesKeyboards;
    Cat(String name, boolean likesKeyboards) {
        super(name);
        this.likesKeyboards = likesKeyboards;
    }
    boolean likesKeyboards() { return this.likesKeyboards; }
    String speak() { return "meow"; }
}

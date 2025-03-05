package edu.brown.cs32.livecode;

public abstract class Animal {
    private final String name;
    Animal(String name) {
        this.name = name;
    }
    String name() { return name; }

    abstract String speak();
}

package edu.brown.cs32.fall21.Nov04Class;

import java.util.HashSet;

public class Dog extends Animal {
    final boolean strength;
    final boolean insolence;

    Dog(String name, boolean strength, boolean insolence) {
        super(name, new HashSet<>());
        this.strength = strength;
        this.insolence = insolence;
    }

    @Override
    public String speak() {
        return "woof!";
    }
}

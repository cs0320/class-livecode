package edu.brown.cs32.fall21.Nov04Prep;

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

    @Override
    public <R> R accept(AnimalVisitor<R> visitor) {
        return visitor.visit(this); // we add this to every Animal class
    }
}

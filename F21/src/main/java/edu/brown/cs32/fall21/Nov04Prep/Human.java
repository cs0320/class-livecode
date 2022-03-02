package edu.brown.cs32.fall21.Nov04Prep;

import java.util.HashSet;

public class Human extends Animal {
    final String poem;

    public Human(String name, String poem) {
        super(name, new HashSet<>());
        this.poem = poem;
    }

    @Override
    public String speak() {
        return poem;
    }

    // Ignore this until attempt 3
    @Override
    public <R> R accept(AnimalVisitor<R> visitor) {
        return visitor.visit(this); // we add this to every Animal class
    }
}

package edu.brown.cs32.fall21.Nov04Class;

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
}

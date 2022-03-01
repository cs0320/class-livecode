package edu.brown.cs32.spring22.Mar01;

public class Student {
    final private String name;
    Student(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

package edu.brown.cs32.livecode;

import java.util.List;

public class MostCommon {
    public static void main(String[] args) {
        Student<String> tim = new Student<>(List.of("lecture notes", "email", "email", "family", "email"));
        System.out.println(tim.mostCommonTodoItem());
    }
}

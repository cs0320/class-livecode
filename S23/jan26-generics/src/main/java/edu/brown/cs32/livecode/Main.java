package edu.brown.cs32.livecode;

import java.util.List;

/**
 * In-class livecode demo: start here
 */
public class Main {
    public static void main(String[] args) {
        Student tim = new Student(
                List.of("lecture notes", "email", "email", "family", "email"));
        System.out.println(tim.mostCommonTodoItem());
    }
}

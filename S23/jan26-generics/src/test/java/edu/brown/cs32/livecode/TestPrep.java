package edu.brown.cs32.livecode;

/*
 * ***PREP***
 * This test suite is meant to check the planned flow of the lecture example, and won't actually be run in class.
 * If you are reading this in advance of the lecture, don't spoil the story in class.
 */

import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPrep {
    @Test
    public void emptyListStudent() {
        Student student = new Student(List.of());
        // Is this _really_ the behavior we want? Should we be testing == null?
        assertNull(student.mostCommonTodoItem());
    }

    @Test
    public void emptyListGenericStudent() {
        // Note: we needed to give a type, even for an empty list. (Why? Think about it.)
        PrepGenericStudent<String> student = new PrepGenericStudent<>(List.of());
        // Oops.
        //assertNull(student.mostCommonTodoItem());
        // Instead: "When I run this function, I'll get this exception"
        assertThrows(IllegalArgumentException.class,
                // A "lambda". Could write this a bunch of diff ways.
                () -> student.mostCommonTodoItem());
    }


}

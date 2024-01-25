package edu.brown.cs32.livecode;

/*
 * ***PREP***
 * This test suite is meant to check the planned flow of the lecture example, and won't actually be run in class.
 * If you are reading this in advance of the lecture, don't spoil the story in class.
 */

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertNull;

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
        GenericStudent<String> student = new GenericStudent<>(List.of());
        // Oops.
        assertNull(student.mostCommonTodoItem());
    }


}

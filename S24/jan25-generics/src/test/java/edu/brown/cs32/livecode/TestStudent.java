package edu.brown.cs32.livecode;

// Make sure you're using JUnit 5 -- add it via Maven, or get IntelliJ to help.

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

// Because assertEquals is static, and we want to avoid having to write Assert.assertEquals:
import static org.junit.jupiter.api.Assertions.*;


//  Remember to include "Test" in the class name for a test class, otherwise the tests might not be detected.
public class TestStudent {

    // Included for demo only, we won't need these today. But keep them in mind...
    @BeforeEach
    public void setup() {
        System.out.println("This runs before _every_ test!");
    }
    @AfterEach
    public void teardown() { System.out.println("This runs after _every_ test!"); }

    @Test
    public void oneObvious() {
        Student<String> student = new Student<>(List.of("lecture notes", "email", "email", "family", "email"));
        assertEquals("email", student.mostCommonTodoItem());
    }
    @Test
    public void oneTwoThree() {
        Student<String> student = new Student<>(List.of("lecture notes", "email", "email", "family", "family", "family"));
        assertEquals("family", student.mostCommonTodoItem());
    }
    @Test
    public void emptyList() {
        Student<String> student = new Student<>(List.of());
        // EXERCISE: Is this _really_ the behavior we want? Should we be testing == null?
        assertNull(student.mostCommonTodoItem()); // not anymore!
    }

}

/* CLASS-PREP SPOILERS BELOW */









/*

        // Instead, does this anonymous function produce that exception when run?
        assertThrows(IllegalStateException.class, () -> {
            student.mostCommonTodoItem();
        });
 */
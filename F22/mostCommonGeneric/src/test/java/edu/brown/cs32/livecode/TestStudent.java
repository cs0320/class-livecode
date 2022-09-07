package edu.brown.cs32.livecode;

// JUnit -- add it via Maven, or get IntelliJ to help.
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

// Because assertEquals is static, and we want to avoid having to write Assert.assertEquals:
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

//  Remember to include "Test" in the class name for a test class.
public class TestStudent {

    // Included for demo only---usually before/after are for resetting state.
    //   Tim is *used* to JUnit 4. JUnit 5 calls these "@BeforeEach" and "@AfterEach", which...are better names.
    @Before
    public void setup() {
        System.out.println("This runs before _every_ test!");
    }
    @After
    public void teardown() {
        System.out.println("This runs after _every_ test!");
    }

    @Test
    public void oneObvious() {
        Student student = new Student(List.of("lecture notes", "email", "email", "family", "email"));
        assertEquals("email", student.mostCommonTodoItem());
    }
    @Test
    public void oneTwoThree() {
        Student student = new Student(List.of("lecture notes", "email", "email", "family", "family", "family"));
        assertEquals("family", student.mostCommonTodoItem());
    }
    @Test
    public void emptyList() {
        Student student = new Student(List.of());
        // Is this _really_ the behavior we want? Should we be testing == null?
        assertNull(student.mostCommonTodoItem());
    }

}

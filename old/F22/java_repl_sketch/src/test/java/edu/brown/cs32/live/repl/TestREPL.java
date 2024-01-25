package edu.brown.cs32.live.repl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repl.REPL;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Testing for our REPL class
 *
 * Because the REPL interacts so heavily with System.out and System.in,
 * we need some tricks to properly script these tests. Please note that
 * this setup isn't ideal: it tangles testing individual functions with
 * testing the REPL itself, and the REPL isn't yet well-written to allow
 * for rich testing (and testing of error handling).
 *
 * Use this as a reference of techniques, *not* as a reference of exactly
 * how to test something like this.
 */
public class TestREPL {
    final PrintStream stdio = System.out;
    // See: https://docs.oracle.com/javase/7/docs/api/java/io/OutputStream.html
    // ByteArrayOutputStream provides a "mock" System.out
    // (We are using the full type rather than just OutputStream, because,
    //  for safety, we want to ensure listener is empty for each test.
    final ByteArrayOutputStream listener = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        // throw away any pre-existing data
        listener.reset();
        // replace the usual System.out with our listener
        System.setOut(new PrintStream(listener));
    }

    @BeforeEach
    public void teardown() {
        // return to the usual System.out behavior
        System.setOut(stdio);
    }

    @Test
    public void proofOfConcept() {
        System.out.println("32");
        // We printed a _line_, and "32" doesn't contain that newline character
        assertNotEquals("32", listener.toString());
        assertEquals("32"+System.lineSeparator(), listener.toString());
    }

    @Test
    public void testREPLError() {
        REPL repl = new REPL();
        repl.start();
        // But there's a problem...
        // Unfortunately even this doesn't work.
    }



}

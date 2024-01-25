package edu.brown.cs32.live.repl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;
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
 * Use this as a reference of mocking techniques, *not* as a reference of
 * exactly how to test something like this.
 */
public class TestREPL {
    // Prepare to mock System.in
    private InputStream mockInputToREPL;
    private OutputStreamWriter mockedKeyboard;

    // Prepare to mock System.out and System.err
    private PrintStream mockOutputFromREPL;
    private BufferedReader mockedTerminal;

    // For a one-command check, this might also work:
    // See: https://docs.oracle.com/javase/7/docs/api/java/io/OutputStream.html
    // ByteArrayOutputStream provides a "mock" System.out
    // (We are using the full type rather than just OutputStream, because,
    //  for safety, we want to ensure listener is empty for each test.
    // final ByteArrayOutputStream listener = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() throws IOException {
        // Prepare to mock System.in
        PipedOutputStream mockOutputStream = new PipedOutputStream();
        mockInputToREPL = new PipedInputStream(mockOutputStream);
        mockedKeyboard = new OutputStreamWriter(mockOutputStream, UTF_8);

        // Prepare to mock System.out and System.err
        PipedInputStream mockInputStream = new PipedInputStream();
        mockOutputFromREPL = new PrintStream(new PipedOutputStream(mockInputStream));
        // Buffer for ease of reading
        mockedTerminal = new BufferedReader(new InputStreamReader(mockInputStream, UTF_8));

    }

    @BeforeEach
    public void teardown() {
    }

    @Test
    public void testREPLErrorOops() {
        REPL repl = new REPL();
        // What happens if we call repl.run(); ?
        //repl.run();
        Thread replRunner = new Thread(repl);
        replRunner.start();
        System.out.println("hello");

        // But there's a problem...
        // Unfortunately even this doesn't work.
    }

    /**
     * For livecode, swap to use REPL
     *
     */
    @Test
    public void testREPLError() throws IOException, InterruptedException {
        REPLPrep2 repl = new REPLPrep2(mockInputToREPL, mockOutputFromREPL, mockOutputFromREPL);
        // Now that we've made REPL implement Runnable, we can create a new
        // thread that will execute the REPL when it is started.
        Thread replRunner = new Thread(repl);
        replRunner.start();
        mockedKeyboard.write("notacommand"+System.lineSeparator());
        mockedKeyboard.flush();
        String output = mockedTerminal.readLine();
        System.out.println(output);
        assertEquals("ERROR: Invalid command.", output);

    }

}

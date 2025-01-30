package edu.brown.cs32.live.repl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

/**
 * How do you test a command-line application? There are a few options. For our purposes, we'd rather not
 * have to do anything "heavyweight", like redirecting the input and output from and to another program
 * via a shell script. There are a few "lightweight" ways to do this, and one of them is useful for
 * giving us practice with two important concepts: mocking and dependency injection.
 *
 * That said, there are some things I've left undone here, so don't take this example as /exactly/
 * right. It's just good enough for our purposes right now.
 *
 * Here's what we'll do:
 *   - Manufacture a "fake" version of System.in that our test method can write commands to.
 *   - Manufacture a "fake" version of System.out, and System.err that our test method can read from.
 *   - Design our command-line application class to accept three new arguments:
 *     input, output, and error streams. Crucially, it must never use "System.in", "System.out", or
 *     "System.err". It must call methods in these provided objects instead.
 *   - Our main method for the real application will pass in System.in, System.out, and System.err
 *     when it creates the application object.
 *   - Our _test_ method will pass in the fake objects instead. And the application will never know the difference.
 *
 *  To do this better, we'd need threads---a topic for later in the course. For now, we'll make sure that
 *  each test case populates its fake input stream with a series of commands, *always* ending with "exit".
 *  When the application sees "exit", it will terminate, which returns control to the test method.
 *
 */
public class TestREPL {

    /**
     * Demonstration of a test of the command-line application using mocked input and output.
     */
    @Test
    public void testREPLMock() throws IOException {
        // This is a "try-with-resources" statement. It's not about catching an exception,
        // but about making sure resources are freed up when we're done with these objects.
        // In this case, the "close()" method will be called on all the various pipes.
        try(MockSystemIn mockIn = MockSystemIn.build();
            MockSystemOut mockOut = MockSystemOut.build();
            MockSystemOut mockErr = MockSystemOut.build()) {

            // Create a new application instance to test. (Ideally, we'd use a setUp method for this, but
            // this is more straightforward for this small example, given this needs to happen inside the
            // try-with-resources.)
            CommandProcessor proc = new CommandProcessor(mockIn.mockSystemIn(), mockOut.mockOutput(), mockErr.mockOutput());

            // Once we start the application, this test method will be unable to add anything else. So we must pre-populate
            // a fixed series of commands. Then run the application.
            mockIn.println("hi");
            mockIn.println("greetings");
            mockIn.println("notacommand");
            mockIn.println("exit");

            proc.run();

            // Now read from the output and error streams, line by line. But: be careful. If we call readLine() for one of
            // the streams and there's nothing there, the program will freeze, because the call will *wait* for something
            // to appear in the stream... and that will not happen. So we test before every line we ready to make sure the
            // stream has something for us first. (This is a way to protect us from a buggy _test method_.)
            // To see why this is important, try commenting out one of the commands above!
            assertTrue(mockOut.terminal().ready());
            String out1 = mockOut.terminal().readLine();
            assertEquals("Hi!", out1);

            assertTrue(mockOut.terminal().ready());
            String out2 = mockOut.terminal().readLine();
            assertEquals("Delightful to meet you, I'm sure.", out2);

            assertTrue(mockErr.terminal().ready());
            String err1 = mockErr.terminal().readLine();
            assertEquals("ERROR: Invalid command.", err1);
        }
    }


    /**
     * Test to demo what happens if a _lot_ of text gets pushed into the buffer.
     */
    @Test
    public void testREPLMockLarge() throws IOException {
        // This example will freeze if we don't pass a higher buffer size.
        // (I picked 65525 = 2^16 arbitrarily; the default is 1024 = 2^10 bytes.)
        try (MockSystemIn mockIn = MockSystemIn.build(65525);
             MockSystemOut mockOut = MockSystemOut.build(65525);
             MockSystemOut mockErr = MockSystemOut.build(65525)) {

            CommandProcessor proc = new CommandProcessor(mockIn.mockSystemIn(), mockOut.mockOutput(), mockErr.mockOutput());

            // Suppose we had very verbose output. What happens? In a real application, the spam floods the terminal screen.
            // Unfortunately, here, there is no outlet -- not until the application closes and we can start reading from the
            // mock streams. Fortunately, we can instantiate the buffers to be arbitrarily large (see the helper classes).
            for (int ii = 0; ii < 10000; ii++) {
                mockIn.println("hi");
            }

            assertTrue(true);
            // Has not frozen. (Would freeze with default buffer size.)
        }
    }
}
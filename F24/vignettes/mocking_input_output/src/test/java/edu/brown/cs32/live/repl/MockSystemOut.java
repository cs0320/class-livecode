package edu.brown.cs32.live.repl;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Both sides of a "fake" System.out or System.err.
 */
public record MockSystemOut(PrintStream mockOutput, BufferedReader terminal) {

    /**
     * Generate a new MockSystemOut object with freshly connected streams for the caller to
     * provide the callee. If the callee treats the resulting mockOutput as if it were System.out (or System.err),
     * it will not know the difference.
     * @param BUFFER_SIZE Size in bytes of the buffer. If too much data is pushed into the pipe without any being
     *                    read, the next write could block and the program will freeze up.
     * @return The new MockSystemOut object
     * @throws IOException if such an exception is generated when creating the streams
     */
    static MockSystemOut build(int BUFFER_SIZE) throws IOException {
        // This is an *input* stream from the *callee's* perspective...
        PipedInputStream in = new PipedInputStream(BUFFER_SIZE);
        // ...but an *output* stream from the *caller's* perspective. Connect them!
        PrintStream out = new PrintStream(new PipedOutputStream(in));
        // Wrap the stream in a buffer for ease of reading by the caller.
        BufferedReader buffer = new BufferedReader(new InputStreamReader(in, UTF_8));

        return new MockSystemOut(out, buffer);
    }

    /**
     * Generate a new MockSystemOut object with freshly connected streams for the caller to
     * provide the callee. If the callee treats the resulting mockOutput as if it were System.out (or System.err),
     * it will not know the difference.
     *
     * This constructor uses the default buffer size for the pipe, which is 1024 bytes.
     *
     * @return The new MockSystemOut object
     * @throws IOException if such an exception is generated when creating the streams
     */
     static MockSystemOut build() throws IOException {
         return MockSystemOut.build(1024);
     }

}

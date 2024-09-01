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
     * @return The new MockSystemOut object
     * @throws IOException if such an exception is generated when creating the streams
     */
    static MockSystemOut build() throws IOException {
        // This is an *input* stream from the *callee's* perspective...
        PipedInputStream in = new PipedInputStream();
        // ...but an *output* stream from the *caller's* perspective. Connect them!
        PrintStream out = new PrintStream(new PipedOutputStream(in));
        // Wrap the stream in a buffer for ease of reading by the caller.
        BufferedReader buffer = new BufferedReader(new InputStreamReader(in, UTF_8));

        return new MockSystemOut(out, buffer);
    }
}

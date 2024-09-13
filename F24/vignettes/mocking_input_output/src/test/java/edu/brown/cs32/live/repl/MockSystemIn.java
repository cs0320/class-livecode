package edu.brown.cs32.live.repl;
import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Both sides of a "fake" System.in.
 */
public record MockSystemIn(InputStream mockSystemIn, OutputStreamWriter keyboard) {

    /**
     * Generate a new MockSystemIn object with freshly connected streams for the caller to
     * provide the callee. If the callee treats the resulting mockOutput as if it were System.in,
     * it will not know the difference.
     * @return The new MockSystemIn object
     * @throws IOException if such an exception is generated when creating the streams
     */
    static MockSystemIn build() throws IOException {
        // This is an *output* stream from the *caller's* perspective...
        PipedOutputStream out = new PipedOutputStream();
        // ...but an *input* stream from the *callee's* perspective. Connect them!
        PipedInputStream in = new PipedInputStream(out);
        OutputStreamWriter keyboard = new OutputStreamWriter(out, UTF_8);
        return new MockSystemIn(in, keyboard);
    }

    /**
     * Adds the given text, followed by a newline, to the input and then "flushes" the input, ensuring it's visible.
     * @param input the line to send
     * @throws IOException if there is an error writing to the stream
     */
    void println(String input) throws IOException {
        keyboard.write(input+System.lineSeparator());
        keyboard.flush();
    }
}


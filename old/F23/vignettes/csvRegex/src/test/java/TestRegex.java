import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Example that shows some regular-expression processing for CSV rows.
 * Note: this regexp may not be perfect, but it *should* suffice for this sprint.
 *
 * Recall that Java "escapes" double-quote characters via backslash. So the double-quote character, within a string,
 * should be written as: \"
 */
public class TestRegex {

    static final Pattern regexSplitCSVRow = Pattern.compile(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*(?![^\\\"]*\\\"))");

    /**
     * Basic comma-separation; we could have done this with String.split(",").
     */
    @Test
    public void testBasic() {
        String line = "Hello, World, 123, ABC";
        String[] result = regexSplitCSVRow.split(line);

        assertEquals(4, result.length);
        assertEquals("Hello", result[0].trim());
        assertEquals("World", result[1].trim());
        assertEquals("123",   result[2].trim());
        assertEquals("ABC",   result[3].trim());
    }

    /**
     * But what if a value needs to contain a comma literal? In this case, we need to use a heavier hammer.
     * For this example, we'll use a regular expression that is defined above.
     */
    @Test
    public void testQuotes() {
        String line = "\"Providence, RI\", 123.456, \"Telson, Nim\"";
        String[] result = regexSplitCSVRow.split(line);

        assertEquals(3, result.length);
        assertEquals("Providence, RI", result[0].trim().replaceAll("\"", ""));
        assertEquals("123.456", result[1].trim().replaceAll("\"", ""));
        assertEquals("Telson, Nim",   result[2].trim().replaceAll("\"", ""));
    }

    /**
     * It gets worse. What if the value needs to, itself, contain double quotes?
     * The convention here is to treat a pair of double-quote characters as a single double-quote literal.
     */
    @Test
    public void testQuotesWithEscaping() {
        String line = "\"Regular expressions are \"\"fun\"\"\", \"However, sometimes they are \"\"useful\"\"\"";
        String[] result = regexSplitCSVRow.split(line);

        assertEquals(2, result.length);
        assertEquals("Regular expressions are fun", result[0].trim().replaceAll("\"", ""));
        assertEquals("However, sometimes they are useful",   result[1].trim().replaceAll("\"", ""));

        // Wait a minute; that's not a careful test. Don't we want to confirm we still have the double-quote literals?
        // The challenge is that the regex above KEEPS all double quote characters. So let's be careful. We'll make a
        // helper function to do the post-processing for us.

        assertEquals("Regular expressions are \"fun\"", postprocess(result[0]));
        assertEquals("However, sometimes they are \"useful\"", postprocess(result[1]));

    }

    /**
     * Elimiate a single instance of leading or trailing double-quote, and replace pairs of double quotes with singles.
     *
     * @param arg the string to process
     * @return the postprocessed string
     */
    public static String postprocess(String arg) {
        return arg
                // Remove extra spaces at beginning and end of the line
                .trim()
                // Remove a beginning quote, if present
                .replaceAll("^\"", "")
                // Remove an ending quote, if present
                .replaceAll("\"$", "")
                // Replace double-double-quotes with double-quotes
                .replaceAll("\"\"", "\"");
    }
}

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Ignore IntelliJ's warning highlights. The point is to understand
 * why we get those warnings!
 */
public class TestEqualsExample_PREP {

    @Test
    public void stringEquality() {
        String s1 = "hi";
        String s2 = "hi";
        // IntelliJ complains about this, telling us to use .equals to compare Strings. But why? == seems to work fine.
        assertTrue(s1 == s2);

        // Let's try this:
        String h = "h";
        String i = "i";
        String hi = h + i;
        assertFalse(s1 == hi);

        // What's going on? The JVM automatically ensures there is only ever one copy of any string literal in memory.
        // A string literal is a quote-delimited string constant like "hi", "h", and "i" above. So the variables <s1>
        // and <s2> refer to the same object in memory, because of this hidden work by the JVM.
        // But the String object that <hi> refers to is constructed from two string literals; it is not itself a
        // string literal. Put another way, the String object that <hi> refers to comes from computation, rather than
        // just being a constant in this source file.

        // It's just not safe to compare Strings using ==, because the JVM can and will allow multiple equivalent
        // strings to exist simultaneously, and == checks whether the objects *are the same object*.

        // Fortunately, .equals() gives us what we need:
        assertTrue(s1.equals(hi));

    }

    @Test
    public void intEquality() {
        // We can compare primitive types, like int, using == because those aren't objects:
        int five = 5;
        int six = 6;
        int elevenA = five + six;
        int elevenB = 11;
        // 11 is always 11
        assertTrue(elevenA == elevenB);

        // This becomes more complicated for "wrapper" classes, like Integer. IntelliJ will complain about this also,
        // but it's useful as a demonstration:
        Integer fiveI = Integer.valueOf(5);
        Integer sixI = Integer.valueOf(6);
        Integer elevenObjectA = fiveI + sixI;
        Integer elevenObjectB = Integer.valueOf(11);
        // These are == because the JVM does another hidden thing: for values between -128 and 127 inclusive,
        //   Integer.valueOf(v) always returns a single canonical Integer object refernece.
        assertTrue(elevenObjectA == elevenObjectB);

        // But as soon as we go above that...
        Integer twoHundredObject = Integer.valueOf(200);
        Integer twoHundredElevenObjectA = twoHundredObject + elevenObjectA;
        Integer twoHundredElevenObjectB = Integer.valueOf(211);
        assertFalse(twoHundredElevenObjectA == twoHundredElevenObjectB);

        // And, again, equals() gives us what we need:
        assertTrue(twoHundredElevenObjectA.equals(twoHundredElevenObjectB));
    }

}

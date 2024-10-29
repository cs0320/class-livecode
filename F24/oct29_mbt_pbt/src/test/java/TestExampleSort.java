import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestExampleSort {

    @Test
    public void testEmpty() {
        // List.of returns an immutable list, but the sort we're testing modifies it.
        List<Integer> items = new ArrayList<>(List.of());
        ExampleSort.bubbleSort(items);
        assertEquals(items, List.of());
    }

    @Test
    public void testInOrderAlready() {
        List<Integer> items = new ArrayList<>(List.of(1,2,3,4,5));
        ExampleSort.bubbleSort(items);
        assertEquals(List.of(1,2,3,4,5), items);
    }

    @Test
    public void reverseOrder() {
        List<Integer> items = new ArrayList<>(List.of(1,2,3,4,5));
        ExampleSort.bubbleSort(items);
        assertEquals(List.of(1,2,3,4,5), items);
    }

    // ... and more test cases ...

    /*
        At this point, we could also fuzz test our sorting method.
        But what *else* can we do with random inputs?

        See this blog post...
        https://randomascii.wordpress.com/2014/01/27/theres-only-four-billion-floatsso-test-them-all/

        This is a pretty clever idea. But we can't "test them all". We can, however, test
        a large number of randomly generated lists in the same way.
    */

    /**
     * Generate a random list of integers
     * @param maxLength the maximum list length (minimum is 0)
     * @param minValue the minimum integer value to generate
     * @param maxValue the maximum integer value to generate
     * @return the randomly generated list
     */
    public List<Integer> randomIntegerList(int maxLength, int minValue, int maxValue) {
        int length = ThreadLocalRandom.current().nextInt(0, maxLength+1);
        List<Integer> result = new ArrayList<>(length);
        for(int idx = 0; idx < length; idx++) {
            result.add(ThreadLocalRandom.current().nextInt(minValue, maxValue+1));
        }
        return result;
    }


    /** Run both the Java library sorting function and Tim's bubbleSort
     *  implementation on a large number of random inputs. Do they produce
     *  the same answer?
     *
     *  This is a kind of "model-based" testing (MBT). We use a known-good artifact
     *  as an oracle of correctness for the artifact under test. This might be:
     *    - an older implementation
     *    - a slower or otherwise unoptimized implementation
     *    - a *partial* implementation for easy cases (in which case, you need
     *      to narrow random generation)
     *    - an actual *model* of the system (see CSCI 1710 Logic for Systems)
     *    - ...
     *
     *  MBT can also mean using a model to guide random input generation. E.g.,
     *  you can use a state machine model to guide sequences of modifications
     *  to a data structure, or to a user interface, etc. This type is often
     *  called "stateful testing". The term MBT is really overloaded!
     */
    @Test
    public void mbtVsLibrarySortIntegers() {
        long numIterations = 10000;

        for(int trial=0;trial<numIterations;trial++) {
            List<Integer> items = randomIntegerList(5, -5, 5);
            List<Integer> copy = new ArrayList<>(items);
            ExampleSort.bubbleSort(items);         // "under-test artifact"
            copy.sort(Comparator.naturalOrder());  // "known-good artifact"
            assertEquals(copy, items);
        }
    }
    ///////////////////////////////////////////////
    // DISCUSSION QUESTION
    // This passes. So what's the danger here?
    // (Scroll down for spoilers.)
    ///////////////////////////////////////////////









    // Suppose we were working with more complex data, like records.
    public record Student(int id, int credits) implements Comparable<Student> {

        // Because this is a record, toString, equals, and hashCode are all
        // implemented for us. But we must implement compareTo ourselves.
        // (The problem we'll discover below is not related to the fact that
        //  comparison is more coarse-grained than equality for this record.)
        @Override
        public int compareTo(Student o) {
            return Integer.compare(this.id(), o.id());
        }
    }

    /**
     * Generate a random list of student record objects.
     * @param maxLength maximum list length (minimum is 0)
     * @param minValue  minimum value to use for both ID and credits
     * @param maxValue  maximum value to use for both ID and credits
     * @return the randomly generated student record list
     */
    public List<Student> randomStudentList(int maxLength, int minValue, int maxValue) {
        int length = ThreadLocalRandom.current().nextInt(0, maxLength+1);
        List<Student> result = new ArrayList<>(length);
        for(int idx = 0; idx < length; idx++) {
            int id = ThreadLocalRandom.current().nextInt(minValue, maxValue+1);
            int credits = ThreadLocalRandom.current().nextInt(minValue, maxValue+1);
            result.add(new Student(id, credits));
        }
        return result;
    }

    // uncomment the test annotation to enable the test and see the problem
    // @Test
    public void mbtVsLibrarySortStudentRecords() {
        long numIterations = 10000;

        for(int trial=0;trial<numIterations;trial++) {
            List<Student> items = randomStudentList(3, -5, 5);
            List<Student> copy = new ArrayList<>(items);
            Collections.sort(items);         // "known-good artifact"
            ExampleSort.bubbleSort(copy);    // "under-test artifact"
            assertEquals(copy, items);
        }
    }

    ///////////////////////////////////////////////
    // DISCUSSION QUESTION
    // (1) Is model-based testing worthless?
    // (2) What can we do?
    // (Scroll down for spoilers.)
    ///////////////////////////////////////////////








    // uncomment the test annotation to enable the test
    // @Test
    public void pbtSortStudentRecords() {
        long numIterations = 10000;

        for(int trial=0;trial<numIterations;trial++) {
            List<Student> items = randomStudentList(3, -5, 5);
            List<Student> copy = new ArrayList<>(items);
            ExampleSort.bubbleSort(copy);    // "under-test artifact"

            // What makes a sort _correct_?
            // EXERCISE: Write some code that compares `items` and `copy`.
        }
    }


}

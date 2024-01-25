import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestExampleSort {

    @Test
    public void testEmpty() {
        // List.of returns an immutable list, but the sort we're testing modifies it.
        List<Integer> items = new ArrayList<>(List.of());
        ExampleSort.sort(items);
        assertEquals(items, List.of());
    }

    @Test
    public void testInOrderAlready() {
        List<Integer> items = new ArrayList<>(List.of(1,2,3,4,5));
        ExampleSort.sort(items);
        assertEquals(List.of(1,2,3,4,5), items);
    }

    @Test
    public void reverseOrder() {
        List<Integer> items = new ArrayList<>(List.of(1,2,3,4,5));
        ExampleSort.sort(items);
        assertEquals(List.of(1,2,3,4,5), items);
    }

    // ... and more test cases ...

    /*
        See this blog post...
        https://randomascii.wordpress.com/2014/01/27/theres-only-four-billion-floatsso-test-them-all/
        This is a pretty clever idea. But we can't "test them all". We can, however, test
        a large number of randomly generated lists in the same way.
    */
    public List<Integer> randomIntegerList(int maxLength, int minValue, int maxValue) {
        int length = ThreadLocalRandom.current().nextInt(0, maxLength+1);
        List<Integer> result = new ArrayList<>(length);
        for(int idx = 0; idx < length; idx++) {
            result.add(ThreadLocalRandom.current().nextInt(minValue, maxValue+1));
        }
        return result;
    }

    @Test
    public void mbtVsLibrarySortIntegers() {
        long numIterations = 10000;

        for(int trial=0;trial<numIterations;trial++) {
            List<Integer> items = randomIntegerList(5, -5, 5);
            List<Integer> copy = new ArrayList<>(items);
            ExampleSort.sort(items);
            copy.sort(Comparator.naturalOrder());
            assertEquals(copy, items);
        }
    }
    // This passes. So what's the danger here?









    // uncomment this to see the problem
    //@Test
    record Student(int id, int credits) implements Comparable<Student> {
        // Because this is a record, toString, equals, and hashCode are all
        // implemented for us.

        @Override
        public int compareTo(Student o) {
            if(this.id() > o.id()) return 1;
            if(o.id() > this.id()) return -1;
            return 0;
        }
    }

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


    //@Test
    public void mbtVsLibrarySortGeolocations() {
        long numIterations = 10000;

        for(int trial=0;trial<numIterations;trial++) {
            List<Student> items = randomStudentList(3, -5, 5);
            List<Student> copy = new ArrayList<>(items);
            ExampleSort.sort(items);
            ExampleSort.selectionSort(copy);
            assertEquals(copy, items);
        }
    }




}

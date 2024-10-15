package edu.brown.cs32.livecode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        // List.of returns something immutable, which is inconvenient...
        List<Integer> exampleList =
                new ArrayList<>(List.of(0, 17, 3, 17, 4, 3, 1, -1));
        ExampleSorter.bubbleSort(exampleList);
        System.out.println(exampleList);

        while(true) {
            List<Integer> in =
                    randomIntegerList(100, -10, 10);
            ExampleSorter.bubbleSort(in);
            // If we didn't crash, we don't care. Keep running!
        }
    }














    /**
     * Generate a random list of integers
     * @param maxLength the maximum list length (minimum is 0)
     * @param minValue the minimum integer value to generate
     * @param maxValue the maximum integer value to generate
     * @return the randomly generated list
     */
    static List<Integer> randomIntegerList(int maxLength, int minValue, int maxValue) {
        int length = ThreadLocalRandom.current().nextInt(0, maxLength+1);
        List<Integer> result = new ArrayList<>(length);
        for(int idx = 0; idx < length; idx++) {
            result.add(ThreadLocalRandom.current().nextInt(minValue, maxValue+1));
        }
        return result;
    }

}
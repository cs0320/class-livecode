package edu.brown.cs32.livecode.feb10;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> myList = new ArrayList<>(Arrays.asList(17, 3, 42, 44, 8, 9, 1, 100));
        System.out.println(myList); // unsorted
        Collections.sort(myList); // SORT THE LIST FOR ME, JAVA!
        System.out.println(myList); // hopefully sorted

        Collections.sort(myList, (a, b) -> -a.compareTo(b)); // SORT THE LIST FOR ME, JAVA!
        System.out.println(myList); // hopefully sorted

        Collections.sort(myList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        }); // SORT THE LIST FOR ME, JAVA!
        System.out.println(myList); // hopefully sorted


    }
}

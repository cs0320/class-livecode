package edu.brown.cs32.live.main;

import edu.brown.cs32.live.diplomacy.Diplomat;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs32.live.diplomacy.Diplomat.RANK;
import edu.brown.cs32.live.diplomacy.PrecedenceComparator;
import edu.brown.cs32.live.sorting.Sorts;

public class Main {
    public static void main(String[] args) {

        // NOTE: List.of makes producing test lists like this more convenient. You don't need to create
        // an empty list and then .add(...) them all individually anymore.
        List<Diplomat> diplomats = List.of(
                new Diplomat(RANK.AMBASSADOR, "Alina"),
                new Diplomat(RANK.MINISTER, "Bryce"),
                new Diplomat(RANK.HIGH_COMMISSIONER, "Catherine"),
                new Diplomat(RANK.MINISTER, "David"),
                new Diplomat(RANK.CHARGE_DAFFAIRES, "Ernie"),
                new Diplomat(RANK.ENVOY, "Francine"),
                new Diplomat(RANK.NUNCIO, "Gerald")
        );
        System.out.println("Original: "+ diplomats);

        // However, there is one snag: List.of returns an *unmodifiable* list, and the sort we're about to use
        //   will modify the list in place. So make a modifiable copy to work with:
        diplomats = new ArrayList<>(diplomats);

        // Sort without nuncios taking precedence
        Sorts.bubbleSort(diplomats, new PrecedenceComparator());
        System.out.println("Sort 1: "+ diplomats);

        // Sort with nuncios taking precedence
        Sorts.bubbleSort(diplomats, new PrecedenceComparator(RANK.NUNCIO));
        System.out.println("Sort 2: "+ diplomats);


    }
}

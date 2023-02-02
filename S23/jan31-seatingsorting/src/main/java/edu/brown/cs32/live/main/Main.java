package edu.brown.cs32.live.main;

import edu.brown.cs32.live.diplomacy.Diplomat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.brown.cs32.live.diplomacy.Diplomat.Rank;
import edu.brown.cs32.live.diplomacy.PrecedenceComparator;
import edu.brown.cs32.live.sorting.Sorts;

public class Main {
    public static void main(String[] args) {

        Diplomat jamesBond = new Diplomat(null, "James");

        // NOTE: List.of makes producing test lists like this more convenient. You don't need to create
        // an empty list and then .add(...) them all individually anymore.
        List<Diplomat> diplomats = List.of(
                new Diplomat(Rank.AMBASSADOR, "Alina"),
                new Diplomat(Rank.MINISTER, "Bryce"),
                new Diplomat(Rank.HIGH_COMMISSIONER, "Catherine"),
                new Diplomat(Rank.MINISTER, "David"),
                new Diplomat(Rank.CHARGE_DAFFAIRES, "Ernie"),
                new Diplomat(Rank.ENVOY, "Francine"),
                new Diplomat(Rank.NUNCIO, "Gerald"),
                jamesBond
        );
        System.out.println("Original: "+ diplomats);


        // However, there is one snag: List.of returns an *unmodifiable* list, and the sort we're about to use
        //   will modify the list in place. So make a modifiable copy to work with:
        diplomats = new ArrayList<>(diplomats);

        // Sort without nuncios taking precedence
        diplomats.sort(new PrecedenceComparator());
        System.out.println("Sort 1: "+ diplomats);

        // Sort with nuncios taking precedence
        diplomats.sort(new PrecedenceComparator(Rank.NUNCIO));
        System.out.println("Sort 2: "+ diplomats);


    }
}


// new Diplomat(null, "James Bond")
package edu.brown.cs32.spring22.livecode.mar17;

import java.util.ArrayList;
import java.util.List;

public class ListNaiveStarSearch {
    private final List<Star> list;

    public ListNaiveStarSearch(List<Star> list) {
        this.list = list;
    }

    public List<Star> getNaiveNearestNeighbors(int k, Star target) {
        if (k == 0 || this.list.isEmpty()) {
            return new ArrayList<>();
        } else {
            List<Star> currentList = this.list;
            currentList.sort((o1, o2) -> {
                double o1Distance = o1.distance(target);
                double o2Distance = o2.distance(target);
                return Double.compare(o1Distance, o2Distance);
            });
            return currentList.subList(0, k);
        }
    }
}

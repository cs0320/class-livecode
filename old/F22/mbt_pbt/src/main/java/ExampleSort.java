import java.util.List;

public class ExampleSort {
    private ExampleSort() {}

    /**
     * Runs an implementation of bubble sort on the input list. The input list
     * will be modified in place, and sorted in ascending order by the natural
     * ordering of its element type (which must implement Comparable).
     * @param items the list to sort in place
     * @param <T> the type of elements in the list
     */
    public static <T extends Comparable<T>> void sort(List<T> items) {
        for(int idx1=0;idx1 < items.size(); idx1++) {
            for(int idx2=idx1+1;idx2 < items.size(); idx2++) {
                if(items.get(idx2).compareTo(items.get(idx1)) < 0) {
                    T swap = items.get(idx2);
                    items.set(idx2, items.get(idx1));
                    items.set(idx1, swap);
                }
            }
        }
    }





    /**
     * An implementation of selection sort, but unlike the usual approach, it
     * builds a sorted sublist from the _right_ side of the list, starting with
     * largest elements. (Suppose this is known correct.)
     * @param items the list to sort
     * @param <T> the type of elements in the list
     */
    public static <T extends Comparable<T>> void selectionSort(List<T> items) {
        // For each index in the list
        for(int idx1=items.size()-1;idx1 > 0; idx1--) {
            // Find the location of the largest element in [0, idx1]
            int largestIdx = idx1;
            for(int idx2=idx1-1;idx2 >= 0; idx2--) {
                if (items.get(idx2).compareTo(items.get(largestIdx)) > 0) {
                    largestIdx = idx2;
                }
            }

            if(largestIdx != idx1)
            {
                T swap = items.get(largestIdx);
                items.set(largestIdx, items.get(idx1));
                items.set(idx1, swap);
            }
        }
    }

}

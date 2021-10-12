package edu.brown.cs32.fall21.Sep28;

/*
  Template for class meeting: September 28, 2021
  CSCI 0320 (Tim)
 */

import java.util.*;

class Record implements Comparable<Record> {
    // Perhaps this split isn't always possible?
    String first;
    String last;

    Record(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public String toString() {
        return this.first+" "+this.last;
    }

    // EXERCISE 1: is this safe?


    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // optim.
        if(!(o instanceof Record)) return false; // o is an Object
        Record record = (Record) o;
        return this.first.equals(record.first) &&
                this.last.equals(record.last);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, last);
    }

    @Override
    public int compareTo(Record o) {
        if(this.last.compareTo(o.last) > 0) return 1;
        if(this.last.compareTo(o.last) < 0) return -1;

        if(this.first.compareTo(o.first) > 0) return 1;
        if(this.first.compareTo(o.first) < 0) return -1;
        return 0;
    }
}

public class Sep28 {

    public static void main(String[] args) {
        Record tim = new Record("Tim", "Nelson");
        Record tim2 = new Record("Tim", "Nelson");
        Record nim = new Record("Nim", "Telson");
        Record alice = new Record("Alice", "Alice");
        Record bob = new Record("Bob", "Bob");
        Record charlie = new Record("Charlie", "Charlie");

        /*Set<Record> ohno = new HashSet<>();
        ohno.add(tim);
        tim.first = "Timothy";*/
        //System.out.println(ohno.contains(tim2));
        //System.out.println(ohno.contains(tim));

        List<Record> lst = new ArrayList(2);
        lst.add(tim); lst.add(nim); lst.add(alice);
        lst.add(bob); lst.add(charlie);
        System.out.println(lst);
        sortSomeRecords(new RecordComparatorByFirstName(false), lst);
        System.out.println(lst);

    }

    static class RecordComparatorByFirstName implements Comparator<Record> {
        final boolean caseMatters;

        RecordComparatorByFirstName(boolean caseMatters) {
            this.caseMatters = caseMatters;
        }

        @Override
        public int compare(Record o1, Record o2) {
            // TODO: modify depending on caseMatters
            if(o1.first.compareTo(o2.first) > 0) return 1;
            if(o1.first.compareTo(o2.first) < 0) return -1;
            return 0;
        }
    }


    public static <T extends Comparable<? super T>> void
    sortSomeRecords(Comparator<T> comp, List<T> lst) {
    //sortSomeRecords2(List<T> lst) {
        for(int i=0; i<lst.size(); i++) {
            for(int j=i+1; j<lst.size(); j++) {
                if(lst.get(j).compareTo(lst.get(i)) < 0) {
                    T swap = lst.get(i);
                    lst.set(i, lst.get(j));
                    lst.set(j, swap);
                }
            }
        }
    }
    public static <T extends Comparable<T>> void sortSomeRecords(List<T> lst) {
        for(int i=0; i<lst.size(); i++) {
            for(int j=i+1; j<lst.size(); j++) {
                // Strings, so cannot use <; must use compareTo
                if(lst.get(j).compareTo(lst.get(i)) < 0) {
                    T swap = lst.get(i);
                    lst.set(i, lst.get(j));
                    lst.set(j, swap);
                }
            }
        }
    }
}

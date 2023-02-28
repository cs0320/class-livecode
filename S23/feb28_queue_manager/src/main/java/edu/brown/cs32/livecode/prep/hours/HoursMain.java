package edu.brown.cs32.livecode.prep.hours;

import java.util.ArrayList;
import java.util.Iterator;

class Student {
    final String name;
    Student(String name) {
        this.name = name;
    }
}

class TA {
    final String name;

    TA(String name) {
        this.name = name;
    }
    public void dispatchToTA(Student s) {
        System.out.println(s.name+" dispatched to "+this.name);
    }
}


public class HoursMain {
    public static void main(String[] args) {
        Iterator<Student> studentIterator = new ArrayList<Student>().iterator();
        HoursDispatcherBasic disp = new HoursDispatcherBasic(studentIterator, "Test");
        // At this point we'd add students etc.
    }
}

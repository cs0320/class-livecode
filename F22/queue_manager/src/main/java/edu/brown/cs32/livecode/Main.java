package edu.brown.cs32.livecode;

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


public class Main {
    public static void main(String[] args) {
        Iterator<Student> studentIterator = new ArrayList<Student>().iterator();
        HoursDispatcher disp = new HoursDispatcher(studentIterator, "Test");
        // At this point we'd add students etc.
    }
}

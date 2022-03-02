package edu.brown.cs32.livecode.feb17;

import java.util.ArrayList;
import java.util.Collection;

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
        HoursDispatcher disp = new HoursDispatcher(new ArrayList<Student>().iterator(), "Test");
        //Collection<String> view = disp.getOnDutyTAsView();

    }
}

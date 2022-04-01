package edu.brown.cs32.spring22.livecode.feb15;

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
}

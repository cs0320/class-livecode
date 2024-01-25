package edu.brown.cs32.livecode.live.hours;

import java.util.Objects;

/**
 * We could have done this with a record, but we might modify these more later.
 *
 * Notice how much longer the code is, though, just with IntelliJ-generated methods.
 */
public class Student {

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }

    final String name;

    Student(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

package edu.brown.cs32.livecode.live.hours;

import java.util.Objects;

/**
 * We could have done this with a record, but we might modify these more later.
 *
 * Notice how much longer the code is, though, just with IntelliJ-generated methods.
 */
public class TA {
    @Override
    public String toString() {
        return "TA{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TA ta = (TA) o;
        return Objects.equals(name, ta.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    final String name;

    TA(String name) {
        this.name = name;
    }

    public void dispatchToTA(Student s) {
        System.out.println(s.name + " dispatched to " + this.name);
    }
}

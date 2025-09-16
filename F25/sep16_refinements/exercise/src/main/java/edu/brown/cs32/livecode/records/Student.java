package edu.brown.cs32.livecode.records;

/** A student-at-hours record. Notice that we can customize the functionality
 *  that the record invisibly generates for us: we're replacing toString(). */
public record Student(String name, String projectID, Integer problem_needs_ms) {

    @Override
    public String toString() {
        return this.name;
    }
}

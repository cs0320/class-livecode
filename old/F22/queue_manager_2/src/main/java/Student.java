public class Student {
    final private String name;
    final private int problem_needs_ms;
    Student(String name, int problem_needs_ms) {
        this.name = name;
        this.problem_needs_ms = problem_needs_ms;
    }
    Student(String name) {
        this.name = name;
        this.problem_needs_ms = 1000;
    }
    public String getName() {
        return name;
    }
    public int getProblemTimeMilliseconds() { return problem_needs_ms; }

    @Override
    public String toString() {
        return name;
    }
}

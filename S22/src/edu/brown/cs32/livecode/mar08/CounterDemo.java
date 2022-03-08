package edu.brown.cs32.livecode.mar08;

class ThingCounter {
    private static long counterForClass = 0;
    private long counterForObject = 0;

    void increment() {
        counterForObject++;
        counterForClass++;
    }
    public long counter() {
        return counterForObject;
    }
    public long allCounter() {
        return counterForClass;
    }
}
public class CounterDemo {
    public static void main(String[] args) {
        ThingCounter c1 = new ThingCounter();
        ThingCounter c2 = new ThingCounter();
        c1.increment();
        c2.increment();
        System.out.println(c1.counter());
        System.out.println(c2.counter());
        System.out.println(c1.allCounter());
        System.out.println(c2.allCounter());
    }
}
package edu.brown.cs32.livecode;

/**
 * What's going on?
 */
public class Main {
    public static void main(String[] args) {
        Animal a = new Dog("Boatswain", true);
        // Java will dispatch this correctly: at runtime, it knows that the object is a Dog
        System.out.println(a.speak());
        // However, Java will call the Animal variant of feed here:
        feed(a);

        // What experiments would you try to better understand what's going on?
    }

    static void feed(Dog d) {
        System.out.println("Buy dog food."); }
    static void feed(Cat c) {
        System.out.println("Buy cat food."); }
    static void feed(Animal a) {
        System.out.println("I don't know what kind of animal this is."); }
}
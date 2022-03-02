package edu.brown.cs32.fall21.Nov04Prep;

public class AnimalWalker {

    public static String walk(Animal animal) {
        return "shouldn't reach here";
    }

    public static String walk(Dog dog) {
        return "A walk? A WALK! OUTSIDE! OUTSIDE IN THE WORLD! SO EXCITING!!!!!!!!";
    }

    public static String walk(Human human) {
        return "Ugh but it's still morning, why";
    }
}

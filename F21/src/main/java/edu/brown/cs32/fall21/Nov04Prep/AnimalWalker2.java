package edu.brown.cs32.fall21.Nov04Prep;

public class AnimalWalker2 {
    public static String walk(Animal animal) {
        if(animal instanceof Human) {
            return "Ugh but it's still morning, why";
        } else if(animal instanceof Dog) {
            return "A walk? A WALK! OUTSIDE! OUTSIDE IN THE WORLD! SO EXCITING!!!!!!!!";
        } else
            throw new UnsupportedOperationException("Unknown animal type");
    }
}

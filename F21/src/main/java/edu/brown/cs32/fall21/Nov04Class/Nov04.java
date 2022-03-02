package edu.brown.cs32.fall21.Nov04Class;

public class Nov04 {
    public static void main(String[] args) {
        Animal byron = new Human("George Gordon Byron",
                "When some proud Son of Man returns to Earth,\n" +
                "Unknown to Glory but upheld by Birth,\n" +
                "The sculptor's art exhausts the pomp of woe,\n" +
                "And storied urns record who rests below.\n" +
                "When all is done, upon the Tomb is seen\n" +
                "Not what he was, but what he should have been.");
        Dog boatswain = new Dog("Boatswain", true, false);
        byron.addFriend(boatswain);
        boatswain.addFriend(byron);

        System.out.println(boatswain.speak());
        System.out.println(byron.speak());

        // At this point, the Animal, Dog, and Human classes are unchangeable for specific features.
        //       You might be able to ask the developer to add *ONE THING* to support *many* different new features.

        // Your challenge: add travel functionality. Let's start with the ability to walk() somewhere.
        //    In the future, we might want to add swim() etc., so think about extensibility.

        System.out.println(Walker.walk(byron));
        System.out.println(Walker.walk(boatswain));
    }

}

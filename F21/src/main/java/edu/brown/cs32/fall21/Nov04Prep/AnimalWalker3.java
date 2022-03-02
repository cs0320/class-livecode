package edu.brown.cs32.fall21.Nov04Prep;

/**
 *  Walking an animal should return the appropriate string
 *  (Thus, here, we know the return type)
 */
public class AnimalWalker3 implements AnimalVisitor<String> {
    @Override
    public String visit(Dog dog) {
        return "A walk? A WALK! OUTSIDE! OUTSIDE IN THE WORLD! SO EXCITING!!!!!!!!";
    }

    @Override
    public String visit(Human human) {
        return "Ugh but it's still morning, why";
    }
}

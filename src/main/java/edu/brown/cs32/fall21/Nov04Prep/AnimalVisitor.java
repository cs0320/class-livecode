package edu.brown.cs32.fall21.Nov04Prep;

/**
  Visitor interface for the Animal class hierarchy.
  Pro:
    - get to keep guarantees of typechecking (unlike instanceof)
    - exhaustiveness will be enforced by subtyping
  Con:
    - If any other types of Animal are added, we need a new method here.
    -  ...but is this really a con? Discuss. :-)

  Note: the gang of four book (in our online reserves!) defines a sort of prototype version
        of this pattern in C++, where visitors return void and accumulate desired data via mutation.
        In 0320, we use generics to implement a more civilized, stateless, visitor.

  In Scala, or other languages that support exhaustive matching on algebraic datatypes,
  this sort of thing becomes much simpler to write. Remember, there are at least *2* reasons visitors
  work this way (not just in Java, but in many languages):
     - respecting the open/closed principle by allowing extension without modification; and
     - working around the limitations of the language without losing the protection of type checking.

 */
public interface AnimalVisitor<R> {
    R visit(Dog dog);
    R visit(Human human);
}

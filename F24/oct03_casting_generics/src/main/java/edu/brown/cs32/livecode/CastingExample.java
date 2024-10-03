package edu.brown.cs32.livecode;

abstract class SpecialException extends Exception {}
/** This exception type should be translated to different abstraction */
class SomeExceptionToChange extends SpecialException {}
/** This exception type should be logged and execution should continue */
class SomeExceptionToLog extends SpecialException {}

public class CastingExample {

    /** Which will it throw? Who knows! */
    static void doSomething() throws SpecialException {
        if(Math.random() > 0.5) {
            throw new SomeExceptionToChange();
        } else {
            throw new SomeExceptionToLog();
        }
    }

    public static void main(String[] args) {
        try {
            doSomething();
        } catch(SpecialException e) {
            // What can we do here?
        }

    }
}

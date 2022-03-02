package edu.brown.cs32.fall21.Nov04Class;

public class Walker {

    static String walk(Animal h) {
        if(h instanceof Dog) {
            return "yay";
        } else if(h instanceof Human) {
            return "nooooo";
        } else
            throw new UnsupportedOperationException();
    }

}

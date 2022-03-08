package edu.brown.cs32.livecode.mar08;

public class StaticClassDemo {
    int value = 0;
    static int staticValue = 0;

    static class NestedClass {
        void increment() {
        //    value++;
            staticValue++;
        }
    }
}

class Foo {
    public static void main(String[] args) {
        StaticClassDemo.NestedClass c = new StaticClassDemo.NestedClass();
    }
}
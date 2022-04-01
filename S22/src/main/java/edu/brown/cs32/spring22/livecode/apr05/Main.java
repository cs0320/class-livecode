package edu.brown.cs32.spring22.livecode.apr05;

// Remember to add the Maven dependency! You may also
// need to option-enter (alt-enter) and pick a
// "Maven: add ... to classpath" option.
import io.github.bonigarcia.wdm.WebDriverManager;

public class Main {
    public static void main(String[] args) {
        WebDriverManager.firefoxdriver().setup();
    }
}

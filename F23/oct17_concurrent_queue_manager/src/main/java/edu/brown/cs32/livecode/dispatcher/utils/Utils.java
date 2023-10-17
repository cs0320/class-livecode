package edu.brown.cs32.livecode.dispatcher.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    private Utils() {} // no constructor
    final static private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("hh:mm:ss:SSS");
    public static String timestamp() {
        return fmt.format(LocalTime.now());
    }

}

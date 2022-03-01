package edu.brown.cs32.spring22.Mar01Prep;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    private Utils() {} // no constructor
    final static private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("hh:mm:ss");
    public static String timestamp() {
        return fmt.format(LocalTime.now());
    }

}

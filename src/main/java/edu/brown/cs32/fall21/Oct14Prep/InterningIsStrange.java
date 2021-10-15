package edu.brown.cs32.fall21.Oct14Prep;

public class InterningIsStrange {
    public static void main(String[] args) {
        System.out.println("----- new Integer -----");
        System.out.println(new Integer(100) == new Integer(100));
        System.out.println(new Integer(200) == new Integer(200));
        System.out.println("----- valueOf -----");
        System.out.println(Integer.valueOf(100) == Integer.valueOf(100));
        System.out.println(Integer.valueOf(200) == Integer.valueOf(200));
        System.out.println("----- Integer x = -----");
        Integer a1 = 100;
        Integer b1 = 100;
        System.out.println(a1 == b1);
        Integer a2 = 200;
        Integer b2 = 200;
        System.out.println(a2 == b2);
    }
}

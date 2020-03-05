package com.javacode;

/**
 * Thinking in java 操作符一章的示例
 */
public class ThinkingInJavaDemoOperators {
    public static void main(String[] args) {
        ThinkingInJavaDemoOperators thinkingInJavaDemoOperators = new ThinkingInJavaDemoOperators();
        thinkingInJavaDemoOperators.shiftOperatorsPage77();
    }

    public void shiftOperatorsPage77(){
        int i = -1;
        System.out.println(Integer.toBinaryString(i));
        i >>>= 10;
        System.out.println(Integer.toBinaryString(i));

        long l = -1;
        System.out.println(Long.toBinaryString(l));
        l >>>= 10;
        System.out.println(Long.toBinaryString(l));

        short s = -1;
        System.out.println(Integer.toBinaryString(s));
        s >>>= 10;
        System.out.println(Integer.toBinaryString(s));

        byte b = -1;
        System.out.println(Integer.toBinaryString(b));
        b >>>= 10;
        System.out.println(Integer.toBinaryString(b));
        b = -1;
        System.out.println(Integer.toBinaryString(b));
        System.out.println(Integer.toBinaryString(b >>> 10));
    }
}

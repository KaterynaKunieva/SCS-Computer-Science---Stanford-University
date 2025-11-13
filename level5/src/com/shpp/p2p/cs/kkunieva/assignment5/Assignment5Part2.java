package com.shpp.p2p.cs.kkunieva.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.util.ArrayList;

/**
 * A program that takes two non-negative integers, represented as strings, and prints their sum.
 */
public class Assignment5Part2 extends TextProgram {
    private static final int RADIX = 10;

    public void run() {
        /* Sit in a loop, reading numbers and adding them. */
        while (true) {
            String n1 = readLine("Enter first number: ");
            String n2 = readLine("Enter second number: ");
            println(n1 + " + " + n2 + " = " + addNumericStrings(n1, n2));
            println();
        }
    }

    /**
     * Returns the integer value of the digit at the specified index in the string.
     *
     * @param str The full numeric string.
     * @param i   The index of the desired digit.
     * @return The integer value of the digit, or 0 if the index is out of bounds of the string.
     */
    private int getIntFromString(String str, int i) {
        return i >= 0 && i < str.length() ? str.charAt(i) - '0' : 0;
    }

    /**
     * Given two string representations of nonnegative integers, adds the
     * numbers represented by those strings and returns the result.
     *
     * @param n1 The first number.
     * @param n2 The second number.
     * @return A String representation of n1 + n2
     */
    protected String addNumericStrings(String n1, String n2) {
        ArrayList<String> resSum = new ArrayList<>();
        int transit = 0; // carry-over to the next column

        // iterates over 2 numbers starting from end
        for (int i = n1.length() - 1, j = n2.length() - 1; i >= 0 || j >= 0; i--, j--) {
            int add1 = getIntFromString(n1, i);
            int add2 = getIntFromString(n2, j);

            int sum = add1 + add2 + transit;
            transit = sum / RADIX;
            resSum.add(sum % RADIX + "");
        }
        if (transit != 0) resSum.add(transit + ""); // final carry-over

        return String.join("", resSum.reversed()); // digits were added in reverse order => reverse
    }
}
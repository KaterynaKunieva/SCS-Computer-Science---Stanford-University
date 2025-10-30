package com.shpp.p2p.cs.kkunieva.assignment3;

import com.shpp.cs.a.console.TextProgram;


/**
 * Implements the Collatz conjecture (from Hofstadter's book Gödel, Escher, Bach).
 * The user enters a positive integer n (>0), and the program repeatedly:
 * - divides n by 2 if n is even
 * - multiplies n by 3 and adds 1 if n is odd
 * until n becomes 1.
 */

public class Assignment3Part2 extends TextProgram {
    /**
     * Reads a positive integer from the user and applies the Collatz sequence,
     * printing each step until the sequence reaches 1.
     */
    @Override
    public void run() {
        int n = readInt("Enter a number: ");
        do {
            if (isEven(n)) {
                println(n + " is even so I take half: " + (n /= 2));
            } else {
                println(n + " is odd so I make 3n + 1: " + (n = 3 * n + 1));
            }
        }
        while (n != 1);
        print("End.");
    }

    /**
     * Checks if a number is even.
     *
     * @param n the number to check
     * @return true if n is even, false otherwise
     */
    private boolean isEven(int n) {
        return n % 2 == 0;
    }
}
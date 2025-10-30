package com.shpp.p2p.cs.kkunieva.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * Implements a method to raise a number to an integer power.
 */

public class Assignment3Part3 extends TextProgram {
    @Override
    public void run() {
    }

    /**
     * Raises a number to the given integer exponent manually.
     * Handles positive and negative exponents, returning 1 for exponent 0.
     *
     * @param base     the number to raise
     * @param exponent the integer exponent
     * @return the result of base raised to exponent
     */
    public double raiseToPower(double base, int exponent) {
        double result = 1.0;
        if (exponent > 0) {
            for (int e = 1; e <= exponent; e++) {
                result *= base;
            }
        } else if (exponent < 0) {
            for (int e = exponent; e < 0; e++) {
                result /= base;
            }
        }
        return result;
    }
}
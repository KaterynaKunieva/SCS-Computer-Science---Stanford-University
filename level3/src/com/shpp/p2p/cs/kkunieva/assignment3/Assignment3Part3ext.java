package com.shpp.p2p.cs.kkunieva.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * Tolerance check result of a method raiseToPower with the Java's Math.pow method.
 */

public class Assignment3Part3ext extends TextProgram {
    // A small epsilon value for tolerance check
    private static final double EPSILON = 1e-12;

    // Algorithm to test
    private static final Assignment3Part3 algorithm = new Assignment3Part3();

    @Override
    public void run() {
        testPowerMethod(0, 0);
        testPowerMethod(0, -1);
        testPowerMethod(2, 0);
        testPowerMethod(1, 0);
        testPowerMethod(1, 1);
        testPowerMethod(2, -2);
        testPowerMethod(2, -7);
        testPowerMethod(10, 7);
        testPowerMethod(2.2, 7);
        testPowerMethod(-2.2, -5);
    }

    /**
     * Tests the raiseToPower method for a specific base and exponent.
     * Compares the result with Math.pow and prints whether the test passed.
     *
     * @param base     the base number
     * @param exponent the exponent
     */
    private void testPowerMethod(double base, int exponent) {
        double testedAlgorithm = algorithm.raiseToPower(base, exponent);
        double goalValue = Math.pow(base, exponent);
        if (Math.abs(testedAlgorithm - goalValue) < EPSILON) {
            println(String.format("OK: %s^(%s)=%s == %s", base, exponent, testedAlgorithm, goalValue));
        } else {
            println(String.format("NOT OK: %s^(%s)=%s. Correct: %s", base, exponent, testedAlgorithm, goalValue));
        }
    }
}
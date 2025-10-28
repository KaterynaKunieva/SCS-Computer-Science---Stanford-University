package com.shpp.p2p.cs.kkunieva.assignment2;

import com.shpp.cs.a.console.TextProgram;

/**
 * Finds the real roots of the quadratic equation of the form:
 * a*(x^2) + b*x + c = 0, where a != 0.
 * Solution based on the discriminant.
 */
public class Assignment2Part1 extends TextProgram {
    private double a, b, c;     // coefficients
    private double x1, x2;      // roots

    public void run() {
        getParameters();
        if (a == 0) {
            println("This equation is not quadratic.");
        } else {
            calculateRoots();
            printRoots();
        }
    }

    /**
     * Reads the coefficients a, b, c from the console input and
     * stores them in corresponding instance fields.
     */
    private void getParameters() {
        a = getDoubleParameter("a");
        b = getDoubleParameter("b");
        c = getDoubleParameter("c");
    }

    /**
     * Asks the user to enter numeric value for parameter.
     *
     * @param parameterName the name of the parameter to request.
     * @return the numeric value entered by the user.
     */
    private double getDoubleParameter(String parameterName) {
        return readDouble(String.format("Please enter %s: ", parameterName));
    }

    /**
     * Calculates the discriminant.
     *
     * @return the discriminant value, computed as (b^2) - 4ac.
     */
    private double calculateDiscriminant() {
        return Math.pow(b, 2) - 4 * a * c;
    }

    /**
     * Computes the real roots of the quadratic equation based on the discriminant and
     * stores them in {@code x1} and {@code x2}.
     */
    private void calculateRoots() {
        double discriminant = calculateDiscriminant();

        if (discriminant > 0) {
            // 2 different real roots
            x1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            x2 = (-b - Math.sqrt(discriminant)) / (2 * a);
        } else if (discriminant == 0) {
            // 2 same real roots
            x1 = x2 = (-b / (2 * a));
        } else if (discriminant < 0) {
            // no real roots
            x1 = x2 = Double.NaN;
        }
    }

    /**
     * Prints message with roots to console indicating whether there are two, one, or no real roots.
     */
    private void printRoots() {
        if (Double.isNaN(x1)) {
            println("There are no real roots");
        } else if (x1 == x2) {
            println("There is one root: " + x1);
        } else {
            println("There are two roots: " + x1 + " and " + x2);
        }
    }
}

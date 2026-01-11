package com.shpp.p2p.cs.kkunieva.assignment11.mathActions;

/**
 * Functional interface for lambda-expressions implementations of math actions.
 */
@FunctionalInterface
public interface MathExecutable {

    /**
     * Executes lambda-expression on args.
     * @param args array of args in sequential order.
     * @return result of expression.
     */
    double execute(double[] args);
}
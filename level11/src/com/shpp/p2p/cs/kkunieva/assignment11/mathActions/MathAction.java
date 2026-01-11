package com.shpp.p2p.cs.kkunieva.assignment11.mathActions;

/**
 * Contract of mathematical actions (functions, operators).
 */
public interface MathAction {

    /**
     * Getter for priority.
     * @return the priority of action.
     */
    int getPriority();

    /**
     * Getter for amount of arguments in action.
     * @return amount of arguments.
     */
    int getArgAmount();

    /**
     * Executes math action on specified arguments
     *
     * @return result of executing mathematical action.
     */
    double apply(double[] args);

    /**
     * Getter for string representation
     * @return id (string representation)
     */
    String getIdentifier();
}
package com.shpp.p2p.cs.kkunieva.assignment10.evaluators;

import java.util.Map;

/**
 * Contract for Algorithm to use in Calculator class as evaluator
 */
public interface Evaluator {

    /**
     * Calculates formula with variables.
     * @param formula formula to calculate, must be without spaces and using dot for point numbers.
     * @param variables map like variableName: variableValue.
     * @return calculated value, if input is valid.
     */
    double evaluate(String formula, Map<String, Double> variables);
}

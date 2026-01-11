package com.shpp.p2p.cs.kkunieva.assignment11;

import com.shpp.p2p.cs.kkunieva.assignment11.evaluators.Evaluator;
import com.shpp.p2p.cs.kkunieva.assignment11.validators.FormulaValidator;

import java.util.Map;

/**
 * Universal calculator, parameterized by the algorithm.
 */
public class Calculator {

    private final FormulaValidator formulaValidator;
    private final Evaluator evaluator;

    /**
     * Constructor for calculator.
     * @param evaluator instance of class, that implements algorithm.
     */
    public Calculator(Evaluator evaluator) {
        this.evaluator = evaluator;
        this.formulaValidator = new FormulaValidator();
    }

    /**
     * Parses and validates formula, runs calculations.
     *
     * @param formula formula with parameters, with any amount of spaces, supports both . and , for point number.
     * @param variables map like parameterName: parameterValue.
     * @return results of calculations, if input is valid.
     */
    public double calculate(String formula, Map<String, Double> variables) {
        String normalizedFormula = formulaValidator.validateAndNormalize(formula);
        return evaluator.evaluate(normalizedFormula, variables);
    }
}

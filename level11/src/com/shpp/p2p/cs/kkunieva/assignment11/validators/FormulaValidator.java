package com.shpp.p2p.cs.kkunieva.assignment11.validators;

/**
 * Validator and normalizer for raw input string formula
 */
public class FormulaValidator {

    /**
     * Allowed symbols in formula: letters, digits, operators and parentheses
     */
    private static final String FORMULA_VALID_SYMBOLS = "^[a-zA-Z\\d.^*/+\\-()]+$";

    /**
     * Formula can't start from operators, except minus
     */
    private static final String INVALID_START_OPERATORS = "^[\\^*/].*";

    /**
     * Formula can't contain one by one (consecutive) operators, except unary minus
     */
    private static final String CONSECUTIVE_OPERATORS = ".*(" +
            "[\\^/*+]{2,}|" +           // allows unary minus after other signs
            "-[\\^/*+]|" +              // not allows unary minus before other signs
            "[\\^/*+-]{3,}" +           // not allows any sequence of 3+ operators
            ").*";

    /**
     * Normalizes and validates formula
     *
     * @param formula formula to parse
     * @return normalizes formula, if valid
     */
    public String validateAndNormalize(String formula) {
        formula = normalizeFormula(formula);
        validateFormula(formula);
        return formula;
    }

    /**
     * Normalizes formula, removes spaces and make sure point numbers use . (not ,).
     *
     * @param formula formula to normalize
     * @return normalized formula
     */
    private String normalizeFormula(String formula) {
        return formula
                .replace(" ", "")
                .replace(",", ".");
    }

    /**
     * Validates formula using specified regular expressions in constant.
     * Throws exception if formula:
     * - is empty;
     * - contains invalid symbols;
     * - starts with an invalid operator;
     * - contains consecutive operators;
     * - contains parts with missed operators (like 2a);
     * - contains not closed parentheses .
     *
     * @param formula formula to validate
     */
    private void validateFormula(String formula) {
        if (formula.isEmpty()) {
            throw new IllegalArgumentException("The formula is empty");
        } else if (!formula.matches(FORMULA_VALID_SYMBOLS)) {
            throw new IllegalArgumentException("Formula %s contains invalid symbols".formatted(formula));
        } else if (formula.matches(INVALID_START_OPERATORS)) {
            throw new IllegalArgumentException("Formula %s starts with an invalid operator".formatted(formula));
        } else if (formula.matches(CONSECUTIVE_OPERATORS)) {
            throw new IllegalArgumentException("Formula %s contains consecutive operators".formatted(formula));
        }
        checkParenthesesBalance(formula);
    }

    /**
     * Counts parentheses in formula and throws exception if parentheses are not valid.
     *
     * @param formula formula to validate.
     */
    private void checkParenthesesBalance(String formula) {
        int balance = 0;
        for (char c : formula.toCharArray()) {
            if (c == '(') balance++;
            if (c == ')') balance--;
            if (balance < 0) {
                throw new IllegalArgumentException("In formula %s closed parenthesis before the open one".formatted(formula));
            }
        }
        if (balance != 0) {
            throw new IllegalArgumentException("Formula %s contains unbalanced parentheses".formatted(formula));
        }
    }
}

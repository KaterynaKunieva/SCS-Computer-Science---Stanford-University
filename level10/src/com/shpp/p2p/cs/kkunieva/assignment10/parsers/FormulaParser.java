package com.shpp.p2p.cs.kkunieva.assignment10.parsers;

/**
 * Parser and validator for String formula
 */
public class FormulaParser {

    /**
     * Allowed symbols in formula: letters, digits, operators and braces
     */
    private static final String FORMULA_VALID_SYMBOLS = "^[a-zA-Z\\d.^*/+\\-()]+$";

    /**
     * Formula can't start from operators, except minus
     */
    private static final String INVALID_START_OPERATORS = "^[\\^*/+].*";

    /**
     * Formula can't contain one by one (consecutive) operators
     */
    private static final String CONSECUTIVE_OPERATORS = ".*[\\^*/+\\-]{2,}.*";

    /**
     * Not allowed format: 2a, 3b (missed operator between operands)
     */
    private static final String MISSED_OPERATOR = ".*(\\d[a-zA-Z]|[a-zA-Z]\\d).*";

    /**
     * Normalizes and validates formula
     *
     * @param formula
     * @return normalizes formula, if valid
     */
    public String parse(String formula) {
        formula = normalizeFormula(formula);
        validateFormula(formula);
        return formula;
    }

    /**
     * Normalizes formula, removes spaces and make sure point numbers use . (not ,).
     * @param formula
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
     * - contains not closed braces.
     * @param formula
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
        } else if (formula.matches(MISSED_OPERATOR)) {
            throw new IllegalArgumentException("In formula %s missed operator between digit and variable"
                                                       .formatted(formula));
        }
        checkBracesBalance(formula);
    }

    /**
     * Counts braces in formula and throws exception if braces are not valid.
     * @param formula formula to validate.
     */
    private void checkBracesBalance(String formula) {
        int balance = 0;
        for (char c : formula.toCharArray()) {
            if (c == '(') balance++;
            if (c == ')') balance--;
            if (balance < 0) {
                throw new IllegalArgumentException("In formula %s closed brace before open one".formatted(formula));
            }
        }
        if (balance != 0) {
            throw new IllegalArgumentException("Formula %s contains unbalanced braces".formatted(formula));
        }
    }
}

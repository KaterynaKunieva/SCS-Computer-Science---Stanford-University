package com.shpp.p2p.cs.kkunieva.assignment10.evaluators;

import java.util.ArrayList;
import java.util.Map;

/**
 * Abstract implementation of Evaluator with common constants and methods for all algorithms.
 */
public abstract class AbstractEvaluator implements Evaluator {

    /**
     * Priorities of operation. Where 3 is higher and 1 is lower priority.
     */
    protected static final Map<String, Integer> OPERATORS_PRIORITIES = Map.of(
            "^", 3,
            "*", 2,
            "/", 2,
            "-", 1,
            "+", 1
    );

    /**
     * Right-associative operators
     */
    protected static final String RIGHT_ASSOCIATIVE_OPERATORS = "^";

    /**
     * Variable name can contain letters and underline.
     */
    private static final String VARIABLE_REGEX = "[A-Za-z_]";

    /**
     * Splits formula into logical tokens: operands, including unary minus in braces and operators.
     * Throws exception, if formula contains braces for prioritizing expressions.
     *
     * @param formula formula to translate.
     * @return list of formula tokens in the same order, without braces near unary minus.
     */
    protected ArrayList<String> tokenize(String formula) {
        ArrayList<String> tokens = new ArrayList<>();
        int expressionLength = formula.length();
        boolean insideBraces = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < expressionLength; i++) {
            char c = formula.charAt(i);
            if (c == '(') {
                insideBraces = true;
                endToken(sb, tokens);
            } else if (c == ')') {
                insideBraces = false;
                endToken(sb, tokens);
            } else if (isDigit(c) || isVariable(c) || isUnaryMinus(c, sb, tokens)) {
                sb.append(c);
            } else if (isOperator(c)) {
                if (insideBraces) {
                    throw new IllegalArgumentException("Braces for expressions are not supported");
                }
                endToken(sb, tokens);
                sb.append(c);
                endToken(sb, tokens); // operator is 1 only symbol => end of current token
            }
        }
        endToken(sb, tokens); // the last one
        return tokens;
    }

    /**
     * Applies arithmetic operator on operands, considering associativity.
     *
     * @param operator      operator to apply.
     * @param operandFirst  first operand
     * @param operandSecond second operand
     * @return result of arithmetic operation.
     */
    protected double applyOperator(String operator, double operandFirst, double operandSecond) {
        return switch (operator) {
            case "^" -> Math.pow(operandFirst, operandSecond);
            case "+" -> operandFirst + operandSecond;
            case "-" -> operandFirst - operandSecond;
            case "*" -> operandFirst * operandSecond;
            case "/" -> operandFirst / operandSecond;
            default -> throw new IllegalArgumentException("Operator %s not found".formatted(operator));
        };
    }

    /**
     * Checks if character is valid part of variable.
     *
     * @param c character to check.
     * @return true, if c is valid part of variable; otherwise false.
     */
    protected boolean isVariable(char c) {
        return (c + "").matches(VARIABLE_REGEX);
    }

    /**
     * Checks if character is operator
     *
     * @param c character to check.
     * @return true, if character is valid operator; otherwise false.
     */
    protected boolean isOperator(char c) {
        return isOperator(c + "");
    }

    /**
     * Checks if string is operator
     *
     * @param c string to check.
     * @return true, if string is valid operator; otherwise false.
     */
    protected boolean isOperator(String c) {
        return OPERATORS_PRIORITIES.containsKey(c);
    }

    /**
     * Checks if character is part of digit.
     *
     * @param c character to check.
     * @return true, if character is part of digit; otherwise false.
     */
    protected boolean isDigit(char c) {
        return Character.isDigit(c) || c == '.';
    }

    /**
     * Adds accumulated string from StringBuilder to list of tokens and cleans StringBuilder to start next one.
     *
     * @param sb     StringBuilder with current token value
     * @param tokens General list of tokens
     */
    private void endToken(StringBuilder sb, ArrayList<String> tokens) {
        if (!sb.isEmpty()) {
            tokens.add(sb.toString());
            sb.setLength(0);
        }
    }

    /**
     * Checks if character is unary minus.
     *
     * @param c      character to check
     * @param sb     StringBuilder with current token value
     * @param tokens General list of tokens
     */
    private boolean isUnaryMinus(char c, StringBuilder sb, ArrayList<String> tokens) {
        return c == '-'
                && sb.isEmpty() // not in the center of some token
                && (tokens.isEmpty() // in the start of formula
                        || isOperator(tokens.getLast()) // after operator
                        || tokens.getLast().equals("(") // after opening brace
        );
    }
}

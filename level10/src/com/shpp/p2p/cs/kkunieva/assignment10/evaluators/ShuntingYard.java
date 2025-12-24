package com.shpp.p2p.cs.kkunieva.assignment10.evaluators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Implementation of Shunting Yard algorithm, based on RPN (Reverse Polish Notation).
 * Has mechanism of caching formulas and their RPN.
 */
public class ShuntingYard extends AbstractEvaluator {

    /**
     * Map for caching formulas and RPN.
     */
    private static final Map<String, ArrayList<Object>> cache = new HashMap<>();

    /**
     * Constructor to create instance of ShuntingYard
     */
    public ShuntingYard() {
    }

    /**
     * Calculates formula with variables.
     *
     * @param formula   formula to calculate, must be without spaces and using dot for point numbers.
     * @param variables map like variableName: variableValue.
     * @return calculated value, if input is valid.
     */
    @Override
    public double evaluate(String formula, Map<String, Double> variables) {
        ArrayList<Object> RPN = getRPNWithCache(formula);
        return calculateResult(RPN, variables);
    }

    /**
     * Calculates result of expression RPN, filling variable values based on provided map.
     * Throws exception, if map doesn't contain all variables.
     *
     * @param RPN       Expression in Reverse Polish Notation
     * @param variables Map like variableName: variableValue.
     * @return calculated result, if input is valid.
     */
    private Double calculateResult(ArrayList<Object> RPN, Map<String, Double> variables) {
        Stack<Double> operands = new Stack<>();
        for (Object token : RPN) {
            if (token instanceof Double) {
                operands.push((Double) token);
            } else if (isOperator(token.toString())) {
                double second = operands.pop();
                double first = operands.pop();
                operands.push(applyOperator(token.toString(), first, second));
            } else { // variable
                String tokenString = token.toString();
                if (tokenString.startsWith("-")) {
                    tokenString = tokenString.substring(1);
                }
                Double value = variables.get(tokenString);
                if (value == null) {
                    throw new IllegalArgumentException("Variable not found: " + token);
                }
                operands.push(value);
            }
        }
        return operands.pop();
    }

    /**
     * Return formula in RPN from cache, if such exists. Creates and saves RPN to cache, if not exists.
     *
     * @param formula formula to search in cache.
     * @return Formula in Reverse Polish Notation.
     */
    private ArrayList<Object> getRPNWithCache(String formula) {
        ArrayList<Object> RPN = cache.get(formula);
        if (RPN == null) {
            RPN = createRPN(formula);
            cache.put(formula, RPN);
            System.out.printf("Saved formula to cache: %s => %s %n", formula, RPN);
        } else {
            System.out.printf("Formula found in cache: %s => %s%n", formula, RPN);
        }
        return RPN;
    }

    /**
     * Translates formula to Postfix notation.
     *
     * @param formula formula in Infix notation
     * @return tokenized formula in Postfix notation.
     */
    private ArrayList<Object> createRPN(String formula) {
        ArrayList<String> tokens = tokenize(formula);
        ArrayList<Object> RPN = new ArrayList<>();
        Stack<String> operators = new Stack<>();
        for (String token : tokens) {
            if (isOperator(token)) {
                processOperator(token, operators, RPN);
            } else {
                processOperand(token, RPN);
            }
        }
        while (!operators.empty()) {
            RPN.add(operators.pop());
        }
        return RPN;
    }

    /**
     * Processes operand and adds to RPN.
     *
     * @param operand to process (digit or variable).
     * @param RPN     RPN list to add operand.
     */
    private static void processOperand(String operand, ArrayList<Object> RPN) {
        try {
            double digit = Double.parseDouble(operand);
            RPN.add(digit);
        } catch (NumberFormatException _) {
            RPN.add(operand); // it is variable
        }
    }

    /**
     * Processes operator, comparing its priority with priorities of other operators in stack,
     * considering their associativity.
     * Adds operators from stack to RPN, if current has lower priority.
     *
     * @param operator  current operator.
     * @param operators operators in stack to compare.
     * @param RPN       Reverse Polish Notation.
     */
    private static void processOperator(String operator, Stack<String> operators, ArrayList<Object> RPN) {
        while (!operators.isEmpty()) {
            int lastPriority = OPERATORS_PRIORITIES.get(operators.peek());
            int currentPriority = OPERATORS_PRIORITIES.get(operator);
            if (lastPriority > currentPriority || (lastPriority == currentPriority &&
                    !RIGHT_ASSOCIATIVE_OPERATORS.contains(operator))
            ) {
                RPN.add(operators.pop());
            } else {
                break;
            }
        }
        operators.push(operator);
    }
}

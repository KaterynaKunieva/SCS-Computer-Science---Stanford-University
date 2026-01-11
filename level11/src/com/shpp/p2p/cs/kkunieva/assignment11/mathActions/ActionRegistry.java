package com.shpp.p2p.cs.kkunieva.assignment11.mathActions;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for searching in MathActions.
 */
public class ActionRegistry {

    /**
     * Map of binary operators, id: Operator
     */
    private static final Map<String, Operator> BINARY_OPERATORS = new HashMap<>();

    /**
     * Map of unary operators, id: Operator
     */
    private static final Map<String, Operator> UNARY_OPERATORS = new HashMap<>();

    /**
     * Map of math functions, id: Function
     */
    private static final Map<String, Function> FUNCTIONS = new HashMap<>();

    static {
        for (Operator op : Operator.values()) {
            String operatorIdentifier = op.getIdentifier();
            if (op.getArgAmount() == 1) {
                UNARY_OPERATORS.put(operatorIdentifier, op);
            } else {
                BINARY_OPERATORS.put(operatorIdentifier, op);
            }
        }
        for (Function func : Function.values()) {
            String functionIdentifier = func.getIdentifier().toLowerCase();
            FUNCTIONS.put(functionIdentifier, func);
        }
    }

    /**
     * Checks if specified string is a binary operator or a function.
     *
     * @param s string to check
     * @return true if string is an action; false otherwise.
     */
    public static boolean isMathAction(String s) {
        String actionIdentifier = s.toLowerCase();
        return FUNCTIONS.containsKey(actionIdentifier)
                || BINARY_OPERATORS.containsKey(actionIdentifier);
    }

    /**
     * Checks if specified string is unary operator.
     * @param s string to check.
     * @return true if string is unary operator; false otherwise.
     */
    public static boolean isUnaryOperator(String s) {
        return UNARY_OPERATORS.containsKey(s.toLowerCase());
    }

    /**
     * Finds the function or binary operator by its id (string representation).
     *
     * @param s string representation of operator or function.
     * @return found math action (function or binary operator), if such exists; otherwise - null.
     */
    public static MathAction find(String s) {
        String actionIdentifier = s.toLowerCase();
        MathAction func = FUNCTIONS.get(actionIdentifier);
        if (func == null) {
            func = BINARY_OPERATORS.get(actionIdentifier);
        }
        return func;
    }

    /**
     * Finds unary operator by its id (string representation).
     * @param s string representation of unary operator
     * @return found unary operator, if such exists; otherwise - null.
     */
    public static Operator findUnaryOperator(String s) {
        return UNARY_OPERATORS.get(s.toLowerCase());
    }
}

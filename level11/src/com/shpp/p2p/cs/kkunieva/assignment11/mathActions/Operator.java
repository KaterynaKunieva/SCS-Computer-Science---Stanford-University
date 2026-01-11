package com.shpp.p2p.cs.kkunieva.assignment11.mathActions;

/**
 * Enum of mathematical operators and methods to work with them.
 */
public enum Operator implements MathAction {
    PLUS("+", 1, MathOperations.sum, false),
    MINUS("-", 1, MathOperations.subtraction, false),
    MULTIPLY("*", 2, MathOperations.multiply, false),
    DIVIDE("/", 2, MathOperations.divide, false),
    UNARY_MINUS("-", 3, MathOperations.unaryMinus, true, 1),
    UNARY_PLUS("+", 3, MathOperations.unaryPlus, true, 1),
    POW("^", 4, MathOperations.pow, true),
    ;

    /**
     * Default amount of MathExecutable arguments
     */
    private static final int DEFAULT_ARG_AMOUNT = 2;

    private final String sign;
    private final int priority;
    private final MathExecutable operation;
    private final boolean isRight;
    private final int argAmount;

    /**
     * Constructor to create mathematical operator
     * @param sign string sign of operator (e.g. "+"), used for searching.
     * @param priority priority of operator.
     * @param operation lambda-function as operator action.
     * @param isRightAssociative if operator is right associative.
     * @param argAmount amount of arguments for math action.
     */
    Operator(String sign, int priority, MathExecutable operation, boolean isRightAssociative, int argAmount) {
        this.sign = sign;
        this.priority = priority;
        this.operation = operation;
        this.isRight = isRightAssociative;
        this.argAmount = argAmount;
    }

    /**
     * Constructor to create mathematical operator with default amount of arguments.
     * @param sign string sign of operator (e.g. "+"), used for searching.
     * @param priority priority of operator.
     * @param operation lambda-function as operator action.
     * @param isRightAssociative if operator is right associative.
     */
    Operator(String sign, int priority, MathExecutable operation, boolean isRightAssociative) {
        this(sign, priority, operation, isRightAssociative, DEFAULT_ARG_AMOUNT);
    }

    /**
     * Executes math action on specified arguments
     *
     * @return result of executing mathematical action.
     */
    @Override
    public double apply(double[] args) {
        return operation.execute(args);
    }

    /**
     * Getter for priority.
     * @return the priority of action.
     */
    @Override
    public int getPriority() {
        return priority;
    }

    /**
     * Getter for amount of arguments in action.
     * @return amount of arguments.
     */
    @Override
    public int getArgAmount() {
        return argAmount;
    }

    /**
     * Getter for string representation
     * @return id (string representation)
     */
    @Override
    public String getIdentifier() {
        return sign;
    }

    /**
     * Getter for is right associative
     * @return true, if operator is right associative; false - otherwise.
     */
    public boolean getIsRight() {
        return isRight;
    }
}

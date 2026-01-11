package com.shpp.p2p.cs.kkunieva.assignment11.mathActions;

/**
 * Enum of mathematical functions and methods to work with them.
 */
public enum Function implements MathAction {
    SIN("sin", MathOperations.sin),
    COS("cos", MathOperations.cos),
    TAN("tan", MathOperations.tan),
    ATAN("atan", MathOperations.atan),
    LOG10("log10", MathOperations.log10),
    LOG2("log2", MathOperations.log2),
    SQRT("sqrt", MathOperations.sqrt),
    ;

    /**
     * Default priority of all functions
     */
    private static final int DEFAULT_PRIORITY = 5;

    /**
     * Default amount of MathExecutable arguments
     */
    private static final int DEFAULT_ARG_AMOUNT = 1;

    private final String name;
    private final MathExecutable function;

    /**
     * Constructor to create mathematical function.
     * @param name name of function (e.g. sin, cos), used for searching.
     * @param function lambda-function to apply.
     */
    Function(String name, MathExecutable function) {
        this.name = name;
        this.function = function;
    }

    /**
     * Getter for priority.
     * @return the priority of action.
     */
    @Override
    public int getPriority() {
        return DEFAULT_PRIORITY;
    }

    /**
     * Getter for amount of arguments in action.
     * @return amount of arguments.
     */
    @Override
    public int getArgAmount() {
        return DEFAULT_ARG_AMOUNT;
    }

    /**
     * Executes math action on specified arguments
     *
     * @return result of executing mathematical action.
     */
    @Override
    public double apply(double[] args) {
        return function.execute(args);
    }

    /**
     * Getter for string representation
     * @return id (string representation)
     */
    @Override
    public String getIdentifier() {
        return name;
    }
}

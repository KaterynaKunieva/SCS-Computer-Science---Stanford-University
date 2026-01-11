package com.shpp.p2p.cs.kkunieva.assignment11.mathActions;

/**
 * Collection of Mathematical operation implementations
 */
public class MathOperations {

    /**
     * Epsilon, small tolerance value
     */
    private static final double EPS = 1e-9;

    /**
     * Infinity cutoff
     */
    private static final double INF = 1.0E308;

    public static final MathExecutable sin = args -> Math.sin(args[0]);
    public static final MathExecutable cos = args -> Math.cos(args[0]);
    public static final MathExecutable tan = args -> {
        double x = args[0];
        if (Math.abs(Math.cos(x)) < EPS) {
            return INF;
        }
        return Math.tan(x);
    };
    public static final MathExecutable atan = args -> Math.atan(args[0]);
    public static final MathExecutable log10 = args -> Math.log10(args[0]);
    public static final MathExecutable log2 = args -> Math.log(args[0]) / Math.log(2);
    public static final MathExecutable sqrt = args -> Math.sqrt(args[0]);
    public static final MathExecutable sum = args -> args[0] + args[1];
    public static final MathExecutable subtraction = args -> args[0] - args[1];
    public static final MathExecutable multiply = args -> args[0] * args[1];
    public static final MathExecutable divide = args -> args[0] / args[1];
    public static final MathExecutable pow = args -> Math.pow(args[0], args[1]);
    public static final MathExecutable unaryMinus = args -> -args[0];
    public static final MathExecutable unaryPlus = args -> args[0];
}

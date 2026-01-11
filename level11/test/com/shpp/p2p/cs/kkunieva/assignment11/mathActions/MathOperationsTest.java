package com.shpp.p2p.cs.kkunieva.assignment11.mathActions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MathOperationsTest {

    @Test
    void testAllMathOperations() {
        double delta = 1e-9;
        double operand1 = 3;
        double operand2 = 4;
        double[] operands = {operand1, operand2};

        assertEquals(0.5, MathOperations.sin.execute(new double[] {Math.PI / 6}), delta);
        assertEquals(1.0, MathOperations.cos.execute(new double[] {0.0}), delta);
        assertEquals(1.0, MathOperations.tan.execute(new double[]{ Math.PI / 4 }), delta);
        assertEquals(Math.PI / 4, MathOperations.atan.execute(new double[]{ 1.0 }), delta);
        assertEquals(3.0, MathOperations.log10.execute(new double[]{ 1000.0 }), delta);
        assertEquals(3.0, MathOperations.log2.execute(new double[]{ 8.0 }), delta);
        assertEquals(4.0, MathOperations.sqrt.execute(new double[]{ 16.0 }), delta);
        assertEquals(7.0, MathOperations.sum.execute(operands));
        assertEquals(-1.0, MathOperations.subtraction.execute(operands));
        assertEquals(12.0, MathOperations.multiply.execute(operands));
        assertEquals(0.75, MathOperations.divide.execute(operands));
        assertEquals(81.0, MathOperations.pow.execute(operands));
    }
}

package com.shpp.p2p.cs.kkunieva.assignment10.evaluators;

import com.shpp.p2p.cs.kkunieva.assignment10.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public abstract class CalculatorTest {

    Calculator calculator;
    Map<String, Double> variables;

    /**
     * Creates and returns instance of tested evaluator
     *
     * @return instance of evaluator
     */
    protected abstract Evaluator createEvaluator();

    @BeforeEach
    void setUp() {
        calculator = new Calculator(createEvaluator());
        variables = new HashMap<>();
    }

    @Test
    void testSimpleExpression() {
        assertEquals(6.0, calculator.calculate("2.0+3.0-0.0*10.0+2.0/2.0", variables));
    }

    @Test
    void testPoweringPositive() {
        assertEquals(8.0, calculator.calculate("2.0^3.0", variables));
    }

    @Test
    void testPoweringNegative() {
        assertEquals(0.125, calculator.calculate("2.0^(-3.0)", variables));
    }

    @Test
    void testOrderOfOperations() {
        assertEquals(6.0, calculator.calculate("2+2*2", variables));
        assertEquals(7.0, calculator.calculate("10-6/2", variables));
    }

    @Test
    void testPowerPriority() {
        assertEquals(18.0, calculator.calculate("2*3^2", variables));
        assertEquals(512.0, calculator.calculate("2^3^2", variables));
    }

    @Test
    void testWithVariables() {
        variables.put("a", 5.0);
        variables.put("b", 2.0);
        variables.put("c", 10.0);
        assertEquals(20.0, calculator.calculate("a*b+c", variables));
    }


    @Test
    void testWithUnaryMinusVariable() {
        variables.put("a", 2.0);
        variables.put("b", -1.0);
        assertEquals(-2.0, calculator.calculate("-a*(-b)", variables));
    }

    @Test
    void testWithNegativeVariables() {
        variables.put("x", -5.0);
        variables.put("y", 2.0);
        assertEquals(-3.0, calculator.calculate("x+y", variables));
        assertEquals(-10.0, calculator.calculate("x*y", variables));
    }

    @Test
    void testComplexExpression() {
        assertEquals(16.0, calculator.calculate("100/10*2+5-3^2", variables));
        assertEquals(1.0, calculator.calculate("1+3+2-5", variables));
    }

    @Test
    void testFloatingPoint() {
        assertEquals(1.0, calculator.calculate("0.5*0.5+0,75", variables));
    }

    @Test
    void testDivisionByFraction() {
        assertEquals(20.0, calculator.calculate("10/0.5", variables));
    }

    @Test
    void testZeroOperations() {
        assertEquals(0.0, calculator.calculate("0*5+0/2", variables));
        assertEquals(5.0, calculator.calculate("5+0-0", variables));
    }

    @Test
    void testPointDigitPowering() {
        assertEquals(9.18958683997628, calculator.calculate("2^3.2", variables));
        assertEquals(14.372392707920499, calculator.calculate("2.3^3.2", variables));
        assertEquals(0.06957783719957142, calculator.calculate("2.3^(-3.2)", variables));
    }

    @Test
    void testReviewUnaryMinus() {
        assertEquals(0.0, calculator.calculate("-1+1", variables));
    }

    @Test
    void testCaseSensitivity() {
        variables.put("a", 5.0);
        variables.put("A", 2.0);
        assertEquals(25.0, calculator.calculate("a*a", variables));
        assertEquals(4.0, calculator.calculate("A^A", variables));
    }

    @Test
    void testPowUnaryMinus() {
        assertEquals(16, calculator.calculate("-4^2", variables));
    }

    @Test
    void testAdditionUnaryMinus() {
        assertEquals(2, calculator.calculate("-4+6", variables));
    }

    @Test
    void testDoublePowering() {
        assertEquals(2.4178516392292583E24, calculator.calculate("2^3^4", variables));
    }

    @Test
    void testNegativeResultPowering() {
        assertEquals(-16, calculator.calculate("(-1)*4^2", variables));
    }

    @Test
    void testNegativeBasePowering() {
        assertEquals(16, calculator.calculate("(-4)^2", variables));
        assertEquals(-8, calculator.calculate("(-2)^3", variables));
    }

    @Test
    void testNegativeResult() {
        assertEquals(-8.0, calculator.calculate("2-10", variables));
    }

    @Test
    void testLargeNumbers() {
        assertEquals(1000000.0, calculator.calculate("1000*1000", variables));
    }

    @Test
    void testCompensatedMultiplication() {
        assertEquals(14.0, calculator.calculate("14/2*2", variables));
    }

    @Test
    void testCompensatedAddition() {
        assertEquals(14.0, calculator.calculate("14+2-2", variables));
    }

    @Test
    void testComplexMathExpressions() {
        assertEquals(-65.0, calculator.calculate("3 * 9 - 8 * 9 + 56.0 / 7 - 6 * 7 + 2 * 7", variables));
    }

    @Test
    void testNegativeBaseFractionalExponent() {
        double result = calculator.calculate("-4 ^ 0.5", variables);
        assertTrue(Double.isNaN(result), "Negative base with fractional exponent should result in NaN");
    }

    @Test
    void testNegativeBaseNegativeFractionalExponent() {
        double result = calculator.calculate("-8 ^ (-0.3333333333333333)", variables);
        assertTrue(Double.isNaN(result));
    }

    @Test
    void testEmptyVariables() {
        variables.put("a", 5.0);
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate("a+b", variables));
    }

    @Test
    void testBracesWithNotMinus() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate("1+(+2)", variables));
    }

    @Test
    void testBracesForOperations() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate("1+(2+1)", variables));
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate("1*(2+1)", variables));
    }
}

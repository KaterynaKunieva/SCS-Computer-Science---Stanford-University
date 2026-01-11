package com.shpp.p2p.cs.kkunieva.assignment11.evaluators;

import com.shpp.p2p.cs.kkunieva.assignment11.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void testUnaryMinusAfterOperator() {
        assertEquals(0.0, calculator.calculate("1+-1", variables));
    }

    @Test
    void testUnaryMinusInParentheses() {
        assertEquals(0.0, calculator.calculate("1+(-1)", variables));
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
        assertEquals(-16.0, calculator.calculate("-4^2", variables));
    }

    @Test
    void testUnaryMinusAtStart() {
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
    void testNotExpressionInParentheses() {
        assertEquals(6.0, calculator.calculate("3+(3)", variables));
    }

    @Test
    void testComplexMathExpressions() {
        assertEquals(-65.0, calculator.calculate("3 * 9 - 8 * 9 + 56.0 / 7 - 6 * 7 + 2 * 7", variables));
    }

    @Test
    void testNegativeBaseFractionalExponent() {
        assertEquals(-2.0, calculator.calculate("-4 ^ 0.5", variables));
    }

    @Test
    void testNestedFunctionsWithNumbers() {
        assertEquals(
                Math.sin(Math.cos(0)),
                calculator.calculate("sin(cos(0))", Map.of()),
                1e-9
        );
    }

    @Test
    void testNestedFunctionsWithUnaryMinus() {
        assertEquals(
                Math.log10(Math.sqrt(16)),
                calculator.calculate("log10(sqrt(16))", Map.of()),
                1e-9
        );
    }

    @Test
    void testDeepNestedFunctions() {
        assertEquals(
                Math.sin(Math.log(Math.sqrt(8)) / Math.log(2)),
                calculator.calculate("sin(log2(sqrt(8)))", Map.of()),
                1e-9
        );
    }


    @Test
    void testNegativeBaseNegativeFractionalExponent() {
        assertEquals(-0.5, calculator.calculate("-8 ^ (-0.3333333333333333)", variables));
    }

    @Test
    void testSimpleParentheses() {
        assertEquals(4.0, calculator.calculate("1+(2+1)", variables));
        assertEquals(3.0, calculator.calculate("1*(2+1)", variables));
        assertEquals(17.0, calculator.calculate("2+3*(1+4)", variables));
    }

    @Test
    void testSinWithNumber() {
        assertEquals(0.0, calculator.calculate("sin(0)", variables), 1e-9);
    }

    @Test
    void testCosWithVariable() {
        variables.put("x", 0.0);
        assertEquals(1.0, calculator.calculate("cos(x)", variables), 1e-9);
    }

    @Test
    void testSqrtWithExpression() {
        variables.put("x", 9.0);
        assertEquals(4.0, calculator.calculate("sqrt(x+7)", variables), 1e-9);
    }

    @Test
    void testLog10WithNumber() {
        assertEquals(2.0, calculator.calculate("log10(100)", variables), 1e-9);
    }

    @Test
    void testLog2WithVariable() {
        variables.put("x", 8.0);
        assertEquals(3.0, calculator.calculate("log2(x)", variables), 1e-9);
    }

    @Test
    void testAtanWithExpression() {
        variables.put("x", 1.0);
        assertEquals(Math.atan(2), calculator.calculate("atan(x+1)", variables), 1e-9);
    }

    @Test
    void testFunctionInsideFormula() {
        variables.put("x", 4.0);
        assertEquals(4.0, calculator.calculate("2+sqrt(x)", variables), 1e-9);
    }

    @Test
    void testNestedFunctions() {
        assertEquals(1.0, calculator.calculate("sqrt(sin(0)+cos(0))", variables), 1e-9);
    }

    @Test
    void testEmptyVariables() {
        variables.put("a", 5.0);
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate("a+b", variables));
    }

    @Test
    void testUnaryMinusBeforeFn() {
        variables.put("x", 0.0);
        assertEquals(-1.0, calculator.calculate("-cos(x)", variables), 1e-9);
    }

    @Test
    void testNotUnarySignAfterParenthesis() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate("(*3+4)", variables));
    }

    @Test
    void testInvalidFormula() {
        variables.put("a", 1.0);
        variables.put("b", 2.0);
        variables.put("abc", 3.0);
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate("abc2+4", variables));
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate("2(a+b)", variables));
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate("(a+b)2", variables));
    }

    @Test
    void testSkippedMultiplyingBeforeParenthesis() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate("2(3+4)", variables));
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate("1-2(3+4)", variables));
    }
}

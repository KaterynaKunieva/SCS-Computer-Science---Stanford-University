package com.shpp.p2p.cs.kkunieva.assignment11.compilers;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormulaTokenizerTest {

    @Test
    void testTokenizeBasicExpression() {
        assertEquals(List.of("2", "+", "2", "*", "x"),
                     FormulaTokenizer.tokenize("2+2*x"));
    }

    @Test
    void testTokenizeExpressionWithUnaryMinusAtStart() {
        assertEquals(List.of("-", "2", "+", "2", "*", "x"),
                     FormulaTokenizer.tokenize("-2+2*x"));
    }

    @Test
    void testTokenizeExpressionWithUnaryMinusInParentheses() {
        assertEquals(List.of(
                             "2", "+",
                             "(", "-", "2", ")",
                             "*",
                             "(", "-", "x", ")",
                             "/",
                             "(", "-", "2", ")",
                             "^",
                             "(", "-", "2", ")"
                     ),
                     FormulaTokenizer.tokenize("2+(-2)*(-x)/(-2)^(-2)")
        );

        assertEquals(List.of(
                             "(", "-", "2", ")", "+", "2", "*", "x"
                     ),
                     FormulaTokenizer.tokenize("(-2)+2*x")
        );
    }

    @Test
    void testTokenizeExpressionWithUnaryMinusWithoutParentheses() {
        assertEquals(List.of(
                             "2", "+",
                             "-", "2",
                             "*",
                             "-", "x",
                             "/",
                             "-", "2",
                             "^",
                             "-", "2"
                     ),
                     FormulaTokenizer.tokenize("2+-2*-x/-2^-2")
        );
    }

    @Test
    void testTokenizeExpressionWithSimpleParentheses() {
        assertEquals(List.of("(", "1", "+", "2", ")"),
                     FormulaTokenizer.tokenize("(1+2)"));
    }

    @Test
    void testTokenizeExpressionWithMultipleParentheses() {
        assertEquals(List.of(
                             "(", "1", "+", "2", ")",
                             "-",
                             "(", "3", "*", "5", ")",
                             "/", "2",
                             "+",
                             "(", "-", "5", ")",
                             "^", "10"
                     ),
                     FormulaTokenizer.tokenize("(1+2)-(3*5)/2+(-5)^10")
        );
    }

    @Test
    void testTokenizeExpressionWithNestedParentheses() {
        assertEquals(List.of(
                             "1", "+",
                             "(", "2", "*",
                             "(", "3", "-", "4", ")",
                             "/",
                             "(", "7", "/", "5", ")",
                             ")"
                     ),
                     FormulaTokenizer.tokenize("1+(2*(3-4)/(7/5))")
        );
    }

    @Test
    void testTokenizeExpressionWithDoubleMinus() {
        assertEquals(List.of(
                             "-", "1",
                             "*",
                             "(",
                             "-",
                             "(",
                             "-", "1",
                             ")",
                             ")"
                     ),
                     FormulaTokenizer.tokenize("-1*(-(-1))")
        );
    }

    @Test
    void testTokenizeExpressionWithUnaryMinusBeforeParentheses() {
        assertEquals(List.of(
                             "2", "+",
                             "-",
                             "(",
                             "10", "-", "-", "3",
                             ")"
                     ),
                     FormulaTokenizer.tokenize("2+-(10--3)")
        );
    }

    @Test
    void testTokenizeExpressionWithMultipleUnaryMinuses() {
        assertEquals(List.of(
                             "3", "+", "-", "-", "-", "5"
                     ),
                     FormulaTokenizer.tokenize("3+---5")
        );
    }

    @Test
    void testSkipUnaryPlus() {
        assertEquals(List.of(
                             "3", "-", "(", "+", "3", ")"
                     ),
                     FormulaTokenizer.tokenize("3-(+3)")
        );
    }

    @Test
    void testStartUnaryPlus() {
        assertEquals(List.of(
                             "+", "3", "-", "(", "+", "3", ")"
                     ),
                     FormulaTokenizer.tokenize("+3-(+3)")
        );
    }

    @Test
    void testUnaryMinusFunction() {
        assertEquals(List.of(
                             "-", "tan", "(", "10", ")"
                     ),
                     FormulaTokenizer.tokenize("-tan(10)")
        );

        assertEquals(List.of(
                             "1", "+", "-", "tan", "(", "10", ")"
                     ),
                     FormulaTokenizer.tokenize("1+-tan(10)")
        );

        assertEquals(List.of(
                             "1", "+",
                             "(",
                             "-", "tan", "(", "10", ")",
                             ")"
                     ),
                     FormulaTokenizer.tokenize("1+(-tan(10))")
        );
    }

    @Test
    void testFunctions() {
        assertEquals(List.of(
                             "+",
                             "2", "+",
                             "sin",
                             "(",
                             "1", "/", "(", "-", "2", ")",
                             ")",
                             "-",
                             "3", "/", "-", "5"
                     ),
                     FormulaTokenizer.tokenize("+2+sin(1/(-2))-3/-5")
        );

        assertEquals(List.of(
                             "log2", "(", "3", ")"
                     ),
                     FormulaTokenizer.tokenize("log2(3)")
        );
    }
}

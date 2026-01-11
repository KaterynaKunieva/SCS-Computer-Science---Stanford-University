package com.shpp.p2p.cs.kkunieva.assignment11.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FormulaParserTest {

    FormulaValidator parser;

    @BeforeEach
    void setUp() {
        parser = new FormulaValidator();
    }

    @Test
    void testNormalization() {
        assertEquals("2+5-10.5", parser.validateAndNormalize("2   +5 - 10,5"));
    }

    @Test
    void testDifferentOperators() {
        assertEquals("13+2*a-5/b^c", parser.validateAndNormalize("13 + 2 * a - 5 / b ^ c"));
    }

    @Test
    void startWithMinus() {
        assertEquals("-2*5-10.5", parser.validateAndNormalize("-2 * 5 - 10,5"));
    }

    @Test
    void testParentheses() {
        assertEquals("2^(b)", parser.validateAndNormalize("2^(b)"));
    }

    @Test
    void longVariable() {
        assertEquals("2*abc", parser.validateAndNormalize("2*abc"));
    }

    @Test
    void startsWithVariable() {
        assertEquals("test*3", parser.validateAndNormalize("test*3"));
    }

    @Test
    void twoMinuses() {
        assertEquals("1--1", parser.validateAndNormalize("1--1"));
    }

    @Test
    void testUnaryMinusWithoutParentheses() {
        assertEquals("23+-b", parser.validateAndNormalize("23+-b"));
    }

    @Test
    void testPoweringNegativeWithoutParentheses() {
        assertEquals("2.0^-3.0", parser.validateAndNormalize("2.0^-3.0"));
    }

    @Test
    void testDifferentOperatorsOneByOne() {
        assertEquals("2.0+-3.0", parser.validateAndNormalize("2.0+-3.0"));
    }

    @Test
    void testUnaryMinusBeforeFunctions() {
        assertEquals("-tan(5)", parser.validateAndNormalize("-tan(5)"));
    }

    @Test
    void testFunctionsParentheses() {
        assertEquals("log2(5+3)", parser.validateAndNormalize("log2(5+3)"));
        assertEquals("log10(5+3)", parser.validateAndNormalize("log10(5+3)"));
        assertEquals("sin(3+(5+9))", parser.validateAndNormalize("sin(3+(5+9))"));
    }

    @Test
    void MultipleMinuses() {
        assertThrows(IllegalArgumentException.class, () -> parser.validateAndNormalize("2---2"));
    }

    @Test
    void MultipleOperators() {
        assertThrows(IllegalArgumentException.class, () -> parser.validateAndNormalize("2-+2*/2++2"));
    }

    @Test
    void testNotEvenOpenParenthesis() {
        assertThrows(IllegalArgumentException.class, () -> parser.validateAndNormalize("2.0+(3.0"));
    }

    @Test
    void testNotEvenCloseParenthesis() {
        assertThrows(IllegalArgumentException.class, () -> parser.validateAndNormalize("2.0+3.0)"));
    }

    @Test
    void testNotEvenParentheses() {
        assertThrows(IllegalArgumentException.class, () -> parser.validateAndNormalize("(2.0)+3.0))"));
    }

    @Test
    void testSameOperatorsOneByOne() {
        assertThrows(IllegalArgumentException.class, () -> parser.validateAndNormalize("2.0++3.0"));
    }

    @Test
    void testEmptyFormula() {
        assertThrows(IllegalArgumentException.class, () -> parser.validateAndNormalize(""));
    }

    @Test
    void testStartWithOperator() {
        assertThrows(IllegalArgumentException.class, () -> parser.validateAndNormalize("*23+b"));
    }
}

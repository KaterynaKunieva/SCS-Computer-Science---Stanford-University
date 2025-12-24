package com.shpp.p2p.cs.kkunieva.assignment10.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FormulaParserTest {

    FormulaParser parser;

    @BeforeEach
    void setUp() {
        parser = new FormulaParser();
    }

    @Test
    void testNormalization() {
        assertEquals("2+5-10.5", parser.parse("2   +5 - 10,5"));
    }

    @Test
    void testDifferentOperators() {
        assertEquals("13+2*a-5/b^c", parser.parse("13 + 2 * a - 5 / b ^ c"));
    }

    @Test
    void startWithMinus() {
        assertEquals("-2*5-10.5", parser.parse("-2 * 5 - 10,5"));
    }

    @Test
    void testBraces() {
        assertEquals("2^(b)", parser.parse("2^(b)"));
    }

    @Test
    void longVariable() {
        assertEquals("2*abc", parser.parse("2*abc"));
    }

    @Test
    void startsWithVariable() {
        assertEquals("test*3", parser.parse("test*3"));
    }

    @Test
    void testNotEvenOpenBrace() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("2.0+(3.0"));
    }

    @Test
    void testNotEvenCloseBrace() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("2.0+3.0)"));
    }

    @Test
    void testNotEvenBraces() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("(2.0)+3.0))"));
    }

    @Test
    void testPoweringNegativeWithoutBraces() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("2.0^-3.0"));
    }

    @Test
    void testDifferentOperatorsOneByOne() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("2.0+-3.0"));
    }

    @Test
    void testSameOperatorsOneByOne() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("2.0++3.0"));
    }

    @Test
    void testEmptyFormula() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(""));
    }

    @Test
    void testStartWithOperator() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("+23*b"));
    }

    @Test
    void testOneByOneOperatorsInFormula() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("23+-b"));
    }

    @Test
    void testSkippedMultiplying() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("2a"));
    }
}

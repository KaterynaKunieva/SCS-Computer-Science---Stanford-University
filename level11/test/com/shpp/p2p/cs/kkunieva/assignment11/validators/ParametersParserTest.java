package com.shpp.p2p.cs.kkunieva.assignment11.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParametersParserTest {

    ParametersParser parser;

    @BeforeEach
    void setUp() {
        parser = new ParametersParser();
    }

    @Test
    void testParserWithSpaces() {
        Map<String, Double> parameters = new HashMap<>();
        parameters.put("a", 1.0);
        assertEquals(parameters, parser.parseKeyValueParameter("a   = 1"));
    }

    @Test
    void testParserWithoutSpaces() {
        Map<String, Double> parameters = new HashMap<>();
        parameters.put("a", 1.0);
        assertEquals(parameters, parser.parseKeyValueParameter("a=1"));
    }

    @Test
    void testParserWithDot() {
        Map<String, Double> parameters = new HashMap<>();
        parameters.put("a", 1.0);
        assertEquals(parameters, parser.parseKeyValueParameter("a=1.000"));
    }

    @Test
    void testParserWithComma() {
        Map<String, Double> parameters = new HashMap<>();
        parameters.put("a", 1.134);
        assertEquals(parameters, parser.parseKeyValueParameter("a=1,134"));
    }

    @Test
    void testParserNegativeValue() {
        Map<String, Double> parameters = new HashMap<>();
        parameters.put("a", -1.0);
        assertEquals(parameters, parser.parseKeyValueParameter("a=-1"));
    }

    @Test
    void testParserNegativeValueWithDot() {
        Map<String, Double> parameters = new HashMap<>();
        parameters.put("a", -1.0);
        assertEquals(parameters, parser.parseKeyValueParameter("a=-1.00"));
    }

    @Test
    void testParserWithLongName() {
        Map<String, Double> parameters = new HashMap<>();
        parameters.put("abc", 1.0);
        assertEquals(parameters, parser.parseKeyValueParameter("abc=1.0"));
    }


    @Test
    void testParserWithLongNameWithUnderline() {
        Map<String, Double> parameters = new HashMap<>();
        parameters.put("a_b_c", 1.0);
        assertEquals(parameters, parser.parseKeyValueParameter("a_b_c=1.0"));
    }

    @Test
    void testParserNegativeValueWithComma() {
        Map<String, Double> parameters = new HashMap<>();
        parameters.put("a", -1.0);
        assertEquals(parameters, parser.parseKeyValueParameter("a=-1,00"));
    }

    @Test
    void testParserStringValue() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseKeyValueParameter("a=error")
        );
    }

    @Test
    void testParserWithDots() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseKeyValueParameter("a=1.12.3")
        );
    }

    @Test
    void testParserKeyWithSpaces() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseKeyValueParameter("err or=1.0")
        );
    }

    @Test
    void testParserWithoutSeparator() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseKeyValueParameter("error")
        );
    }

    @Test
    void testParserOnlySeparator() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseKeyValueParameter("=")
        );
    }

    @Test
    void testParserDoubleSeparators() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseKeyValueParameter("==")
        );
    }

    @Test
    void testParserParameterDoubleSeparators() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseKeyValueParameter("a==2")
        );
    }

    @Test
    void testParserMultipleSeparators() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseKeyValueParameter("===")
        );
    }

    @Test
    void testParserWithMultipleSeparators() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseKeyValueParameter("error=1.0=")
        );
    }

    @Test
    void testParserWithoutValue() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseKeyValueParameter("error=")
        );
    }

    @Test
    void testParserWithEmptyValue() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseKeyValueParameter("error= ")
        );
    }

    @Test
    void testParserKeyWithDigits() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseKeyValueParameter("a1=1.0")
        );
    }
}

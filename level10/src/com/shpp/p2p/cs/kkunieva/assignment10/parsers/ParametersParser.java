package com.shpp.p2p.cs.kkunieva.assignment10.parsers;

import java.util.HashMap;
import java.util.Map;

/**
 * Parser for parameters (variables) from Strings like: a=2, ABC=10, D=3,0 to name-value pair.
 */
public class ParametersParser {

    /**
     * Symbol for separating name from value.
     */
    private static final String PARAMETER_SEPARATOR = "=";

    /**
     * Allowed symbols in parameter names (like a_Bc).
     */
    private static final String PARAMETER_NAME_REGEX = "^[A-Za-z_]+$";

    /**
     * Parses parameter name and value from String, validating its size and symbols.
     *
     * @param input string to parse.
     * @return map like parameterName: parameterValue.
     */
    public Map<String, Double> parseKeyValueParameter(String input) {
        Map<String, Double> parameter = new HashMap<>();
        String[] parametersArray = input.split(PARAMETER_SEPARATOR);
        if (parametersArray.length != 2 || input.endsWith(PARAMETER_SEPARATOR)) {
            throw new IllegalArgumentException(
                    ("Parameter '%s' is invalid: It must contain name of parameter, separator '%s' and value. " +
                            "Examples: a=1.5 or b = 2")
                            .formatted(input, PARAMETER_SEPARATOR));
        }

        String name = parseName(parametersArray[0]);
        double value = parseValue(parametersArray[1]);
        parameter.put(name, value);

        return parameter;
    }

    /**
     * Parses name from input, validating its symbols.
     * @param input String to parse.
     * @return parsed name, if is valid.
     */
    private String parseName(String input) {
        String name = input.trim();
        if (!name.matches(PARAMETER_NAME_REGEX)) {
            throw new IllegalArgumentException(
                    "Parameter '%s' is invalid: key '%s' must be valid"
                            .formatted(input, name));
        }
        return name;
    }

    /**
     * Parses and normalizes value of parameter.
     * @param input String to parse.
     * @return parsed and normalized value, if is valid.
     */
    private double parseValue(String input) {
        double digitValue;
        String normalizedValue = input.trim().replace(",", ".");

        try {
            digitValue = Double.parseDouble(normalizedValue);
        } catch (NumberFormatException _) {
            throw new IllegalArgumentException(
                    "Parameter '%s' is invalid: value '%s' must be convertible to double"
                            .formatted(input, input));
        }

        return digitValue;
    }
}

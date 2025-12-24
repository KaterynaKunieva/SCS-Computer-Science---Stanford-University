package com.shpp.p2p.cs.kkunieva.assignment10;

import com.shpp.p2p.cs.kkunieva.assignment10.evaluators.AbstractEvaluator;
import com.shpp.p2p.cs.kkunieva.assignment10.evaluators.ShuntingYard;
import com.shpp.p2p.cs.kkunieva.assignment10.parsers.ParametersParser;

import java.util.HashMap;

/**
 * Console interface for interaction with Calculator.
 * To run program specify parameters like: <formula> [arg1] [arg2] ... [argN].
 * Important:
 * - Doesn't support braces for operations: 1+(1+2).
 * - Doesn't support multiple operators one by one: 1--1, 1++1. Valid form: 1-(-1), 1^(-1).
 * - Formula can't start with operator, except unary minus.
 * - Names of variables are case-sensitive.
 * - Examples of supported variables:
 *      a = 1
 *      a = 1,0
 *      abc = 1
 *      a_b_c = 1.0
 *      ABC=1
 * - Examples of not supported variables: a = b, a = 1 =
 */
class Assignment10Part1 {

    /**
     * Validates and parses input, prints result of calculations.
     * @param args <formula> [arg1] [arg2] ... [argN]
     */
   static void main(String[] args) {

        if (args.length == 0) {
            throw new IllegalArgumentException(
                    "Run a program with parameters like: <formula> [arg1] [arg2] ... [argN]");
        }
        Calculator calculator = new Calculator(new ShuntingYard());

        ParametersParser parametersParser = new ParametersParser();
        HashMap<String, Double> parameters = new HashMap<>();
        for (int i = 1; i < args.length; i++) {
            parameters.putAll(parametersParser.parseKeyValueParameter(args[i]));
        }
        System.out.println(calculator.calculate(args[0], parameters));
    }
}
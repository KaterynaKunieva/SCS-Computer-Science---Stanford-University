package com.shpp.p2p.cs.kkunieva.assignment11;

import com.shpp.p2p.cs.kkunieva.assignment11.evaluators.ShuntingYard;
import com.shpp.p2p.cs.kkunieva.assignment11.validators.ParametersParser;

import java.util.HashMap;

/**
 * Console interface for interaction with Calculator.
 * To run program specify parameters like: <formula> [arg1] [arg2] ... [argN].
 * - Supports parentheses for operations: 1+(1+2).
 * - Supports unary minus in such forms: 1--2, 1-(-2), -2+1, (-2)+1.
 * - Doesn't support multiple operators one by one: 1---1, 1++1, 1**1, 1//1.
 * - Formula must contain explicit multiplying sign (2*a), not allowed forms: 2a, 3b.
 * - Formula can't start with operator, except unary minus/plus.
 * - Names of variables are case-sensitive.
 * - Examples of supported variables:
 *      a = 1
 *      a = 1,0
 *      abc = 1
 *      a_b_c = 1.0
 *      ABC=1
 * - Examples of not supported variables: a = b, a = 1 =
 */
class Assignment11Part1 {

    /**
     * Validates and parses input, prints result of calculations.
     * @param args <formula> [arg1] [arg2] ... [argN]
     */
   public static void main(String[] args) {

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
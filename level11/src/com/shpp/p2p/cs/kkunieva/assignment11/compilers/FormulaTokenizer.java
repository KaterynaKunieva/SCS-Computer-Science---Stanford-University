package com.shpp.p2p.cs.kkunieva.assignment11.compilers;

import java.util.ArrayList;

/**
 * Formula tokenizer. Brings formula into the form of a logical tokens sequence.
 */
public class FormulaTokenizer {

    /**
     * Allowed symbols in operand
     */
    private static final String OPERAND_ALLOWED_SYMBOLS = "._";

    /**
     * Symbols representing operators
     */
    private static final String OPERATOR_SYMBOLS = "+-*/^()";

    /**
     * Splits formula into mathematical tokens: operands, parentheses, operators and formulas.
     *
     * @param formula formula to split.
     * @return list of formula tokens in the same order.
     */
    public static ArrayList<String> tokenize(String formula) {
        ArrayList<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < formula.length(); i++) {
            char c = formula.charAt(i);
            if (isPartOfOperand(c)) {
                sb.append(c);
            }
            else if (isOperator(c)) {
                endToken(sb, tokens); // end previous
                sb.append(c);         // add operator
                endToken(sb, tokens); // end operator
            }
        }
        endToken(sb, tokens); // the last one complex token (digit, operand)
        return tokens;
    }

    /**
     * Checks if character is part of operand (digit, variable or function)
     * @param c character to check.
     * @return true, if character can be part of operand; otherwise - false.
     */
    private static boolean isPartOfOperand(char c) {
        return Character.isLetterOrDigit(c) || OPERAND_ALLOWED_SYMBOLS.contains(Character.toString(c));
    }

    /**
     * Checks if character is an operator.
     * @param c character to check.
     * @return true, if character is an operator; otherwise - false.
     */
    private static boolean isOperator(char c){
        return OPERATOR_SYMBOLS.contains(Character.toString(c));
    }

    /**
     * Adds accumulated string from StringBuilder to list of tokens and cleans StringBuilder to start next one.
     *
     * @param sb     StringBuilder with current token value
     * @param tokens General list of tokens
     */
    private static void endToken(StringBuilder sb, ArrayList<String> tokens) {
        if (!sb.isEmpty()) {
            tokens.add(sb.toString());
            sb.setLength(0);
        }
    }
}

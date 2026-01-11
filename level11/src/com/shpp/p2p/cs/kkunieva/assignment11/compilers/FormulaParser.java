package com.shpp.p2p.cs.kkunieva.assignment11.compilers;

import com.shpp.p2p.cs.kkunieva.assignment11.mathActions.ActionRegistry;
import com.shpp.p2p.cs.kkunieva.assignment11.mathActions.MathAction;
import com.shpp.p2p.cs.kkunieva.assignment11.mathActions.Operator;
import com.shpp.p2p.cs.kkunieva.assignment11.formulaNodes.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for formula. Brings formula into the form of a nodes sequence.
 */
public class FormulaParser {

    /**
     * Builds a nodes sequence, iterating over String tokens of formula.
     * Throws exception if formula has missed multiplier.
     *
     * @param formulaTokens list of string tokens of formula, example: 1, +, (, 3, -, 1, )
     * @return formula in the form of nodes list, if formula is valid.
     */
    public static List<FormulaNode> parse(ArrayList<String> formulaTokens) {
        ArrayList<FormulaNode> nodes = new ArrayList<>();

        for (String token : formulaTokens) {
            // compares prev and new nodes to detect skipped multiplier
            FormulaNode prevNode = nodes.isEmpty() ? null : nodes.getLast();
            classifyToken(token, nodes);
            if (prevNode == null) continue;
            FormulaNode newNode = nodes.getLast();
            try {
                validateSkippedMultiplier(prevNode, newNode);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Validation error while parsing formula %s: %s"
                                                           .formatted(String.join("", formulaTokens),
                                                                      e.getMessage()
                                                           ));
            }
        }
        return nodes;
    }

    /**
     * Classifies formula token to one of categories and adds relevant node.
     *
     * @param token token to classify.
     * @param nodes list of formula nodes to add new one.
     */
    private static void classifyToken(String token, ArrayList<FormulaNode> nodes) {
        if (ActionRegistry.isUnaryOperator(token) && isUnaryPosition(nodes)) {
            nodes.add(new ActionNode(ActionRegistry.findUnaryOperator(token)));
        } else if (ActionRegistry.isMathAction(token)) {
            nodes.add(new ActionNode(ActionRegistry.find(token)));
        } else if (token.equals("(")) {
            nodes.add(new ParenthesisNode(ParenthesisNode.Type.OPEN));
        } else if (token.equals(")")) {
            nodes.add(new ParenthesisNode(ParenthesisNode.Type.CLOSE));
        } else {
            try {
                double operand = Double.parseDouble(token);
                nodes.add(new DigitNode(operand));
            } catch (NumberFormatException _) {
                nodes.add(new VariableNode(token));
            }
        }
    }

    /**
     * Validates 2 consecutive nodes (previous and new): if skipped multiplier missed, throws exception.
     *
     * @param prevNode previous node
     * @param newNode new created node
     */
    private static void validateSkippedMultiplier(FormulaNode prevNode, FormulaNode newNode) {
        boolean isPrevOperand = prevNode instanceof DigitNode || prevNode instanceof VariableNode;
        boolean isPrevCloseParenthesis = prevNode instanceof ParenthesisNode parenthesis && !parenthesis.isOpen();
        boolean isNewCloseParenthesis = newNode instanceof ParenthesisNode parenthesis && !parenthesis.isOpen();
        boolean isNewOperator = newNode instanceof ActionNode action &&
                action.getNodeValue() instanceof MathAction mAction && mAction instanceof Operator;

        if ((isPrevOperand || isPrevCloseParenthesis) && !isNewOperator && !isNewCloseParenthesis) {
            throw new IllegalArgumentException("Skipped multiplier between %s and %s"
                                                       .formatted(prevNode, newNode));
        }
    }

    /**
     * Checks if position of next one node is allowes unary operator
     * @param nodes nodes of formula
     * @return true, if position is unary; false - otherwise.
     */
    private static boolean isUnaryPosition(ArrayList<FormulaNode> nodes) {
        boolean isFirstToken = nodes.isEmpty();
        boolean isAfterOpenedParenthesis = false;
        boolean isAfterOperator = false;
        FormulaNode prevToken = isFirstToken ? null : nodes.getLast();
        if (prevToken instanceof ParenthesisNode parenthesis) {
            isAfterOpenedParenthesis = parenthesis.isOpen();
        } else if (prevToken instanceof ActionNode action) {
            isAfterOperator = action.getNodeValue() instanceof MathAction mAction && mAction instanceof Operator;
        }
        return isFirstToken || isAfterOpenedParenthesis || isAfterOperator;
    }
}

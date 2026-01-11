package com.shpp.p2p.cs.kkunieva.assignment11.evaluators;

import com.shpp.p2p.cs.kkunieva.assignment11.compilers.FormulaParser;
import com.shpp.p2p.cs.kkunieva.assignment11.mathActions.Function;
import com.shpp.p2p.cs.kkunieva.assignment11.mathActions.MathAction;
import com.shpp.p2p.cs.kkunieva.assignment11.mathActions.Operator;
import com.shpp.p2p.cs.kkunieva.assignment11.compilers.FormulaTokenizer;
import com.shpp.p2p.cs.kkunieva.assignment11.formulaNodes.*;

import java.util.*;

/**
 * Implementation of Shunting Yard algorithm, based on RPN (Reverse Polish Notation).
 * Has mechanism of caching formulas and their RPN.
 */
public class ShuntingYard implements Evaluator {

    /**
     * Map for caching formulas and RPN.
     */
    private static final Map<String, ArrayList<FormulaNode>> cache = new HashMap<>();

    /**
     * Calculates formula with variables.
     *
     * @param formula   formula to calculate, must be without spaces and using dot for point numbers.
     * @param variables map like variableName: variableValue.
     * @return calculated value, if input is valid.
     */
    @Override
    public double evaluate(String formula, Map<String, Double> variables) {
        ArrayList<FormulaNode> rpn = getRPNWithCache(formula);
        try {
            return calculateRPNExpression(rpn, variables);
        } catch (EmptyStackException _) {
            throw new IllegalArgumentException("Error while calculating RPN. Check the validity of the formula %s"
                                                       .formatted(formula));
        }
    }

    /**
     * Return formula in RPN from cache, if such exists. Creates and saves RPN to cache, if not exists.
     *
     * @param formula formula to search in cache.
     * @return Formula in Reverse Polish Notation.
     */
    private ArrayList<FormulaNode> getRPNWithCache(String formula) {
        ArrayList<FormulaNode> rpn = cache.get(formula);
        if (rpn == null) {
            try {
                rpn = buildRPN(formula);
                cache.put(formula, rpn);
            } catch (EmptyStackException _) {
                throw new IllegalArgumentException("Error while creating RPN. Check the validity of the formula %s"
                                                           .formatted(formula));
            }
        }
        return rpn;
    }

    /**
     * Builds Reverse Polish Notation for formula in Infix notation.
     *
     * @param formula formula in Infix notation
     * @return formula in Postfix notation.
     */
    private ArrayList<FormulaNode> buildRPN(String formula) {
        List<FormulaNode> nodes = FormulaParser.parse(FormulaTokenizer.tokenize(formula));
        ArrayList<FormulaNode> rpn = new ArrayList<>();
        Stack<FormulaNode> operators = new Stack<>();
        for (FormulaNode node : nodes) {
            classifyNode(node, operators, rpn);
        }
        while (!operators.empty()) {
            rpn.add(operators.pop());
        }
        return rpn;
    }

    /**
     * Classifies node of formula to operators stack or rpn based on its type.
     * @param node node to classify.
     * @param operators stack of operators.
     * @param rpn Reverse Polish Notation of full formula.
     */
    private static void classifyNode(FormulaNode node, Stack<FormulaNode> operators, ArrayList<FormulaNode> rpn) {
        if (node instanceof ActionNode action) { // operator or function
            addToOperatorStack(action, operators, rpn);
        } else if (node instanceof DigitNode || node instanceof VariableNode) {
            rpn.add(node);
        } else if (node instanceof ParenthesisNode parenthesis) {
            if (parenthesis.isOpen()) {
                operators.push(node);
            } else {
                addClosingParenthesisToRPN(operators, rpn);
            }
        }
    }

    /**
     * Adds closing parenthesis to RPN with relevant effect: moving operators to RPN until "(".
     *
     * @param operators stack of operators
     * @param rpn       Reverse Polish Notation
     */
    private static void addClosingParenthesisToRPN(Stack<FormulaNode> operators, ArrayList<FormulaNode> rpn) {
        while (!operators.isEmpty()) {
            FormulaNode operator = operators.pop();
            if (operator instanceof ParenthesisNode parenthesis && parenthesis.isOpen()) {
                break;
            }
            rpn.add(operator);
        }
        if (!operators.isEmpty()
                && operators.peek() instanceof ActionNode action
                && action.getNodeValue() instanceof Function) {
            rpn.add(operators.pop());
        }
    }

    /**
     * Adds action to operators stack, comparing its priority with priorities of other operators in stack,
     * considering their associativity.
     * Adds operators from stack to RPN, if top in stack has lower priority.
     *
     * @param node      action to add.
     * @param operators operators in stack to compare.
     * @param rpn       Reverse Polish Notation.
     */
    private static void addToOperatorStack(ActionNode node, Stack<FormulaNode> operators, ArrayList<FormulaNode> rpn) {
        MathAction action = node.getNodeValue();
        int currentPriority = action.getPriority();
        boolean isCurrentRight = action instanceof Operator operator && operator.getIsRight();
        boolean isUnary = action.getArgAmount() == 1;
        while (!operators.isEmpty() && !isUnary) {
            FormulaNode top = operators.peek();
            if (top instanceof ActionNode topNode && topNode.getNodeValue() instanceof MathAction topOperator) {
                int topPriority = topOperator.getPriority();
                if (currentPriority < topPriority || (!isCurrentRight && currentPriority == topPriority)) {
                    rpn.add(operators.pop());
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        operators.push(node);
    }

    /**
     * Calculates result of expression in RPN, filling variable values based on provided map.
     * Throws exception, if map doesn't contain all variables or RPN in invalid.
     *
     * @param rpn       Expression in Reverse Polish Notation
     * @param variables Map like variableName: variableValue.
     * @return calculated result, if input is valid.
     */
    private Double calculateRPNExpression(ArrayList<FormulaNode> rpn, Map<String, Double> variables) {
        Stack<Double> operands = new Stack<>();
        for (FormulaNode node : rpn) {
            calculateNode(variables, node, operands);
        }
        return operands.pop();
    }

    /**
     * Calculates node in RPN, depending on its type.
     *
     * @param variables map like variableName: variableValue.
     * @param node      node to calculate
     * @param operands  stack of results from previous steps
     */
    private static void calculateNode(Map<String, Double> variables, FormulaNode node, Stack<Double> operands) {
        if (node instanceof DigitNode operandNode) {
            operands.push(operandNode.getNodeValue());
        } else if (node instanceof VariableNode operandNode) {
            String variableName = operandNode.getNodeValue();
            Double value = variables.get(variableName);
            if (value == null) {
                throw new IllegalArgumentException("Undefined part of formula: " + variableName);
            }
            operands.push(value);
        } else if (node instanceof ActionNode actionNode) {
            MathAction action = actionNode.getNodeValue();
            int argsAmount = action.getArgAmount();
            double[] args = new double[argsAmount];
            for (int i = argsAmount - 1; i >= 0; i--) {
                args[i] = operands.pop();
            }
            operands.push(action.apply(args));
        }
    }
}
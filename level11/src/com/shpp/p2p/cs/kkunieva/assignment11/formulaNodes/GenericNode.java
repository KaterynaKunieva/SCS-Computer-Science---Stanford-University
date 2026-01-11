package com.shpp.p2p.cs.kkunieva.assignment11.formulaNodes;

/**
 * Base for nodes with value (e.g. value of operand, name of variable, function).
 * @param <T> type of value.
 */
public class GenericNode<T> implements FormulaNode {
    private final T nodeValue;

    public GenericNode(T nodeValue) {
        this.nodeValue = nodeValue;
    }

    public T getNodeValue() {
        return nodeValue;
    }

    @Override
    public String toString(){
        return nodeValue.toString();
    }
}

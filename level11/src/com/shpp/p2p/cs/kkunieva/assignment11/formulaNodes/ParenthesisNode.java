package com.shpp.p2p.cs.kkunieva.assignment11.formulaNodes;

/**
 * Node in formula nodes representing parenthesis (opened or closed)
 */
public class ParenthesisNode implements FormulaNode {

    public enum Type {
        OPEN,
        CLOSE
    }
    private final Type type;

    public ParenthesisNode(Type type) {
        this.type = type;
    }

    public boolean isOpen() {
        return type == Type.OPEN;
    }

    @Override
    public String toString(){
        return isOpen() ? "(" : ")";
    }
}

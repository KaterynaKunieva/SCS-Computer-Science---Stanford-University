package com.shpp.p2p.cs.kkunieva.assignment11.formulaNodes;

import com.shpp.p2p.cs.kkunieva.assignment11.mathActions.MathAction;

/**
 * Node in formula nodes representing MathAction (operator or function)
 */
public class ActionNode extends GenericNode<MathAction> {
        
    public ActionNode(MathAction nodeValue) {
        super(nodeValue);
    }
}

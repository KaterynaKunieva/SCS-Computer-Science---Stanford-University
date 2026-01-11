package com.shpp.p2p.cs.kkunieva.assignment11.evaluators;

public class ShuntingYardTest extends CalculatorTest {

    /**
     * Creates and returns instance of tested evaluator
     *
     * @return instance of evaluator
     */
    @Override
    protected Evaluator createEvaluator() {
        return new ShuntingYard();
    }
}

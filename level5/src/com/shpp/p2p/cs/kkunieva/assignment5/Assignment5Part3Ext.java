package com.shpp.p2p.cs.kkunieva.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Assignment5Part3Ext extends TextProgram {
    Assignment5Part3 testClass = new Assignment5Part3();

    public void run() {
        HashMap<String, Integer> WORD_CASES = new HashMap<>();
        WORD_CASES.put("QqQ", 0);
        WORD_CASES.put("NPT", 713);
        WORD_CASES.put("XCX", 2);

        for (Map.Entry<String, Integer> entry : WORD_CASES.entrySet()) {
            checkFindWords(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Given a test case and an expected result for that test case, runs the test
     * and reports the result.
     *
     * @param testCase        The test case to try out.
     * @param expectedAmount  What the result of the test should be.
     */
    private void checkFindWords(String testCase, Integer expectedAmount) {
        ArrayList<String> result = testClass.findWords(testCase);
        if (result.size() == expectedAmount) {
            println("Pass: " + testCase +  ". Result: " + "(" + result.size() + ")" + result);
        } else {
            println("FAIL: " + testCase +
                    ". Result: " +
                    "(" + result.size() + "!=" + expectedAmount + ")" + result
            );
        }
    }
}
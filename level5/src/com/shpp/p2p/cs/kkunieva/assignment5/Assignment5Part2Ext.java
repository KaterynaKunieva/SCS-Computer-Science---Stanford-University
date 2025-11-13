package com.shpp.p2p.cs.kkunieva.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.util.HashMap;
import java.util.Map;

public class Assignment5Part2Ext extends TextProgram {
    Assignment5Part2 testClass = new Assignment5Part2();

    public void run() {
        HashMap<String[], String> CASES = new HashMap<>();
        CASES.put(new String[]{"0", "0"}, "0");
        CASES.put(new String[]{"1", "2"}, "3");
        CASES.put(new String[]{"999", "879"}, "1878");
        CASES.put(new String[]{"879", "999"}, "1878");
        CASES.put(new String[]{"137", "865"}, "1002");
        CASES.put(new String[]{"1", "999"}, "1000");
        CASES.put(new String[]{"6993309969", "1"}, "6993309970");
        CASES.put(new String[]{"6993309969", "234884"}, "6993544853");
        CASES.put(new String[]{"43252003274489856000", "11111"}, "43252003274489867111");
        CASES.put(new String[]{"43252003274489856000", "2384398327849823"}, "43254387672817705823");
        CASES.put(new String[]{"43252003274489856000", "43252003274489856000"}, "86504006548979712000");
        CASES.put(new String[]{"5", "5"}, "10");
        CASES.put(new String[]{"10", "90"}, "100");
        CASES.put(new String[]{"9999", "1"}, "10000");
        CASES.put(new String[]{"123456789", "987654321"}, "1111111110");
        CASES.put(new String[]{"100000000000000000000", "1"}, "100000000000000000001");
        CASES.put(new String[]{"500000000000000000000", "500000000000000000000"}, "1000000000000000000000");
        CASES.put(new String[]{"12345678901234567890", "98765432109876543210"}, "111111111011111111100");
        CASES.put(new String[]{"999999999999999999999", "999999999999999999999"}, "1999999999999999999998");
        CASES.put(new String[]{"1000000000000000000000", "999999999999999999999"}, "1999999999999999999999");
        CASES.put(new String[]{"42", "0"}, "42");
        CASES.put(new String[]{"0", "42"}, "42");
        CASES.put(new String[]{"1000000000000000", "999999999999999"}, "1999999999999999");

        for (Map.Entry<String[], String> entry : CASES.entrySet()) {
            check(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Given a test case and an expected result for that test case, runs the test
     * and reports the result.
     *
     * @param testCase       The test case to try out.
     * @param expectedResult What the result of the test should be.
     */
    private void check(String[] testCase, String expectedResult) {
        String result = testClass.addNumericStrings(testCase[0], testCase[1]);
        if (result.equals(expectedResult)) {
            println("Pass: " + String.join("+", testCase) + "=" + result);
        } else {
            println("FAIL: " + String.join("+", testCase) + "=" + result + " != " + expectedResult);
        }
    }
}

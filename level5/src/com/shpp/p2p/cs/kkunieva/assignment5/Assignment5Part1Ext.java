package com.shpp.p2p.cs.kkunieva.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.util.HashMap;
import java.util.Map;

public class Assignment5Part1Ext extends TextProgram {
    Assignment5Part1 testClass = new Assignment5Part1();

    public void run() {
        HashMap<String, Integer> CASES = new HashMap<>();
        CASES.put("E", 1);
        CASES.put("Unity", 3);
        CASES.put("Unite", 2);
        CASES.put("Growth", 1);
        CASES.put("Possibilities", 5);
        CASES.put("Nimble", 1); // 2, but in simple algorithm is 1
        CASES.put("Me", 1);
        CASES.put("Beautiful", 3);
        CASES.put("Manatee", 3);
        CASES.put("sycee", 2);
        CASES.put("askee", 2);
        CASES.put("the", 1);
        CASES.put("be", 1);
        CASES.put("she", 1);
        CASES.put("quokka", 2);
        CASES.put("springbok", 2);
        CASES.put("syllable", 2); // 3, but in simple algorithm is 2
        CASES.put("are", 1);
        CASES.put("area", 2); // 3, but in simple algorithm is 2
        CASES.put("Cookie", 2);
        CASES.put("Rookie", 2);
        CASES.put("Fairy", 2);
        CASES.put("Daisy", 2);
        CASES.put("Key", 1);
        CASES.put("Money", 2);
        CASES.put("Journey", 2);
        CASES.put("Honey", 2);
        CASES.put("Monkey", 2);
        CASES.put("Turkey", 2);
        CASES.put("Donkey", 2);
        CASES.put("Alley", 2);
        CASES.put("Valley", 2);
        CASES.put("Freeway", 2);
        CASES.put("Runway", 2);
        CASES.put("Bluejay", 2);
        CASES.put("Trolley", 2);
        CASES.put("Essay", 2);
        CASES.put("Subway", 2);
        CASES.put("Okay", 2);
        CASES.put("Delay", 2);
        CASES.put("Obey", 2);
        CASES.put("Relay", 2);
        CASES.put("Survey", 2);
        CASES.put("Sunday", 2);
        CASES.put("Display", 2);
        CASES.put("Decay", 2);
        CASES.put("Boy", 1);
        CASES.put("Toy", 1);
        CASES.put("Joy", 1);
        CASES.put("Annoy", 2);
        CASES.put("Employ", 2);
        CASES.put("Enjoy", 2);
        CASES.put("Destroy", 2);
        CASES.put("Eat", 1);
        CASES.put("Each", 1);
        CASES.put("Beach", 1);
        CASES.put("Teach", 1);
        CASES.put("Reach", 1);
        CASES.put("Peach", 1);
        CASES.put("Leaf", 1);
        CASES.put("Speak", 1);
        CASES.put("Dream", 1);
        CASES.put("Stream", 1);
        CASES.put("Team", 1);
        CASES.put("Cream", 1);
        CASES.put("Steam", 1);
        CASES.put("Clean", 1);
        CASES.put("Mean", 1);
        CASES.put("Neat", 1);
        CASES.put("Bead", 1);
        CASES.put("Read", 1);
        CASES.put("Lead", 1);
        CASES.put("Head", 1);
        CASES.put("Bread", 1);
        CASES.put("Dead", 1);
        CASES.put("Meat", 1);
        CASES.put("Heat", 1);
        CASES.put("Seat", 1);
        CASES.put("Beast", 1);
        CASES.put("Feast", 1);
        CASES.put("Least", 1);
        CASES.put("East", 1);
        CASES.put("Near", 1);
        CASES.put("Clear", 1);
        CASES.put("Year", 1);
        CASES.put("Tear", 1);
        CASES.put("Fear", 1);
        CASES.put("Appear", 2);
        CASES.put("Idea", 2); // 3, but in simple algorithm is 2
        CASES.put("Ocean", 2);
        CASES.put("Feature", 2);
        CASES.put("Creature", 2);
        CASES.put("Leader", 2);
        CASES.put("Reader", 2);
        CASES.put("Eager", 2);
        CASES.put("Meaning", 2);
        CASES.put("Season", 2);
        CASES.put("Reason", 2);
        CASES.put("Pleasure", 2);
        CASES.put("Measure", 2);
        CASES.put("Theater", 2);
        CASES.put("Teacher", 2);
        CASES.put("Really", 2);

        for (Map.Entry<String, Integer> entry : CASES.entrySet()) {
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
    private void check(String testCase, int expectedResult) {
        int result = testClass.syllablesInWord(testCase);
        if (result == expectedResult) {
            println("Pass: " + testCase + ". Result: " + result);
        } else {
            println("FAIL: " + testCase + ". Result: " + result + ". Expected: " + expectedResult);
        }
    }
}
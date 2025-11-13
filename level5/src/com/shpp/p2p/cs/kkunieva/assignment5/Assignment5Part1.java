package com.shpp.p2p.cs.kkunieva.assignment5;

import com.shpp.cs.a.console.TextProgram;

/**
 * A program that takes a word (a set of at least 1 letter) as input and outputs an approximate number of
 * syllables.
 * It uses a simple algorithm to count non-consecutive vowels + 'Y' in the word,
 * with special handling for a final 'E'.
 */
public class Assignment5Part1 extends TextProgram {
    private static final String[] VOWELS = {"A", "E", "I", "O", "U", "Y"};

    /**
     * Repeatedly prompts the user for a word and print out the estimated
     * number of syllables in that word.
     */
    public void run() {
        while (true) {
            String word = readLine("Enter a single word: ");
            println("  Syllable count: " + syllablesInWord(word));
        }
    }

    /**
     * Checks if a single-character string is considered a vowel, ignoring case.
     *
     * @param letter The single-character string to check.
     * @return true if the letter is a vowel, false otherwise.
     */
    private static boolean isVowel(String letter) {
        for (String vowel : VOWELS) {
            if (letter.equalsIgnoreCase(vowel)) return true;
        }
        return false;
    }

    /**
     * Returns the letter at the specified index in the string.
     *
     * @param str The full string (word or sentence).
     * @param i   The index inside the string.
     * @return A single-character string containing the letter, or an empty string if the index is out of bounds.
     */
    private static String getLetter(String str, int i) {
        return i >= 0 && i < str.length() ? "" + str.charAt(i) : "";
    }

    /**
     * Checks if the letter at the specified index is the last letter of the string and equals the specified letter.
     *
     * @param str             The full string.
     * @param letterToCompare The letter to compare against.
     * @param i               The index of the letter to compare.
     * @return true, if the last at index is the last letter and matches letterToCompare, false otherwise.
     */
    private static boolean isLastLetter(String str, String letterToCompare, int i) {
        String letter = getLetter(str, i);
        return i == str.length() - 1 && letter.equalsIgnoreCase(letterToCompare);
    }

    /**
     * Checks conditions to skip a letter (meaning it does not count as new syllable).
     *
     * @param word Full word.
     * @param i    Index of letter inside word.
     * @return true, if the letter should be skipped, false otherwise.
     *
     */
    private boolean shouldSkipLetter(String word, int i) {
        String letter = getLetter(word, i);
        boolean currentNotVowel = !isVowel(letter);
        boolean isLastE = isLastLetter(word, "E", i) && !isVowel(getLetter(word, i - 1));
        boolean nextIsVowel = isVowel(getLetter(word, i + 1));
        return currentNotVowel || isLastE || nextIsVowel;
    }

    /**
     * Given a word, estimates the number of syllables in that word according to the heuristic specified in the handout.
     *
     * @param word A string containing a single word.
     * @return An estimate of the number of syllables in that word.
     */
    protected int syllablesInWord(String word) {
        int syllables = 0;
        for (int i = 0; i < word.length(); i++) {
            if (shouldSkipLetter(word, i)) continue;
            syllables++;
        }
        return syllables == 0 ? 1 : syllables;
    }
}
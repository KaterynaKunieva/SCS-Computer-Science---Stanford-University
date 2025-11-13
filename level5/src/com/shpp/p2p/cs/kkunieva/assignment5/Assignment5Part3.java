package com.shpp.p2p.cs.kkunieva.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.util.ArrayList;

/**
 * Repeatedly prompts the user for a specified amount of letters.
 * It searches words that contain all the input letters in the same relative order in the dictionary,
 * and prints a report of the found words.
 */
public class Assignment5Part3 extends TextProgram {
    /**
     * Path to the dictionary file.
     */
    private static final String DICTIONARY_PATH = "en-dictionary.txt";

    /**
     * Amount of letters to ask user for.
     */
    private static final int LETTERS_AMOUNT = 3;

    /**
     * Reads the dictionary file, then repeatedly prompts letters and prints the report of found words.
     */
    public void run() {
        ArrayList<String> dictionary = IOHelper.getFileLines(DICTIONARY_PATH);
        if (dictionary.isEmpty()) {
            println(String.format("Dictionary on provided path %s is empty. \n" +
                    "Please check the constant DICTIONARY_PATH, location and content of the file. ", DICTIONARY_PATH));
            return;
        }
        runConsoleUI(dictionary);
    }

    /**
     * Runs loop of console interface: prompts user to enter sequence of letters and prints output.
     *
     * @param dictionary dictionary file line by line.
     */
    private void runConsoleUI(ArrayList<String> dictionary) {
        while (true) {
            String str = readLine(String.format("Enter %d letters: ", LETTERS_AMOUNT));
            ArrayList<String> words = findWords(str, dictionary);
            if (words.isEmpty()) {
                println(String.format("\tCannot create word using your letters \"%s\". \n" +
                        "\tTry to input another combination or update dictionary and restart program. ", str));
            } else {
                println(String.format("\tWords: (%s) %s", words.size(), String.join(", ", words)));
            }
            println();
        }
    }

    /**
     * Finds words in the dictionary that contain all the provided letters in the same order.
     * The search is case-insensitive.
     *
     * @param letters    Order of letters to search for.
     * @param dictionary List of words to search within.
     * @return A list of found words.
     */
    private ArrayList<String> findWords(String letters, ArrayList<String> dictionary) {
        ArrayList<String> words = new ArrayList<>();
        for (String word : dictionary) {
            if (shouldAddWord(word, letters)) {
                words.add(word);
            }
        }
        return words;
    }

    /**
     * Checks if each symbol in sequence appears in the word in the same order.
     *
     * @param word     word to check
     * @param sequence sequence of symbols to check
     * @return true, if letters appear in the word in correct order; otherwise, false.
     */
    private boolean shouldAddWord(String word, String sequence) {
        int previousIndex = -1;
        boolean wordFound = true;
        for (int i = 0; i < sequence.length(); i++) {
            char letter = sequence.charAt(i);
            // First correct letter after previous one
            int currentIndex = word.toLowerCase().indexOf(Character.toLowerCase(letter),
                    previousIndex + 1);
            if (currentIndex == -1) {
                wordFound = false;
                break;
            }
            previousIndex = currentIndex;
        }
        return wordFound;

    }

    /**
     * Finds words that contain all the provided letters in the same order using default dictionary.
     * The search is case-insensitive.
     *
     * @param letters Order of letters to search for.
     * @return A list of found words.
     */
    protected ArrayList<String> findWords(String letters) {
        return findWords(letters, IOHelper.getFileLines(DICTIONARY_PATH));
    }
}
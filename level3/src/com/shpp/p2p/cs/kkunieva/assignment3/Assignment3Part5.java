package com.shpp.p2p.cs.kkunieva.assignment3;

import com.shpp.cs.a.console.TextProgram;

import java.util.Random;

/**
 * Simulates the Bernoulli Casino coin-toss game.
 * The player tosses a coin repeatedly, doubling the prize for "Heads"
 * and collecting the prize for "Tails", until a total of $20 is earned.
 */
public class Assignment3Part5 extends TextProgram {
    public static final String[] COIN_SIDES = {"Heads", "Tails"};
    private final Random random = new Random();

    /**
     * Runs the Bernoulli Casino simulation.
     * Tosses a coin repeatedly, updating the prize and total winnings:
     * - If "Heads", the prize doubles.
     * - If "Tails", the prize is added to total winnings, and the prize resets to 1.
     * Prints the results of each round and the total number of games played
     * to reach $20 winnings.
     */
    @Override
    public void run() {
        int winAmount = 0;
        int prize = 1;
        int counter = 0;
        while (winAmount < 20) {
            String coinSide = getRandomValue(COIN_SIDES); // toss a coin
            if (coinSide.equals(COIN_SIDES[0])) {
                prize *= 2; // double prize
            } else {
                counter++;
                winAmount += prize;
                println(String.format("This game, you earned $%d", prize));
                println(String.format("Your total is $%d", winAmount));
                prize = 1;
            }
        }
        println(String.format("It took %d games to earn $20", counter));
    }

    /**
     * Returns a randomly selected element from the given array.
     *
     * @param values array of elements to choose from
     * @return a random element from the array
     */
    private String getRandomValue(String[] values) {
        return values[random.nextInt(values.length)];
    }
}
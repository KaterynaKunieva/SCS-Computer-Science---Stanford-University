package com.shpp.p2p.cs.kkunieva.assignment4;

/**
 * Stores and manages the state of the game.
 */
public class GameState {
    /**
     * Amount of bricks left on the window.
     */
    private int bricksLeft;

    /**
     * Flag indicating whether the player has won the game.
     */
    private boolean isWin;

    /**
     * Constructor to create instance of GameState.
     *
     * @param bricksAmount start amount of bricks on the window.
     */
    public GameState(int bricksAmount) {
        this.bricksLeft = bricksAmount;
        this.isWin = false;
    }

    /**
     * Returns the number of bricks left.
     *
     * @return amount of left bricks.
     */
    public int getBricksLeft() {
        return this.bricksLeft;
    }

    /**
     * Returns the win status of the game.
     *
     * @return value of the isWin.
     */
    public boolean getIsWin() {
        return this.isWin;
    }

    /**
     * Decrements the count of remaining bricks and updates the win status if the brick count reaches zero.
     */
    public void destroyBrick() {
        this.bricksLeft--;
        isWin = bricksLeft <= 0;
    }
}

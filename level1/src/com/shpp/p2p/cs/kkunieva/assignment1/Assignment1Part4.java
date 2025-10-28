package com.shpp.p2p.cs.kkunieva.assignment1;

/**
 * File: Assignment1Part4.java
 * Solution for the task of building chessboard pattern using beepers on a map of any size.
 * Prerequisites: Karel starts in the southwest corner, facing East.
 * Result: The map is filled with a chessboard pattern of beepers, starting with a beeper on
 * Karel's initial cell.
* */

public class Assignment1Part4 extends SuperKarel {
    @Override
    public void run() throws Exception {
        buildChess();
    }

    /**
     * Recursively build the chessboard pattern staring from Karel's current position.
     * Karel fills one row, moves to the next, and repeats until the top is reached.
     * Prerequisites: Karel is in the southwest corner, facing East.
     * Result: The entire map is filled in a chessboard pattern.
     * */
    public void buildChess() throws Exception {
        drawRow();

        // Check is there another row above
        if ((facingWest() && rightIsClear()) || (facingEast() && leftIsClear())) {
            if (beepersPresent()) {
                moveToNextRow();
                ensureBeeperPresent(); // mark that previous row ended with a beeper,
                // this marker will be removed in the next row before drawing
                buildChess();
            }else {
                moveToNextRow();
                buildChess();
            }
        }
    }

    /**
     * Moves Karel up one row (if possible), turning him to correct direction for drawing the next row.
     * Prerequisites: Karel is in the end of the row, facing East or West.
     * Result: Karel moved to next row and turn to the correct direction to start drawing the next row.
     * */
    public void moveToNextRow() throws Exception {
        if (facingWest() && rightIsClear()) {
            turnRight();
            move();
            turnRight();
        }else if (facingEast() && leftIsClear()) {
            turnLeft();
            move();
            turnLeft();
        }
    }

    /**
     * Fills a row in a chess pattern, starting with a beeper on a current cell.
     * Prerequisites: Karel is at the start of a row, facing the right direction.
     * Result:
     * - Karel located on the opposite end of a row, facing the same direction.
     * - The row is filled by beepers in chess pattern.
     * */
    public void drawRowStartingWithBeeper() throws Exception {
        ensureBeeperPresent();
        while (frontIsClear()) {
            move();
            if (frontIsClear()) {
                move();
                ensureBeeperPresent();
            }
        }
    }

    /**
     * Fills a row in a chess pattern, starting without a beeper on the first cell.
     * Prerequisites: Karel is on the cell from which start to draw a chess, facing the right direction.
     * Result: Karel located on the end of start row, facing the original direction. The row if filled
     * by beepers in chess pattern.
     * */
    public void drawRowStartingWithoutBeeper() throws Exception {
        if (frontIsClear()) {
            move();
            drawRowStartingWithBeeper();
        }
    }

    /**
     * SDecides whether the row should start with a beeper or not based of the state of the current cell.
     * Prerequisites: Karel is at the start of the row, facing to opposite end of the row.
     * Result: Karel located on the end of the row, facing the original direction.
     * The row is filled with a chessboard pattern of beepers.
     * */
    public void drawRow() throws Exception {
        if (beepersPresent()) {
            pickBeeper(); // remove the marker from the previous row
            drawRowStartingWithoutBeeper();
        }
        else {
            drawRowStartingWithBeeper();
        }
    }
}

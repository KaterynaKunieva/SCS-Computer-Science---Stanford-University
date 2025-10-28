package com.shpp.p2p.cs.kkunieva.assignment1;

/**
 * File: Assignment1Part3.java
 * Solution for the task of finding the middle of the South side.
 * Prerequisites: Karel starts in the southwest corner, facing East.
 * Result: A beeper is placed in the center cell of the South side
 * (or in one of the two central cells if the width is even).
* */

public class Assignment1Part3 extends SuperKarel {
    @Override
    public void run() throws Exception {
        // Handle worlds with only one column (1xN)
        turnLeft();
        if (leftIsBlocked() && rightIsBlocked()) {
            ensureBeeperPresent();
        }
        else {
            turnRight();
            findMiddle(); // middle is found (cell with no beepers)

            // Move to the end of the row
            turnAround();
            moveUntilWall();
            turnAround();

            // Reverse beepers in each cell in the row
            invertBeepers();
            while (frontIsClear()) {
                move();
                invertBeepers();
            }
        }
    }

    /**
     * Recursively finds the middle in the side.
     * Places beepers from both sides toward the center until the middle cell is reached.
     * Prerequisites: Karel is on an empty cell before an existing beeper or wall, facing inward.
     * Result: The middle cell is left empty, all others contain beepers.
     * */
    public void findMiddle() throws Exception {
        ensureBeeperPresent();
        moveUntilBeeper();
        // Check the next cell
        if (frontIsClear()) {
            move();
            if (noBeepersPresent()) {
                turnAround();
                move();
                turnAround();
                findMiddle();
            }
        }
    }

    /**
     * Inverts cell: beeper => empty; empty => beeper.
     * Prerequisites: Karel located on the cell to be inverted.
     * Result: state of cell if toggled.
     * */
    public void invertBeepers() throws Exception {
        if (beepersPresent()) {
            pickAllBeepers();
        }
        else {
            putBeeper();
        }
    }

    /**
     * Moves Karel forward until a beeper or wall is encountered.
     * Prerequisites: Karel is facing the direction where the next beeper should be found.
     * Result: Karel located on the cell before beeper or wall, facing the opposite direction.
     * */
    public void moveUntilBeeper() throws Exception {
        if (frontIsClear()) {
            move();
            if (beepersPresent()) {
                // Stop one cell before beeper
                turnAround();
                move();
            }
            else {
                moveUntilBeeper();
            }
        }
        else {
            // Stop one cell before wall
            turnAround();
        }
    }
}

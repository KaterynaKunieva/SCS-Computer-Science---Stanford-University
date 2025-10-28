package com.shpp.p2p.cs.kkunieva.assignment1;

/**
 * File: Assignment1Part1.java
 * Solution for the task about picking up newspaper inside the house.
 * Prerequisites: Karel located in the northwest corner, facing East side.
 * Newspaper located in the gap in East wall.
 * Result: Karel located on the start position with the picked beeper (newspaper),
 * facing original direction.
* */

public class Assignment1Part1 extends SuperKarel {
    @Override
    public void run() throws Exception {
        moveToNewspaper();          // 1. move to the newspaper
        pickAllBeepers();           // 2. pick up the newspaper
        returnStartPosition();      // 3. return to start position
    }
    
    /**
     * Moves Karel clockwise along the wall until a gap is found.
     * Prerequisites: A wall is located on Karel's left side.
     * Result: A gap is located on Karel’s left side, and Karel is facing South.
     * */
    public void moveClockwiseAlongWall() throws Exception {
        if (frontIsClear() && leftIsBlocked()) {
            move();
            moveClockwiseAlongWall();
        }
        else if (frontIsBlocked() && leftIsBlocked()) {
            turnRight();
            moveClockwiseAlongWall();
        }
    }

    /**
     * Moves Karel to the newspaper’s location by following the wall and entering the opening.
     * Prerequisites: Karel located in the northwest corner, facing East side.
     * Result: Karel located in the newspaper location, facing East side.
     * */
    public void moveToNewspaper() throws Exception {
        moveClockwiseAlongWall();
        turnLeft();
        move();
        move();
    }

    /**
     * Moves Karel from the newspaper location back to the start position.
     * Prerequisites: Karel located in the newspaper location, facing East side.
     * Result: Karel located on start position, facing East side.
     * */
    public void returnStartPosition() throws Exception {
        turnAround();
        moveUntilWall();
        turnRight();
        moveUntilWall();
        turnRight();
    }
}

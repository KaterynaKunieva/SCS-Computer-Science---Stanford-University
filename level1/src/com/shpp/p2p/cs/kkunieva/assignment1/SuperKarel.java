package com.shpp.p2p.cs.kkunieva.assignment1;
import com.shpp.karel.*;

/**
 * File: SuperKarel.java
 * A utility superclass that extends KarelTheRobot
* */

public class SuperKarel extends KarelTheRobot {
    /**
    * Turns Karel 180 degrees.
    * Prerequisites: Any position of Karel.
    * Result: Karel turns 180 degrees.
    * */
    public void turnAround() throws Exception {
        for (int i = 0; i < 2; i++) {
            turnLeft();
        }
    }

    /**
     * Turns Karel to the right, using 3 left rotations.
     * Prerequisites: Any position of Karel.
     * Result: Karel rotated to the right.
     * */
    public void turnRight() throws Exception {
        for (int i = 0; i < 3; i++) {
            turnLeft();
        }
    }

    /**
    * Picks up all beepers on the current cell.
    * Prerequisites: Any position of Karel on a cell with zero or more beepers.
    * Result: No beepers remain on the current cell.
    *  */
    public void pickAllBeepers() throws Exception {
        while (beepersPresent()) {
            pickBeeper();
        }
    }

    /**
     * Puts a beeper if none exist on the current cell.
     * Prerequisites: Karel on the cell with or without beepers.
     * Result: At least one beeper is present on the current cell.
     * */
    public void ensureBeeperPresent() throws Exception {
        if (noBeepersPresent()) {
            putBeeper();
        }
    }

    /**
     * Moves Karel until the wall on the way.
     * Prerequisites: Any position of Karel, facing the direction of the moving.
     * Result: Karel facing the wall.
     * */
    public void moveUntilWall() throws Exception {
        while(frontIsClear()){
            move();
        }
    }
}

package com.shpp.p2p.cs.kkunieva.assignment1;

/**
 * File: AssignmentPart2.java
 * Solution for the task of building columns using beepers.
 * Karel moves along the southern edge between columns and processes
 * each column twice — going up to build it, then returning down.
 * Prerequisites:
 * - Karel starts in the southwest corner, facing East.
 * - Karel's backpack is full of beepers.
 * - Columns (walls) are placed every 4th cell, starting from the east wall.
 * - Map ends with column on the East side.
 * - No isolated walls (all connected to building).
 * - The first southern row contains no walls.
 * Result:
 * - Each column has a complete row of beepers underneath it.
 * - Karel located in the last cell on the south side, facing South.
* */

public class Assignment1Part2 extends SuperKarel {
    @Override
    public void run() throws Exception {
        buildAllColumns();
    }
    
    /**
     * Recursively builds all columns of the building.
     * Prerequisites: Karel located on the southwest corner, facing East.
     * Result: All columns are built. Karel finishes in the last cell of south side, facing South.
     * */
    public void buildAllColumns() throws Exception {
        turnLeft();
        buildColumn();          // build the current column (bottom => top)
        moveToStartOfColumn();  // return to the bottom

        if (leftIsClear()) {
            // move to next column and repeat
            turnLeft();
            moveToNextColumn();
            buildAllColumns();
        }
    }

    /**
     * Moves Karel 4 cells forward to next column.
     * Prerequisites: Karel has enough free columns in front.
     * Result: Karel moves up to 4 cells forward (or until blocked).
     * */
    public void moveToNextColumn() throws Exception {
        for (int i=0; i<4; i++) {
            if (frontIsClear()) {
                move();
            }
        }
    }

    /**
     * Moves Karel to start of the column.
     * Prerequisites: Karel is at the top of a column, facing North.
     * Result: Karel is at the bottom of the same column, facing South.
     * */
    public void moveToStartOfColumn() throws Exception {
        turnAround();
        moveUntilWall();
    }

    /**
     * Builds a single column by filling each cell with a beeper.
     * Prerequisites: Karel is at the start of the column (south side), facing North.
     * Result: Karel located on the top of the column, facing North,
     * and every cell in the column contains a beeper.
     * */
    public void buildColumn() throws Exception {
        ensureBeeperPresent();
        while (frontIsClear()) {
            move();
            ensureBeeperPresent();
        }
    }
}

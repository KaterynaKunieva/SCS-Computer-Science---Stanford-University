package com.shpp.p2p.cs.kkunieva.assignment3;

import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * Graphic program that builds a horizontally centered pyramid of bricks.
 * The pyramid has a fixed number of bricks in the base, and each row above
 * has one less brick than the row below.
 * Brick dimensions and colors are configurable via constants.
 */

public class Assignment3Part4 extends WindowProgram {
    private static final int BRICK_HEIGHT = 30;
    private static final int BRICK_WIDTH = 150;
    private static final int BRICKS_IN_BASE = 4;

    private static final Color BRICK_FILLING = new Color(162, 82, 55);
    private static final Color BRICK_BORDER = new Color(70, 74, 66);

    /**
     * Entry point of the program. Calculates the starting Y-coordinate
     * for the pyramid and calls buildPyramid.
     */
    @Override
    public void run() {
        double startY = getHeight();
        buildPyramid(startY);
    }

    /**
     * Builds a pyramid of bricks, row by row, starting from the bottom.
     *
     * @param startY Y-coordinate of the bottom edge of the pyramid
     */
    private void buildPyramid(double startY) {
        for (int brickRow = 0; brickRow < BRICKS_IN_BASE; brickRow++) {
            int bricksInRow = BRICKS_IN_BASE - brickRow;
            double rowY = startY - (brickRow + 1) * BRICK_HEIGHT;
            buildRow(bricksInRow, rowY);
        }
    }

    /**
     * Builds a row of bricks centered horizontally.
     *
     * @param baseAmount number of bricks in this row
     * @param startY     Y-coordinate of the top edge of the row
     */
    private void buildRow(int baseAmount, double startY) {
        double rowX = getCenteredX(baseAmount * BRICK_WIDTH);
        for (int brickCol = 0; brickCol < baseAmount; brickCol++) {
            double brickX = rowX + brickCol * BRICK_WIDTH;
            buildBrick(brickX, startY);
        }
    }

    /**
     * Adds a single brick (GRect) at the specified coordinates with the configured
     * color and border.
     *
     * @param x X-coordinate of top-left corner of the brick
     * @param y Y-coordinate of top-left corner of the brick
     */
    private void buildBrick(double x, double y) {
        GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        brick.setFilled(true);
        brick.setColor(BRICK_BORDER);
        brick.setFillColor(BRICK_FILLING);
        add(brick);
    }

    /**
     * Returns the X-coordinate required to horizontally center an object
     * of the specified width within the window.
     *
     * @param width the width of the object
     * @return X-coordinate for horizontal centering
     */
    private double getCenteredX(int width) {
        return (getWidth() - width) / 2.0;
    }
}
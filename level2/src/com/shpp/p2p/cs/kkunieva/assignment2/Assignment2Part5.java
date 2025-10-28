package com.shpp.p2p.cs.kkunieva.assignment2;

import acm.graphics.GRect;

import java.awt.*;

/**
 * A graphics program that draws a centered grid of black boxes separated by white spaces.
 */
public class Assignment2Part5 extends SuperWindowProgram {
    /* The number of rows and columns in the grid, respectively. */
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 6;

    /* The width and height of each box. */
    private static final double BOX_SIZE = 40;

    /* The horizontal and vertical spacing between the boxes. */
    private static final double BOX_SPACING = 10;

    public void run() {
        double centeredX = getCenteredX();
        double centeredY = getCenteredY();
        drawGrid(centeredX, centeredY);
    }

    /**
     * Computes total width of the grid and returns x-coordinate of the top-left corner
     * to center grid horizontally.
     *
     * @return The centered x-coordinate.
     */
    private double getCenteredX() {
        double gridWidth = BOX_SIZE * NUM_COLS + (NUM_COLS - 1) * BOX_SPACING;
        return (getWidth() - gridWidth) / 2;
    }

    /**
     * Computes total height of the grid and returns y-coordinate of the top-left corner
     * to center grid vertically.
     *
     * @return The centered y-coordinate.
     */
    private double getCenteredY() {
        double gridHeight = BOX_SIZE * NUM_ROWS + (NUM_ROWS - 1) * BOX_SPACING;
        return (getHeight() - gridHeight) / 2;
    }

    /**
     * Draws grid filled by black boxes starting from (x, y) position.
     *
     * @param x x-coordinate of the top-left corner of the grid.
     * @param y y-coordinate of the top-left corner of the grid.
     */
    private void drawGrid(double x, double y) {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                double boxX = x + col * (BOX_SIZE + BOX_SPACING);
                double boxY = y + row * (BOX_SIZE + BOX_SPACING);
                drawBox(boxX, boxY);
            }
        }
    }

    /**
     * Draws a filled black box at the specified position (x, y).
     *
     * @param x x-coordinate of the top-left corner of the box.
     * @param y y-coordinate of the top-left corner of the box.
     */
    private void drawBox(double x, double y) {
        GRect box = new GRect(x, y, BOX_SIZE, BOX_SIZE);
        fillObject(box, new Color(0, 0, 0));
        add(box);
    }
}

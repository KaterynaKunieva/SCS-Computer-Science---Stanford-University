package com.shpp.p2p.cs.kkunieva.assignment2;

import acm.graphics.GOval;

import java.awt.*;

/**
 * A graphics program that draws black pawprints.
 */

public class Assignment2Part3 extends SuperWindowProgram {
    /* Constants controlling the relative positions of the
     * three toes to the upper-left corner of the pawprint.
     */
    private static final double FIRST_TOE_OFFSET_X = 0;
    private static final double FIRST_TOE_OFFSET_Y = 20;
    private static final double SECOND_TOE_OFFSET_X = 30;
    private static final double SECOND_TOE_OFFSET_Y = 0;
    private static final double THIRD_TOE_OFFSET_X = 60;
    private static final double THIRD_TOE_OFFSET_Y = 20;

    /* The position of the heel relative to the upper-left
     * corner of the pawprint.
     */
    private static final double HEEL_OFFSET_X = 20;
    private static final double HEEL_OFFSET_Y = 40;

    /* Each toe is an oval with this width and height. */
    private static final double TOE_WIDTH = 20;
    private static final double TOE_HEIGHT = 30;

    /* The heel is an oval with this width and height. */
    private static final double HEEL_WIDTH = 40;
    private static final double HEEL_HEIGHT = 60;

    /* The default width and height of the window. These constants will tell Java to
     * create a window whose size is *approximately* given by these dimensions.
     */
    public static final int APPLICATION_WIDTH = 270;
    public static final int APPLICATION_HEIGHT = 220;

    public void run() {
        drawPawprint(20, 20);
        drawPawprint(180, 70);
    }

    /**
     * Draws a pawprint. The parameters should specify the upper-left corner of the
     * bounding box containing that pawprint.
     *
     * @param x The x coordinate of the upper-left corner of the bounding box for the pawprint.
     * @param y The y coordinate of the upper-left corner of the bounding box for the pawprint.
     */
    private void drawPawprint(double x, double y) {
        drawPawToes(x, y);
        drawPawHeel(x, y);
    }

    /**
     * Draws toes of the pawprint.
     *
     * @param x The x-coordinate of the top-left corner of full pawprint.
     * @param y The y-coordinate of the top-left corner of full pawprint.
     */
    private void drawPawToes(double x, double y) {
        double[][] offsets = {
                {FIRST_TOE_OFFSET_X, FIRST_TOE_OFFSET_Y},
                {SECOND_TOE_OFFSET_X, SECOND_TOE_OFFSET_Y},
                {THIRD_TOE_OFFSET_X, THIRD_TOE_OFFSET_Y},
        };
        for (double[] offset : offsets) {
            drawOval(x + offset[0], y + offset[1], TOE_WIDTH, TOE_HEIGHT);
        }
    }

    /**
     * Draws the heel of the pawprint.
     *
     * @param x The x-coordinate of the top-left corner of full pawprint.
     * @param y The y-coordinate of the top-left corner of full pawprint.
     */
    private void drawPawHeel(double x, double y) {
        drawOval(x + HEEL_OFFSET_X, y + HEEL_OFFSET_Y, HEEL_WIDTH, HEEL_HEIGHT);
    }

    /**
     * Draws black oval on specified location with specified sizes.
     *
     * @param x      The x-coordinate of the top-left corner of the oval.
     * @param y      The y-coordinate of the top-left corner of the oval.
     * @param width  Width of the oval.
     * @param height Height of the oval.
     */
    private void drawOval(double x, double y, double width, double height) {
        GOval oval = new GOval(x, y, width, height);
        fillObject(oval, new Color(0, 0, 0));
        add(oval);
    }
}
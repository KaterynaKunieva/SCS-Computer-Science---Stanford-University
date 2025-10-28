package com.shpp.p2p.cs.kkunieva.assignment2;

import acm.graphics.GOval;

import java.awt.*;

/**
 * A graphics program that draws a caterpillar composed of overlapping circles.
 */
public class Assignment2Part6 extends SuperWindowProgram {
    /* Amount of segments in the caterpillar. */
    private static final int SEGMENTS_AMOUNT = 5;

    /* Diameter of each segment. */
    private static final double SEGMENT_DIAMETER = 110;

    /* Colors of filling and border of the caterpillar segment, respectively. */
    private static final Color SEGMENT_FILL_COLOR = new Color(0, 178, 0);
    private static final Color SEGMENT_BORDER_COLOR = new Color(162, 49, 46);

    /* Offset ratio, for prettier result use 0.1 - 0.5.  */
    private static final double OFFSET_RATIO = 0.3;
    private static final double SEGMENT_SHIFT = OFFSET_RATIO * SEGMENT_DIAMETER;

    public void run() {
        double startX = 0;
        double startY = getCenteredY();
        drawCaterpillar(startX, startY);
    }

    /**
     * Calculates y-coordinate of the top-left corner of the caterpillar to center it vertically.
     *
     * @return Centered y-coordinate.
     * */
    private double getCenteredY(){
        return (getHeight() - (1 - OFFSET_RATIO) * SEGMENT_DIAMETER) / 2.0;
    }

    /**
     * Draws the full caterpillar starting at (x, y).
     *
     * @param x The x-coordinate of the first segment.
     * @param y The y-coordinate of the first segment.
     */
    private void drawCaterpillar(double x, double y) {
        for (int i = 0; i < SEGMENTS_AMOUNT; i++) {
            double segmentX = x + i * (SEGMENT_DIAMETER - SEGMENT_SHIFT);
            double segmentY = i % 2 == 0 ? y : y - SEGMENT_SHIFT;
            drawSegment(segmentX, segmentY);
        }
    }

    /**
     * Draws segment (circle) of the caterpillar on the specified coordinates.
     *
     * @param x x-coordinate of top-left corner of segment.
     * @param y y-coordinate of top-left corner of segment.
     */
    private void drawSegment(double x, double y) {
        GOval segment = new GOval(x, y, SEGMENT_DIAMETER, SEGMENT_DIAMETER);
        fillObject(segment, SEGMENT_FILL_COLOR);
        segment.setColor(SEGMENT_BORDER_COLOR);
        add(segment);
    }
}

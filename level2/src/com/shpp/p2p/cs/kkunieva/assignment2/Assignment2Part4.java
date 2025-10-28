package com.shpp.p2p.cs.kkunieva.assignment2;

import acm.graphics.GLabel;
import acm.graphics.GRect;

import java.awt.*;

/**
 * A graphics program that draws a triband flag that can be either vertical or horizontal.
 * The flag is centered on the canvas and labeled with the country's name.
 */
public class Assignment2Part4 extends SuperWindowProgram {
    /* The width and height of flag, respectively. */
    private static final int FLAG_WIDTH = 200;
    private static final int FLAG_HEIGHT = 140;

    /* The flag colors, listed in display order (top-to-bottom, left-to-right). */
    private static final Color[] FLAG_COLORS = {
            new Color(0, 33, 83),
            new Color(255, 255, 255),
            new Color(207, 9, 33),
    };

    /* Determines the flag orientation: true for horizontal, false for vertical. */
    private static final boolean IS_HORIZONTAL = false;

    /* Name of the country. */
    private static final String COUNTRY_NAME = "France";

    public static final int APPLICATION_WIDTH = 300;
    public static final int APPLICATION_HEIGHT = 300;

    public void run() {
        drawFlag();
        addCountryLabel();
    }

    /**
     * Draws a flag using colors defined in {@code FLAG_COLORS}.
     * Selects horizontal or vertical layout based on {@code IS_HORIZONTAL}.
     */
    private void drawFlag() {
        double centeredX = getCenteredX();
        double centeredY = getCenteredY();

        if (IS_HORIZONTAL) {
            drawHorizontalFlag(centeredX, centeredY);
        } else {
            drawVerticalFlag(centeredX, centeredY);
        }
    }

    /**
     * Calculates the x-coordinate of the flag's top-left corner so that
     * the flag is centered horizontally in the window.
     *
     * @return The centered x-coordinate.
     */
    private double getCenteredX() {
        return (getWidth() - FLAG_WIDTH) / 2.0;
    }

    /**
     * Calculates the y-coordinate of the flag's top-left corner so that
     * the flag is centered vertically in the window.
     *
     * @return The centered y-coordinate.
     */
    private double getCenteredY() {
        return (getHeight() - FLAG_HEIGHT) / 2.0;
    }

    /**
     * Draws the flag divided into horizontal stripes of equal height.
     *
     * @param x The x-coordinate of the flag's top-left corner.
     * @param y The y-coordinate of the flag's top-left corner.
     */
    private void drawHorizontalFlag(double x, double y) {
        double colorHeight = (double) FLAG_HEIGHT / FLAG_COLORS.length;
        for (int i = 0; i < FLAG_COLORS.length; i++) {
            double segmentY = y + i * colorHeight;
            drawFlagSegment(x, segmentY, FLAG_WIDTH, colorHeight, FLAG_COLORS[i]);
        }
    }

    /**
     * Draws the flag divided into vertical stripes of equal width.
     *
     * @param x The x-coordinate of the flag's top-left corner.
     * @param y The y-coordinate of the flag's top-left corner.
     */
    private void drawVerticalFlag(double x, double y) {
        double colorWidth = (double) FLAG_WIDTH / FLAG_COLORS.length;
        for (int i = 0; i < FLAG_COLORS.length; i++) {
            double segmentX = x + i * colorWidth;
            drawFlagSegment(segmentX, y, colorWidth, FLAG_HEIGHT, FLAG_COLORS[i]);
        }
    }

    /**
     * Draws a single stripe (rectangle) of the flag.
     *
     * @param x      The x-coordinate of the stripe's top-left corner.
     * @param y      The y-coordinate of the stripe's top-left corner.
     * @param width  The stripe's width.
     * @param height The stripe's height.
     * @param color  The stripe's fill color.
     */
    private void drawFlagSegment(double x, double y, double width, double height, Color color) {
        GRect colorSegment = new GRect(x, y, width, height);
        fillObject(colorSegment, color);
        colorSegment.setColor(color);
        add(colorSegment);
    }

    /**
     * Adds a text label with name of the country in the bottom-right corner of the window.
     */
    private void addCountryLabel() {
        GLabel label = new GLabel("Flag of " + COUNTRY_NAME);
        label.setFont("Arial-15");
        double labelX = getWidth() - label.getWidth();
        // Subtract the font descent to place the label fully above the bottom edge
        double labelY = getHeight() - label.getDescent();
        label.setLocation(labelX, labelY);
        add(label);
    }
}

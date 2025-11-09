package com.shpp.p2p.cs.kkunieva.assignment4;

import acm.graphics.GRect;

/**
 * Represents the paddle object (a GRect) in the Breakout Game.
 */
public class Paddle extends GRect {
    /**
     * Fixed Y coordinate of the top side of the paddle.
     */
    private final double fixedY;

    /**
     * Constructor to create a Paddle instance.
     *
     * @param width  width of the paddle.
     * @param height height of the paddle.
     * @param y      y-coordinate of the top side of the paddle.
     */
    Paddle(int width, int height, double y) {
        super(width, height);
        this.fixedY = y;
    }

    /**
     * Moves paddle horizontally to specified x coordinate of the left side of the paddle.
     *
     * @param x target x-coordinate of left side of the paddle.
     */
    public void slide(double x) {
        this.setLocation(x, this.fixedY);
    }
}

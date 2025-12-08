package com.shpp.p2p.cs.kkunieva.assignment8;

import acm.graphics.GRect;

import java.awt.*;

/**
 * Square with animation: jumping from window walls
 */
public class AnimatedSquare extends GRect {
    private double dx;
    private double dy;
    private final double maxX;
    private final double maxY;

    /**
     * Constructor to create animated square.
     * Places square by coordinates and fills it with color.
     *
     * @param size  width/height of square
     * @param color color of square filling
     * @param x     X-coordinate of square
     * @param y     Y-coordinate of square
     * @param dx    step/change of X-coordinate per animation frame
     * @param dy    step/change of Y-coordinate per animation frame
     * @param maxX  max value of X-coordinate to jump from right wall
     * @param maxY  max value of Y-coordinate to jump from bottom wall
     */
    public AnimatedSquare(double size, Color color, double x, double y, double dx, double dy, double maxX, double maxY) {
        super(x, y, size, size);
        super.setFilled(true);
        super.setFillColor(color);
        this.dx = dx;
        this.dy = dy;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Applies changes to square per one frame.
     */
    public void animateFrame() {
        double newX = this.getX() + dx;
        double newY = this.getY() + dy;
        if (newX < 0 || newX > maxX) {
            dx = -dx; // jump
            newX = this.getX() + dx;
        }
        if (newY < 0 || newY > maxY) {
            dy = -dy; // jump
            newY = this.getY() + dy;
        }
        this.setLocation(newX, newY);
    }
}

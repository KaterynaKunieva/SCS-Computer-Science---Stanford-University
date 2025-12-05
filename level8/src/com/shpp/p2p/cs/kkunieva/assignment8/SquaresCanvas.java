package com.shpp.p2p.cs.kkunieva.assignment8;

import acm.graphics.GCanvas;
import acm.graphics.GObject;
import acm.graphics.GRect;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Solution for the task described in TASK.TXT file.
 * Contains methods to randomly add squares, mouse moving handlers and main logic for moving squares
 * depending on cursor.
 */
public class SquaresCanvas extends GCanvas implements MouseMotionListener {
    private final double squareSize;
    private final Color squareColor;
    private final List<GRect> squaresApproach = new ArrayList<>();
    private final List<GRect> squaresEscape = new ArrayList<>();
    private final Random random = new Random();

    /**
     * Constructor to create canvas
     */
    public SquaresCanvas(double squareSize, Color squareColor) {
        addMouseMotionListener(this);
        this.squareSize = squareSize;
        this.squareColor = squareColor;
    }

    /**
     * Adds squares on canvas randomly
     *
     * @param amount amount of squares to add
     */
    public void addSquares(int amount) {
        int canvasHeight = getHeight();
        int canvasWidth = getWidth();

        for (int i = 0; i < amount; i++) {
            double x = random.nextDouble(0, canvasWidth - this.squareSize);
            double y = random.nextDouble(0, canvasHeight - this.squareSize);

            GRect square = addSquare(x, y);

            classifySquare(square, i);
        }
    }

    /**
     * Creates square with specified size and color, adds it to canvas by specified coordinates
     *
     * @param x X-coordinate of square
     * @param y Y-coordinate of square
     * @return created square
     */
    private GRect addSquare(double x, double y) {
        GRect square = new GRect(x, y, this.squareSize, this.squareSize);
        square.setFilled(true);
        square.setFillColor(this.squareColor);
        this.add(square);
        return square;
    }

    /**
     * Decides to which category save square
     *
     * @param square element to store
     * @param index  index among other squares during creation
     */
    private void classifySquare(GRect square, int index) {
        if (index % 2 == 0) {
            squaresEscape.add(square);
        } else {
            squaresApproach.add(square);
        }
    }

    /**
     * Handles mouse moving without click
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        handleMouseMove(e, false);
    }

    /**
     * Handles mouse moving with click
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        handleMouseMove(e, true);
    }

    /**
     * Handles mouse move. Validates cursor coordinates and moves squares, if necessary.
     *
     * @param e            MouseEvent object from event handler
     * @param mousePressed sign if mouse pressed (dragging) or not (just moving)
     */
    private void handleMouseMove(MouseEvent e, boolean mousePressed) {
        double cursorX = e.getX();
        double cursorY = e.getY();

        if (cursorX < 0 || cursorX > getWidth() || cursorY < 0 || cursorY > getHeight()) {
            System.out.println("Cursor out of window");
            return;
        }

        moveSquares(cursorX, cursorY, mousePressed);
    }

    /**
     * Moves squares after mouse move
     *
     * @param cursorX      X-coordinate of mouse cursor
     * @param cursorY      Y-coordinate of mouse cursor
     * @param mousePressed sign if mouse pressed (dragging) or not (just moving)
     */
    private void moveSquares(double cursorX, double cursorY, boolean mousePressed) {
        for (GRect square : squaresApproach) {
            moveSquare(square, cursorX, cursorY, !mousePressed);
        }
        for (GRect square : squaresEscape) {
            moveSquare(square, cursorX, cursorY, mousePressed);
        }
    }

    /**
     * Moves square depending on its category, mouse coordinates and its status (pressed or not).
     *
     * @param square         Element to move
     * @param cursorX        X-coordinate of mouse cursor
     * @param cursorY        Y-coordinate of mouse cursor
     * @param shouldApproach true, if square should approach to mouse, otherwise - false
     */
    private void moveSquare(GRect square, double cursorX, double cursorY, boolean shouldApproach) {
        double dx = cursorX - square.getX() + square.getWidth() / 2;
        double dy = cursorY - square.getY() + square.getHeight() / 2;

        double distance = Math.sqrt(dx * dx + dy * dy);

        double stepX = dx / distance;
        double stepY = dy / distance;

        if (shouldApproach) {
            moveWithBoundsValidation(square, stepX, stepY);
        } else {
            moveWithBoundsValidation(square, -stepX, -stepY);
        }
    }

    /**
     * Moves object inside window checking the window bounds.
     *
     * @param object object to move
     * @param dx     delta of X-coordinate
     * @param dy     delta of Y-coordinate
     */
    private void moveWithBoundsValidation(GObject object, double dx, double dy) {
        double newX = getValueInBounds(object.getX() + dx, 0, getWidth() - object.getWidth());
        double newY = getValueInBounds(object.getY() + dy, 0, getHeight() - object.getHeight());

        object.setLocation(newX, newY);
    }

    /**
     * Returns value in specified bounds:
     * if less than min => min;
     * less than max => max;
     * in bounds [min; max] => current value;
     *
     * @param current value to check
     * @param min min bound
     * @param max max bound
     * @return value in specified bounds
     * */
    private double getValueInBounds(double current, double min, double max){
        if (current < min) {
            current = min;
        } else if (current > max) {
            current = max;
        }

        return current;
    }
}

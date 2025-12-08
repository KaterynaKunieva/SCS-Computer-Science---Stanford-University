package com.shpp.p2p.cs.kkunieva.assignment8;

import acm.graphics.GCanvas;
import acm.graphics.GObject;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Solution for the task "Vibrations", described in TASK.TXT file.
 * Contains methods for adding elements to canvas, handling mouse events and rules for squares animations
 * depending on mouse location.
 */
public class VibrationsCanvas extends GCanvas implements MouseMotionListener, MouseListener {

    private final List<AnimatedSquare> squaresApproach = new ArrayList<>();
    private final List<AnimatedSquare> squaresEscape = new ArrayList<>();

    private final Random random = new Random();

    private double mouseX;
    private double mouseY;
    private boolean isMousePressed = false;
    private boolean isMouseInsideWindow = false;

    /**
     * Constructor to create canvas. Adds mouse listeners.
     */
    public VibrationsCanvas() {
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    /**
     * Handles mouse moving without click
     *
     * @param e MouseEvent object from event handler
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        handleMouseMove(e);
    }

    /**
     * Handles mouse moving with click
     *
     * @param e MouseEvent object from event handler
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        handleMouseMove(e);
    }

    /**
     * Handles mouse pressing
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        isMousePressed = true;
    }

    /**
     * Handles mouse releasing
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        isMousePressed = false;
    }

    /**
     * Handles mouse entering to the canvas.
     * Starts to follow mouse coordinates.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        isMouseInsideWindow = true;
        setMouseLocation(e);
    }

    /**
     * Handles mouse leaving the canvas
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        isMouseInsideWindow = false;
    }

    /**
     * Adds squares on canvas randomly, configures and classifies each square.
     *
     * @param amount       amount of squares to add
     * @param size         size of square sides
     * @param color        color or square filling
     * @param absoluteStep change of square coordinate per animation frame
     */
    public void addSquares(int amount, double size, Color color, double absoluteStep) {
        int canvasHeight = getHeight();
        int canvasWidth = getWidth();

        for (int i = 0; i < amount; i++) {
            double x = random.nextDouble(0, canvasWidth - size);
            double y = random.nextDouble(0, canvasHeight - size);

            AnimatedSquare square = new AnimatedSquare(size, color, x, y,
                                                       getRandomSign(absoluteStep), getRandomSign(absoluteStep),
                                                       getWidth() - size, getHeight() - size);
            add(square);
            classifyObject(square, i);
        }
    }

    /**
     * Handles each animation frame: selects type based on mouse position and applies it to each square.
     */
    public void handleFrameAnimation() {
        if (isMouseInsideWindow) {
            handleMouseInteraction();
        } else {
            handleMouseSearching();
        }
    }

    /**
     * Handles interaction squares with mouse, iterating over categories of squares
     */
    private void handleMouseInteraction() {
        for (AnimatedSquare square : squaresApproach) {
            interactWithMouse(square, !isMousePressed);
        }
        for (AnimatedSquare square : squaresEscape) {
            interactWithMouse(square, isMousePressed);
        }
    }

    /**
     * Handles squares animation, when mouse out of the window (like imitation of searching it)
     */
    private void handleMouseSearching() {
        for (AnimatedSquare square : squaresEscape) {
            square.animateFrame();
        }
        for (AnimatedSquare square : squaresApproach) {
            square.animateFrame();
        }
    }

    /**
     * Adds negative or positive sign to absolute value randomly.
     *
     * @param absValue absolute value
     * @return -absValue or +absValue
     */
    private double getRandomSign(double absValue) {
        return random.nextBoolean() ? absValue : -absValue;
    }

    /**
     * Decides to which category save square
     *
     * @param square element to save
     * @param index  index among other squares during creation
     */
    private void classifyObject(AnimatedSquare square, int index) {
        if (index % 2 == 0) {
            squaresEscape.add(square);
        } else {
            squaresApproach.add(square);
        }
    }

    /**
     * Moves square depending on its category, mouse coordinates and its status (pressed or not).
     *
     * @param square Element to move
     */
    private void interactWithMouse(AnimatedSquare square, boolean shouldApproach) {
        double dx = mouseX - square.getX() + square.getWidth() / 2;
        double dy = mouseY - square.getY() + square.getHeight() / 2;

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
     * @param dx delta of X-coordinate
     * @param dy delta of Y-coordinate
     */
    private void moveWithBoundsValidation(GObject object, double dx, double dy) {
        double newX = getValueInBounds(object.getX() + dx, 0, getWidth() - object.getWidth());
        double newY = getValueInBounds(object.getY() + dy, 0, getHeight() - object.getHeight());

        object.setLocation(newX, newY);
    }

    /**
     * Returns value in specified bounds:
     * - less than min => min;
     * - more than max => max;
     * - in bounds [min; max] => current value;
     *
     * @param current value to check
     * @param min     min bound
     * @param max     max bound
     * @return value in specified bounds
     */
    private double getValueInBounds(double current, double min, double max) {
        if (current < min) {
            current = min;
        } else if (current > max) {
            current = max;
        }

        return current;
    }

    /**
     * Handles mouse move. Validates mouse coordinates and, if valid, saves mouse position.
     *
     * @param e MouseEvent object from event handler
     */
    private void handleMouseMove(MouseEvent e) {
        if (!isMouseInsideWindow) {
            System.out.println("Mouse is out of window");
            return;
        }
        setMouseLocation(e);
    }

    /**
     * Extracts X and Y coordinates of the mouse from the event and sets to class fields.
     *
     * @param e MouseEvent object from event handler
     */
    private void setMouseLocation(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
}

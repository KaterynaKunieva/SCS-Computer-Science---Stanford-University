package com.shpp.p2p.cs.kkunieva.assignment2;

import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;

import java.awt.*;

/**
 * A graphics program that draws illusory contour consisting of
 * four filled circles and a white rectangle connecting their centers.
 * <p>
 * The circle diameters and spacing are calculated randomly
 * within the allowed canvas range.
 * <p>
 * Constraints:
 * 2 * MIN_DIAMETER + MIN_SPACE_BETWEEN_CIRCLES <= min(canvas width, canvas height).
 */
public class Assignment2Part2 extends SuperWindowProgram {
    /* Minimal distance between borders of 2 circles lying on the same line */
    private static final double MIN_SPACE_BETWEEN_CIRCLES = 20;

    /* Minimal value of circle diameter */
    private static final double MIN_DIAMETER = 1000;

    /* Colors of filling and border of the circles  */
    private static final Color CIRCLES_COLOR = new Color(0, 0, 0);
    private static final Color RECTANGLE_COLOR = new Color(255, 255, 255);

    public static final int APPLICATION_WIDTH = 3000;
    public static final int APPLICATION_HEIGHT = 300;

    public void run() {
        if(!isFigureValid()){
            displayError();
            return;
        }

        double circleDiameter = calculateCircleDiameter();
        drawCircles(circleDiameter);
        drawConnectingRectangle(circleDiameter);
    }

    /**
     * Checks the result figure fits inside the canvas.
     *
     * @return Boolean result of checking.
     * */
    private boolean isFigureValid(){
        double minSide = 2 * MIN_DIAMETER + MIN_SPACE_BETWEEN_CIRCLES;
        return minSide <= getWidth() && minSide <= getHeight();
    }

    /**
     * Displays an error message about figure sizes.
     */
    private void displayError() {
        GLabel label = new GLabel("Please update config sizes and run again");
        label.setLocation(0, label.getAscent());
        label.setColor(Color.RED);
        label.setFont("Arial-14");
        add(label);
    }

    /**
     * Calculates a random circle diameter that ensures
     * all circles fit within the canvas boundaries.
     *
     * @return the circle diameter.
     */
    private double calculateCircleDiameter() {
        double smallestSide = Math.min(getWidth(), getHeight());
        double maxDiameter = (smallestSide - MIN_SPACE_BETWEEN_CIRCLES) / 2.0;
        return ((Math.random() * (maxDiameter - MIN_DIAMETER)) + MIN_DIAMETER);
    }

    /**
     * Draws a white rectangle connecting the centers of the 4 circles.
     *
     * @param circleDiameter The diameter of each circle.
     * */
    private void drawConnectingRectangle(double circleDiameter){
        double rectangleX = circleDiameter / 2.0;               // center of circle
        double rectangleY = circleDiameter / 2.0;
        double rectangleWidth = getWidth() - circleDiameter;    // minus 2 halves of a circle
        double rectangleHeight = getHeight() - circleDiameter;
        drawRectangle(rectangleX, rectangleY, rectangleWidth, rectangleHeight);
    }

    /**
     * Draws 4 circles in the canvas corners.
     *
     * @param diameter the diameter of each circle
     */
    private void drawCircles(double diameter) {
        double canvasWidth = getWidth();
        double canvasHeight = getHeight();
        double[][] coordinates = {
                {0, 0},                                             // top-left
                {canvasWidth - diameter, 0},                        // top-right
                {canvasWidth - diameter, canvasHeight - diameter},  // bottom-right
                {0, canvasHeight - diameter},                       // bottom-left
        };
        for (double[] coordinate : coordinates) {
            drawCircle(coordinate[0], coordinate[1], diameter);
        }
    }

    /**
     * Draws a filled circle with the specified size and position.
     *
     * @param x        The x-coordinate of the top-left corner of the circle.
     * @param y        The y-coordinate of the top-left corner of the circle.
     * @param diameter The diameter of the circle.
     */
    private void drawCircle(double x, double y, double diameter) {
        GOval circle = new GOval(x, y, diameter, diameter);
        fillObject(circle, CIRCLES_COLOR);
        add(circle);
    }

    /**
     * Draws a filled white rectangle with corners in the centers of circles.
     *
     * @param x      The x-coordinate of the top-left corner of the rectangle.
     * @param y      The y-coordinate of the top-left corner of the rectangle.
     * @param width  The rectangle's width.
     * @param height The rectangle's height.
     */
    private void drawRectangle(double x, double y, double width, double height) {
        GRect rectangle = new GRect(x, y, width, height);
        fillObject(rectangle, RECTANGLE_COLOR);
        rectangle.setColor(RECTANGLE_COLOR);
        add(rectangle);
    }
}

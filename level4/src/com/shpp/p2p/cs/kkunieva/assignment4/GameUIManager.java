package com.shpp.p2p.cs.kkunieva.assignment4;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * Manages all UI elements of the Breakout Game, including the bricks, paddle, ball, and labels.
 */
public class GameUIManager {
    /**
     * The main WindowProgram instance.
     */
    private final WindowProgram window;

    /**
     * Paddle object on the window.
     */
    private Paddle paddle;

    /**
     * Ball object on the window.
     */
    private Ball ball;

    /**
     * Label to display information (like instruction or current try).
     */
    private GLabel infoLabel;

    /**
     * Label to display results of the game (win/loss).
     */
    private GLabel resultLabel;

    /**
     * Constructor to create instance to manage UI on specified window.
     *
     * @param window The main WindowProgram instance.
     */
    public GameUIManager(WindowProgram window) {
        this.window = window;
    }

    /**
     * Returns the main WindowProgram instance.
     *
     * @return main WindowProgram instance.
     */
    public WindowProgram getWindow() {
        return this.window;
    }

    /**
     * Returns the Paddle instance.
     *
     * @return Paddle instance from the window.
     */
    public Paddle getPaddle() {
        return this.paddle;
    }

    /**
     * Returns the Ball instance.
     *
     * @return Ball instance from the window.
     */
    public Ball getBall() {
        return this.ball;
    }

    /**
     * Returns label instance, displaying text.
     *
     * @return label instance, displaying text on the window.
     */
    public GLabel getInfoLabel() {
        return this.infoLabel;
    }

    /**
     * Returns result label, displaying game results.
     *
     * @return label instance, displaying game results on the window.
     */
    public GLabel getResultLabel() {
        return this.resultLabel;
    }

    /**
     * Calculates the x-coordinate required to center an object of the given width horizontally in the window.
     *
     * @param width width of the object.
     * @return the x-coordinate of left side of the object.
     */
    public double getCenteredX(double width) {
        return (window.getWidth() - width) / 2.0;
    }

    /**
     * Removes the specified object from the window.
     *
     * @param object object to extract.
     */
    public void removeObject(GObject object) {
        window.remove(object);
    }

    /**
     * Pauses the program execution for the specified time in milliseconds.
     *
     * @param milliseconds time of pause in milliseconds.
     */
    public void pause(int milliseconds) {
        window.pause(milliseconds);
    }

    /**
     * Returns width of the window.
     *
     * @return width of the window.
     */
    public int getWindowWidth() {
        return window.getWidth();
    }

    /**
     * Returns height of the window.
     *
     * @return height of the window.
     */
    public int getWindowHeight() {
        return window.getHeight();
    }

    /**
     * Stops execution of animations and waits for user click.
     */
    public void waitForClick() {
        window.waitForClick();
    }

    /**
     * Adds brick rows to the window accordingly to parameters.
     * Centers the result figure horizontally. Calculates width of the brick, if passed is 0.
     *
     * @param rows          amount of rows.
     * @param bricksPerRow  amount of bricks in the row.
     * @param brickSep      gap between brick columns and rows.
     * @param brickWidth    width of each brick.
     * @param brickHeight   height of each brick.
     * @param colors        color sequence to fill rows.
     * @param rowsSameColor amount of one by one rows filled with the same color.
     * @param startY        coordinate of top side of the first row.
     */
    public void addBricks(int rows, int bricksPerRow, int brickSep, int brickWidth, int brickHeight, Color[] colors,
                          int rowsSameColor, double startY) {
        int sepSizePerRow = (bricksPerRow - 1) * brickSep;
        double calculatedBrickWidth = brickWidth == 0 ?
                (window.getWidth() - sepSizePerRow) / (double) bricksPerRow :
                (double) brickWidth;
        double rowX = getCenteredX(calculatedBrickWidth * bricksPerRow + sepSizePerRow);
        for (int i = 0; i < rows; i++) {
            double rowY = startY + i * (brickHeight + brickSep);
            Color rowColor = colors[(i / rowsSameColor) % colors.length];
            for (int j = 0; j < bricksPerRow; j++) {
                double brickX = rowX + (calculatedBrickWidth + brickSep) * j;
                GRect brick = new GRect(brickX, rowY, calculatedBrickWidth, brickHeight);
                brick.setFilled(true);
                brick.setColor(rowColor);
                window.add(brick);
            }
        }
    }

    /**
     * Initializes and adds the Paddle object to the window, placing it at its starting location.
     *
     * @param width  width of the paddle.
     * @param height height of the paddle.
     * @param y      y-coordinate of the top side of the paddle.
     */
    public void setupPaddle(int width, int height, int y) {
        if (paddle == null) {
            paddle = new Paddle(width, height, y);
            paddle.setFilled(true);
            paddle.setColor(new Color(0, 0, 0));
            window.add(paddle);
        }
        placePaddleAtStart();
    }

    /**
     * Moves paddle to the start location.
     */
    public void placePaddleAtStart() {
        double paddleStartX = getCenteredX(paddle.getWidth());
        paddle.slide(paddleStartX);
    }

    /**
     * Initializes and adds the Ball object to the window, placing it at its starting location.
     *
     * @param radius radius of the ball.
     */
    public void setupBall(int radius) {
        if (ball == null) {
            ball = new Ball(radius);
            ball.setFilled(true);
            ball.setColor(new Color(0, 0, 0));
            window.add(ball); 
        }
        placeBallAtStart();
    }

    /**
     * Moves ball to the start location.
     */
    public void placeBallAtStart() {
        double ballStartX = getCenteredX(ball.getWidth());
        double ballStartY = (window.getHeight() - ball.getHeight()) / 2.0;
        ball.setLocation(ballStartX, ballStartY);
    }

    /**
     * Initializes and adds the label object to the window.
     *
     * @param font font-family and font-size.
     * @return created label object.
     */
    private GLabel initLabel(Font font) {
        GLabel label = new GLabel("");
        label.setFont(font);
        window.add(label);
        return label;
    }

    /**
     * Displays the final game result (win/lose) with a specified color and updates the info label with instructions
     * to restart.
     *
     * @param result text of the Game results.
     * @param color  color of the text.
     */
    public void displayGameResults(String result, Color color) {
        resultLabel.setLabel(result);
        resultLabel.setColor(color);
        infoLabel.setLabel("Reload to start again");
        placeLabels();
    }

    /**
     * Creates labels, if not created, and places them at the certain locations.
     */
    public void placeLabels() {
        if (infoLabel == null) infoLabel = initLabel(new Font("Arial", Font.PLAIN, 14));
        if (resultLabel == null) resultLabel = initLabel(new Font("Arial", Font.BOLD, 30));

        infoLabel.setLocation(
                getCenteredX(infoLabel.getWidth()),
                window.getHeight() - infoLabel.getDescent()
        );
        resultLabel.setLocation(
                getCenteredX(resultLabel.getWidth()),
                (window.getHeight() - resultLabel.getDescent()) / 2.0);
    }

    /**
     * Updates the text content of the label and recalculates its position to remain centered.
     *
     * @param label   label object to update.
     * @param newText new text of the label.
     */
    public void updateLabel(GLabel label, String newText) {
        label.setLabel(newText);
        placeLabels();
    }
}

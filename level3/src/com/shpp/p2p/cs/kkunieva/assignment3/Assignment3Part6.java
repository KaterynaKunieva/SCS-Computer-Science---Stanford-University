package com.shpp.p2p.cs.kkunieva.assignment3;

import acm.graphics.GLabel;
import acm.graphics.GOval;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * A graphical program that animates a flower opening and closing.
 * The animation is based on the polar coordinate system:
 * - Each petal’s center position is defined by its radial distance and angle.
 * - During the animation, the radial distance increases (flower opens) and then decreases (flower closes).
 */
public class Assignment3Part6 extends WindowProgram {
    // Number of petals in the flower
    private static final int PETALS_AMOUNT = 8;

    // Radius (size) of each petal
    private static final int PETAL_RADIUS = 25;

    // Total duration of the animation, in milliseconds
    private static final int DURATION = 5000;

    // Frames per second (controls animation smoothness)
    private static final int FPS = 60;

    // Total number of frames in the animation
    private static final int FRAMES = DURATION * FPS / 1000;

    // Duration of one frame, in milliseconds
    private static final double FRAME_DURATION = 1000.0 / FPS;

    // Timestamp when the animation starts
    private long animationStartTime;

    // Array storing all petal objects
    private final GOval[] petals = new GOval[PETALS_AMOUNT];

    // Center position of the flower on the canvas
    private double flowerCenterX;
    private double flowerCenterY;

    // Application window size
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 400;

    /**
     * Entry point of the program.
     * Calculates the flower center, creates petals, adds a timer label, and starts the animation.
     */
    @Override
    public void run() {
        flowerCenterX = getCenterX();
        flowerCenterY = getCenterY();

        buildFlower();

        // Maximum possible radial distance for a petal’s center. Formula:
        // Triangle with sides: radialMax-radialMax-2*PETAL_RADIUS and corner 2*Pi/PETALS_AMOUNT.
        // Use median and bisector => right triangle: hypotenuse - radialMax, leg - PETAL_RADIUS.
        // Formula: sin(Pi/PETALS_AMOUNT) = PETAL_RADIUS / radialMax. 
        double radialMax = PETAL_RADIUS / Math.sin(Math.PI / PETALS_AMOUNT);

        GLabel timerLabel = addTimer();
        executeAnimation(radialMax, timerLabel);
    }

    /**
     * Adds a timer label to the top-left corner of the canvas.
     *
     * @return the created GLabel displaying elapsed time.
     */
    private GLabel addTimer() {
        GLabel timer = new GLabel("Time: 0.00s");
        timer.setFont("Arial-18");
        timer.setColor(Color.BLACK);
        timer.setLocation(10, timer.getAscent() + 10);
        add(timer);
        return timer;
    }

    /**
     * Updates the timer label based on elapsed time since the animation started.
     *
     * @param timerLabel the label to update
     */
    private void updateTimer(GLabel timerLabel) {
        long elapsedTime = System.currentTimeMillis() - animationStartTime;
        double seconds = elapsedTime / 1000.0;
        timerLabel.setLabel(String.format("Time: %.2fs", seconds));
    }

    /**
     * @return the X-coordinate of the canvas center.
     */
    private double getCenterX() {
        return getWidth() / 2.0;
    }

    /**
     * @return the Y-coordinate of the canvas center.
     */
    private double getCenterY() {
        return getHeight() / 2.0;
    }

    /**
     * Converts polar coordinates to Cartesian X position for a petal.
     * Used formula: x = r * cos(angle)
     *
     * @param radial distance from the flower center to the petal center
     * @param angle  polar angle (azimuth)
     * @return X-coordinate of the top-left corner of the petal
     */
    private double calculatePetalX(double radial, double angle) {
        return flowerCenterX + radial * Math.cos(angle) - PETAL_RADIUS;
    }

    /**
     * Converts polar coordinates to Cartesian Y position for a petal.
     * Used formula: y = r * sin(angle)
     *
     * @param radial distance from the flower center to the petal center
     * @param angle  polar angle (azimuth)
     * @return Y-coordinate of the top-left corner of the petal
     */
    private double calculatePetalY(double radial, double angle) {
        return flowerCenterY + radial * Math.sin(angle) - PETAL_RADIUS;
    }

    /**
     * Updates all petal positions according to the specified radial distance.
     *
     * @param radial distance from flower center to each petal’s center
     */
    private void updatePetals(double radial) {
        for (int i = 0; i < PETALS_AMOUNT; i++) {
            double angle = i * 2 * Math.PI / PETALS_AMOUNT;
            double x = calculatePetalX(radial, angle);
            double y = calculatePetalY(radial, angle);
            petals[i].setLocation(x, y);
        }
    }

    /**
     * Creates and positions all petals for the flower.
     */
    private void buildFlower() {
        for (int i = 0; i < PETALS_AMOUNT; i++) {
            GOval petal = new GOval(PETAL_RADIUS * 2, PETAL_RADIUS * 2);
            petals[i] = petal;
            add(petal);
        }
        updatePetals(PETAL_RADIUS);
    }

    /**
     * Runs the flower animation: gradually opens and closes petals.
     * Each frame updates petal positions and the on-screen timer.
     *
     * @param radialMax  maximum distance from flower center to petal center
     * @param timerLabel label displaying elapsed time
     */
    private void executeAnimation(double radialMax, GLabel timerLabel) {
        animationStartTime = System.currentTimeMillis();  // mark start time

        for (int frame = 0; frame <= FRAMES; frame++) {
            double radial = getRadial(radialMax, frame);

            updatePetals(radial);
            updateTimer(timerLabel);

            long pauseTime = getPauseTime(frame);

            if (pauseTime > 0) {
                pause(pauseTime);
            }
        }
    }

    /**
     * Calculates how long to pause before rendering the next frame,
     * to maintain consistent animation timing across machines.
     *
     * @param frame current frame index
     * @return pause duration in milliseconds
     */
    private long getPauseTime(int frame) {
        long targetTime = animationStartTime + (long) ((frame + 1) * FRAME_DURATION);
        long currentTime = System.currentTimeMillis();
        return targetTime - currentTime;
    }

    /**
     * Computes the current radial distance for a given animation frame.
     * The distance increases linearly during the first half (opening),
     * and decreases linearly during the second half (closing).
     *
     * @param radialMax maximum possible radial distance
     * @param frame     current frame number
     * @return radial distance for this frame
     */
    private static double getRadial(double radialMax, int frame) {
        double framesPerPhase = (double) FRAMES / 2;

        if (frame < framesPerPhase) {
            // Opening phase: radial grows from 0 => radialMax
            double openRatio = frame / framesPerPhase;
            return radialMax * openRatio;
        } else {
            // Closing phase: radial decreases from radialMax => 0
            double closeRatio = (frame - framesPerPhase) / framesPerPhase;
            return radialMax * (1 - closeRatio);
        }
    }
}

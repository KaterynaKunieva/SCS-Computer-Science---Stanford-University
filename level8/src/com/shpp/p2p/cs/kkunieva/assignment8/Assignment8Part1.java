package com.shpp.p2p.cs.kkunieva.assignment8;

import com.shpp.cs.a.simple.SimpleProgram;

import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Random;

/**
 * IMPORTANT: to start program add library with acm (as on level7).
 * Entry point to the program and its configurations.
 */
public class Assignment8Part1 extends SimpleProgram implements MouseListener {

    /**
     * Width/height of square
     */
    private static final double SQUARE_SIZE = 20;

    /**
     * Color of square
     */
    private static final Color SQUARE_COLOR = Color.BLACK;

    /**
     * Absolute step of square during animation frame, in pixels
     */
    private static final double SQUARE_STEP = 1;

    /**
     * Range of squares on canvas, inclusively
     */
    private static final int[] SQUARES_AMOUNT = new int[]{20, 40};

    /**
     * Pause of animation, in ms
     */
    private static final int ANIMATION_PAUSE = 10;

    /**
     * Entry point to program. Calculates amount of squares randomly, adds them to canvas and starts animation loop.
     */
    @Override
    public void run() {
        Random random = new Random();
        int squaresAmount = random.nextInt(SQUARES_AMOUNT[0], SQUARES_AMOUNT[1] + 1);

        VibrationsCanvas canvas = new VibrationsCanvas();
        add(canvas);

        canvas.addSquares(squaresAmount, SQUARE_SIZE, SQUARE_COLOR, SQUARE_STEP);

        // animation main loop
        while (true) {
            canvas.handleFrameAnimation();
            this.pause(ANIMATION_PAUSE);
        }
    }
}

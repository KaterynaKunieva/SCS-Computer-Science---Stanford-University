package com.shpp.p2p.cs.kkunieva.assignment8;

import com.shpp.cs.a.simple.SimpleProgram;

import java.awt.*;
import java.util.Random;

/**
 * IMPORTANT: to start program add library with acm (as on level7).
 * Entry point to the program and its configurations.
 */
public class Assignment8Part1 extends SimpleProgram {

    /**
     * Width/height of square
     */
    private static final double SQUARE_SIZE = 20;

    /**
     * Color of square
     */
    private static final Color SQUARE_COLOR = Color.BLACK;

    /**
     * Range of squares on canvas, inclusively
     */
    private static final int[] SQUARES_AMOUNT = new int[]{20, 40};

    /**
     * Entry point to program. Calculates amount of squares randomly and adds to canvas.
     */
    @Override
    public void run() {
        Random random = new Random();
        int squareAmount = random.nextInt(SQUARES_AMOUNT[0], SQUARES_AMOUNT[1] + 1);

        SquaresCanvas squareCanvas = new SquaresCanvas(SQUARE_SIZE, SQUARE_COLOR);
        add(squareCanvas);

        squareCanvas.addSquares(squareAmount); // IMPORTANT: must be after add(squareCanvas)
    }
}
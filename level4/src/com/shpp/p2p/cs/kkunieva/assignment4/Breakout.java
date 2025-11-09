package com.shpp.p2p.cs.kkunieva.assignment4;

import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * The main class for the Breakout Game, containing all game configurations and the execution entry point.
 */
public class Breakout extends WindowProgram {
    /**
     * Width and height of application window in pixels
     */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;

    /**
     * Dimensions of the paddle
     */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;

    /**
     * Offset of the paddle up from the bottom
     */
    private static final int PADDLE_Y_OFFSET = 30;

    /**
     * Number of bricks per row
     */
    private static final int NBRICKS_PER_ROW = 10;

    /**
     * Number of rows of bricks
     */
    private static final int NBRICK_ROWS = 10;

    /**
     * Separation between bricks
     */
    private static final int BRICK_SEP = 4;

    /**
     * Width of a brick. Set to 0 to calculate automatically, depending on width of window
     */
    private static final int BRICK_WIDTH = 0;

    /**
     * Height of a brick
     */
    private static final int BRICK_HEIGHT = 8;

    /**
     * Radius of the ball in pixels
     */
    private static final int BALL_RADIUS = 10;

    /**
     * Offset of the top brick row from the top
     */
    private static final int BRICK_Y_OFFSET = 70;

    /**
     * Number of turns
     */
    private static final int NTURNS = 3;

    /**
     * Sequence of colors for brick rows
     */
    private static final Color[] BRICKS_COLORS = {
            Color.RED,
            Color.ORANGE,
            Color.YELLOW,
            Color.GREEN,
            Color.CYAN,
    };

    /**
     * Amount of rows with the same color
     */
    private static final int ROWS_SAME_COLOR = 2;

    /**
     * Pause to reset elements to start position
     */
    private static final int PAUSE_BETWEEN_TURNS = 200;

    /**
     * Animation pause for moving ball
     */
    private static final int ANIMATION_PAUSE = 15;

    /**
     * Initial value of y velocity, must be positive.
     */
    private static final double INITIAL_BALL_VY = 3.0;

    /**
     * Bounds of x velocity value to calculate randomly. Length always must be 2.
     */
    private static final double[] BALL_VX_BOUNDS = {1.0, 3.0};

    /**
     * Probability of changing sign of x velocity to negative. Value must be in bounds [0, 1]
     */
    private static final double NEGATIVE_PROBABILITY = 0.5;

    /**
     * Instance variable to manage UI elements on the window (add, update, place)
     */
    private GameUIManager ui;

    /**
     * Sets up the game by initializing the UI, game state, and starting the main game loop.
     */
    public void run() {
        ui = new GameUIManager(this);
        ui.addBricks(NBRICK_ROWS, NBRICKS_PER_ROW, BRICK_SEP, BRICK_WIDTH, BRICK_HEIGHT, BRICKS_COLORS,
                ROWS_SAME_COLOR, BRICK_Y_OFFSET);
        ui.setupPaddle(PADDLE_WIDTH, PADDLE_HEIGHT, getHeight() - PADDLE_Y_OFFSET);
        ui.setupBall(BALL_RADIUS);
        addMouseListeners();

        /* To store and manage game states (left bricks and results) */
        GameState gameState = new GameState(NBRICK_ROWS * NBRICKS_PER_ROW);

        /* Main loop of the game */
        GameController gameController = new GameController(ui, gameState, ANIMATION_PAUSE);
        gameController.playGame(NTURNS, BALL_VX_BOUNDS, NEGATIVE_PROBABILITY, INITIAL_BALL_VY, PAUSE_BETWEEN_TURNS);
    }

    /**
     * Slides the paddle horizontally to center it relative to the mouse cursor's x-coordinate, constrained by the
     * window boundaries.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        double newX = e.getX() - PADDLE_WIDTH / 2.0;
        newX = Utils.getNumInBounds(newX, 0, getWidth() - ui.getPaddle().getWidth());
        ui.getPaddle().slide(newX);
    }
}

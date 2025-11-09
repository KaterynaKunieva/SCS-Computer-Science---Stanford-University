package com.shpp.p2p.cs.kkunieva.assignment4;

import acm.graphics.GObject;

import java.awt.*;
import java.util.ArrayList;

/**
 * Manages the core game processes: starting the game, controlling turns, ball movement, and handling
 * win/loss conditions.
 */
public class GameController {
    /**
     * The time in milliseconds to pause between animation frames to regulate smoothness.
     */
    private final int animationPause;

    /**
     * The instance used to manipulate UI objects on the game window.
     */
    private final GameUIManager ui;

    /**
     * The instance used to control states of the game.
     */
    private final GameState gameState;

    /**
     * Constructor to create instance of GameController.
     *
     * @param ui             instance with objects and methods to manipulate UI.
     * @param gameState      instance to share game states.
     * @param animationPause time to pause between animation frames, in milliseconds.
     */
    public GameController(GameUIManager ui, GameState gameState, int animationPause) {
        this.ui = ui;
        this.gameState = gameState;
        this.animationPause = animationPause;
    }

    /**
     * Starts the Breakout game, managing turns and displaying the final game results (win/loss).
     *
     * @param turnsAmount         amount of allowed turns.
     * @param possibleVx          all possible values of x velocity to select randomly.
     * @param negativeProbability probability of selecting opposite sign for the x velocity value.
     * @param vy                  y velocity.
     * @param pauseBetweenTurns   time to pause to show the end position of the turn.
     */
    public void playGame(int turnsAmount, double[] possibleVx, double negativeProbability, double vy,
                         int pauseBetweenTurns) {
        int currentTurn = 1;
        while (currentTurn <= turnsAmount) {
            if (Utils.VERBOSE) System.out.println("Start turn " + currentTurn);
            startTurn(currentTurn, turnsAmount, possibleVx, negativeProbability, vy);
            ui.pause(pauseBetweenTurns);
            currentTurn++;
            if (gameState.getIsWin()) {
                ui.displayGameResults("Win!", Color.GREEN);
                return;
            }
        }
        ui.displayGameResults("Game Over", Color.RED);
    }

    /**
     * Starts a new turn: resets UI elements, sets the ball's initial velocity, and waits for a user click to launch the ball.
     *
     * @param currentTurn         current turn number
     * @param turnsAmount         amount of allowed turns
     * @param possibleVx          all possible values of x velocity to select randomly
     * @param negativeProbability probability of selecting negative value for x velocity
     * @param vy                  y velocity (must be positive for start)
     */
    private void startTurn(int currentTurn, int turnsAmount, double[] possibleVx, double negativeProbability,
                           double vy) {
        ui.placeLabels();
        ui.placePaddleAtStart();
        ui.placeBallAtStart();
        ui.updateLabel(ui.getInfoLabel(), "Click to start " + currentTurn + "/" + turnsAmount + " turn");
        ui.waitForClick();
        ui.updateLabel(ui.getInfoLabel(), "Current turn: " + currentTurn + "/" + turnsAmount);
        ui.getBall().initVelocity(possibleVx, negativeProbability, vy);
        launchBall();
    }

    /**
     * Initiates the ball's animation, managing continuous movement, wall bounces, object collisions, and checking
     * for win/loss conditions.
     */
    private void launchBall() {
        while (true) {
            ui.getBall().move(ui.getBall().getVx(), ui.getBall().getVy());
            ui.pause(animationPause);
            if (ui.getBall().getY() >= ui.getWindowHeight()) {
                if (Utils.VERBOSE) System.out.println("Bottom wall: end");
                return;
            }
            PhysicsUtils.handleWallBounce(ui.getBall(), ui.getWindowWidth());
            ArrayList<GObject> colliders = PhysicsUtils.getCollidingObjects(ui.getBall(), ui.getWindow());
            if (handleObjectCollisions(colliders)) return;
        }
    }

    /**
     * Handles collisions between the ball and other objects. If a brick is crashed, it is destroyed,
     * and the win condition is checked.
     *
     * @param colliders list of colliding objects.
     * @return true, if gamer win, otherwise - false.
     */
    private boolean handleObjectCollisions(ArrayList<GObject> colliders) {
        int counter = 1; // process only 1st valid object
        for (GObject collider : colliders) {
            if (shouldIgnoreCollision(collider) || counter != 1) continue;
            counter++;
            boolean isPaddle = collider == ui.getPaddle();
            PhysicsUtils.handleObjectBounce(ui.getBall(), collider, isPaddle);
            if (!isPaddle) {
                handleBrickDestruction(collider);
                if (gameState.getIsWin()) {
                    if (Utils.VERBOSE) System.out.println("WIN");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a collision with the given object should be ignored (e.g. if it is some label).
     *
     * @param collider object to check.
     * @return true, if collision should be ignored.
     */
    private boolean shouldIgnoreCollision(Object collider) {
        return collider == ui.getInfoLabel() || collider == ui.getResultLabel();
    }

    /**
     * Handles the destruction of a brick: remove object from the canvas, update game state.
     *
     * @param brick brick to destroy.
     */
    private void handleBrickDestruction(GObject brick) {
        ui.removeObject(brick);
        gameState.destroyBrick();
        if (Utils.VERBOSE) System.out.println(gameState.getBricksLeft() + " left bricks");
    }
}

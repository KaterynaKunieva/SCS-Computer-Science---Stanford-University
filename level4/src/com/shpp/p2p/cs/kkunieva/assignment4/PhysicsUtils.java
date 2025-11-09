package com.shpp.p2p.cs.kkunieva.assignment4;

import acm.graphics.GObject;
import acm.graphics.GPoint;
import com.shpp.cs.a.graphics.WindowProgram;

import java.util.ArrayList;

/**
 * Provides static utility methods for handling the physics of objects, particularly the ball, within the game window.
 */
public class PhysicsUtils {
    /**
     * Handles the ball's bounce off the top, left, and right walls by reversing the appropriate velocity component
     * (vx or vy).
     *
     * @param ball        ball to bounce off.
     * @param windowWidth width of the window.
     */
    public static void handleWallBounce(Ball ball, int windowWidth) {
        double ballX = ball.getX();
        double ballY = ball.getY();
        if (ballY <= 0) {
            if (Utils.VERBOSE) System.out.println("Bounce from top wall");
            ball.setVelocity(ball.getVx(), -ball.getVy());
        }
        if (ballX <= 0 || ballX + ball.getWidth() >= windowWidth) {
            if (Utils.VERBOSE) System.out.println("Bounce from left|right wall");
            ball.setVelocity(-ball.getVx(), ball.getVy());
        }
    }

    /**
     * Handles the ball's bounce off a colliding object (brick or paddle) by reversing the appropriate velocity
     * (vx or vy).
     *
     * @param ball     ball to bounce off.
     * @param object   colliding object to bounce from.
     * @param isPaddle signal if object is paddle.
     */
    public static void handleObjectBounce(Ball ball, GObject object, boolean isPaddle) {
        if (Utils.VERBOSE) System.out.println("Bounce from " + (isPaddle ? "paddle" : "brick and remove it"));
        double[] newV = getBouncingVelocity(ball, object);
        if (isPaddle) fixBallPosition(ball, object);
        ball.setVelocity(newV[0], newV[1]);
    }

    /**
     * Checks if ball crashes into the top side of the object.
     *
     * @param ballPoints   4 points of the ball corners.
     * @param objectPoints 4 points of the object corners.
     * @param vy           y velocity of the ball.
     * @return true, if ball crashes top side of the object.
     */
    private static boolean isCrashesToTop(double[] ballPoints, double[] objectPoints, double vy) {
        return Math.abs(ballPoints[Utils.BOTTOM_Y] - objectPoints[Utils.TOP_Y]) <= Math.abs(vy) && vy > 0;
    }

    /**
     * Checks if ball crashes into the bottom side of the object.
     *
     * @param ballPoints   4 points of the ball corners.
     * @param objectPoints 4 points of the object corners.
     * @param vy           y velocity of the ball.
     * @return true, if ball crashes bottom side of the object.
     */
    private static boolean isCrashesToBottom(double[] ballPoints, double[] objectPoints, double vy) {
        return Math.abs(ballPoints[Utils.TOP_Y] - objectPoints[Utils.BOTTOM_Y]) <= Math.abs(vy) && vy < 0;
    }

    /**
     * Checks if ball crashes into the left side of the object.
     *
     * @param ballPoints   4 points of the ball corners.
     * @param objectPoints 4 points of the object corners.
     * @param vx           x velocity of the ball.
     * @return true, if ball crashes left side of the object.
     */
    private static boolean isCrashesToLeft(double[] ballPoints, double[] objectPoints, double vx) {
        return Math.abs(ballPoints[Utils.RIGHT_X] - objectPoints[Utils.LEFT_X]) <= Math.abs(vx) && vx > 0;
    }

    /**
     * Checks if ball crashes into the right side of the object.
     *
     * @param ballPoints   4 points of the ball corners.
     * @param objectPoints 4 points of the object corners.
     * @param vx           x velocity of the ball.
     * @return true, if ball crashes right side of the object.
     */
    private static boolean isCrashesToRight(double[] ballPoints, double[] objectPoints, double vx) {
        return Math.abs(ballPoints[Utils.LEFT_X] - objectPoints[Utils.RIGHT_X]) <= Math.abs(vx) && vx < 0;
    }

    /**
     * Pulls the ball out of the object.
     *
     * @param ball   ball to pull.
     * @param object object to check.
     */
    public static void fixBallPosition(Ball ball, GObject object) {
        double vx = ball.getVx(), vy = ball.getVy();
        double[] ballPoints = Utils.getObjectPoints(ball);
        double[] objectPoints = Utils.getObjectPoints(object);

        // vertically
        if (isCrashesToTop(ballPoints, objectPoints, vy)) {
            ball.setLocation(ball.getX(), objectPoints[Utils.TOP_Y] - ball.getWidth());
        } else if (isCrashesToBottom(ballPoints, objectPoints, vy)) {
            ball.setLocation(ball.getX(), objectPoints[Utils.BOTTOM_Y]);
        }

        // horizontally
        if (isCrashesToLeft(ballPoints, objectPoints, vx)) {
            ball.setLocation(objectPoints[Utils.LEFT_X] - ball.getWidth(), ball.getY());
        } else if (isCrashesToRight(ballPoints, objectPoints, vx)) {
            ball.setLocation(objectPoints[Utils.RIGHT_X], ball.getY());
        }
    }

    /**
     * Calculates the new x and y velocity after a collision, based on the ball's current velocity and the collision
     * side.
     *
     * @param ball   ball to calculate velocity.
     * @param object colliding object.
     * @return array with new x and y velocity: [vx, vy].
     */
    private static double[] getBouncingVelocity(Ball ball, GObject object) {
        double vx = ball.getVx(), vy = ball.getVy();
        double newVX = vx, newVY = vy;

        double[] ballPoints = Utils.getObjectPoints(ball);
        double[] objectPoints = Utils.getObjectPoints(object);

        if (isCrashesToTop(ballPoints, objectPoints, vy) || (isCrashesToBottom(ballPoints, objectPoints, vy))) {
            newVY = -newVY;
            if (Utils.VERBOSE) System.out.println("Crashed to top|bottom");
        }
        if (isCrashesToLeft(ballPoints, objectPoints, vx) || (isCrashesToRight(ballPoints, objectPoints, vx))) {
            newVX = -newVX;
            if (Utils.VERBOSE) System.out.println("Crashed to right|left");
        }

        return new double[]{newVX, newVY};
    }

    /**
     * Finds and returns a list of all objects that are currently colliding with the ball.
     *
     * @param ball   ball to search collision with.
     * @param window window, where elements are located.
     */
    public static ArrayList<GObject> getCollidingObjects(Ball ball, WindowProgram window) {
        ArrayList<GObject> collidingObjects = new ArrayList<>();
        double[] ballCoords = Utils.getObjectPoints(ball);
        GPoint[] ballPoints = {
                new GPoint(ballCoords[Utils.LEFT_X], ballCoords[Utils.TOP_Y]),
                new GPoint(ballCoords[Utils.RIGHT_X], ballCoords[Utils.TOP_Y]),
                new GPoint(ballCoords[Utils.LEFT_X], ballCoords[Utils.BOTTOM_Y]),
                new GPoint(ballCoords[Utils.RIGHT_X], ballCoords[Utils.BOTTOM_Y]),
        };
        for (GPoint point : ballPoints) {
            GObject collidingObject = window.getElementAt(point);
            if (collidingObject == null || collidingObjects.contains(collidingObject)) continue;
            collidingObjects.add(collidingObject);
        }
        if (Utils.VERBOSE && !collidingObjects.isEmpty())
            System.out.println(collidingObjects.size() + " colliding object(s)");
        return collidingObjects;
    }
}

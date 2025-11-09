package com.shpp.p2p.cs.kkunieva.assignment4;

import acm.graphics.GOval;
import acm.util.RandomGenerator;

/**
 * Represents the ball object (a GOval) in the Breakout Game.
 */
public class Ball extends GOval {
    private double vx, vy;

    /**
     * Constructor to create a Ball instance.
     *
     * @param radius radius of the ball.
     */
    public Ball(double radius) {
        super(radius * 2, radius * 2);
    }

    /**
     * Sets the x and y velocity of the ball.
     *
     * @param vx velocity of x.
     * @param vy velocity of y.
     */
    public void setVelocity(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }

    /**
     * Returns the x velocity.
     *
     * @return x velocity.
     */
    public double getVx() {
        return vx;
    }

    /**
     * Returns the y velocity.
     *
     * @return y velocity.
     */
    public double getVy() {
        return vy;
    }

    /**
     * Calculates and sets a random initial x velocity (vx) and a specified y velocity (vy) for the ball.
     *
     * @param vxBounds            array of x velocity to select randomly.
     * @param negativeProbability probability of sign changing of x velocity.
     * @param vy                  y velocity.
     */
    public void initVelocity(double[] vxBounds, double negativeProbability, double vy) {
        RandomGenerator rgen = RandomGenerator.getInstance();
        double calculatedVx = rgen.nextDouble(vxBounds[0], vxBounds[1]);
        if (rgen.nextBoolean(negativeProbability))
            calculatedVx = -calculatedVx;
        this.setVelocity(calculatedVx, vy);
        if (Utils.VERBOSE) System.out.println("Velocity for turn: vx=" + calculatedVx + " vy=" + vy);
    }
}

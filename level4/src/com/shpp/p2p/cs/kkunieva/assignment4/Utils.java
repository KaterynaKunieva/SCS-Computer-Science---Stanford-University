package com.shpp.p2p.cs.kkunieva.assignment4;

import acm.graphics.GObject;

/**
 * Provides static utility methods for general, minor calculations and operations.
 */
public class Utils {
    /**
     * If true, enables verbose debugging messages to be printed to the console.
     */
    public static final boolean VERBOSE = false;

    /**
     * Indexes of object points array returned by getObjectPoints.
     */
    public static final int LEFT_X = 0;
    public static final int TOP_Y = 1;
    public static final int RIGHT_X = 2;
    public static final int BOTTOM_Y = 3;

    /**
     * Constrains a number to be within the specified minimum and maximum bounds.
     *
     * @param num number to check.
     * @param min minimum limit of bounds.
     * @param max maximum limit of bounds.
     * @return value in specified bounds.
     */
    public static double getNumInBounds(double num, double min, double max) {
        if (num < min) {
            num = min;
        } else if (num > max) {
            num = max;
        }
        return num;
    }

    /**
     * Calculates and returns the corner coordinates (leftX, topY, rightX, bottomY) of the given object.
     *
     * @param object object to calculate.
     * @return array with points of the object corners in clockwise, starting from the x coordinate of the
     * top-left corner.
     */
    public static double[] getObjectPoints(GObject object) {
        double x = object.getX();
        double y = object.getY();
        double width = object.getWidth();
        double height = object.getHeight();
        return new double[]{
                x, y, x + width, y + height
        };
    }
}

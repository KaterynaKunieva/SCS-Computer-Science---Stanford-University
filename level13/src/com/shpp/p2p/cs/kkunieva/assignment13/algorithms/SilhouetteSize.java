package com.shpp.p2p.cs.kkunieva.assignment13.algorithms;

import java.util.List;

/**
 * Area and max/min coordinates of silhouette
 */
public class SilhouetteSize {

    private int area;
    private int minX = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxY = Integer.MIN_VALUE;

    /**
     * Updates coordinates, if x or y are maximum/minimum
     *
     * @param x X-coordinate of pixel
     * @param y Y-coordinate of pixel
     */
    public void updatePixel(int x, int y) {
        this.minX = Math.min(minX, x);
        this.maxX = Math.max(maxX, x);
        this.minY = Math.min(minY, y);
        this.maxY = Math.max(maxY, y);
    }

    /**
     * Processes pixel: counts it to area and updates silhouette coordinates
     *
     * @param x X-coordinate of pixel
     * @param y Y-coordinate of pixel
     */
    public void processPixel(int x, int y) {
        updatePixel(x, y);
        area++;
    }

    /**
     * Getter for silhouette area
     *
     * @return silhouette area (amount of pixels)
     */
    public int getArea() {
        return area;
    }

    /**
     * Calculates aspect ratio based on max and min pixel coordinates.
     *
     * @return approximate aspect ratio of silhouette (max side/min side)
     */
    public double getAspectRatio() {
        int width = getWidth();
        int height = getHeight();
        if (width == 0 || height == 0) {
            return 0;
        }
        return (double) Math.max(width, height) / Math.min(width, height);
    }

    /**
     * Finds maximal area from list of silhouettes
     *
     * @param silhouettes list of silhouette sizes
     * @return found maximal area
     */
    public static int getMaxArea(List<SilhouetteSize> silhouettes) {
        int maxArea = 0;
        for (SilhouetteSize size : silhouettes) {
            int area = size.getArea();
            if (area > maxArea) {
                maxArea = area;
            }
        }
        return maxArea;
    }

    /**
     * Calculates approximate width of silhouette based on min and max X-coordinates
     *
     * @return width of silhouette
     */
    private int getWidth() {
        if (area == 0) {
            return 0;
        }
        return maxX - minX + 1; // inclusively
    }

    /**
     * Calculates approximate height of silhouette based on min and max Y-coordinates
     *
     * @return height of silhouette
     */
    private int getHeight() {
        if (area == 0) {
            return 0;
        }
        return maxY - minY + 1; // inclusively
    }
}

package com.shpp.p2p.cs.kkunieva.assignment13.utils;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Erosion {

    /**
     * Forbidden to instantiate
     */
    private Erosion() {
    }

    /**
     * Image erosion (shrinking). Removes pixels from silhouettes that are adjacent to the background.
     *
     * @param image image to erode
     */
    public static void shrinkImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
        int[] resultPixels = new int[pixels.length];
        Arrays.fill(resultPixels, ImageUtils.LIGHT_COLOR);

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                if (ImageUtils.isSilhouetteColor(pixels[y * width + x])
                        && allNeighborsSilhouettes(pixels, y, width, x)) {
                    resultPixels[y * width + x] = ImageUtils.DARK_COLOR;
                }
            }
        }
        image.setRGB(0, 0, width, height, resultPixels, 0, width);
    }

    /**
     * Analyzes neighbors of pixel: checks if each of them is part of silhouette.
     *
     * @param pixels pixels of image
     * @param y      Y-coordinate of current pixel
     * @param width  width of image
     * @param x      X-coordinate of current pixel
     * @return true, if all pixels are part of silhouette; false - otherwise.
     */
    private static boolean allNeighborsSilhouettes(int[] pixels, int y, int width, int x) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (ImageUtils.isBackgroundColor(pixels[(y + dy) * width + (x + dx)])) {
                    return false;
                }
            }
        }
        return true;
    }
}

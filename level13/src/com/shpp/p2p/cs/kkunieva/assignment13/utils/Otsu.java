package com.shpp.p2p.cs.kkunieva.assignment13.utils;

import java.awt.image.BufferedImage;

public class Otsu {

    /**
     * Forbidden to instantiate
     */
    private Otsu() {
    }

    /**
     * Weight of corner color
     */
    private static final int CORNER_WEIGHT = 10;

    /**
     * Prevalence rate of background color weight on corners
     */
    private static final double PREVALENCE_RATE = 2;

    /**
     * Binarizes image (marks each pixel as object or as background) based on Otsu's threshold and histogram of
     * luminances.
     *
     * @param image image to binarize
     */
    public static void binarizeImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] luminances = ImageUtils.getLuminances(image);
        int[] histogram = ImageUtils.histogramFor(luminances);
        int threshold = findOtsuThreshold(histogram, width * height);

        int lightPixelsWeight = 0;
        int darkPixelsWeight = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!ImageUtils.isEdge(y, height, x, width)) {
                    continue;
                }

                int weight = ImageUtils.isCorner(y, height, x, width) ? CORNER_WEIGHT : 1;

                if (luminances[y][x] > threshold) {
                    lightPixelsWeight += weight;
                } else {
                    darkPixelsWeight += weight;
                }
            }
        }

        repaintImage(image, luminances, threshold,
                     isBackgroundLight(threshold, histogram, width, height, lightPixelsWeight, darkPixelsWeight));
    }

    /**
     * Decides if background is light based on PREVALENCE_RATE and total amount of pixels of each type
     *
     * @param threshold         Otsu's threshold
     * @param histogram         histogram of the frequencies of luminances
     * @param width             width of image
     * @param height            height of image
     * @param lightPixelsWeight weight of light pixels
     * @param darkPixelsWeight  weight of dark pixels
     * @return true, if background is light; false - otherwise.
     */
    private static boolean isBackgroundLight(int threshold, int[] histogram, int width, int height,
                                             int lightPixelsWeight, int darkPixelsWeight) {
        boolean backgroundIsLight;
        if (lightPixelsWeight > darkPixelsWeight * PREVALENCE_RATE) {
            backgroundIsLight = true;
        } else if (darkPixelsWeight > lightPixelsWeight * PREVALENCE_RATE) {
            backgroundIsLight = false;
        } else {
            int lightPixelsTotal = 0;
            for (int i = ++threshold; i < histogram.length; i++) {
                lightPixelsTotal += histogram[i];
            }
            int darkPixelsTotal = (width * height) - lightPixelsTotal;
            backgroundIsLight = lightPixelsTotal > darkPixelsTotal;
        }
        return backgroundIsLight;
    }

    /**
     * Changes colors of image pixels to binary: background or object. Paints background always to LIGHT_COLOR.
     *
     * @param image             image to process
     * @param luminances        luminances of pixels
     * @param threshold         Otsu's threshold
     * @param backgroundIsLight flag if background is light
     */
    private static void repaintImage(BufferedImage image, int[][] luminances, int threshold,
                                     boolean backgroundIsLight) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                boolean isLight = luminances[y][x] > threshold;
                if (isLight == backgroundIsLight) {
                    image.setRGB(x, y, ImageUtils.LIGHT_COLOR);
                } else {
                    image.setRGB(x, y, ImageUtils.DARK_COLOR);
                }
            }
        }
    }

    /**
     * Otsu's method. Calculates threshold based on histogram of luminances.
     *
     * @param histogram   histogram of the frequencies of luminances
     * @param totalPixels total amount of pixels in image
     * @return threshold for image binarization
     */
    private static int findOtsuThreshold(int[] histogram, int totalPixels) {
        double luminanceSum = 0;
        for (int i = 0; i <= ImageUtils.MAX_COLOR_COMPONENT; i++) {
            luminanceSum += i * histogram[i];
        }
        return calculateThreshold(histogram, totalPixels, luminanceSum);
    }

    /**
     * Calculates the threshold value between dark and light classes based on maximum dispersion.
     *
     * @param histogram    histogram of the frequencies of luminances
     * @param totalPixels  total amount of pixels in image
     * @param luminanceSum sum of all pixel luminances
     * @return threshold for image binarization
     */
    private static int calculateThreshold(int[] histogram, int totalPixels, double luminanceSum) {
        double sumClass1 = 0;
        int countClass1 = 0;
        double maxDispersion = 0;
        int bestThreshold = 0;

        for (int i = 0; i <= ImageUtils.MAX_COLOR_COMPONENT; i++) {
            countClass1 += histogram[i];
            int countClass2 = totalPixels - countClass1;
            sumClass1 += i * histogram[i];
            double dispersion = calculateClassDispersion(sumClass1, luminanceSum - sumClass1, countClass1,
                                                         countClass2);

            if (dispersion > maxDispersion) {
                maxDispersion = dispersion;
                bestThreshold = i;
            }
        }

        return bestThreshold;
    }

    /**
     * Calculates dispersion between 2 classes of pixels.
     *
     * @param sumClass1   sum of luminances in first class
     * @param sumClass2   sum of luminances in second class
     * @param countClass1 number of pixels in first class
     * @param countClass2 number of pixels in second class
     * @return dispersion between two classes
     */
    private static double calculateClassDispersion(double sumClass1, double sumClass2, int countClass1, int countClass2) {
        if (countClass1 == 0 || countClass2 == 0) {
            return 0.0;
        }
        double avg1 = sumClass1 / countClass1;
        double avg2 = sumClass2 / countClass2;
        double diff = avg1 - avg2;
        return (double) countClass1 * countClass2 * diff * diff;
    }
}

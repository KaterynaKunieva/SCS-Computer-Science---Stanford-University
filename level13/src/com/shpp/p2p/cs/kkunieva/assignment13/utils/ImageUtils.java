package com.shpp.p2p.cs.kkunieva.assignment13.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Utility methods to process images
 */
public final class ImageUtils {

    /**
     * Maximum possible value of color components (rgba, luminance)
     */
    public static final int MAX_COLOR_COMPONENT = 255;

    /**
     * Color of light class
     */
    public static final int LIGHT_COLOR = 0xFFFFFFFF;

    /**
     * Color of dark class
     */
    public static final int DARK_COLOR = 0xFF000000;

    /**
     * Coefficient of transformation: RED color channel to brightness
     */
    private static final double R_LUMINANCE_RATE = 0.2126;

    /**
     * Coefficient of transformation: GREEN color channel to brightness
     */
    private static final double G_LUMINANCE_RATE = 0.7152;

    /**
     * Coefficient of transformation: BLUE color channel to brightness
     */
    private static final double B_LUMINANCE_RATE = 0.0722;

    /**
     * Forbidden to instantiate
     */
    private ImageUtils() {
    }

    /**
     * Reads image file and returns BufferedImage, if image is valid and has supported extension;
     * otherwise - null.
     *
     * @param imgPath path to image file
     * @return BufferedImage or null, if IOException appeared
     */
    public static BufferedImage bufferImage(String imgPath) {
        try {
            BufferedImage original = ImageIO.read(new File(imgPath));
            BufferedImage copy = new BufferedImage(original.getWidth(), original.getHeight(),
                                                   BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = copy.createGraphics();
            g.drawImage(original, 0, 0, null);
            g.dispose();
            return copy;
        } catch (IOException _) {
            return null;
        }
    }

    /**
     * Validates coordinates of pixel (x, y) inside image bounds based on width and height of image.
     *
     * @param imgWidth  width of image
     * @param imgHeight height of image
     * @param x         X-coordinate of pixel
     * @param y         Y-coordinate of pixel
     * @return true, if pixel is inside of image bounds; false - otherwise.
     */
    public static boolean isInsideImage(int imgWidth, int imgHeight, int x, int y) {
        return x >= 0 && x < imgWidth && y >= 0 && y < imgHeight;
    }

    /**
     * Checks if pixel is on edge of image.
     *
     * @param y      Y-coordinate
     * @param height height of image
     * @param x      X-coordinate
     * @param width  width of image
     * @return true, if pixel is on image edge; false - otherwise
     */
    public static boolean isEdge(int y, int height, int x, int width) {
        return y == 0 || y == height - 1 || x == 0 || x == width - 1;
    }

    /**
     * Checks if pixel is corner of image.
     *
     * @param y      Y-coordinate
     * @param height height of image
     * @param x      X-coordinate
     * @param width  width of image
     * @return true, if pixel is corner of image; false - otherwise
     */
    public static boolean isCorner(int y, int height, int x, int width) {
        return (y == 0 || y == height - 1) && (x == 0 || x == width - 1);
    }

    /**
     * Checks if color is a background color (by default binarized image has light background)
     *
     * @param argb color to check
     * @return true if color is the same as background color; false - otherwise.
     */
    public static boolean isBackgroundColor(int argb) {
        return argb == LIGHT_COLOR;
    }

    /**
     * Checks if color is a silhouette color (by default binarized image has dark background)
     *
     * @param argb color to check
     * @return true if color is the same as silhouette color; false - otherwise.
     */
    public static boolean isSilhouetteColor(int argb) {
        return argb == DARK_COLOR;
    }

    /**
     * Calculates luminance of each pixel in image.
     *
     * @param image image to process.
     * @return matrix of luminances for each pixel
     */
    public static int[][] getLuminances(BufferedImage image) {
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        int[][] luminances = new int[imgHeight][imgWidth];

        for (int y = 0; y < imgHeight; ++y) {
            for (int x = 0; x < imgWidth; ++x) {
                int pixelARGB = image.getRGB(x, y);
                luminances[y][x] = getLuminance(pixelARGB);
            }
        }
        return luminances;
    }

    /**
     * Calculates histogram of the frequencies of luminances
     *
     * @param luminances luminances in the image
     * @return histogram of those luminances
     */
    public static int[] histogramFor(int[][] luminances) {
        int[] histogram = new int[MAX_COLOR_COMPONENT + 1];

        for (int[] ints : luminances) {
            for (int luminance : ints) {
                histogram[luminance]++;
            }
        }

        return histogram;
    }

    /**
     * Calculate relative luminance for argb color
     *
     * @param argb color to calculate
     * @return value of luminance in range from 0 to MAX_LUMINANCE, inclusive.
     */
    private static int getLuminance(int argb) {
        Color color = new Color(argb, true);
        int alpha = color.getAlpha();
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        if (alpha < MAX_COLOR_COMPONENT) { // alpha blending to white bg
            double opacity = (double) alpha / MAX_COLOR_COMPONENT; // 0-255 => 0-1
            double backgroundRatio = MAX_COLOR_COMPONENT * (1 - opacity); // Each channel of white is maximum
            red = (int) (red * opacity + backgroundRatio);
            green = (int) (green * opacity + backgroundRatio);
            blue = (int) (blue * opacity + backgroundRatio);
        }

        return (int) (R_LUMINANCE_RATE * red + G_LUMINANCE_RATE * green + B_LUMINANCE_RATE * blue);
    }
}
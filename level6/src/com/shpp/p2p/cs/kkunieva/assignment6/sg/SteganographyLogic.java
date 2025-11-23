package com.shpp.p2p.cs.kkunieva.assignment6.sg;

import acm.graphics.GImage;

/**
 * Implements steganography by encoding and decoding a message (black&white image) inside an image,
 * using the parity of the red color channel:
 * - even value represents white;
 * - odd value represents black.
 */
public class SteganographyLogic {
    /**
     * Given a GImage containing a hidden message, finds the hidden message
     * contained within it and returns a boolean array containing that message.
     * <p/>
     * A message has been hidden in the input image as follows.  For each pixel
     * in the image, if that pixel has a red component that is an even number,
     * the message value at that pixel is false.  If the red component is an odd
     * number, the message value at that pixel is true.
     *
     * @param source The image containing the hidden message.
     * @return The hidden message, expressed as a boolean array.
     */
    public static boolean[][] findMessage(GImage source) {
        int[][] pixels = source.getPixelArray();
        int imageHeight = pixels.length;
        int imageWidth = pixels[0].length;

        boolean[][] message = new boolean[imageHeight][imageWidth];

        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                int redPixel = GImage.getRed(pixels[i][j]);
                message[i][j] = isBlackEncoded(redPixel);
            }
        }
        return message;
    }

    /**
     * Hides the given message inside the specified image.
     * <p/>
     * The image will be given to you as a GImage of some size, and the message will
     * be specified as a boolean array of pixels, where each white pixel is denoted
     * false and each black pixel is denoted true.
     * <p/>
     * The message should be hidden in the image by adjusting the red channel of all
     * the pixels in the original image.  For each pixel in the original image, you
     * should make the red channel an even number if the message color is white at
     * that position, and odd otherwise.
     * <p/>
     * You can assume that the dimensions of the message and the image are the same.
     * <p/>
     *
     * @param message The message to hide.
     * @param source  The source image.
     * @return A GImage whose pixels have the message hidden within it.
     */
    public static GImage hideMessage(boolean[][] message, GImage source) {
        int[][] pixels = source.getPixelArray();
        int imageHeight = pixels.length;
        int imageWidth = pixels[0].length;
        int[][] outputPixels = new int[imageHeight][imageWidth];

        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                outputPixels[i][j] = encryptPixel(pixels[i][j], message[i][j]);
            }
        }
        return new GImage(outputPixels);
    }

    /**
     * Determines whether a pixel encodes a black message pixel based on its red color component.
     *
     * @param redPixel the red component of the pixel (0-255).
     * @return true if the pixel represents black in the hidden message, false otherwise.
     */
    private static boolean isBlackEncoded(int redPixel) {
        return redPixel % 2 != 0;
    }

    /**
     * Returns a new pixel with the message encoded in the red component.
     * If messagePixel is true, the red component is made odd;
     * if false, it is made even. Other color components remain unchanged.
     *
     * @param imgPixel     the original pixel.
     * @param messagePixel true if the message pixel is black, false for white.
     * @return a new pixel with the message encoded.
     */
    private static int encryptPixel(int imgPixel, boolean messagePixel) {
        int redPixel = GImage.getRed(imgPixel);
        int deltaRed = 0;
        boolean isPixelEncodesBlack = isBlackEncoded(redPixel);

        if ((messagePixel && !isPixelEncodesBlack) ||
                (!messagePixel && isPixelEncodesBlack)) {
            deltaRed = redPixel == 0 ? 1 : -1;
        }
        return deltaRed == 0 ?
                imgPixel :
                GImage.createRGBPixel(
                        redPixel + deltaRed,
                        GImage.getGreen(imgPixel),
                        GImage.getBlue(imgPixel));
    }
}

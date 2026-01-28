package com.shpp.p2p.cs.kkunieva.assignment13.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Provides window with image to animate process of filling silhouettes
 */
public class Visualizer {

    /**
     * Width of window, px
     */
    private static final int WINDOW_WIDTH = 500;

    /**
     * Height of window, px
     */
    private static final int WINDOW_HEIGHT = 400;

    /**
     * Delay between frames, 1ms
     */
    private static final int ANIMATION_DELAY = 1;

    /**
     * List of colors to select color of silhouette
     */
    private static final int[] COLORS = new int[]{
            Color.GREEN.getRGB(),
            Color.RED.getRGB(),
            Color.YELLOW.getRGB(),
            Color.BLUE.getRGB(),
            Color.MAGENTA.getRGB(),
            Color.CYAN.getRGB(),
    };

    private final JLabel imageLabel;
    private BufferedImage image;

    /**
     * Creates empty window with static size
     */
    public Visualizer() {
        JFrame frame = new JFrame("Algorithm Visualization");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        frame.add(imageLabel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Sets image to window, scaling its sizes
     *
     * @param image image to set
     */
    public void setImage(BufferedImage image) {
        this.image = image;

        Image scaled = scaleToWindow(image);
        imageLabel.setIcon(new ImageIcon(scaled));
    }

    /**
     * Animates coloring pixel
     *
     * @param x       pixel X-coordinate
     * @param y       pixel Y-coordinate
     * @param colorId ID of filling color
     */
    public void animatePixel(int x, int y, int colorId) {
        if (image == null) {
            return;
        }

        image.setRGB(x, y, COLORS[colorId % COLORS.length]);
        imageLabel.setIcon(new ImageIcon(scaleToWindow(image)));

        try {
            Thread.sleep(ANIMATION_DELAY);
        } catch (InterruptedException _) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Scales image to size of window
     *
     * @param image image to scale
     * @return scaled image
     */
    private Image scaleToWindow(BufferedImage image) {
        double coefficient = Math.min(
                (double) Visualizer.WINDOW_WIDTH / image.getWidth(),
                (double) Visualizer.WINDOW_HEIGHT / image.getHeight()
        );

        int width = (int) (image.getWidth() * coefficient);
        int height = (int) (image.getHeight() * coefficient);

        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                           RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();

        return scaledImage;
    }
}

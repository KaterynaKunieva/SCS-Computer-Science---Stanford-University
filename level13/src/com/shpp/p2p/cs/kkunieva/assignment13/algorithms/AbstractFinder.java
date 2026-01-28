package com.shpp.p2p.cs.kkunieva.assignment13.algorithms;

import com.shpp.p2p.cs.kkunieva.assignment13.AlgorithmConfig;
import com.shpp.p2p.cs.kkunieva.assignment13.utils.Erosion;
import com.shpp.p2p.cs.kkunieva.assignment13.utils.ImageUtils;
import com.shpp.p2p.cs.kkunieva.assignment13.utils.Otsu;
import com.shpp.p2p.cs.kkunieva.assignment13.utils.Visualizer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Finder of silhouettes without implementation of crawling silhouette (it must be implemented in custom algorithm)
 */
public abstract class AbstractFinder implements AlgorithmFinder {

    /**
     * Directions to explore pixels (x, y)
     */
    protected static final Point[] DIRECTIONS = {
            new Point(0, 1),
            new Point(1, 0),
            new Point(0, -1),
            new Point(-1, 0),
    };

    protected Visualizer visualizer;
    protected BufferedImage image;
    protected AlgorithmConfig config;

    /**
     * Creates instance of finder for specified image.
     *
     * @param image  image with silhouettes
     * @param config instance with all configurations for algorithm
     */
    AbstractFinder(BufferedImage image, AlgorithmConfig config) {
        this.image = image;
        this.config = config;
        if (config.useVisualizer()) {
            this.visualizer = new Visualizer();
            this.visualizer.setImage(image);
        }
    }

    /**
     * Crawls silhouette, marking pixels as visited.
     *
     * @param x              X-coordinate of current pixel of silhouette
     * @param y              Y-coordinate of current pixel of silhouette
     * @param silhouetteSize object with silhouette sizes
     * @param silhouetteId   unique identifier of silhouette (necessary for color visualization)
     * @param visitedPixels  list of all pixels marked as visited or not
     */
    protected abstract void crawlSilhouette(int x, int y, SilhouetteSize silhouetteSize, int silhouetteId, boolean[] visitedPixels);

    /**
     * Counts silhouettes in raw image: prepares image, visualizer (if necessary), returns counted valid silhouettes.
     *
     * @return counted silhouettes
     */
    public int findSilhouettes() {
        if (visualizer != null) {
            visualizer.setImage(image);
        }
        prepareBinaryImage();
        return countSilhouettes();
    }

    /**
     * Checks if pixel is part of silhouette based on image bounds, other silhouettes and its color.
     *
     * @param x             X-coordinate of pixel
     * @param y             Y-coordinate of pixel
     * @param visitedPixels list of pixel marks "is already part of silhouette (or not)"
     * @return true, if pixel is valid part of silhouette; false - otherwise.
     */
    private boolean isSilhouette(int x, int y, boolean[] visitedPixels) {
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        return ImageUtils.isInsideImage(imgWidth, imgHeight, x, y) && !visitedPixels[y * imgWidth + x]
                && ImageUtils.isSilhouetteColor(image.getRGB(x, y));
    }

    /**
     * Collects, filters and counts silhouettes
     *
     * @return amount of silhouettes without "garbage"
     */
    private int countSilhouettes() {
        ArrayList<SilhouetteSize> silhouettes = collectImageSilhouettes();
        return countValidSilhouettes(silhouettes);
    }

    /**
     * Validates silhouettes by size and counts valid.
     *
     * @param silhouettes silhouettes areas
     * @return amount of valid silhouettes
     */
    private int countValidSilhouettes(ArrayList<SilhouetteSize> silhouettes) {
        if (silhouettes.isEmpty()) {
            return 0;
        }
        int maxArea = SilhouetteSize.getMaxArea(silhouettes);

        int amount = 0;
        for (SilhouetteSize silhouetteSize : silhouettes) {
            double percentFromMax = (double) silhouetteSize.getArea() / maxArea * 100;
            if (percentFromMax < config.garbageSizePercent()
                    && silhouetteSize.getAspectRatio() < config.garbageAspectRatio()
            ) {
                continue;
            }
            amount++;
        }
        return amount;
    }

    /**
     * Collects areas of all silhouettes (continuous shapes of similar color)
     *
     * @return list of silhouettes areas
     */
    private ArrayList<SilhouetteSize> collectImageSilhouettes() {
        int width = image.getWidth();
        int height = image.getHeight();
        boolean[] visitedPixels = new boolean[width * height];
        ArrayList<SilhouetteSize> silhouetteSizes = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // crawl found silhouette
                int rgb = image.getRGB(x, y);
                if (ImageUtils.isBackgroundColor(rgb)) {
                    continue;
                }
                if (!visitedPixels[y * width + x]) {
                    SilhouetteSize silhouetteSize = new SilhouetteSize();
                    crawlSilhouette(x, y, silhouetteSize, silhouetteSizes.size(), visitedPixels);
                    silhouetteSizes.add(silhouetteSize);
                }
            }
        }
        return silhouetteSizes;
    }

    /**
     * If pixel is silhouette, marks it as visited and counts to silhouette sizes.
     *
     * @param x              X-coordinate of pixel
     * @param y              Y-coordinate of pixel
     * @param silhouetteId   unique identifier of silhouette (necessary for color visualization)
     * @param visitedPixels  list of all pixels marked as visited or not
     * @param silhouetteSize object with silhouette sizes
     * @return true, if pixel is valid part of silhouette; false - otherwise.
     */
    protected boolean handlePixel(int x, int y, int silhouetteId, boolean[] visitedPixels, SilhouetteSize silhouetteSize) {
        if (isSilhouette(x, y, visitedPixels)) {
            visitedPixels[y * image.getWidth() + x] = true;

            if (visualizer != null) {
                visualizer.animatePixel(x, y, silhouetteId);
            }
            silhouetteSize.processPixel(x, y);

            return true;
        }

        return false;
    }

    /**
     * Prepares raw image for searching silhouettes: does binarization and erosion.
     */
    private void prepareBinaryImage() {
        Otsu.binarizeImage(image);
        for (int i = 0; i < config.eroseIterations(); i++) {
            Erosion.shrinkImage(image);
        }
    }
}

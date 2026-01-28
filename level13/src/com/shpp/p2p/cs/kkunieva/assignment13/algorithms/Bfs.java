package com.shpp.p2p.cs.kkunieva.assignment13.algorithms;

import com.shpp.p2p.cs.kkunieva.assignment13.AlgorithmConfig;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Breadth-First-Search algorithm for crawling over silhouette on pixel matrix of image.
 */
public class Bfs extends AbstractFinder {

    /**
     * Creates instance of finder for specified image.
     *
     * @param image  image with silhouettes
     * @param config instance with all configurations for algorithm
     */
    public Bfs(BufferedImage image, AlgorithmConfig config) {
        super(image, config);
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
    @Override
    protected void crawlSilhouette(int x, int y, SilhouetteSize silhouetteSize, int silhouetteId,
                                   boolean[] visitedPixels) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(x, y));

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            int xPixel = (int) p.getX();
            int yPixel = (int) p.getY();
            if (handlePixel(xPixel, yPixel, silhouetteId, visitedPixels, silhouetteSize)) {
                for (Point dir : DIRECTIONS) {
                    queue.add(new Point(xPixel + (int) dir.getX(), yPixel + (int) dir.getY()));
                }
            }
        }

    }
}

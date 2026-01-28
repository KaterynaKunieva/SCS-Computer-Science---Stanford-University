package com.shpp.p2p.cs.kkunieva.assignment13.algorithms;

import com.shpp.p2p.cs.kkunieva.assignment13.AlgorithmConfig;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Recursive Depth-First-Search (DFS) for crawling over silhouette on pixel matrix of image.
 */
public class RecursiveDfs extends AbstractFinder {

    public RecursiveDfs(BufferedImage image, AlgorithmConfig config) {
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
        if (!handlePixel(x, y, silhouetteId, visitedPixels, silhouetteSize)) {
            return;
        }

        for (Point dir : DIRECTIONS) {
            crawlSilhouette((int) (x + dir.getX()), (int) (y + dir.getY()),
                            silhouetteSize, silhouetteId, visitedPixels);
        }

    }
}

package com.shpp.p2p.cs.kkunieva.assignment13;

import com.shpp.p2p.cs.kkunieva.assignment13.algorithms.AlgorithmFinder;

import java.awt.image.BufferedImage;

/**
 * Parametrized finder of silhouettes on images.
 * Multiple images can be processed by same Finder (same config, different images).
 *
 * @param algorithm algorithm to use for crawling silhouettes
 * @param config    instance with all configurations for algorithm
 */
public record SilhouettesFinder(AlgorithmFactory algorithm, AlgorithmConfig config) {

    /**
     * Creates algorithm instance and runs method to count silhouettes.
     *
     * @param image image with silhouettes
     * @return amount of found silhouettes
     */
    public int findSilhouettes(BufferedImage image) {
        AlgorithmFinder algorithmFinder = algorithm.createInstance(image, config);
        return algorithmFinder.findSilhouettes();
    }
}

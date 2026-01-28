package com.shpp.p2p.cs.kkunieva.assignment13.algorithms;

/**
 * Contract for algorithm to use in Finder (must contain implementation of specified methods)
 */
public interface AlgorithmFinder {

    /**
     * Counts silhouettes in raw image: prepares image, visualizer (if necessary), returns counted valid silhouettes.
     *
     * @return counted silhouettes
     */
    int findSilhouettes();
}

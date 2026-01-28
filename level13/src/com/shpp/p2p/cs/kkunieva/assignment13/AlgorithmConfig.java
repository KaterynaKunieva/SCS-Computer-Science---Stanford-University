package com.shpp.p2p.cs.kkunieva.assignment13;

/**
 * Configuration class for Finder
 *
 * @param garbageSizePercent maximal percent size of objects to be considered as garbage, in %
 * @param garbageAspectRatio maximal aspect ratio of objects to be considered as garbage (min side/max side)
 * @param useVisualizer      flag to use visualizer window or not
 * @param eroseIterations    number of erosion iterations
 */
public record AlgorithmConfig(double garbageSizePercent, double garbageAspectRatio, boolean useVisualizer,
                              int eroseIterations) {
}

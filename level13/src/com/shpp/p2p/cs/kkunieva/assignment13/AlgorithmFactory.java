package com.shpp.p2p.cs.kkunieva.assignment13;

import com.shpp.p2p.cs.kkunieva.assignment13.algorithms.AlgorithmFinder;

import java.awt.image.BufferedImage;

/**
 * Contract for algorithm class. Used to have possibility to create new instance of algorithm for each image without
 * creating new instance of Finder.
 */
@FunctionalInterface
public interface AlgorithmFactory {
    AlgorithmFinder createInstance(BufferedImage image, AlgorithmConfig config);
}

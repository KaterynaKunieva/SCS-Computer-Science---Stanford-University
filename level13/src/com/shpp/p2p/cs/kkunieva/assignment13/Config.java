package com.shpp.p2p.cs.kkunieva.assignment13;

import com.shpp.p2p.cs.kkunieva.assignment13.algorithms.Bfs;

/**
 * Constants for configuration
 */
public final class Config {

    /**
     * Forbidden to instantiate
     */
    private Config() {
    }

    /**
     * Path to image, if not received from user
     */
    public static final String DEFAULT_IMG_PATH = "test.jpg";

    /**
     * Flag for visualizer to show the process of crawling silhouettes in Window
     */
    public static final boolean WITH_VISUALISATION = false;

    /**
     * Percent size of objects to be considered as garbage and not counted
     * (based on the size of biggest figure on image), in %
     */
    public static final double GARBAGE_SIZE_PERCENT = 3;

    /**
     * Minimal aspect ratio of objects to be considered as garbage:
     * max(width, height) / min(width, height)
     */
    public static final double GARBAGE_ASPECT_RATIO = 10;

    /**
     * Amount of erose iterations to be performed
     */
    public static final int EROSE_ITERATIONS = 1;

    /**
     * Constructor of algorithm to use. Examples: Bfs::new, RecursiveDfs::new
     */
    public static final AlgorithmFactory ALGORITHM_FACTORY = Bfs::new;
}

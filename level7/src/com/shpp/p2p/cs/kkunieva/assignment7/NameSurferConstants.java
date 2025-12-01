package com.shpp.p2p.cs.kkunieva.assignment7;


/**
 * Declares several constants that are shared by the different modules in the NameSurfer application.
 * Any class that implements this interface can use these constants.
 */
public interface NameSurferConstants {

    /**
     * The width of the application window
     */
    int APPLICATION_WIDTH = 800;

    /**
     * The height of the application window
     */
    int APPLICATION_HEIGHT = 600;

    /**
     * The name of the file containing the data
     */
    String NAMES_DATA_FILE = "names-data.txt";

    /**
     * The first decade in the database
     */
    int START_DECADE = 1900;

    /**
     * The number of decades
     */
    int NDECADES = 12;

    /**
     * The maximum rank in the database
     */
    int MAX_RANK = 1000;

    /**
     * The number of pixels to reserve at the top and bottom
     */
    int GRAPH_MARGIN_SIZE = 20;
}

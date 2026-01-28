package com.shpp.p2p.cs.kkunieva.assignment13;

import com.shpp.p2p.cs.kkunieva.assignment13.utils.ImageUtils;

import java.awt.image.BufferedImage;

/**
 * Program for counting the number of silhouettes on image.
 * The silhouette is considered as a monolithic figure located on a contrasting background.
 * Important to note that the program:
 * - can handle any colors that are sufficiently contrasting against the background because it relies on Otsu's method.
 * - analyzes pixels along the image perimeter, so for correct operation, the image must satisfy the following
 * requirements: 1) do not contain frames; 2) silhouettes must not touch the borders.
 * - relies on the ImageIO library for file reading, so the supported formats include png, jpg, bmp, gif.
 * Depending on image quality, adhesion between silhouettes or one of the list item above, results may be inaccurate.
 * For usage DFS the JVM argument -Xss1024m must be set; otherwise, the program may crash with a StackOverflowError.
 * Constants to configure program's behavior located in the class Config.
 */
public class Assignment13Part1 {

    /**
     * Entry point to the program. Determines path to image, creates finder based on configuration and
     * prints results of counting or throws exception.
     *
     * @param args array of string arguments, may contain path to the image in the file system.
     */
    public static void main(String[] args) {
        String imgPath;
        if (args == null || args.length == 0 || args[0] == null) {
            imgPath = Config.DEFAULT_IMG_PATH;
        } else {
            imgPath = args[0];
        }

        BufferedImage image = ImageUtils.bufferImage(imgPath);
        if (image == null) {
            throw new IllegalArgumentException("Error while reading file %s".formatted(imgPath));
        }
        SilhouettesFinder silhouettesFinder = new SilhouettesFinder(Config.ALGORITHM_FACTORY,
                                                                    new AlgorithmConfig(Config.GARBAGE_SIZE_PERCENT,
                                                                                        Config.GARBAGE_ASPECT_RATIO,
                                                                                        Config.WITH_VISUALISATION,
                                                                                        Config.EROSE_ITERATIONS
                                                                    ));
        System.out.println(silhouettesFinder.findSilhouettes(image));
    }
}

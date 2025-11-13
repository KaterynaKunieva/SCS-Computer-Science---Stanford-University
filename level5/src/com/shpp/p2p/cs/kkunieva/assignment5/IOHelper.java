package com.shpp.p2p.cs.kkunieva.assignment5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Utility class containing static methods to manage file I/O operations.
 */
public class IOHelper {
    /**
     * Reads a file located at the provided path line by line.
     * Handles exceptions by printing an error message and returning an empty list.
     *
     * @param filename The path/name of the file to read.
     * @return The list of the file lines; null, if file content cannot be read.
     */
    static ArrayList<String> getFileLines(String filename) {
        ArrayList<String> fileLines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while (true) {
                String line = br.readLine();
                if (line == null) break;
                fileLines.add(line);
            }
            br.close();
        } catch (IOException e) {
            System.out.println(String.format("Error while reading file file '%s': '%s'", filename, e.getMessage()));
        }

        return fileLines;
    }
}

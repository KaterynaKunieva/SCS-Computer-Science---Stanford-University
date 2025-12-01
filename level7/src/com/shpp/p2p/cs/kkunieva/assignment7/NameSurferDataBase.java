package com.shpp.p2p.cs.kkunieva.assignment7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Represents a database of names popularity and an interface for interacting via its methods.
 * The data is stored in RAM.
 */
public class NameSurferDataBase implements NameSurferConstants {

    private final Map<String, NameSurferEntry> database;

    /**
     * Creates a new NameSurferDataBase and initializes it using the
     * data in the specified file.
     *
     * @param filename Path to the file.
     * @throws IOException If the requested file does not exist or if an error occurs as the file is being read.
     */
    public NameSurferDataBase(String filename) throws IOException {
        this.database = loadDatabase(filename);
    }

    /**
     * Returns the NameSurferEntry associated with this name, if one
     * exists. If the name does not appear in the database, this
     * method returns null.
     */
    public NameSurferEntry findEntry(String name) {
        return database.get(name.toLowerCase());
    }

    /**
     * Loads all names and their popularity data from the file into a HashMap for quick lookup by name.
     * All keys in map are stored in lowercases.
     *
     * @param filename Path to file.
     * @return Map with database.
     * @throws IOException If file does not exist.
     */
    private Map<String, NameSurferEntry> loadDatabase(String filename) throws IOException {
        Map<String, NameSurferEntry> db = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                NameSurferEntry nameSurferEntry = new NameSurferEntry(line);
                db.put(nameSurferEntry.getName().toLowerCase(), nameSurferEntry);
            }
        }
        return db;
    }
}

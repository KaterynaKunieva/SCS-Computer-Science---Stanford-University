package com.shpp.p2p.cs.kkunieva.assignment7;

import java.util.Arrays;


/**
 * Data model of database entry.
 * Each NameSurferEntry contains a name and a list giving the popularity of that name for each decade.
 */
public class NameSurferEntry implements NameSurferConstants {

    private final String name;
    private final int[] ranks = new int[NDECADES];

    /**
     * Creates a new NameSurferEntry from a data line as it appears
     * in the data file. Each line begins with the name, which is
     * followed by integers giving the rank of that name for each
     * decade.
     */
    public NameSurferEntry(String line) {
        String[] lineParts = line.split(" ");
        this.name = lineParts[0];
        for (int i = 1; i < lineParts.length; i++) {
            ranks[i - 1] = Integer.parseInt(lineParts[i]);
        }
    }

    /**
     * Returns the name associated with this entry.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the rank associated with an entry for a particular
     * decade. The decade value is an integer indicating how many
     * decades have passed since the first year in the database,
     * which is given by the constant START_DECADE. If a name does
     * not appear in a decade, the rank value is 0.
     */
    public int getRank(int decade) {
        if (decade < 0 || decade >= ranks.length) {
            return 0;
        }
        return ranks[decade];
    }

    /**
     * Returns a string that makes it easy to see the value of a
     * NameSurferEntry.
     */
    @Override
    public String toString() {
        return this.name + " " + Arrays.toString(this.ranks);
    }
}


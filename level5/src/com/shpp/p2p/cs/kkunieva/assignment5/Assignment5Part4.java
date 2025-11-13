package com.shpp.p2p.cs.kkunieva.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.util.ArrayList;

/**
 * Parses a CSV file and extracts a column specified by a constant index.
 * The program handles standard CSV formatting rules.
 * <p>
 * Notes on Parsing Logic:
 * 1. An empty value (e.g., "" or skipped by a separator ',') is parsed as an empty string.
 * 2. An empty row is parsed as null.
 * 3. The algorithm does not explicitly handle CRLF line endings within quoted fields.
 */
public class Assignment5Part4 extends TextProgram {
    /**
     * Index of column to extract.
     */
    private static final int COLUMN = 0;

    /**
     * Path to CSV file
     */
    private static final String CSV_PATH = ".csv";

    /**
     * Character used as the separator inside the CSV file.
     */
    private static final char CSV_SEPARATOR = ',';

    /**
     * Character used as the quote inside the CSV file
     */
    private static final char CSV_QUOTE = '"';

    /**
     * Extracts the specified column, prints the result or a warning message, if the file is missing.
     */
    public void run() {
        ArrayList<String> col = extractColumn(CSV_PATH, COLUMN);
        if (col == null) {
            println("File is empty or not exists. \n" +
                    "Please check value of constant CSV_PATH, file location and content. ");
            return;
        }
        println("Column " + COLUMN);
        println(String.join("\n", col));
        println();
    }

    /**
     * Reads the file and extracts the value from the specified column index for each row.
     *
     * @param filename    Path to the file.
     * @param columnIndex Index of the column to extract.
     * @return A list of extracted values or null, if file content unreadable.
     * A null value in the list indicates the column index was out of bounds for that specific row.
     */
    private ArrayList<String> extractColumn(String filename, int columnIndex) {
        ArrayList<String> fileLines = IOHelper.getFileLines(filename);
        if (fileLines.isEmpty()) return null;
        ArrayList<String> columns = new ArrayList<>();
        for (String line : fileLines) {
            ArrayList<String> lineColumns = fieldsIn(line);
            // Add the column value, or null if the columnIndex is out of bounds for this row
            columns.add(columnIndex < lineColumns.size() ? lineColumns.get(columnIndex) : null);
        }
        return columns;
    }

    /**
     * Adds a specified value to the string at a provided index, checking ensuring the list size is sufficient.
     * If the index is out of bounds, it initializes new list elements up to the index.
     *
     * @param list  The list to update.
     * @param i     The index of the string to append to.
     * @param value The string to append to the existing value.
     */
    private void updateList(ArrayList<String> list, int i, String value) {
        if (i >= list.size()) list.add(i, "");
        String prevString = list.get(i);
        String newString = prevString + value;
        list.set(i, newString);
    }

    /**
     * Parses a single CSV line, dividing it into cells according to CSV formatting rules.
     *
     * @param line The line to divide.
     * @return A list of the divided cell values.
     */
    private ArrayList<String> fieldsIn(String line) {
        ArrayList<String> fields = new ArrayList<>();

        boolean insideQuotes = false; // toggles for outer quotes
        int currentIndex = 0; // index of the cell in the table
        int addedChars = 0; // to handle an empty cells like "" or ,,

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == CSV_QUOTE) {
                if (insideQuotes && i + 1 < line.length() && line.charAt(i + 1) == CSV_QUOTE) {
                    // "" => "
                    updateList(fields, currentIndex, "" + CSV_QUOTE);
                    i++;
                } else {
                    // skip outer quote
                    if (addedChars == 0) updateList(fields, currentIndex, "");
                    insideQuotes = !insideQuotes;
                }
            } else if (ch == CSV_SEPARATOR && !insideQuotes) {
                // start new cell
                if (addedChars == 0) updateList(fields, currentIndex, "");
                currentIndex++;
                addedChars = 0;
            } else {
                // append to current cell
                updateList(fields, currentIndex, "" + ch);
                addedChars++;
            }
        }
        // handle coma in the end
        if (!line.isEmpty() && line.charAt(line.length() - 1) == CSV_SEPARATOR) {
            fields.add("");
        }
        return fields;
    }
}
package com.shpp.p2p.cs.kkunieva.assignment7;

import com.shpp.cs.a.simple.SimpleProgram;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Entry point to program for graphical interaction with name popularity over time data and display of graphs.
 * The program is not sensitive to the case of the letters entered.
 */
public class NameSurfer extends SimpleProgram implements NameSurferConstants {

    /**
     * Size of input for search query, in columns.
     */
    private static final int SEARCH_INPUT_SIZE = 30;

    /**
     * Text command to draw the graph, in format as it will appear in the button text.
     */
    private static final String GRAPH_COMMAND = "Graph";

    /**
     * Text command to clear the window, in format as it will appear in the button text.
     */
    private static final String CLEAR_COMMAND = "Clear";

    private JTextField searchInput;
    private NameSurferDataBase nameSurferDataBase;
    private NameSurferGraph graph;

    /**
     * Connects to database and adds interactors to the window.
     */
    @Override
    public void init() {
        try {
            nameSurferDataBase = new NameSurferDataBase(NAMES_DATA_FILE);
        } catch (IOException e) {
            System.out.printf("Error connecting to database from file '%s': '%s'", NAMES_DATA_FILE, e);
            return;
        }

        addInteractors();
    }

    /**
     * Called when a button is clicked or enter is pressed.
     *
     * @param e Event which indicates that action occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        handleCommand(e.getActionCommand());
    }

    /**
     * Adds interactors to the window (objects + listeners)
     */
    private void addInteractors() {
        // add objects
        this.add(new JLabel("Name"), NORTH);
        searchInput = new JTextField();
        searchInput.setColumns(SEARCH_INPUT_SIZE);
        this.add(searchInput, NORTH);
        this.add(new JButton(GRAPH_COMMAND), NORTH);
        this.add(new JButton(CLEAR_COMMAND), NORTH);
        graph = new NameSurferGraph();
        add(graph);

        // add event listeners
        searchInput.setActionCommand(GRAPH_COMMAND);
        searchInput.addActionListener(this);
        this.addActionListeners();
    }

    /**
     * Executes command on canvas: clears or draws graph, depending on text command.
     * Method is not sensitive to the case of the command text.
     *
     * @param command text of command: graph or clear.
     */
    private void handleCommand(String command) {
        if (command.equalsIgnoreCase(GRAPH_COMMAND)) {
            drawGraph();
        } else if (command.equalsIgnoreCase(CLEAR_COMMAND)) {
            clearCanvas();
        }
    }

    /**
     * Handles clearing the canvas: cleans search input, removes graph.
     */
    private void clearCanvas() {
        searchInput.setText("");
        graph.clear();
        graph.update();
    }

    /**
     * Handles drawing graph on the canvas: searches by specified query
     * and, if found, adds graph and cleans search input.
     */
    private void drawGraph() {
        String searchQuery = searchInput.getText().trim();
        NameSurferEntry surferEntry = nameSurferDataBase.findEntry(searchQuery);
        if (surferEntry != null) {
            searchInput.setText("");
            graph.addEntry(surferEntry);
            graph.update();
        }
    }
}

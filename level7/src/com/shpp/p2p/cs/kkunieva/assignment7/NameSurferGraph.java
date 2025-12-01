package com.shpp.p2p.cs.kkunieva.assignment7;

import acm.graphics.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


/**
 * Represents the canvas on which the graph of names is drawn.
 * Responsible for updating (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */
public class NameSurferGraph extends GCanvas
        implements NameSurferConstants, ComponentListener {

    /**
     * Color palette for each entry on the plot (cycles if more than 4).
     */
    private static final Color[] COLORS = new Color[]{
            Color.BLUE,
            Color.RED,
            Color.MAGENTA,
            Color.BLACK
    };

    /**
     * Left margin of the entire graph area.
     */
    private static final int GRAPH_MARGIN_LEFT = 5;

    /**
     * Left margin for each time label relative to left grid line.
     */
    private static final int LABELS_MARGIN_LEFT = 1;

    /**
     * Time step for each segment on the graph
     */
    private static final int TIME_STEP = 10;

    private final List<NameSurferEntry> nameSurferEntries = new ArrayList<>();

    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    public NameSurferGraph() {
        addComponentListener(this);
    }

    /**
     * Clears the list of name surfer entries stored inside this class.
     */
    public void clear() {
        nameSurferEntries.clear();
    }

    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * To rerender the graph call {@code update}.
     *
     * @param entry Entry to add.
     */
    public void addEntry(NameSurferEntry entry) {
        if (nameSurferEntries.contains(entry)) {
            System.out.println("Such entry already exists: " + entry);
            return;
        }
        nameSurferEntries.add(entry);
    }

    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries.
     */
    public void update() {
        removeAll();
        int[] gridCoordinates = drawCoordinateSystem();
        for (int i = 0; i < nameSurferEntries.size(); ++i) {
            NameSurferEntry entry = nameSurferEntries.get(i);
            drawEntryPlot(entry, COLORS[i % COLORS.length], gridCoordinates);
        }
    }

    /**
     * Draws plot (lines and texts) for entry with specified color.
     *
     * @param entry Entry.
     * @param color Color of line and text.
     */
    private void drawEntryPlot(NameSurferEntry entry, Color color, int[] gridCoordinates) {
        String name = entry.getName();
        for (int decadeIndex = 0; decadeIndex < NDECADES; decadeIndex++) {
            int currentRank = entry.getRank(decadeIndex);
            int nextDecadeIndex = decadeIndex + 1;
            addSegmentLabel(name, currentRank, color, gridCoordinates[decadeIndex]);
            if (nextDecadeIndex < NDECADES) {
                drawConnectLine(currentRank, entry.getRank(nextDecadeIndex), color,
                                gridCoordinates[decadeIndex], gridCoordinates[nextDecadeIndex]);
            }
        }
    }

    /**
     * Translates value of popularity to Y in Coordinates System.
     *
     * @param rankValue Value of popularity.
     * @return Y-coordinate on plot representing rankValue.
     */
    private double mapRankToPixel(int rankValue) {
        if (rankValue == 0) {
            return (double) getHeight() - GRAPH_MARGIN_SIZE; // rank 0 should be displayed at the bottom
        }
        double plotHeight = (double) getHeight() - 2 * GRAPH_MARGIN_SIZE;
        double rankFraction = (double) rankValue / MAX_RANK;
        return rankFraction * plotHeight + GRAPH_MARGIN_SIZE;
    }

    /**
     * Adds label to represent rank value in specified color.
     * If value is 0 it displays as *.
     *
     * @param name  Name of the line.
     * @param rank  Rank on the segment.
     * @param color Color of the label.
     * @param x     X-coordinate of the label.
     */
    private void addSegmentLabel(String name, int rank, Color color, int x) {
        String labelText = name + " " + (rank == 0 ? "*" : rank);
        GLabel label = new GLabel(labelText);
        label.setLocation(x, mapRankToPixel(rank));
        label.setColor(color);
        add(label);
    }

    /**
     * Draws line between 2 points in segment in specified color.
     *
     * @param startValue Y-coordinate of 1st point.
     * @param endValue   Y-coordinate of 2nd point.
     * @param color      Color of the line.
     * @param startX     X-coordinate of the line start.
     * @param endX       X-coordinate of the line end.
     */
    private void drawConnectLine(int startValue, int endValue, Color color, int startX, int endX) {
        GLine line = new GLine(
                startX, // x1
                mapRankToPixel(startValue), // y1
                endX, // x2
                mapRankToPixel(endValue) // y2
        );
        line.setColor(color);
        add(line);
    }

    /**
     * Draws Coordinate System:
     * - horizontal lines as top and bottom borders;
     * - vertical lines as segments for displaying changes.
     *
     * @return Array with X of each segment on coordinate system
     */
    private int[] drawCoordinateSystem() {
        // horizontal lines
        drawHorizontalLine(GRAPH_MARGIN_SIZE);
        drawHorizontalLine(getHeight() - GRAPH_MARGIN_SIZE);

        // vertical lines & labels
        int[] gridCoordinates = new int[NDECADES];
        int segmentWidth = (getWidth() - GRAPH_MARGIN_LEFT) / NDECADES;

        for (int i = 0; i < NDECADES; i++) {
            int lineX = GRAPH_MARGIN_LEFT + segmentWidth * i;
            drawVerticalLine(lineX);
            addTimeLabel(START_DECADE + i * TIME_STEP + "", lineX);
            gridCoordinates[i] = lineX;
        }

        return gridCoordinates;
    }

    /**
     * Draws full width horizontal line on specified Y-coordinate.
     *
     * @param y Y-coordinate of line.
     */
    private void drawHorizontalLine(int y) {
        GLine line = new GLine(0, y, getWidth(), y);
        add(line);
    }

    /**
     * Draws full height vertical line on specified X-coordinate.
     *
     * @param x X-coordinate of line.
     */
    private void drawVerticalLine(int x) {
        GLine line = new GLine(x, 0, x, getHeight());
        add(line);
    }

    /**
     * Draws time label on bottom part of the graph to show changes during time.
     *
     * @param text Text of time label
     * @param x    X-coordinate of label
     */
    private void addTimeLabel(String text, int x) {
        GLabel timeValue = new GLabel(text);
        timeValue.setLocation((double) x + LABELS_MARGIN_LEFT, getHeight() - timeValue.getDescent());
        add(timeValue);
    }

    /* Implementation of the ComponentListener interface */
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        update();
    }

    public void componentShown(ComponentEvent e) {
    }
}

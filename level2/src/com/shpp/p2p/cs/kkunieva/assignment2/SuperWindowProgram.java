package com.shpp.p2p.cs.kkunieva.assignment2;

import acm.graphics.GFillable;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * Super class with utility methods for extending functionality
 */

public class SuperWindowProgram extends WindowProgram {
    /**
     * Fills object with color
     *
     * @param obj   Object to fill
     * @param color Color of filling
     */
    public void fillObject(GFillable obj, Color color) {
        obj.setFilled(true);
        obj.setFillColor(color);
    }
}
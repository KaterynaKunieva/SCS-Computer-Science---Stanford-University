package com.shpp.p2p.cs.kkunieva.assignment3;

/**
 * Utility class for Assignment3Part1
 * Class represents configuration of report:
 * - name
 * - days
 * - minutes
 * - purposeText - string to use in report template to specify goal
 * */

public class ReportConfig {
    public String name;
    public String purposeText;
    public int days;
    public int minutes;

    public ReportConfig(String name, int days, int minutes, String purposeText) {
        this.name = name;
        this.days = days;
        this.minutes = minutes;
        this.purposeText = purposeText;
    }
}

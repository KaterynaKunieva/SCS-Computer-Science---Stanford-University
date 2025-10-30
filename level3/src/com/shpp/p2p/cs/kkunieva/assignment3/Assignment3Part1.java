package com.shpp.p2p.cs.kkunieva.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * Aerobics consultant program.
 * Asks the user for daily activity and generates reports based on ReportConfig goals.
 */

public class Assignment3Part1 extends TextProgram {
    // Configurations for different reports: name, required days, goal minutes, and purpose text
    private static final ReportConfig[] REPORT_CONFIGS = {
            new ReportConfig("Cardiovascular health", 5, 30, "for cardiovascular health"),
            new ReportConfig("Blood pressure", 3, 40, "to keep a low blood pressure")
    };

    // Number of days to track user activity per week
    private static final int DAYS_AMOUNT = 7;

    // Stores user's activity minutes for each day
    private int[] userActivity = new int[DAYS_AMOUNT];

    // Template for positive report message
    private static final String POSITIVE_TEMPLATE = "%s: \n\tGreat job! You've done enough exercise %s.";

    // Template for negative report message
    private static final String NEGATIVE_TEMPLATE =
            "%s: \n\tYou needed to train hard for at least %d more day(s) a week!";

    @Override
    public void run() {
        getUserActivity();
        for (ReportConfig reportConfig : REPORT_CONFIGS) {
            doReport(reportConfig);
        }
    }

    /**
     * Asks the user for activity minutes for each day and stores in userActivity array.
     */
    private void getUserActivity() {
        for (int day = 0; day < DAYS_AMOUNT; day++) {
            userActivity[day] = askUserActivity(day + 1);
        }
    }

    /**
     * Asks the user for the number of active minutes on a specific day.
     *
     * @param day - day number to ask.
     * @return int value of answered user minutes.
     */
    private int askUserActivity(int day) {
        return readInt(String.format("How many minutes did you do on day %d?", day));
    }

    /**
     * Generates a report for the given ReportConfig and prints it.
     * Chooses a positive or negative template depending on user's performance.
     *
     * @param reportConfig object representing report configuration.
     */
    private void doReport(ReportConfig reportConfig) {
        int satisfiedDaysCounter = countSatisfiedDays(reportConfig.minutes);
        int missedDays = reportConfig.days - satisfiedDaysCounter;

        String reportText = missedDays <= 0 ?
                generatePositiveReport(reportConfig) :
                generateNegativeReport(reportConfig, missedDays);
        println(reportText);
    }

    /**
     * Generates a positive report message using POSITIVE_TEMPLATE and the given ReportConfig.
     *
     * @param reportConfig object representing report configuration.
     * @return text of positive report.
     */
    private String generatePositiveReport(ReportConfig reportConfig) {
        return String.format(POSITIVE_TEMPLATE, reportConfig.name, reportConfig.purposeText);
    }

    /**
     * Generates a negative report message using NEGATIVE_TEMPLATE and the given ReportConfig,
     * indicating how many days the user missed.
     *
     * @param reportConfig object representing report configuration.
     * @param missedDays   number of unsatisfactory days.
     * @return text of the negative report.
     */
    private String generateNegativeReport(ReportConfig reportConfig, int missedDays) {
        return String.format(NEGATIVE_TEMPLATE, reportConfig.name, missedDays);
    }

    /**
     * Counts days when user met or exceeded goalMinutes.
     *
     * @param goalMinutes goal amount of minutes per day.
     * @return amount of satisfied days.
     */
    private int countSatisfiedDays(int goalMinutes) {
        int counter = 0;
        for (int minutes : userActivity) {
            if (minutes >= goalMinutes) {
                counter++;
            }
        }
        return counter;
    }
}

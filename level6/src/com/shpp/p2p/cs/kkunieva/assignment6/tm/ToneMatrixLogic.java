package com.shpp.p2p.cs.kkunieva.assignment6.tm;

/**
 * Contains logic for converting the state of the Tone Matrix (represented as a boolean matrix of selected notes and
 * samples for each cell) into music (summed samples).
 */
public class ToneMatrixLogic {
    /**
     * Given the contents of the tone matrix, returns a string of notes that should be played
     * to represent that matrix.
     *
     * @param toneMatrix The contents of the tone matrix.
     * @param column     The column number that is currently being played.
     * @param samples    The sound samples associated with each row.
     * @return A sound sample corresponding to all notes currently being played.
     */
    public static double[] matrixToMusic(boolean[][] toneMatrix, int column, double[][] samples) {
        double[] result = new double[ToneMatrixConstants.sampleSize()];

        for (int row = 0; row < toneMatrix.length; row++) {
            // if cell selected
            if (toneMatrix[row][column]) {
                // get sound on specified cell (sum samples)
                for (int i = 0; i < result.length; i++) {
                    result[i] += samples[row][i];
                }
            }
        }

        normalizeSoundWave(result);

        return result;
    }

    /**
     * Normalizes values of the given sound wave within [-1; 1] range, based on the absolute maximum.
     *
     * @param soundWave Sound wave to be normalized.
     */
    private static void normalizeSoundWave(double[] soundWave) {
        double maxValue = 0.0;
        for (double volume : soundWave) {
            maxValue = Math.max(Math.abs(volume), maxValue);
        }

        if (maxValue != 0) {
            for (int i = 0; i < soundWave.length; i++) {
                soundWave[i] /= maxValue;
            }
        }
    }
}

package com.shpp.p2p.cs.kkunieva.assignment13;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Assignment13Part1Test {
    private final PrintStream standardOut = System.out;
    private ByteArrayOutputStream outputStream;

    @AfterEach
    public void restoreSystemOutStream() {
        System.setOut(standardOut);
    }

    @BeforeEach
    public void setUpStreams() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @ParameterizedTest
    @CsvSource({
            "fromdiscord1.png, 3",
            "fromtask.png, 4",
            "1.png, 5",
            "2.png, 36",
            "3.png, 5",
            "4.png, 9",
            "5.png, 5",
            "6.png, 8",
            "7.png, 17",
            "8.png, 10",
            "9.png, 9",
            "10.png, 10",
            "11.png, 30",
            "12.png, 36",
            "13.png, 5",
            "14.png, 77",
            "14.jpg, 77",
            "test.jpg, 4",
            "f26480fd5bf0f81c.jpg, 0",
            "4307c80e949a81b7.jpg, 0",
            "15.png, 5",
            "imgSmall.png, 1",
            "unknown.png, 1",
            "squares.png, 5",
            "red.png, 8",
            "soccer.png, 12",
            "smSquare.png, 1",
            "binarysuperbeerman.jpg, 1",
            "fromTask2.png, 4",
            "fromTask2.bmp, 4",
            "fromTask2.gif, 4",
            "1_4.png, 1",
            "2024-01-14_20.34.28.png, 1",
            "stars.png, 3",
            "1_negate.jpg, 1",
            "4444.jpg, 20",
            "mytest33.png, 3",
            "hairA.jpg, 3",
            "many.jpg, 42",
            "groups.jpg, 31",
            "hair.jpg, 5",
            "gaps.png, 5",
            "fairy.jpg, 16",
            "multicolor.jpg, 24",
            "tx25_7.jpg, 7",
            "tx24_1.jpg, 1",
            "tx21_1.jpg, 1",
            "inv1.jpg, 1",
            "coloredBirds.jpg, 1",
            "unicorn.jpg, 1",
            "unicornGs.png, 1",
            "Screenshot 2026-01-20 132550.png, 29",
            "blank.png, 0",
            "yellowstar.png, 1",
    })
    void test(String imgPath, int expected) {
        Assignment13Part1.main(new String[]{"test/test_pictures/" + imgPath});
        String actual = outputStream.toString().trim();
        assertEquals(expected, Integer.parseInt(actual));
    }

    @Test
    void testNulls() {
        assertThrows(IllegalArgumentException.class, () -> Assignment13Part1.main(null));
        assertThrows(IllegalArgumentException.class, () -> Assignment13Part1.main(new String[]{"null"}));
        assertThrows(IllegalArgumentException.class, () -> Assignment13Part1.main(new String[]{null}));
    }
}
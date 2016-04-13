package com.tallgeorge.engine;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CalculatePiTest {

    private final Logger logger = LoggerFactory.getLogger(CalculatePiTest.class);

    @Test
    public void testBruteForceDouble() throws Exception {
        CalculatePi c = new CalculatePi();
        assertEquals(4.000000000000000, c.bruteForce(1), 1e-16);
        assertEquals(3.1414926535900345, c.bruteForce(10000), 1e-16);
    }

    @Test
    public void testBruteForceBigDecimal() throws Exception {
        int precision = 32;
        String format = "%2." + precision + "f";

        CalculatePi c = new CalculatePi();
        BigDecimal expected = new BigDecimal(4);
        expected.setScale(precision, BigDecimal.ROUND_HALF_UP);
        assertEquals(String.format(format, expected), String.format(format, c.bruteForceBig(1, precision)));

        expected = new BigDecimal("3.14149265359004323845951838337535");
        expected.setScale(32, BigDecimal.ROUND_HALF_UP);
        assertEquals(String.format(format, expected), String.format(format, c.bruteForceBig(10000, precision)));
    }

    @Test
    public void testSpigot() throws Exception {

        // Calculate the first 100 digits of pi.
        int numberOfDigits = 100;
        int[] expected = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8, 9, 7, 9, 3, 2, 3, 8, 4, 6, 2, 6, 4, 3, 3, 8, 3, 2, 7, 9, 5, 0, 2, 8, 8, 4, 1, 9, 7, 1, 6, 9, 3, 9, 9, 3, 7, 5, 1, 0, 5, 8, 2, 0, 9, 7, 4, 9, 4, 4, 5, 9, 2, 3, 0, 7, 8, 1, 6, 4, 0, 6, 2, 8, 6, 2, 0, 8, 9, 9, 8, 6, 2, 8, 0, 3, 4, 8, 2, 5, 3, 4, 2, 1, 1, 7, 0, 6, 7};
        CalculatePi c = new CalculatePi();

        assertArrayEquals(expected, c.spigot(numberOfDigits));

        /*
        int[] result = c.spigot(10000);

        // The 1,000th digit of pi is 8.
        assertEquals(8, result[999]);

        // The 10,000th digit of pi is 7.
        assertEquals(7, result[9999]);
        */
    }
}
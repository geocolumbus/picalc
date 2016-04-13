package com.tallgeorge.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.stream.IntStream;

/**
 * Class for trying various pi calculation methods.
 */
public class CalculatePi {

    private final Logger logger = LoggerFactory.getLogger(CalculatePi.class);

    /**
     * Calculate pi with java double using the brute force method.
     * @param iterations int, the number of iterations.
     * @return double, calculated value of pi.
     */
    public double bruteForce(int iterations) {
        double pi = 0;

        for (int i = 0; i < iterations; i++) {
            pi += i % 2 == 1 ? -4.0 / (i * 2 + 1) : 4.0 / (i * 2 + 1);
        }
        return pi;
    }

    /**
     * Calculate pi with BigDecimal using the brute force method.
     * @param iterations int, the number of iterations.
     * @return BigDecimal, calculated value of pi.
     */
    public BigDecimal bruteForceBig(int iterations, int precision) {

        BigDecimal one = new BigDecimal("1");
        BigDecimal two = new BigDecimal("2");
        BigDecimal four = new BigDecimal("4");

        BigDecimal pi = new BigDecimal("0");
        BigDecimal inc = new BigDecimal("0");
        BigDecimal iBig = new BigDecimal("0");

        one.setScale(precision, BigDecimal.ROUND_HALF_UP);
        two.setScale(precision, BigDecimal.ROUND_HALF_UP);
        four.setScale(precision, BigDecimal.ROUND_HALF_UP);
        pi.setScale(precision, BigDecimal.ROUND_HALF_UP);
        inc.setScale(precision, BigDecimal.ROUND_HALF_UP);
        iBig.setScale(precision, BigDecimal.ROUND_HALF_UP);


        for (int i = 0; i < iterations; i++) {
            iBig = new BigDecimal(i);

            if (i % 2 == 1) {
                inc = four.divide(one.add(two.multiply(iBig)), precision, BigDecimal.ROUND_HALF_UP);
                inc = inc.negate();
            } else {
                inc = four.divide(one.add(two.multiply(iBig)), precision, BigDecimal.ROUND_HALF_UP);
            }
            pi = pi.add(inc);
        }

        return pi;
    }


    /**
     * The Rabinowitz and Wagon spigot algorithm for generating digits of Pi.
     * http://www.jjj.de/hfloat/spigot.haenel.txt
     * <p>
     * To calculate the expected time in msec on a 2014 MacBook Pro:
     * <p>
     * msec = 10^(2.01075*log10(numberOfDigits/1000)+1.57978)
     * <p>
     * DIGITS    TIME
     * ------    ----
     * 10,000     6 sec
     * 100,000    6.6 min
     * 1,000,000  11.3 hours
     * @param numberOfDigits the number of digits to calculate
     * @return an array of digits
     */
    public int[] spigot(int numberOfDigits) {
        int[] digits = new int[numberOfDigits];
        int arrayCounter = 0;
        int chainLength = (10 * numberOfDigits) / 3 + 1;
        int digitCounter, chainCounter, preDigit = 0, nines = 0;
        int[] chain = new int[chainLength];

        for (digitCounter = numberOfDigits; digitCounter != 0; ) {
            int q = 0;
            int x = 0;
            int k = chainLength + chainLength - 1;

            for (chainCounter = chainLength; chainCounter > 0; --chainCounter) {
                x = (digitCounter == numberOfDigits ? 20 : 10 * chain[chainCounter - 1]) + q * chainCounter;
                q = x / k;
                chain[chainCounter - 1] = x - q * k;
                k -= 2;
            }

            k = x % 10;
            if (k == 9) ++nines;

            else {
                --digitCounter;
                digits[arrayCounter++] = preDigit + x / 10;

                for (; nines != 0; --nines) {
                    if (digitCounter != 0) {
                        --digitCounter;
                        digits[arrayCounter++] = x >= 10 ? 0 : 9;
                    }
                }

                preDigit = k;
            }
        }
        return digits;
    }
}

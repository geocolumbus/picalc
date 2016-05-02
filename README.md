# Algorithms for calculating Pi

Three algorithms for calculating Pi.

## Pi, brute force method.

The worst algorithm for calculating Pi:

<pre>
public double bruteForce(int iterations) {
        double pi = 0;

        for (int i = 0; i < iterations; i++) {
            pi += i % 2 == 1 ? -4.0 / (i * 2 + 1) : 4.0 / (i * 2 + 1);
        }
        return pi;
    }
</pre>

## Pi, but force method with bignum.

<pre>
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
</pre>

## The Rabinowitz and Wagon spigot algorithm.

See [http://www.jjj.de/hfloat/spigot.haenel.txt](http://www.jjj.de/hfloat/spigot.haenel.txt)

<pre>
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
        SpigotParams spigotParams = new SpigotParams();

        spigotParams.digits = new int[numberOfDigits];
        spigotParams.arrayCounter = 0;
        spigotParams.preDigit = 0;
        spigotParams.nines = 0;
        spigotParams.digitCounter = 0;
        spigotParams.chainLength = (10 * numberOfDigits) / 3 + 1;
        spigotParams.chain = new int[spigotParams.chainLength];

        for (spigotParams.digitCounter = numberOfDigits; spigotParams.digitCounter != 0; ) {
            calculateDigit(numberOfDigits, spigotParams);
        }

        return spigotParams.digits;
    }


    /**
     * Perform one calculation for a digit.
     * @param numberOfDigits int, the number of digits to be calculated
     * @param spigotParams the calculation parameters that change state for each digit's calculation.
     */
    public void calculateDigit(int numberOfDigits, SpigotParams spigotParams) {
        int q = 0;
        int x = 0;
        int k = spigotParams.chainLength + spigotParams.chainLength - 1;

        for (int chainCounter = spigotParams.chainLength; chainCounter > 0; --chainCounter) {
            x = (spigotParams.digitCounter == numberOfDigits ? 20 : 10 * spigotParams.chain[chainCounter - 1]) + q * chainCounter;
            q = x / k;
            spigotParams.chain[chainCounter - 1] = x - q * k;
            k -= 2;
        }

        k = x % 10;
        if (k == 9) ++spigotParams.nines;

        else {
            --spigotParams.digitCounter;
            spigotParams.digits[spigotParams.arrayCounter++] = spigotParams.preDigit + x / 10;

            for (; spigotParams.nines != 0; --spigotParams.nines) {
                if (spigotParams.digitCounter != 0) {
                    --spigotParams.digitCounter;
                    spigotParams.digits[spigotParams.arrayCounter++] = x >= 10 ? 0 : 9;
                }
            }

            spigotParams.preDigit = k;
        }
    }
</pre>

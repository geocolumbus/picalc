package com.tallgeorge.engine;

import org.junit.Before;
import org.junit.Test;

public class CalculateCellTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testCalculateForce() throws Exception {
        int rows = 10;
        int cols = 10;
        CalculateCell c = new CalculateCell(rows, cols);
        c.forces[0][3].x0 = 2;
        c.forces[0][4].x0 = 2;
        c.forces[0][5].x0 = 2;
        c.forces[0][6].x0 = 2;
        c.forces[0][7].x0 = 2;
        c.solve();
        System.out.println(c);
    }
}
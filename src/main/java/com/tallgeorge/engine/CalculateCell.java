package com.tallgeorge.engine;

/**
 * A particle's position.
 */
class Cell {
    double x;
    double y;

    Cell(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

/**
 * The forces acting on a particle.
 */
class Force {
    double x;
    double y;
    double x0;
    double y0;

    Force(double x, double y) {
        this.x = x;
        this.y = y;
        this.x0 = 0;
        this.y0 = 0;
    }
}

/**
 * A cell force and position calculator.
 */
public class CalculateCell {

    int rows;
    int cols;
    Cell[][] cells;
    Force[][] forces;


    /**
     * Initialize the data structures.
     * @param rows
     * @param cols
     */
    public CalculateCell(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows][cols];
        this.forces = new Force[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cells[row][col] = new Cell(row, col);
                forces[row][col] = new Force(0, 0);
            }
        }
    }

    /**
     * Iterate until a solution is found.
     */
    public void solve() {
        for (int iter = 0; iter < 100000; iter++) {
            calculateForces();
            moveCells();
        }
    }

    /**
     * Move the cells based on the forces
     */
    private void moveCells() {
        for (int row = 0; row < rows-1; row++) {
            for (int col = 1; col < cols-1; col++) {
                cells[row][col].x += (forces[row][col].x + forces[row][col].x0) / 100;
                cells[row][col].y += (forces[row][col].y + forces[row][col].y0) / 100;
            }
        }
    }

    /**
     * Calculate all the forces acting on the grid.
     */
    private void calculateForces() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Force f = calculateCellForce(row, col);
                forces[row][col].x += f.x;
                forces[row][col].y += f.y;
            }
        }
    }

    /**
     * Calculate the forces on a cell
     * @param row integer, the row identifier
     * @param col integer, the column identifier
     * @return Force, the forces acting on the cell
     */
    private Force calculateCellForce(int row, int col) {
        Force force = new Force(0, 0);
        for (int i = 0; i < 8; i++) {
            int row1 = row, col1 = col;
            double d0 = 0;
            double f;

            switch (i) {
                case 0:
                    row1 = row - 1;
                    col1 = col;
                    d0 = 1;
                    break;
                case 1:
                    row1 = row - 1;
                    col1 = col + 1;
                    d0 = Math.sqrt(2);
                    break;
                case 2:
                    row1 = row;
                    col1 = col + 1;
                    d0 = 1;
                    break;
                case 3:
                    row1 = row + 1;
                    col1 = col + 1;
                    d0 = Math.sqrt(2);
                    break;
                case 4:
                    row1 = row + 1;
                    col1 = col;
                    d0 = 1;
                    break;
                case 5:
                    row1 = row + 1;
                    col1 = col - 1;
                    d0 = Math.sqrt(2);
                    break;
                case 6:
                    row1 = row;
                    col1 = col - 1;
                    d0 = 1;
                    break;
                case 7:
                    row1 = row - 1;
                    col1 = col - 1;
                    d0 = Math.sqrt(2);
                    break;
            }
            if (row1 >= 0 && row1 < rows && col1 >= 0 && col1 < cols) {
                double deltaX = cells[row1][col1].x - cells[row][col].x;
                double deltaY = cells[row1][col1].y - cells[row][col].y;
                double magnitude = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
                double unitX = deltaX / magnitude;
                double unitY = deltaY / magnitude;

                f = Math.pow(magnitude - d0, 2);
                if (magnitude < d0) {
                    f = -f;
                }
                force.x += unitX * f;
                force.y += unitY * f;
            }
        }
        return force;
    }

    @Override
    public String toString() {
        String[][] board = new String[rows][cols];

        // Initialize the board.
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] = "   ";
            }
        }

        // Plot asterisks in board sections that contain cells
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = (int) Math.round(cells[row][col].x);
                int y = (int) Math.round(cells[row][col].y);
                if (x < 0 || x >= rows || y < 0 || y >= cols) {
                    continue;
                }
                board[x][y] = "***";
            }
        }

        // Draw the board.
        String output = "";
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                output += board[row][col];
            }
            output += "\n";
        }

        // Output the positions
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                output += String.format("% 4.2f,% 4.2f ", cells[row][col].x, cells[row][col].y);
            }
            output += "\n";
        }
        output += "--------------------\n";
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                output += String.format("% 4.2f,% 4.2f ", forces[row][col].x + forces[row][col].x0, forces[row][col].y + forces[row][col].y0);
            }
            output += "\n";
        }
        // Output the forces

        return output;
    }
}

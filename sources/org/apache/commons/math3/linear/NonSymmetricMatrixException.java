package org.apache.commons.math3.linear;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.d;

public class NonSymmetricMatrixException extends MathIllegalArgumentException {
    private static final long serialVersionUID = -7518495577824189882L;
    private final int column;
    private final int row;
    private final double threshold;

    public NonSymmetricMatrixException(int row2, int column2, double threshold2) {
        super(d.NON_SYMMETRIC_MATRIX, Integer.valueOf(row2), Integer.valueOf(column2), Double.valueOf(threshold2));
        this.row = row2;
        this.column = column2;
        this.threshold = threshold2;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public double getThreshold() {
        return this.threshold;
    }
}

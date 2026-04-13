package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.c;
import org.apache.commons.math3.exception.util.d;

public class DimensionMismatchException extends MathIllegalNumberException {
    private static final long serialVersionUID = -8415396756375798143L;
    private final int dimension;

    public DimensionMismatchException(c specific, int wrong, int expected) {
        super(specific, Integer.valueOf(wrong), Integer.valueOf(expected));
        this.dimension = expected;
    }

    public DimensionMismatchException(int wrong, int expected) {
        this(d.DIMENSIONS_MISMATCH_SIMPLE, wrong, expected);
    }

    public int getDimension() {
        return this.dimension;
    }
}

package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.c;
import org.apache.commons.math3.exception.util.d;

public class MultiDimensionMismatchException extends MathIllegalArgumentException {
    private static final long serialVersionUID = -8415396756375798143L;
    private final Integer[] expected;
    private final Integer[] wrong;

    public MultiDimensionMismatchException(Integer[] wrong2, Integer[] expected2) {
        this(d.DIMENSIONS_MISMATCH, wrong2, expected2);
    }

    public MultiDimensionMismatchException(c specific, Integer[] wrong2, Integer[] expected2) {
        super(specific, wrong2, expected2);
        this.wrong = (Integer[]) wrong2.clone();
        this.expected = (Integer[]) expected2.clone();
    }

    public Integer[] getWrongDimensions() {
        return (Integer[]) this.wrong.clone();
    }

    public Integer[] getExpectedDimensions() {
        return (Integer[]) this.expected.clone();
    }

    public int getWrongDimension(int index) {
        return this.wrong[index].intValue();
    }

    public int getExpectedDimension(int index) {
        return this.expected[index].intValue();
    }
}

package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.c;
import org.apache.commons.math3.exception.util.d;

public class OutOfRangeException extends MathIllegalNumberException {
    private static final long serialVersionUID = 111601815794403609L;
    private final Number hi;
    private final Number lo;

    public OutOfRangeException(Number wrong, Number lo2, Number hi2) {
        this(d.OUT_OF_RANGE_SIMPLE, wrong, lo2, hi2);
    }

    public OutOfRangeException(c specific, Number wrong, Number lo2, Number hi2) {
        super(specific, wrong, lo2, hi2);
        this.lo = lo2;
        this.hi = hi2;
    }

    public Number getLo() {
        return this.lo;
    }

    public Number getHi() {
        return this.hi;
    }
}

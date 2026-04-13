package org.apache.commons.math3.fraction;

import org.apache.commons.math3.exception.ConvergenceException;
import org.apache.commons.math3.exception.util.d;

public class FractionConversionException extends ConvergenceException {
    private static final long serialVersionUID = -4661812640132576263L;

    public FractionConversionException(double value, int maxIterations) {
        super(d.FAILED_FRACTION_CONVERSION, Double.valueOf(value), Integer.valueOf(maxIterations));
    }

    public FractionConversionException(double value, long p, long q) {
        super(d.FRACTION_CONVERSION_OVERFLOW, Double.valueOf(value), Long.valueOf(p), Long.valueOf(q));
    }
}

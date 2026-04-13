package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.c;

public class NotPositiveException extends NumberIsTooSmallException {
    private static final long serialVersionUID = -2250556892093726375L;

    public NotPositiveException(Number value) {
        super(value, MathIllegalNumberException.INTEGER_ZERO, true);
    }

    public NotPositiveException(c specific, Number value) {
        super(specific, value, MathIllegalNumberException.INTEGER_ZERO, true);
    }
}

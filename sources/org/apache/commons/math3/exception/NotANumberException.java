package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.d;

public class NotANumberException extends MathIllegalNumberException {
    private static final long serialVersionUID = 20120906;

    public NotANumberException() {
        super(d.NAN_NOT_ALLOWED, Double.valueOf(Double.NaN), new Object[0]);
    }
}

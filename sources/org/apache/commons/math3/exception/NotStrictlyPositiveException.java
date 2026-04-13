package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.c;

public class NotStrictlyPositiveException extends NumberIsTooSmallException {
    private static final long serialVersionUID = -7824848630829852237L;

    public NotStrictlyPositiveException(Number value) {
        super(value, MathIllegalNumberException.INTEGER_ZERO, false);
    }

    public NotStrictlyPositiveException(c specific, Number value) {
        super(specific, value, MathIllegalNumberException.INTEGER_ZERO, false);
    }
}

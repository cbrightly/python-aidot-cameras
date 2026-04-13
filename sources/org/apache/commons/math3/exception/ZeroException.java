package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.c;
import org.apache.commons.math3.exception.util.d;

public class ZeroException extends MathIllegalNumberException {
    private static final long serialVersionUID = -1960874856936000015L;

    public ZeroException() {
        this(d.ZERO_NOT_ALLOWED, new Object[0]);
    }

    public ZeroException(c specific, Object... arguments) {
        super(specific, MathIllegalNumberException.INTEGER_ZERO, arguments);
    }
}

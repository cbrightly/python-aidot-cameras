package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.c;
import org.apache.commons.math3.exception.util.d;

public class InsufficientDataException extends MathIllegalArgumentException {
    private static final long serialVersionUID = -2629324471511903359L;

    public InsufficientDataException() {
        this(d.INSUFFICIENT_DATA, new Object[0]);
    }

    public InsufficientDataException(c pattern, Object... arguments) {
        super(pattern, arguments);
    }
}

package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.c;
import org.apache.commons.math3.exception.util.d;

public class NullArgumentException extends MathIllegalArgumentException {
    private static final long serialVersionUID = -6024911025449780478L;

    public NullArgumentException() {
        this(d.NULL_NOT_ALLOWED, new Object[0]);
    }

    public NullArgumentException(c pattern, Object... arguments) {
        super(pattern, arguments);
    }
}

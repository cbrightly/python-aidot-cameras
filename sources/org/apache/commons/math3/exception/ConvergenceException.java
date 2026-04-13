package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.c;
import org.apache.commons.math3.exception.util.d;

public class ConvergenceException extends MathIllegalStateException {
    private static final long serialVersionUID = 4330003017885151975L;

    public ConvergenceException() {
        this(d.CONVERGENCE_FAILED, new Object[0]);
    }

    public ConvergenceException(c pattern, Object... args) {
        getContext().addMessage(pattern, args);
    }
}

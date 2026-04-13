package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.c;
import org.apache.commons.math3.exception.util.d;

public class NotFiniteNumberException extends MathIllegalNumberException {
    private static final long serialVersionUID = -6100997100383932834L;

    public NotFiniteNumberException(Number wrong, Object... args) {
        this(d.NOT_FINITE_NUMBER, wrong, args);
    }

    public NotFiniteNumberException(c specific, Number wrong, Object... args) {
        super(specific, wrong, args);
    }
}

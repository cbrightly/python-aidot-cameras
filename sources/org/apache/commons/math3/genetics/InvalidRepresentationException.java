package org.apache.commons.math3.genetics;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.c;

public class InvalidRepresentationException extends MathIllegalArgumentException {
    private static final long serialVersionUID = 1;

    public InvalidRepresentationException(c pattern, Object... args) {
        super(pattern, args);
    }
}

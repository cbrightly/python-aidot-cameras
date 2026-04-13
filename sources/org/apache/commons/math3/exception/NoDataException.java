package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.c;
import org.apache.commons.math3.exception.util.d;

public class NoDataException extends MathIllegalArgumentException {
    private static final long serialVersionUID = -3629324471511904459L;

    public NoDataException() {
        this(d.NO_DATA);
    }

    public NoDataException(c specific) {
        super(specific, new Object[0]);
    }
}

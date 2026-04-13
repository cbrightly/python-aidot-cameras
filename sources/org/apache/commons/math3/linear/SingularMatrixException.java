package org.apache.commons.math3.linear;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.d;

public class SingularMatrixException extends MathIllegalArgumentException {
    private static final long serialVersionUID = -4206514844735401070L;

    public SingularMatrixException() {
        super(d.SINGULAR_MATRIX, new Object[0]);
    }
}

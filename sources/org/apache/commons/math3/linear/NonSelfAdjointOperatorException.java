package org.apache.commons.math3.linear;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.d;

public class NonSelfAdjointOperatorException extends MathIllegalArgumentException {
    private static final long serialVersionUID = 1784999305030258247L;

    public NonSelfAdjointOperatorException() {
        super(d.NON_SELF_ADJOINT_OPERATOR, new Object[0]);
    }
}

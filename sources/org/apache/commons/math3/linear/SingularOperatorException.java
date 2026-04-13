package org.apache.commons.math3.linear;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.d;

public class SingularOperatorException extends MathIllegalArgumentException {
    private static final long serialVersionUID = -476049978595245033L;

    public SingularOperatorException() {
        super(d.SINGULAR_OPERATOR, new Object[0]);
    }
}

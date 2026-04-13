package org.apache.commons.math3.linear;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.d;

public class NonPositiveDefiniteOperatorException extends MathIllegalArgumentException {
    private static final long serialVersionUID = 917034489420549847L;

    public NonPositiveDefiniteOperatorException() {
        super(d.NON_POSITIVE_DEFINITE_OPERATOR, new Object[0]);
    }
}

package org.apache.commons.math3.ode;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.d;

public class JacobianMatrices$MismatchedEquations extends MathIllegalArgumentException {
    private static final long serialVersionUID = 20120902;

    public JacobianMatrices$MismatchedEquations() {
        super(d.UNMATCHED_ODE_IN_EXPANDED_SET, new Object[0]);
    }
}

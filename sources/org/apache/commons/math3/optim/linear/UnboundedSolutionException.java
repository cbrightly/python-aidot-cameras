package org.apache.commons.math3.optim.linear;

import org.apache.commons.math3.exception.MathIllegalStateException;
import org.apache.commons.math3.exception.util.d;

public class UnboundedSolutionException extends MathIllegalStateException {
    private static final long serialVersionUID = 940539497277290619L;

    public UnboundedSolutionException() {
        super(d.UNBOUNDED_SOLUTION, new Object[0]);
    }
}

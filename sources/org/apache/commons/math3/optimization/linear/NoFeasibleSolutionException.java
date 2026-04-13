package org.apache.commons.math3.optimization.linear;

import org.apache.commons.math3.exception.MathIllegalStateException;
import org.apache.commons.math3.exception.util.d;

@Deprecated
public class NoFeasibleSolutionException extends MathIllegalStateException {
    private static final long serialVersionUID = -3044253632189082760L;

    public NoFeasibleSolutionException() {
        super(d.NO_FEASIBLE_SOLUTION, new Object[0]);
    }
}

package org.apache.commons.math3.linear;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.util.d;

public class NonSquareOperatorException extends DimensionMismatchException {
    private static final long serialVersionUID = -4145007524150846242L;

    public NonSquareOperatorException(int wrong, int expected) {
        super(d.NON_SQUARE_OPERATOR, wrong, expected);
    }
}

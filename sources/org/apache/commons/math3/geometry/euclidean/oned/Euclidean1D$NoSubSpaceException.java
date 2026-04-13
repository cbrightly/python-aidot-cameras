package org.apache.commons.math3.geometry.euclidean.oned;

import org.apache.commons.math3.exception.MathUnsupportedOperationException;
import org.apache.commons.math3.exception.util.d;

public class Euclidean1D$NoSubSpaceException extends MathUnsupportedOperationException {
    private static final long serialVersionUID = 20140225;

    public Euclidean1D$NoSubSpaceException() {
        super(d.NOT_SUPPORTED_IN_DIMENSION_N, 1);
    }
}

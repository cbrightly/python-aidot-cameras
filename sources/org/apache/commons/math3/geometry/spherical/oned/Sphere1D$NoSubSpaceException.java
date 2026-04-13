package org.apache.commons.math3.geometry.spherical.oned;

import org.apache.commons.math3.exception.MathUnsupportedOperationException;
import org.apache.commons.math3.exception.util.d;

public class Sphere1D$NoSubSpaceException extends MathUnsupportedOperationException {
    private static final long serialVersionUID = 20140225;

    public Sphere1D$NoSubSpaceException() {
        super(d.NOT_SUPPORTED_IN_DIMENSION_N, 1);
    }
}

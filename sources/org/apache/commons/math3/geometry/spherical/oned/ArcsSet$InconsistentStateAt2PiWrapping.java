package org.apache.commons.math3.geometry.spherical.oned;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.d;

public class ArcsSet$InconsistentStateAt2PiWrapping extends MathIllegalArgumentException {
    private static final long serialVersionUID = 20140107;

    public ArcsSet$InconsistentStateAt2PiWrapping() {
        super(d.INCONSISTENT_STATE_AT_2_PI_WRAPPING, new Object[0]);
    }
}

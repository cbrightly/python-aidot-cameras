package org.apache.commons.math3.geometry.euclidean.threed;

import org.apache.commons.math3.exception.MathIllegalStateException;
import org.apache.commons.math3.exception.util.d;

public class CardanEulerSingularityException extends MathIllegalStateException {
    private static final long serialVersionUID = -1360952845582206770L;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CardanEulerSingularityException(boolean isCardan) {
        super(isCardan ? d.CARDAN_ANGLES_SINGULARITY : d.EULER_ANGLES_SINGULARITY, new Object[0]);
    }
}

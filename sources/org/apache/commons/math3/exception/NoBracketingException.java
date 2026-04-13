package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.c;
import org.apache.commons.math3.exception.util.d;

public class NoBracketingException extends MathIllegalArgumentException {
    private static final long serialVersionUID = -3629324471511904459L;
    private final double fHi;
    private final double fLo;
    private final double hi;
    private final double lo;

    public NoBracketingException(double lo2, double hi2, double fLo2, double fHi2) {
        this(d.SAME_SIGN_AT_ENDPOINTS, lo2, hi2, fLo2, fHi2, new Object[0]);
    }

    public NoBracketingException(c specific, double lo2, double hi2, double fLo2, double fHi2, Object... args) {
        super(specific, Double.valueOf(lo2), Double.valueOf(hi2), Double.valueOf(fLo2), Double.valueOf(fHi2), args);
        this.lo = lo2;
        this.hi = hi2;
        this.fLo = fLo2;
        this.fHi = fHi2;
    }

    public double getLo() {
        return this.lo;
    }

    public double getHi() {
        return this.hi;
    }

    public double getFLo() {
        return this.fLo;
    }

    public double getFHi() {
        return this.fHi;
    }
}

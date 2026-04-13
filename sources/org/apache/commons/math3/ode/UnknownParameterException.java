package org.apache.commons.math3.ode;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.d;

public class UnknownParameterException extends MathIllegalArgumentException {
    private static final long serialVersionUID = 20120902;
    private final String name;

    public UnknownParameterException(String name2) {
        super(d.UNKNOWN_PARAMETER, name2);
        this.name = name2;
    }

    public String getName() {
        return this.name;
    }
}

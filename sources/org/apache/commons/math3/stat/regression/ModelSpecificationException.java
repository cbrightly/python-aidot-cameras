package org.apache.commons.math3.stat.regression;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.c;

public class ModelSpecificationException extends MathIllegalArgumentException {
    private static final long serialVersionUID = 4206514456095401070L;

    public ModelSpecificationException(c pattern, Object... args) {
        super(pattern, args);
    }
}

package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.d;

public class TooManyIterationsException extends MaxCountExceededException {
    private static final long serialVersionUID = 20121211;

    public TooManyIterationsException(Number max) {
        super(max);
        getContext().addMessage(d.ITERATIONS, new Object[0]);
    }
}

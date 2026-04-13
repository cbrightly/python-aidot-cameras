package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.d;

public class TooManyEvaluationsException extends MaxCountExceededException {
    private static final long serialVersionUID = 4330003017885151975L;

    public TooManyEvaluationsException(Number max) {
        super(max);
        getContext().addMessage(d.EVALUATIONS, new Object[0]);
    }
}

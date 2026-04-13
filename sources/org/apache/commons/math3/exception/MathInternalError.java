package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.c;
import org.apache.commons.math3.exception.util.d;

public class MathInternalError extends MathIllegalStateException {
    private static final long serialVersionUID = -6276776513966934846L;

    public MathInternalError() {
        getContext().addMessage(d.INTERNAL_ERROR, "https://issues.apache.org/jira/browse/MATH");
    }

    public MathInternalError(Throwable cause) {
        super(cause, d.INTERNAL_ERROR, "https://issues.apache.org/jira/browse/MATH");
    }

    public MathInternalError(c pattern, Object... args) {
        super(pattern, args);
    }
}

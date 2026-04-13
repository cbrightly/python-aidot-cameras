package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.b;
import org.apache.commons.math3.exception.util.c;

public class MathRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 20120926;
    private final b context;

    public MathRuntimeException(c pattern, Object... args) {
        b bVar = new b(this);
        this.context = bVar;
        bVar.addMessage(pattern, args);
    }

    public b getContext() {
        return this.context;
    }

    public String getMessage() {
        return this.context.getMessage();
    }

    public String getLocalizedMessage() {
        return this.context.getLocalizedMessage();
    }
}

package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.b;
import org.apache.commons.math3.exception.util.c;
import org.apache.commons.math3.exception.util.d;

public class MathIllegalStateException extends IllegalStateException {
    private static final long serialVersionUID = -6024911025449780478L;
    private final b context;

    public MathIllegalStateException(c pattern, Object... args) {
        b bVar = new b(this);
        this.context = bVar;
        bVar.addMessage(pattern, args);
    }

    public MathIllegalStateException(Throwable cause, c pattern, Object... args) {
        super(cause);
        b bVar = new b(this);
        this.context = bVar;
        bVar.addMessage(pattern, args);
    }

    public MathIllegalStateException() {
        this(d.ILLEGAL_STATE, new Object[0]);
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

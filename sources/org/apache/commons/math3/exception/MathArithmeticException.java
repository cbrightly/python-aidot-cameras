package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.b;
import org.apache.commons.math3.exception.util.c;
import org.apache.commons.math3.exception.util.d;

public class MathArithmeticException extends ArithmeticException {
    private static final long serialVersionUID = -6024911025449780478L;
    private final b context;

    public MathArithmeticException() {
        b bVar = new b(this);
        this.context = bVar;
        bVar.addMessage(d.ARITHMETIC_EXCEPTION, new Object[0]);
    }

    public MathArithmeticException(c pattern, Object... args) {
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

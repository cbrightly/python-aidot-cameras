package org.apache.commons.lang3.concurrent;

public class ConcurrentRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -6582182735562919670L;

    protected ConcurrentRuntimeException() {
    }

    public ConcurrentRuntimeException(Throwable cause) {
        super(a.a(cause));
    }

    public ConcurrentRuntimeException(String msg, Throwable cause) {
        super(msg, a.a(cause));
    }
}

package io.netty.util.internal.logging;

public final class FormattingTuple {
    private final String message;
    private final Throwable throwable;

    FormattingTuple(String message2, Throwable throwable2) {
        this.message = message2;
        this.throwable = throwable2;
    }

    public String getMessage() {
        return this.message;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }
}

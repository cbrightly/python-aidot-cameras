package io.netty.handler.ssl;

import io.netty.util.internal.ObjectUtil;

public abstract class SslCompletionEvent {
    private final Throwable cause;

    SslCompletionEvent() {
        this.cause = null;
    }

    SslCompletionEvent(Throwable cause2) {
        this.cause = (Throwable) ObjectUtil.checkNotNull(cause2, "cause");
    }

    public final boolean isSuccess() {
        return this.cause == null;
    }

    public final Throwable cause() {
        return this.cause;
    }

    public final String toString() {
        StringBuilder sb;
        Throwable cause2 = cause();
        if (cause2 == null) {
            sb.append(getClass().getSimpleName());
            sb.append("(SUCCESS)");
            return sb.toString();
        }
        sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append('(');
        sb.append(cause2);
        sb.append(')');
        return sb.toString();
    }
}

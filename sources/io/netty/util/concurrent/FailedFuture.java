package io.netty.util.concurrent;

import io.netty.util.internal.PlatformDependent;

public final class FailedFuture<V> extends CompleteFuture<V> {
    private final Throwable cause;

    public FailedFuture(EventExecutor executor, Throwable cause2) {
        super(executor);
        if (cause2 != null) {
            this.cause = cause2;
            return;
        }
        throw new NullPointerException("cause");
    }

    public Throwable cause() {
        return this.cause;
    }

    public boolean isSuccess() {
        return false;
    }

    public Future<V> sync() {
        PlatformDependent.throwException(this.cause);
        return this;
    }

    public Future<V> syncUninterruptibly() {
        PlatformDependent.throwException(this.cause);
        return this;
    }

    public V getNow() {
        return null;
    }
}

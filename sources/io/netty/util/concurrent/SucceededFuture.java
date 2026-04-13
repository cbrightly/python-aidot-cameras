package io.netty.util.concurrent;

public final class SucceededFuture<V> extends CompleteFuture<V> {
    private final V result;

    public SucceededFuture(EventExecutor executor, V result2) {
        super(executor);
        this.result = result2;
    }

    public Throwable cause() {
        return null;
    }

    public boolean isSuccess() {
        return true;
    }

    public V getNow() {
        return this.result;
    }
}

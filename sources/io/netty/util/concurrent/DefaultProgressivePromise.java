package io.netty.util.concurrent;

public class DefaultProgressivePromise<V> extends DefaultPromise<V> implements ProgressivePromise<V> {
    public DefaultProgressivePromise(EventExecutor executor) {
        super(executor);
    }

    protected DefaultProgressivePromise() {
    }

    public ProgressivePromise<V> setProgress(long progress, long total) {
        if (total < 0) {
            total = -1;
            if (progress < 0) {
                throw new IllegalArgumentException("progress: " + progress + " (expected: >= 0)");
            }
        } else if (progress < 0 || progress > total) {
            throw new IllegalArgumentException("progress: " + progress + " (expected: 0 <= progress <= total (" + total + "))");
        }
        if (!isDone()) {
            notifyProgressiveListeners(progress, total);
            return this;
        }
        throw new IllegalStateException("complete already");
    }

    public boolean tryProgress(long progress, long total) {
        if (total < 0) {
            total = -1;
            if (progress < 0 || isDone()) {
                return false;
            }
        } else if (progress < 0 || progress > total || isDone()) {
            return false;
        }
        notifyProgressiveListeners(progress, total);
        return true;
    }

    public ProgressivePromise<V> addListener(GenericFutureListener<? extends Future<? super V>> listener) {
        super.addListener((GenericFutureListener) listener);
        return this;
    }

    public ProgressivePromise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
        super.addListeners((GenericFutureListener[]) listeners);
        return this;
    }

    public ProgressivePromise<V> removeListener(GenericFutureListener<? extends Future<? super V>> listener) {
        super.removeListener((GenericFutureListener) listener);
        return this;
    }

    public ProgressivePromise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
        super.removeListeners((GenericFutureListener[]) listeners);
        return this;
    }

    public ProgressivePromise<V> sync() {
        super.sync();
        return this;
    }

    public ProgressivePromise<V> syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    public ProgressivePromise<V> await() {
        super.await();
        return this;
    }

    public ProgressivePromise<V> awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }

    public ProgressivePromise<V> setSuccess(V result) {
        super.setSuccess(result);
        return this;
    }

    public ProgressivePromise<V> setFailure(Throwable cause) {
        super.setFailure(cause);
        return this;
    }
}

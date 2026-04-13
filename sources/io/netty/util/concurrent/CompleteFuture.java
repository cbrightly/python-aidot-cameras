package io.netty.util.concurrent;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.concurrent.TimeUnit;

public abstract class CompleteFuture<V> extends AbstractFuture<V> {
    private final EventExecutor executor;

    protected CompleteFuture(EventExecutor executor2) {
        this.executor = executor2;
    }

    /* access modifiers changed from: protected */
    public EventExecutor executor() {
        return this.executor;
    }

    public Future<V> addListener(GenericFutureListener<? extends Future<? super V>> listener) {
        if (listener != null) {
            DefaultPromise.notifyListener(executor(), this, listener);
            return this;
        }
        throw new NullPointerException(ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
    }

    public Future<V> addListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
        if (listeners != null) {
            for (GenericFutureListener<? extends Future<? super V>> l : listeners) {
                if (l == null) {
                    break;
                }
                DefaultPromise.notifyListener(executor(), this, l);
            }
            return this;
        }
        throw new NullPointerException("listeners");
    }

    public Future<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        return this;
    }

    public Future<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr) {
        return this;
    }

    public Future<V> await() {
        if (!Thread.interrupted()) {
            return this;
        }
        throw new InterruptedException();
    }

    public boolean await(long timeout, TimeUnit unit) {
        if (!Thread.interrupted()) {
            return true;
        }
        throw new InterruptedException();
    }

    public Future<V> sync() {
        return this;
    }

    public Future<V> syncUninterruptibly() {
        return this;
    }

    public boolean await(long timeoutMillis) {
        if (!Thread.interrupted()) {
            return true;
        }
        throw new InterruptedException();
    }

    public Future<V> awaitUninterruptibly() {
        return this;
    }

    public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
        return true;
    }

    public boolean awaitUninterruptibly(long timeoutMillis) {
        return true;
    }

    public boolean isDone() {
        return true;
    }

    public boolean isCancellable() {
        return false;
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }
}

package io.reactivex.internal.schedulers;

import io.reactivex.disposables.b;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: AbstractDirectTask */
public abstract class a extends AtomicReference<Future<?>> implements b {
    protected static final FutureTask<Void> DISPOSED;
    protected static final FutureTask<Void> FINISHED;
    private static final long serialVersionUID = 1811839108042568751L;
    protected final Runnable runnable;
    protected Thread runner;

    static {
        Runnable runnable2 = io.reactivex.internal.functions.a.b;
        FINISHED = new FutureTask<>(runnable2, (Object) null);
        DISPOSED = new FutureTask<>(runnable2, (Object) null);
    }

    a(Runnable runnable2) {
        this.runnable = runnable2;
    }

    public final void dispose() {
        Future<?> future;
        Future<?> f = (Future) get();
        if (f != FINISHED && f != (future = DISPOSED) && compareAndSet(f, future) && f != null) {
            f.cancel(this.runner != Thread.currentThread());
        }
    }

    public final boolean isDisposed() {
        Future<?> f = (Future) get();
        return f == FINISHED || f == DISPOSED;
    }

    public final void setFuture(Future<?> future) {
        Future<?> f;
        do {
            f = (Future) get();
            if (f != FINISHED) {
                if (f == DISPOSED) {
                    future.cancel(this.runner != Thread.currentThread());
                    return;
                }
            } else {
                return;
            }
        } while (!compareAndSet(f, future));
    }

    public Runnable getWrappedRunnable() {
        return this.runnable;
    }
}

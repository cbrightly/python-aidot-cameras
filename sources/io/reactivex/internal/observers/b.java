package io.reactivex.internal.observers;

import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: BasicIntQueueDisposable */
public abstract class b<T> extends AtomicInteger implements io.reactivex.internal.fuseable.b<T> {
    private static final long serialVersionUID = -1001730202384742097L;

    public abstract /* synthetic */ void clear();

    public abstract /* synthetic */ void dispose();

    public abstract /* synthetic */ boolean isDisposed();

    public abstract /* synthetic */ boolean isEmpty();

    public abstract /* synthetic */ T poll();

    public abstract /* synthetic */ int requestFusion(int i);

    public final boolean offer(T t) {
        throw new UnsupportedOperationException("Should not be called");
    }

    public final boolean offer(T t, T t2) {
        throw new UnsupportedOperationException("Should not be called");
    }
}

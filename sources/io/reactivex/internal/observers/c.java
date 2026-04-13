package io.reactivex.internal.observers;

import io.reactivex.internal.fuseable.b;

/* compiled from: BasicQueueDisposable */
public abstract class c<T> implements b<T> {
    public final boolean offer(T t) {
        throw new UnsupportedOperationException("Should not be called");
    }
}

package io.reactivex.disposables;

import io.reactivex.internal.functions.b;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ReferenceDisposable */
public abstract class d<T> extends AtomicReference<T> implements b {
    private static final long serialVersionUID = 6537757548749041217L;

    /* access modifiers changed from: protected */
    public abstract void onDisposed(T t);

    d(T value) {
        super(b.e(value, "value is null"));
    }

    public final void dispose() {
        T value;
        if (get() != null && (value = getAndSet((Object) null)) != null) {
            onDisposed(value);
        }
    }

    public final boolean isDisposed() {
        return get() == null;
    }
}

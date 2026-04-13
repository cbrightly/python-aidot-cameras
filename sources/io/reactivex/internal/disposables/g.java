package io.reactivex.internal.disposables;

import io.reactivex.disposables.b;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: SequentialDisposable */
public final class g extends AtomicReference<b> implements b {
    private static final long serialVersionUID = -754898800686245608L;

    public g() {
    }

    public g(b initial) {
        lazySet(initial);
    }

    public boolean update(b next) {
        return c.set(this, next);
    }

    public boolean replace(b next) {
        return c.replace(this, next);
    }

    public void dispose() {
        c.dispose(this);
    }

    public boolean isDisposed() {
        return c.isDisposed((b) get());
    }
}

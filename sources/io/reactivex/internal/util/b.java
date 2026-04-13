package io.reactivex.internal.util;

import java.util.concurrent.atomic.AtomicReference;

/* compiled from: AtomicThrowable */
public final class b extends AtomicReference<Throwable> {
    private static final long serialVersionUID = 3949248817947090603L;

    public boolean addThrowable(Throwable t) {
        return f.a(this, t);
    }

    public Throwable terminate() {
        return f.b(this);
    }

    public boolean isTerminated() {
        return get() == f.a;
    }
}

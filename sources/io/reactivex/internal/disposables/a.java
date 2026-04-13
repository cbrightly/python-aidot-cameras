package io.reactivex.internal.disposables;

import io.reactivex.disposables.b;
import io.reactivex.functions.d;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: CancellableDisposable */
public final class a extends AtomicReference<d> implements b {
    private static final long serialVersionUID = 5718521705281392066L;

    public a(d cancellable) {
        super(cancellable);
    }

    public boolean isDisposed() {
        return get() == null;
    }

    public void dispose() {
        d c;
        if (get() != null && (c = (d) getAndSet((Object) null)) != null) {
            try {
                c.cancel();
            } catch (Exception ex) {
                io.reactivex.exceptions.a.b(ex);
                io.reactivex.plugins.a.q(ex);
            }
        }
    }
}

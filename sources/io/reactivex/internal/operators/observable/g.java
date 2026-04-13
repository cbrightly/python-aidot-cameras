package io.reactivex.internal.operators.observable;

import io.reactivex.internal.disposables.d;
import io.reactivex.internal.fuseable.e;
import io.reactivex.l;
import io.reactivex.q;

/* compiled from: ObservableEmpty */
public final class g extends l<Object> implements e<Object> {
    public static final l<Object> c = new g();

    private g() {
    }

    /* access modifiers changed from: protected */
    public void a0(q<? super Object> o) {
        d.complete((q<?>) o);
    }

    public Object call() {
        return null;
    }
}

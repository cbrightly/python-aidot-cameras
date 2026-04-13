package io.reactivex.internal.operators.observable;

import io.reactivex.l;
import io.reactivex.o;
import io.reactivex.q;

/* compiled from: ObservableFromUnsafeSource */
public final class m<T> extends l<T> {
    final o<T> c;

    public m(o<T> source) {
        this.c = source;
    }

    /* access modifiers changed from: protected */
    public void a0(q<? super T> observer) {
        this.c.a(observer);
    }
}

package io.reactivex.internal.operators.observable;

import io.reactivex.o;
import io.reactivex.s;

/* compiled from: ObservableSingleSingle */
public final class d0<T> extends s<T> {
    final o<? extends T> a;
    final T b;

    public d0(o<? extends T> source, T defaultValue) {
        this.a = source;
        this.b = defaultValue;
    }
}

package io.reactivex.internal.operators.observable;

import io.reactivex.l;
import io.reactivex.o;

/* compiled from: AbstractObservableWithUpstream */
public abstract class a<T, U> extends l<U> {
    protected final o<T> c;

    a(o<T> source) {
        this.c = source;
    }
}

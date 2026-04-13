package io.reactivex.internal.operators.flowable;

import io.reactivex.e;
import io.reactivex.internal.functions.b;

/* compiled from: AbstractFlowableWithUpstream */
public abstract class a<T, R> extends e<R> {
    protected final e<T> d;

    a(e<T> source) {
        this.d = (e) b.e(source, "source is null");
    }
}

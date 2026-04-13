package io.reactivex.internal.operators.flowable;

import io.reactivex.e;
import io.reactivex.internal.subscriptions.d;
import org.reactivestreams.b;

/* compiled from: FlowableJust */
public final class o<T> extends e<T> implements io.reactivex.internal.fuseable.e<T> {
    private final T d;

    public o(T value) {
        this.d = value;
    }

    /* access modifiers changed from: protected */
    public void L(b<? super T> s) {
        s.onSubscribe(new d(s, this.d));
    }

    public T call() {
        return this.d;
    }
}

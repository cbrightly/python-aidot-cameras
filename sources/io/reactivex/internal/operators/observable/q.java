package io.reactivex.internal.operators.observable;

import io.reactivex.internal.fuseable.e;
import io.reactivex.internal.operators.observable.ObservableScalarXMap;
import io.reactivex.internal.operators.observable.b0;
import io.reactivex.l;

/* compiled from: ObservableJust */
public final class q<T> extends l<T> implements e<T> {
    private final T c;

    public q(T value) {
        this.c = value;
    }

    /* access modifiers changed from: protected */
    public void a0(io.reactivex.q<? super T> observer) {
        ObservableScalarXMap.ScalarDisposable<T> sd = new b0.a<>(observer, this.c);
        observer.onSubscribe(sd);
        sd.run();
    }

    public T call() {
        return this.c;
    }
}

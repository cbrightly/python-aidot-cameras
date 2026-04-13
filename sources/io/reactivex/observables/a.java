package io.reactivex.observables;

import io.reactivex.disposables.b;
import io.reactivex.functions.e;
import io.reactivex.internal.operators.observable.w;
import io.reactivex.internal.operators.observable.x;
import io.reactivex.internal.operators.observable.y;
import io.reactivex.l;

/* compiled from: ConnectableObservable */
public abstract class a<T> extends l<T> {
    public abstract void n0(e<? super b> eVar);

    private a<T> o0() {
        if (this instanceof x) {
            return io.reactivex.plugins.a.o(new w(((x) this).b()));
        }
        return this;
    }

    public l<T> p0() {
        return io.reactivex.plugins.a.m(new y(o0()));
    }
}

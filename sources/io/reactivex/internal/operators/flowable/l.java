package io.reactivex.internal.operators.flowable;

import io.reactivex.e;
import org.reactivestreams.a;
import org.reactivestreams.b;

/* compiled from: FlowableFromPublisher */
public final class l<T> extends e<T> {
    final a<? extends T> d;

    public l(a<? extends T> publisher) {
        this.d = publisher;
    }

    /* access modifiers changed from: protected */
    public void L(b<? super T> s) {
        this.d.a(s);
    }
}

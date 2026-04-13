package io.reactivex.internal.operators.flowable;

import io.reactivex.e;
import io.reactivex.internal.subscriptions.c;
import org.reactivestreams.b;

/* compiled from: FlowableEmpty */
public final class g extends e<Object> implements io.reactivex.internal.fuseable.e<Object> {
    public static final e<Object> d = new g();

    private g() {
    }

    public void L(b<? super Object> s) {
        c.complete(s);
    }

    public Object call() {
        return null;
    }
}

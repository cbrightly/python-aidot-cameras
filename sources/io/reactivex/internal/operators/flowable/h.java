package io.reactivex.internal.operators.flowable;

import io.reactivex.e;
import io.reactivex.exceptions.a;
import io.reactivex.internal.subscriptions.c;
import java.util.concurrent.Callable;
import org.reactivestreams.b;

/* compiled from: FlowableError */
public final class h<T> extends e<T> {
    final Callable<? extends Throwable> d;

    public h(Callable<? extends Throwable> errorSupplier) {
        this.d = errorSupplier;
    }

    public void L(b<? super T> s) {
        try {
            t = (Throwable) io.reactivex.internal.functions.b.e(this.d.call(), "Callable returned null throwable. Null values are generally not allowed in 2.x operators and sources.");
        } catch (Throwable th) {
            t = th;
            a.b(t);
            Throwable th2 = t;
        }
        c.error(t, s);
    }
}

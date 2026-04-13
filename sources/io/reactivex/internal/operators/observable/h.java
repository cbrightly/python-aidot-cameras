package io.reactivex.internal.operators.observable;

import io.reactivex.exceptions.a;
import io.reactivex.internal.disposables.d;
import io.reactivex.internal.functions.b;
import io.reactivex.l;
import io.reactivex.q;
import java.util.concurrent.Callable;

/* compiled from: ObservableError */
public final class h<T> extends l<T> {
    final Callable<? extends Throwable> c;

    public h(Callable<? extends Throwable> errorSupplier) {
        this.c = errorSupplier;
    }

    public void a0(q<? super T> observer) {
        try {
            t = (Throwable) b.e(this.c.call(), "Callable returned null throwable. Null values are generally not allowed in 2.x operators and sources.");
        } catch (Throwable th) {
            t = th;
            a.b(t);
            Throwable th2 = t;
        }
        d.error(t, (q<?>) observer);
    }
}

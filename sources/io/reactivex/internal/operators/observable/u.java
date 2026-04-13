package io.reactivex.internal.operators.observable;

import io.reactivex.disposables.b;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.functions.f;
import io.reactivex.internal.disposables.c;
import io.reactivex.o;
import io.reactivex.q;

/* compiled from: ObservableOnErrorReturn */
public final class u<T> extends a<T, T> {
    final f<? super Throwable, ? extends T> d;

    public u(o<T> source, f<? super Throwable, ? extends T> valueSupplier) {
        super(source);
        this.d = valueSupplier;
    }

    public void a0(q<? super T> t) {
        this.c.a(new a(t, this.d));
    }

    /* compiled from: ObservableOnErrorReturn */
    public static final class a<T> implements q<T>, b {
        final q<? super T> c;
        final f<? super Throwable, ? extends T> d;
        b f;

        a(q<? super T> actual, f<? super Throwable, ? extends T> valueSupplier) {
            this.c = actual;
            this.d = valueSupplier;
        }

        public void onSubscribe(b d2) {
            if (c.validate(this.f, d2)) {
                this.f = d2;
                this.c.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f.dispose();
        }

        public boolean isDisposed() {
            return this.f.isDisposed();
        }

        public void onNext(T t) {
            this.c.onNext(t);
        }

        public void onError(Throwable t) {
            try {
                T v = this.d.apply(t);
                if (v == null) {
                    NullPointerException e = new NullPointerException("The supplied value is null");
                    e.initCause(t);
                    this.c.onError(e);
                    return;
                }
                this.c.onNext(v);
                this.c.onComplete();
            } catch (Throwable e2) {
                io.reactivex.exceptions.a.b(e2);
                this.c.onError(new CompositeException(t, e2));
            }
        }

        public void onComplete() {
            this.c.onComplete();
        }
    }
}

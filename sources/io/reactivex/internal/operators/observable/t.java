package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.disposables.b;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.functions.f;
import io.reactivex.internal.disposables.g;
import io.reactivex.internal.operators.observable.ObservableOnErrorNext;
import io.reactivex.o;
import io.reactivex.q;

/* compiled from: ObservableOnErrorNext */
public final class t<T> extends a<T, T> {
    final f<? super Throwable, ? extends o<? extends T>> d;
    final boolean f;

    public t(o<T> source, f<? super Throwable, ? extends o<? extends T>> nextSupplier, boolean allowFatal) {
        super(source);
        this.d = nextSupplier;
        this.f = allowFatal;
    }

    public void a0(q<? super T> t) {
        ObservableOnErrorNext.OnErrorNextObserver<T> parent = new a<>(t, this.d, this.f);
        t.onSubscribe(parent.q);
        this.c.a(parent);
    }

    /* compiled from: ObservableOnErrorNext */
    public static final class a<T> implements q<T> {
        final q<? super T> c;
        final f<? super Throwable, ? extends o<? extends T>> d;
        final boolean f;
        final g q = new g();
        boolean x;
        boolean y;

        a(q<? super T> actual, f<? super Throwable, ? extends o<? extends T>> nextSupplier, boolean allowFatal) {
            this.c = actual;
            this.d = nextSupplier;
            this.f = allowFatal;
        }

        public void onSubscribe(b d2) {
            this.q.replace(d2);
        }

        public void onNext(T t) {
            if (!this.y) {
                this.c.onNext(t);
            }
        }

        public void onError(Throwable t) {
            if (!this.x) {
                this.x = true;
                if (!this.f || (t instanceof Exception)) {
                    try {
                        ObservableSource<? extends T> p = (o) this.d.apply(t);
                        if (p == null) {
                            NullPointerException npe = new NullPointerException("Observable is null");
                            npe.initCause(t);
                            this.c.onError(npe);
                            return;
                        }
                        p.a(this);
                    } catch (Throwable e) {
                        io.reactivex.exceptions.a.b(e);
                        this.c.onError(new CompositeException(t, e));
                    }
                } else {
                    this.c.onError(t);
                }
            } else if (this.y) {
                io.reactivex.plugins.a.q(t);
            } else {
                this.c.onError(t);
            }
        }

        public void onComplete() {
            if (!this.y) {
                this.y = true;
                this.x = true;
                this.c.onComplete();
            }
        }
    }
}

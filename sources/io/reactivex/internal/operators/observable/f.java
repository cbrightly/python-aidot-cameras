package io.reactivex.internal.operators.observable;

import io.reactivex.disposables.b;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.functions.e;
import io.reactivex.internal.disposables.c;
import io.reactivex.o;
import io.reactivex.q;

/* compiled from: ObservableDoOnEach */
public final class f<T> extends a<T, T> {
    final e<? super T> d;
    final e<? super Throwable> f;
    final io.reactivex.functions.a q;
    final io.reactivex.functions.a x;

    public f(o<T> source, e<? super T> onNext, e<? super Throwable> onError, io.reactivex.functions.a onComplete, io.reactivex.functions.a onAfterTerminate) {
        super(source);
        this.d = onNext;
        this.f = onError;
        this.q = onComplete;
        this.x = onAfterTerminate;
    }

    public void a0(q<? super T> t) {
        this.c.a(new a(t, this.d, this.f, this.q, this.x));
    }

    /* compiled from: ObservableDoOnEach */
    public static final class a<T> implements q<T>, b {
        final q<? super T> c;
        final e<? super T> d;
        final e<? super Throwable> f;
        final io.reactivex.functions.a q;
        final io.reactivex.functions.a x;
        b y;
        boolean z;

        a(q<? super T> actual, e<? super T> onNext, e<? super Throwable> onError, io.reactivex.functions.a onComplete, io.reactivex.functions.a onAfterTerminate) {
            this.c = actual;
            this.d = onNext;
            this.f = onError;
            this.q = onComplete;
            this.x = onAfterTerminate;
        }

        public void onSubscribe(b d2) {
            if (c.validate(this.y, d2)) {
                this.y = d2;
                this.c.onSubscribe(this);
            }
        }

        public void dispose() {
            this.y.dispose();
        }

        public boolean isDisposed() {
            return this.y.isDisposed();
        }

        public void onNext(T t) {
            if (!this.z) {
                try {
                    this.d.accept(t);
                    this.c.onNext(t);
                } catch (Throwable e) {
                    io.reactivex.exceptions.a.b(e);
                    this.y.dispose();
                    onError(e);
                }
            }
        }

        public void onError(Throwable t) {
            if (this.z) {
                io.reactivex.plugins.a.q(t);
                return;
            }
            this.z = true;
            try {
                this.f.accept(t);
            } catch (Throwable e) {
                io.reactivex.exceptions.a.b(e);
                t = new CompositeException(t, e);
            }
            this.c.onError(t);
            try {
                this.x.run();
            } catch (Throwable e2) {
                io.reactivex.exceptions.a.b(e2);
                io.reactivex.plugins.a.q(e2);
            }
        }

        public void onComplete() {
            if (!this.z) {
                try {
                    this.q.run();
                    this.z = true;
                    this.c.onComplete();
                    try {
                        this.x.run();
                    } catch (Throwable e) {
                        io.reactivex.exceptions.a.b(e);
                        io.reactivex.plugins.a.q(e);
                    }
                } catch (Throwable e2) {
                    io.reactivex.exceptions.a.b(e2);
                    onError(e2);
                }
            }
        }
    }
}

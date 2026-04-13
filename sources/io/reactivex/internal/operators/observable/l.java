package io.reactivex.internal.operators.observable;

import io.reactivex.internal.disposables.d;
import io.reactivex.internal.functions.b;
import io.reactivex.internal.observers.c;
import io.reactivex.internal.operators.observable.ObservableFromIterable;
import io.reactivex.q;
import java.util.Iterator;

/* compiled from: ObservableFromIterable */
public final class l<T> extends io.reactivex.l<T> {
    final Iterable<? extends T> c;

    public l(Iterable<? extends T> source) {
        this.c = source;
    }

    public void a0(q<? super T> observer) {
        try {
            Iterator<? extends T> it = this.c.iterator();
            try {
                if (!it.hasNext()) {
                    d.complete((q<?>) observer);
                    return;
                }
                ObservableFromIterable.FromIterableDisposable<T> d = new a<>(observer, it);
                observer.onSubscribe(d);
                if (!d.q) {
                    d.a();
                }
            } catch (Throwable e) {
                io.reactivex.exceptions.a.b(e);
                d.error(e, (q<?>) observer);
            }
        } catch (Throwable e2) {
            io.reactivex.exceptions.a.b(e2);
            d.error(e2, (q<?>) observer);
        }
    }

    /* compiled from: ObservableFromIterable */
    public static final class a<T> extends c<T> {
        final q<? super T> c;
        final Iterator<? extends T> d;
        volatile boolean f;
        boolean q;
        boolean x;
        boolean y;

        a(q<? super T> actual, Iterator<? extends T> it) {
            this.c = actual;
            this.d = it;
        }

        /* access modifiers changed from: package-private */
        public void a() {
            while (!isDisposed()) {
                try {
                    this.c.onNext(b.e(this.d.next(), "The iterator returned a null value"));
                    if (!isDisposed()) {
                        try {
                            if (!this.d.hasNext()) {
                                if (!isDisposed()) {
                                    this.c.onComplete();
                                    return;
                                }
                                return;
                            }
                        } catch (Throwable e) {
                            io.reactivex.exceptions.a.b(e);
                            this.c.onError(e);
                            return;
                        }
                    } else {
                        return;
                    }
                } catch (Throwable e2) {
                    io.reactivex.exceptions.a.b(e2);
                    this.c.onError(e2);
                    return;
                }
            }
        }

        public int requestFusion(int mode) {
            if ((mode & 1) == 0) {
                return 0;
            }
            this.q = true;
            return 1;
        }

        public T poll() {
            if (this.x) {
                return null;
            }
            if (!this.y) {
                this.y = true;
            } else if (!this.d.hasNext()) {
                this.x = true;
                return null;
            }
            return b.e(this.d.next(), "The iterator returned a null value");
        }

        public boolean isEmpty() {
            return this.x;
        }

        public void clear() {
            this.x = true;
        }

        public void dispose() {
            this.f = true;
        }

        public boolean isDisposed() {
            return this.f;
        }
    }
}

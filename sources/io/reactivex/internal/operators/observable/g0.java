package io.reactivex.internal.operators.observable;

import io.reactivex.disposables.b;
import io.reactivex.internal.disposables.c;
import io.reactivex.internal.disposables.d;
import io.reactivex.o;
import io.reactivex.q;

/* compiled from: ObservableTake */
public final class g0<T> extends a<T, T> {
    final long d;

    public g0(o<T> source, long limit) {
        super(source);
        this.d = limit;
    }

    /* access modifiers changed from: protected */
    public void a0(q<? super T> observer) {
        this.c.a(new a(observer, this.d));
    }

    /* compiled from: ObservableTake */
    public static final class a<T> implements q<T>, b {
        final q<? super T> c;
        boolean d;
        b f;
        long q;

        a(q<? super T> actual, long limit) {
            this.c = actual;
            this.q = limit;
        }

        public void onSubscribe(b d2) {
            if (c.validate(this.f, d2)) {
                this.f = d2;
                if (this.q == 0) {
                    this.d = true;
                    d2.dispose();
                    d.complete((q<?>) this.c);
                    return;
                }
                this.c.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.d) {
                long j = this.q;
                long j2 = j - 1;
                this.q = j2;
                if (j > 0) {
                    boolean stop = j2 == 0;
                    this.c.onNext(t);
                    if (stop) {
                        onComplete();
                    }
                }
            }
        }

        public void onError(Throwable t) {
            if (this.d) {
                io.reactivex.plugins.a.q(t);
                return;
            }
            this.d = true;
            this.f.dispose();
            this.c.onError(t);
        }

        public void onComplete() {
            if (!this.d) {
                this.d = true;
                this.f.dispose();
                this.c.onComplete();
            }
        }

        public void dispose() {
            this.f.dispose();
        }

        public boolean isDisposed() {
            return this.f.isDisposed();
        }
    }
}

package io.reactivex.internal.operators.observable;

import io.reactivex.disposables.b;
import io.reactivex.internal.disposables.c;
import io.reactivex.o;
import io.reactivex.q;

/* compiled from: ObservableSkip */
public final class e0<T> extends a<T, T> {
    final long d;

    public e0(o<T> source, long n) {
        super(source);
        this.d = n;
    }

    public void a0(q<? super T> observer) {
        this.c.a(new a(observer, this.d));
    }

    /* compiled from: ObservableSkip */
    public static final class a<T> implements q<T>, b {
        final q<? super T> c;
        long d;
        b f;

        a(q<? super T> actual, long n) {
            this.c = actual;
            this.d = n;
        }

        public void onSubscribe(b d2) {
            if (c.validate(this.f, d2)) {
                this.f = d2;
                this.c.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            long j = this.d;
            if (j != 0) {
                this.d = j - 1;
            } else {
                this.c.onNext(t);
            }
        }

        public void onError(Throwable t) {
            this.c.onError(t);
        }

        public void onComplete() {
            this.c.onComplete();
        }

        public void dispose() {
            this.f.dispose();
        }

        public boolean isDisposed() {
            return this.f.isDisposed();
        }
    }
}

package io.reactivex.internal.operators.observable;

import io.reactivex.o;
import io.reactivex.q;
import io.reactivex.r;
import java.util.concurrent.TimeUnit;

/* compiled from: ObservableDelay */
public final class e<T> extends a<T, T> {
    final long d;
    final TimeUnit f;
    final r q;
    final boolean x;

    public e(o<T> source, long delay, TimeUnit unit, r scheduler, boolean delayError) {
        super(source);
        this.d = delay;
        this.f = unit;
        this.q = scheduler;
        this.x = delayError;
    }

    public void a0(q<? super T> t) {
        q<? super T> qVar;
        if (this.x) {
            qVar = t;
        } else {
            qVar = new io.reactivex.observers.a<>(t);
        }
        this.c.a(new a(qVar, this.d, this.f, this.q.a(), this.x));
    }

    /* compiled from: ObservableDelay */
    public static final class a<T> implements q<T>, io.reactivex.disposables.b {
        final q<? super T> c;
        final long d;
        final TimeUnit f;
        final r.c q;
        final boolean x;
        io.reactivex.disposables.b y;

        a(q<? super T> actual, long delay, TimeUnit unit, r.c w, boolean delayError) {
            this.c = actual;
            this.d = delay;
            this.f = unit;
            this.q = w;
            this.x = delayError;
        }

        public void onSubscribe(io.reactivex.disposables.b d2) {
            if (io.reactivex.internal.disposables.c.validate(this.y, d2)) {
                this.y = d2;
                this.c.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.q.c(new c(t), this.d, this.f);
        }

        public void onError(Throwable t) {
            this.q.c(new b(t), this.x ? this.d : 0, this.f);
        }

        public void onComplete() {
            this.q.c(new C0304a(), this.d, this.f);
        }

        public void dispose() {
            this.y.dispose();
            this.q.dispose();
        }

        public boolean isDisposed() {
            return this.q.isDisposed();
        }

        /* compiled from: ObservableDelay */
        public final class c implements Runnable {
            private final T c;

            c(T t) {
                this.c = t;
            }

            public void run() {
                a.this.c.onNext(this.c);
            }
        }

        /* compiled from: ObservableDelay */
        public final class b implements Runnable {
            private final Throwable c;

            b(Throwable throwable) {
                this.c = throwable;
            }

            public void run() {
                try {
                    a.this.c.onError(this.c);
                } finally {
                    a.this.q.dispose();
                }
            }
        }

        /* renamed from: io.reactivex.internal.operators.observable.e$a$a  reason: collision with other inner class name */
        /* compiled from: ObservableDelay */
        public final class C0304a implements Runnable {
            C0304a() {
            }

            public void run() {
                try {
                    a.this.c.onComplete();
                } finally {
                    a.this.q.dispose();
                }
            }
        }
    }
}

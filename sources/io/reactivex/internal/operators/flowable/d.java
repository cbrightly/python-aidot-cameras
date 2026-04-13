package io.reactivex.internal.operators.flowable;

import io.reactivex.e;
import io.reactivex.h;
import io.reactivex.internal.subscriptions.f;
import io.reactivex.r;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.b;

/* compiled from: FlowableDelay */
public final class d<T> extends a<T, T> {
    final long f;
    final TimeUnit q;
    final r x;
    final boolean y;

    public d(e<T> source, long delay, TimeUnit unit, r scheduler, boolean delayError) {
        super(source);
        this.f = delay;
        this.q = unit;
        this.x = scheduler;
        this.y = delayError;
    }

    /* access modifiers changed from: protected */
    public void L(b<? super T> t) {
        b<? super T> bVar;
        if (this.y) {
            bVar = t;
        } else {
            bVar = new io.reactivex.subscribers.b<>(t);
        }
        this.d.K(new a(bVar, this.f, this.q, this.x.a(), this.y));
    }

    /* compiled from: FlowableDelay */
    public static final class a<T> implements h<T>, org.reactivestreams.c {
        final org.reactivestreams.b<? super T> c;
        final long d;
        final TimeUnit f;
        final r.c q;
        final boolean x;
        org.reactivestreams.c y;

        a(org.reactivestreams.b<? super T> actual, long delay, TimeUnit unit, r.c w, boolean delayError) {
            this.c = actual;
            this.d = delay;
            this.f = unit;
            this.q = w;
            this.x = delayError;
        }

        public void onSubscribe(org.reactivestreams.c s) {
            if (f.validate(this.y, s)) {
                this.y = s;
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
            this.q.c(new C0299a(), this.d, this.f);
        }

        public void request(long n) {
            this.y.request(n);
        }

        public void cancel() {
            this.y.cancel();
            this.q.dispose();
        }

        /* compiled from: FlowableDelay */
        public final class c implements Runnable {
            private final T c;

            c(T t) {
                this.c = t;
            }

            public void run() {
                a.this.c.onNext(this.c);
            }
        }

        /* compiled from: FlowableDelay */
        public final class b implements Runnable {
            private final Throwable c;

            b(Throwable t) {
                this.c = t;
            }

            public void run() {
                try {
                    a.this.c.onError(this.c);
                } finally {
                    a.this.q.dispose();
                }
            }
        }

        /* renamed from: io.reactivex.internal.operators.flowable.d$a$a  reason: collision with other inner class name */
        /* compiled from: FlowableDelay */
        public final class C0299a implements Runnable {
            C0299a() {
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

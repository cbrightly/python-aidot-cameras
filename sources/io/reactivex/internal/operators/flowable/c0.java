package io.reactivex.internal.operators.flowable;

import io.reactivex.e;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.d;
import io.reactivex.internal.subscriptions.f;
import io.reactivex.r;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.b;
import org.reactivestreams.c;

/* compiled from: FlowableTimer */
public final class c0 extends e<Long> {
    final r d;
    final long f;
    final TimeUnit q;

    public c0(long delay, TimeUnit unit, r scheduler) {
        this.f = delay;
        this.q = unit;
        this.d = scheduler;
    }

    public void L(b<? super Long> s) {
        a ios = new a(s);
        s.onSubscribe(ios);
        ios.setResource(this.d.c(ios, this.f, this.q));
    }

    /* compiled from: FlowableTimer */
    public static final class a extends AtomicReference<io.reactivex.disposables.b> implements c, Runnable {
        private static final long serialVersionUID = -2809475196591179431L;
        final b<? super Long> downstream;
        volatile boolean requested;

        a(b<? super Long> downstream2) {
            this.downstream = downstream2;
        }

        public void request(long n) {
            if (f.validate(n)) {
                this.requested = true;
            }
        }

        public void cancel() {
            io.reactivex.internal.disposables.c.dispose(this);
        }

        public void run() {
            if (get() == io.reactivex.internal.disposables.c.DISPOSED) {
                return;
            }
            if (this.requested) {
                this.downstream.onNext(0L);
                lazySet(d.INSTANCE);
                this.downstream.onComplete();
                return;
            }
            lazySet(d.INSTANCE);
            this.downstream.onError(new MissingBackpressureException("Can't deliver value due to lack of requests"));
        }

        public void setResource(io.reactivex.disposables.b d) {
            io.reactivex.internal.disposables.c.trySet(this, d);
        }
    }
}

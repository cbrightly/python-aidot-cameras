package io.reactivex.internal.operators.flowable;

import io.reactivex.e;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.schedulers.o;
import io.reactivex.internal.subscriptions.f;
import io.reactivex.r;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.b;
import org.reactivestreams.c;

/* compiled from: FlowableInterval */
public final class n extends e<Long> {
    final r d;
    final long f;
    final long q;
    final TimeUnit x;

    public n(long initialDelay, long period, TimeUnit unit, r scheduler) {
        this.f = initialDelay;
        this.q = period;
        this.x = unit;
        this.d = scheduler;
    }

    public void L(b<? super Long> s) {
        a is = new a(s);
        s.onSubscribe(is);
        r sch = this.d;
        if (sch instanceof o) {
            r.c worker = sch.a();
            is.setResource(worker);
            worker.d(is, this.f, this.q, this.x);
            return;
        }
        is.setResource(sch.d(is, this.f, this.q, this.x));
    }

    /* compiled from: FlowableInterval */
    public static final class a extends AtomicLong implements c, Runnable {
        private static final long serialVersionUID = -2809475196591179431L;
        long count;
        final b<? super Long> downstream;
        final AtomicReference<io.reactivex.disposables.b> resource = new AtomicReference<>();

        a(b<? super Long> downstream2) {
            this.downstream = downstream2;
        }

        public void request(long n) {
            if (f.validate(n)) {
                io.reactivex.internal.util.c.a(this, n);
            }
        }

        public void cancel() {
            io.reactivex.internal.disposables.c.dispose(this.resource);
        }

        public void run() {
            if (this.resource.get() == io.reactivex.internal.disposables.c.DISPOSED) {
                return;
            }
            if (get() != 0) {
                b<? super Long> bVar = this.downstream;
                long j = this.count;
                this.count = j + 1;
                bVar.onNext(Long.valueOf(j));
                io.reactivex.internal.util.c.d(this, 1);
                return;
            }
            b<? super Long> bVar2 = this.downstream;
            bVar2.onError(new MissingBackpressureException("Can't deliver value " + this.count + " due to lack of requests"));
            io.reactivex.internal.disposables.c.dispose(this.resource);
        }

        public void setResource(io.reactivex.disposables.b d) {
            io.reactivex.internal.disposables.c.setOnce(this.resource, d);
        }
    }
}

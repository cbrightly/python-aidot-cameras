package io.reactivex.internal.operators.observable;

import io.reactivex.disposables.b;
import io.reactivex.internal.disposables.c;
import io.reactivex.internal.schedulers.o;
import io.reactivex.l;
import io.reactivex.q;
import io.reactivex.r;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ObservableIntervalRange */
public final class p extends l<Long> {
    final r c;
    final long d;
    final long f;
    final long q;
    final long x;
    final TimeUnit y;

    public p(long start, long end, long initialDelay, long period, TimeUnit unit, r scheduler) {
        this.q = initialDelay;
        this.x = period;
        this.y = unit;
        this.c = scheduler;
        this.d = start;
        this.f = end;
    }

    public void a0(q<? super Long> observer) {
        a is = new a(observer, this.d, this.f);
        observer.onSubscribe(is);
        r sch = this.c;
        if (sch instanceof o) {
            r.c worker = sch.a();
            is.setResource(worker);
            worker.d(is, this.q, this.x, this.y);
            return;
        }
        is.setResource(sch.d(is, this.q, this.x, this.y));
    }

    /* compiled from: ObservableIntervalRange */
    public static final class a extends AtomicReference<b> implements b, Runnable {
        private static final long serialVersionUID = 1891866368734007884L;
        long count;
        final q<? super Long> downstream;
        final long end;

        a(q<? super Long> actual, long start, long end2) {
            this.downstream = actual;
            this.count = start;
            this.end = end2;
        }

        public void dispose() {
            c.dispose(this);
        }

        public boolean isDisposed() {
            return get() == c.DISPOSED;
        }

        public void run() {
            if (!isDisposed()) {
                long c = this.count;
                this.downstream.onNext(Long.valueOf(c));
                if (c == this.end) {
                    c.dispose(this);
                    this.downstream.onComplete();
                    return;
                }
                this.count = 1 + c;
            }
        }

        public void setResource(b d) {
            c.setOnce(this, d);
        }
    }
}

package io.reactivex.internal.operators.observable;

import io.reactivex.disposables.b;
import io.reactivex.internal.disposables.c;
import io.reactivex.l;
import io.reactivex.q;
import io.reactivex.r;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ObservableInterval */
public final class o extends l<Long> {
    final r c;
    final long d;
    final long f;
    final TimeUnit q;

    public o(long initialDelay, long period, TimeUnit unit, r scheduler) {
        this.d = initialDelay;
        this.f = period;
        this.q = unit;
        this.c = scheduler;
    }

    public void a0(q<? super Long> observer) {
        a is = new a(observer);
        observer.onSubscribe(is);
        r sch = this.c;
        if (sch instanceof io.reactivex.internal.schedulers.o) {
            r.c worker = sch.a();
            is.setResource(worker);
            worker.d(is, this.d, this.f, this.q);
            return;
        }
        is.setResource(sch.d(is, this.d, this.f, this.q));
    }

    /* compiled from: ObservableInterval */
    public static final class a extends AtomicReference<b> implements b, Runnable {
        private static final long serialVersionUID = 346773832286157679L;
        long count;
        final q<? super Long> downstream;

        a(q<? super Long> downstream2) {
            this.downstream = downstream2;
        }

        public void dispose() {
            c.dispose(this);
        }

        public boolean isDisposed() {
            return get() == c.DISPOSED;
        }

        public void run() {
            if (get() != c.DISPOSED) {
                q<? super Long> qVar = this.downstream;
                long j = this.count;
                this.count = 1 + j;
                qVar.onNext(Long.valueOf(j));
            }
        }

        public void setResource(b d) {
            c.setOnce(this, d);
        }
    }
}

package io.reactivex.internal.operators.observable;

import io.reactivex.disposables.b;
import io.reactivex.internal.disposables.c;
import io.reactivex.internal.disposables.d;
import io.reactivex.l;
import io.reactivex.q;
import io.reactivex.r;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ObservableTimer */
public final class j0 extends l<Long> {
    final r c;
    final long d;
    final TimeUnit f;

    public j0(long delay, TimeUnit unit, r scheduler) {
        this.d = delay;
        this.f = unit;
        this.c = scheduler;
    }

    public void a0(q<? super Long> observer) {
        a ios = new a(observer);
        observer.onSubscribe(ios);
        ios.setResource(this.c.c(ios, this.d, this.f));
    }

    /* compiled from: ObservableTimer */
    public static final class a extends AtomicReference<b> implements b, Runnable {
        private static final long serialVersionUID = -2809475196591179431L;
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
            if (!isDisposed()) {
                this.downstream.onNext(0L);
                lazySet(d.INSTANCE);
                this.downstream.onComplete();
            }
        }

        public void setResource(b d) {
            c.trySet(this, d);
        }
    }
}

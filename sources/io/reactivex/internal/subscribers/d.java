package io.reactivex.internal.subscribers;

import io.reactivex.h;
import io.reactivex.internal.subscriptions.f;
import io.reactivex.internal.util.g;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.b;
import org.reactivestreams.c;

/* compiled from: StrictSubscriber */
public class d<T> extends AtomicInteger implements h<T>, c {
    private static final long serialVersionUID = -4945028590049415624L;
    volatile boolean done;
    final b<? super T> downstream;
    final io.reactivex.internal.util.b error = new io.reactivex.internal.util.b();
    final AtomicBoolean once = new AtomicBoolean();
    final AtomicLong requested = new AtomicLong();
    final AtomicReference<c> upstream = new AtomicReference<>();

    public d(b<? super T> downstream2) {
        this.downstream = downstream2;
    }

    public void request(long n) {
        if (n <= 0) {
            cancel();
            onError(new IllegalArgumentException("§3.9 violated: positive request amount required but it was " + n));
            return;
        }
        f.deferredRequest(this.upstream, this.requested, n);
    }

    public void cancel() {
        if (!this.done) {
            f.cancel(this.upstream);
        }
    }

    public void onSubscribe(c s) {
        if (this.once.compareAndSet(false, true)) {
            this.downstream.onSubscribe(this);
            f.deferredSetOnce(this.upstream, this.requested, s);
            return;
        }
        s.cancel();
        cancel();
        onError(new IllegalStateException("§2.12 violated: onSubscribe must be called at most once"));
    }

    public void onNext(T t) {
        g.f(this.downstream, t, this, this.error);
    }

    public void onError(Throwable t) {
        this.done = true;
        g.d(this.downstream, t, this, this.error);
    }

    public void onComplete() {
        this.done = true;
        g.b(this.downstream, this, this.error);
    }
}

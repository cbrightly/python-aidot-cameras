package io.reactivex.internal.operators.flowable;

import io.reactivex.h;
import io.reactivex.internal.subscriptions.f;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.a;
import org.reactivestreams.c;

/* compiled from: FlowableRepeatWhen */
public final class v<T, U> extends AtomicInteger implements h<Object>, c {
    private static final long serialVersionUID = 2827772011130406689L;
    final AtomicLong requested = new AtomicLong();
    final a<T> source;
    w<T, U> subscriber;
    final AtomicReference<c> upstream = new AtomicReference<>();

    v(a<T> source2) {
        this.source = source2;
    }

    public void onSubscribe(c s) {
        f.deferredSetOnce(this.upstream, this.requested, s);
    }

    public void onNext(Object t) {
        if (getAndIncrement() == 0) {
            while (this.upstream.get() != f.CANCELLED) {
                this.source.a(this.subscriber);
                if (decrementAndGet() == 0) {
                    return;
                }
            }
        }
    }

    public void onError(Throwable t) {
        this.subscriber.cancel();
        this.subscriber.downstream.onError(t);
    }

    public void onComplete() {
        this.subscriber.cancel();
        this.subscriber.downstream.onComplete();
    }

    public void request(long n) {
        f.deferredRequest(this.upstream, this.requested, n);
    }

    public void cancel() {
        f.cancel(this.upstream);
    }
}

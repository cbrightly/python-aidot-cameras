package io.reactivex.internal.operators.flowable;

import io.reactivex.h;
import io.reactivex.internal.subscriptions.e;
import io.reactivex.processors.a;
import org.reactivestreams.b;
import org.reactivestreams.c;

/* compiled from: FlowableRepeatWhen */
public abstract class w<T, U> extends e implements h<T> {
    private static final long serialVersionUID = -5604623027276966720L;
    protected final b<? super T> downstream;
    protected final a<U> processor;
    private long produced;
    protected final c receiver;

    public abstract /* synthetic */ void onComplete();

    public abstract /* synthetic */ void onError(Throwable th);

    w(b<? super T> actual, a<U> processor2, c receiver2) {
        super(false);
        this.downstream = actual;
        this.processor = processor2;
        this.receiver = receiver2;
    }

    public final void onSubscribe(c s) {
        setSubscription(s);
    }

    public final void onNext(T t) {
        this.produced++;
        this.downstream.onNext(t);
    }

    /* access modifiers changed from: protected */
    public final void again(U signal) {
        setSubscription(io.reactivex.internal.subscriptions.c.INSTANCE);
        long p = this.produced;
        if (p != 0) {
            this.produced = 0;
            produced(p);
        }
        this.receiver.request(1);
        this.processor.onNext(signal);
    }

    public final void cancel() {
        super.cancel();
        this.receiver.cancel();
    }
}

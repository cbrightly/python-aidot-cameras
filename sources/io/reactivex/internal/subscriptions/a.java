package io.reactivex.internal.subscriptions;

import io.reactivex.internal.fuseable.d;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: BasicIntQueueSubscription */
public abstract class a<T> extends AtomicInteger implements d<T> {
    private static final long serialVersionUID = -6671519529404341862L;

    public abstract /* synthetic */ void cancel();

    public abstract /* synthetic */ void clear();

    public abstract /* synthetic */ boolean isEmpty();

    public abstract /* synthetic */ T poll();

    public abstract /* synthetic */ void request(long j);

    public abstract /* synthetic */ int requestFusion(int i);

    public final boolean offer(T t) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    public final boolean offer(T t, T t2) {
        throw new UnsupportedOperationException("Should not be called!");
    }
}

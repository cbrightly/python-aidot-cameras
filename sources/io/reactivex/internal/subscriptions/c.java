package io.reactivex.internal.subscriptions;

import io.reactivex.internal.fuseable.d;
import org.reactivestreams.b;

/* compiled from: EmptySubscription */
public enum c implements d<Object> {
    INSTANCE;

    public void request(long n) {
        f.validate(n);
    }

    public void cancel() {
    }

    public String toString() {
        return "EmptySubscription";
    }

    public static void error(Throwable e, b<?> s) {
        s.onSubscribe(INSTANCE);
        s.onError(e);
    }

    public static void complete(b<?> s) {
        s.onSubscribe(INSTANCE);
        s.onComplete();
    }

    public Object poll() {
        return null;
    }

    public boolean isEmpty() {
        return true;
    }

    public void clear() {
    }

    public int requestFusion(int mode) {
        return mode & 2;
    }

    public boolean offer(Object value) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    public boolean offer(Object v1, Object v2) {
        throw new UnsupportedOperationException("Should not be called!");
    }
}

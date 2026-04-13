package io.reactivex.internal.disposables;

import io.reactivex.c;
import io.reactivex.internal.fuseable.b;
import io.reactivex.k;
import io.reactivex.q;
import io.reactivex.t;

/* compiled from: EmptyDisposable */
public enum d implements b<Object> {
    INSTANCE,
    NEVER;

    public void dispose() {
    }

    public boolean isDisposed() {
        return this == INSTANCE;
    }

    public static void complete(q<?> observer) {
        observer.onSubscribe(INSTANCE);
        observer.onComplete();
    }

    public static void complete(k<?> observer) {
        observer.onSubscribe(INSTANCE);
        observer.onComplete();
    }

    public static void error(Throwable e, q<?> observer) {
        observer.onSubscribe(INSTANCE);
        observer.onError(e);
    }

    public static void complete(c observer) {
        observer.onSubscribe(INSTANCE);
        observer.onComplete();
    }

    public static void error(Throwable e, c observer) {
        observer.onSubscribe(INSTANCE);
        observer.onError(e);
    }

    public static void error(Throwable e, t<?> observer) {
        observer.onSubscribe(INSTANCE);
        observer.onError(e);
    }

    public static void error(Throwable e, k<?> observer) {
        observer.onSubscribe(INSTANCE);
        observer.onError(e);
    }

    public boolean offer(Object value) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    public boolean offer(Object v1, Object v2) {
        throw new UnsupportedOperationException("Should not be called!");
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
}

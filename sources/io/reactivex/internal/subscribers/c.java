package io.reactivex.internal.subscribers;

import io.reactivex.disposables.b;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.functions.a;
import io.reactivex.functions.e;
import io.reactivex.h;
import io.reactivex.internal.subscriptions.f;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: LambdaSubscriber */
public final class c<T> extends AtomicReference<org.reactivestreams.c> implements h<T>, org.reactivestreams.c, b {
    private static final long serialVersionUID = -7251123623727029452L;
    final a onComplete;
    final e<? super Throwable> onError;
    final e<? super T> onNext;
    final e<? super org.reactivestreams.c> onSubscribe;

    public c(e<? super T> onNext2, e<? super Throwable> onError2, a onComplete2, e<? super org.reactivestreams.c> onSubscribe2) {
        this.onNext = onNext2;
        this.onError = onError2;
        this.onComplete = onComplete2;
        this.onSubscribe = onSubscribe2;
    }

    public void onSubscribe(org.reactivestreams.c s) {
        if (f.setOnce(this, s)) {
            try {
                this.onSubscribe.accept(this);
            } catch (Throwable ex) {
                io.reactivex.exceptions.a.b(ex);
                s.cancel();
                onError(ex);
            }
        }
    }

    public void onNext(T t) {
        if (!isDisposed()) {
            try {
                this.onNext.accept(t);
            } catch (Throwable e) {
                io.reactivex.exceptions.a.b(e);
                ((org.reactivestreams.c) get()).cancel();
                onError(e);
            }
        }
    }

    public void onError(Throwable t) {
        Object obj = get();
        f fVar = f.CANCELLED;
        if (obj != fVar) {
            lazySet(fVar);
            try {
                this.onError.accept(t);
            } catch (Throwable e) {
                io.reactivex.exceptions.a.b(e);
                io.reactivex.plugins.a.q(new CompositeException(t, e));
            }
        } else {
            io.reactivex.plugins.a.q(t);
        }
    }

    public void onComplete() {
        Object obj = get();
        f fVar = f.CANCELLED;
        if (obj != fVar) {
            lazySet(fVar);
            try {
                this.onComplete.run();
            } catch (Throwable e) {
                io.reactivex.exceptions.a.b(e);
                io.reactivex.plugins.a.q(e);
            }
        }
    }

    public void dispose() {
        cancel();
    }

    public boolean isDisposed() {
        return get() == f.CANCELLED;
    }

    public void request(long n) {
        ((org.reactivestreams.c) get()).request(n);
    }

    public void cancel() {
        f.cancel(this);
    }

    public boolean hasCustomOnError() {
        return this.onError != io.reactivex.internal.functions.a.f;
    }
}

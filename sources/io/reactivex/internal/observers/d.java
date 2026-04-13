package io.reactivex.internal.observers;

import io.reactivex.disposables.b;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.functions.a;
import io.reactivex.functions.e;
import io.reactivex.internal.disposables.c;
import io.reactivex.q;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: LambdaObserver */
public final class d<T> extends AtomicReference<b> implements q<T>, b {
    private static final long serialVersionUID = -7251123623727029452L;
    final a onComplete;
    final e<? super Throwable> onError;
    final e<? super T> onNext;
    final e<? super b> onSubscribe;

    public d(e<? super T> onNext2, e<? super Throwable> onError2, a onComplete2, e<? super b> onSubscribe2) {
        this.onNext = onNext2;
        this.onError = onError2;
        this.onComplete = onComplete2;
        this.onSubscribe = onSubscribe2;
    }

    public void onSubscribe(b d) {
        if (c.setOnce(this, d)) {
            try {
                this.onSubscribe.accept(this);
            } catch (Throwable ex) {
                io.reactivex.exceptions.a.b(ex);
                d.dispose();
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
                ((b) get()).dispose();
                onError(e);
            }
        }
    }

    public void onError(Throwable t) {
        if (!isDisposed()) {
            lazySet(c.DISPOSED);
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
        if (!isDisposed()) {
            lazySet(c.DISPOSED);
            try {
                this.onComplete.run();
            } catch (Throwable e) {
                io.reactivex.exceptions.a.b(e);
                io.reactivex.plugins.a.q(e);
            }
        }
    }

    public void dispose() {
        c.dispose(this);
    }

    public boolean isDisposed() {
        return get() == c.DISPOSED;
    }

    public boolean hasCustomOnError() {
        return this.onError != io.reactivex.internal.functions.a.f;
    }
}

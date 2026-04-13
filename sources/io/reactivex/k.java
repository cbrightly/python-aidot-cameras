package io.reactivex;

import io.reactivex.disposables.b;

/* compiled from: MaybeObserver */
public interface k<T> {
    void onComplete();

    void onError(Throwable th);

    void onSubscribe(b bVar);
}

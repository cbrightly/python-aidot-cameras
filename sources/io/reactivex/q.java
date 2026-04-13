package io.reactivex;

import io.reactivex.disposables.b;

/* compiled from: Observer */
public interface q<T> {
    void onComplete();

    void onError(Throwable th);

    void onNext(T t);

    void onSubscribe(b bVar);
}

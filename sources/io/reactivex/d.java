package io.reactivex;

/* compiled from: Emitter */
public interface d<T> {
    void onComplete();

    void onError(Throwable th);

    void onNext(T t);
}

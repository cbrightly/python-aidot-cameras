package org.reactivestreams;

/* compiled from: Subscriber */
public interface b<T> {
    void onComplete();

    void onError(Throwable th);

    void onNext(T t);

    void onSubscribe(c cVar);
}

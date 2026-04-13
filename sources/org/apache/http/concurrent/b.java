package org.apache.http.concurrent;

/* compiled from: FutureCallback */
public interface b<T> {
    void a(Exception exc);

    void cancelled();

    void completed(T t);
}

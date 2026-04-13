package org.glassfish.grizzly;

public interface CompletionHandler<E> {
    void cancelled();

    void completed(E e);

    void failed(Throwable th);

    void updated(E e);
}

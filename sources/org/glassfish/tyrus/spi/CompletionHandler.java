package org.glassfish.tyrus.spi;

public abstract class CompletionHandler<E> {
    public void cancelled() {
    }

    public void failed(Throwable throwable) {
    }

    public void completed(E e) {
    }

    public void updated(E e) {
    }
}

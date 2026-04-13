package org.glassfish.grizzly;

public class EmptyCompletionHandler<E> implements CompletionHandler<E> {
    public void cancelled() {
    }

    public void failed(Throwable throwable) {
    }

    public void completed(E e) {
    }

    public void updated(E e) {
    }
}

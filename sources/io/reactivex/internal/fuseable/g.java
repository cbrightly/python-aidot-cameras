package io.reactivex.internal.fuseable;

/* compiled from: SimpleQueue */
public interface g<T> {
    void clear();

    boolean isEmpty();

    boolean offer(T t);

    T poll();
}

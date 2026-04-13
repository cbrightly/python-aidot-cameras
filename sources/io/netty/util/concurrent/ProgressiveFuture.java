package io.netty.util.concurrent;

public interface ProgressiveFuture<V> extends Future<V> {
    ProgressiveFuture<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

    ProgressiveFuture<V> addListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr);

    ProgressiveFuture<V> await();

    ProgressiveFuture<V> awaitUninterruptibly();

    ProgressiveFuture<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

    ProgressiveFuture<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr);

    ProgressiveFuture<V> sync();

    ProgressiveFuture<V> syncUninterruptibly();
}

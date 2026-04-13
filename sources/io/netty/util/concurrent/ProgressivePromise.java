package io.netty.util.concurrent;

public interface ProgressivePromise<V> extends Promise<V>, ProgressiveFuture<V> {
    ProgressivePromise<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

    ProgressivePromise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr);

    ProgressivePromise<V> await();

    ProgressivePromise<V> awaitUninterruptibly();

    ProgressivePromise<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

    ProgressivePromise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr);

    ProgressivePromise<V> setFailure(Throwable th);

    ProgressivePromise<V> setProgress(long j, long j2);

    ProgressivePromise<V> setSuccess(V v);

    ProgressivePromise<V> sync();

    ProgressivePromise<V> syncUninterruptibly();

    boolean tryProgress(long j, long j2);
}

package io.netty.util.concurrent;

import io.netty.util.concurrent.ProgressiveFuture;

public interface GenericProgressiveFutureListener<F extends ProgressiveFuture<?>> extends GenericFutureListener<F> {
    void operationProgressed(F f, long j, long j2);
}

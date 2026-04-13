package org.glassfish.grizzly.impl;

import org.glassfish.grizzly.GrizzlyFuture;

public interface FutureImpl<R> extends GrizzlyFuture<R> {
    void failure(Throwable th);

    R getResult();

    void result(R r);
}

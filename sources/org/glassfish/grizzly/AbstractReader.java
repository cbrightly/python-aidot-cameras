package org.glassfish.grizzly;

import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.utils.Futures;

public abstract class AbstractReader<L> implements Reader<L> {
    public final GrizzlyFuture<ReadResult<Buffer, L>> read(Connection<L> connection) {
        return read(connection, (Buffer) null);
    }

    public final GrizzlyFuture<ReadResult<Buffer, L>> read(Connection<L> connection, Buffer buffer) {
        FutureImpl<ReadResult<Buffer, L>> future = Futures.createSafeFuture();
        read(connection, buffer, Futures.toCompletionHandler(future), (Interceptor<ReadResult>) null);
        return future;
    }

    public final void read(Connection<L> connection, Buffer buffer, CompletionHandler<ReadResult<Buffer, L>> completionHandler) {
        read(connection, buffer, completionHandler, (Interceptor<ReadResult>) null);
    }
}

package org.glassfish.grizzly;

import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.utils.Futures;

public abstract class AbstractWriter<L> implements Writer<L> {
    public final GrizzlyFuture<WriteResult<WritableMessage, L>> write(Connection<L> connection, WritableMessage message) {
        return write(connection, (Object) null, message);
    }

    public final void write(Connection<L> connection, WritableMessage message, CompletionHandler<WriteResult<WritableMessage, L>> completionHandler) {
        write(connection, null, message, completionHandler, (MessageCloner<WritableMessage>) null);
    }

    public final GrizzlyFuture<WriteResult<WritableMessage, L>> write(Connection<L> connection, L dstAddress, WritableMessage message) {
        FutureImpl<WriteResult<WritableMessage, L>> future = Futures.createSafeFuture();
        write(connection, dstAddress, message, Futures.toCompletionHandler(future), (MessageCloner<WritableMessage>) null);
        return future;
    }

    public final void write(Connection<L> connection, L dstAddress, WritableMessage message, CompletionHandler<WriteResult<WritableMessage, L>> completionHandler) {
        write(connection, dstAddress, message, completionHandler, (MessageCloner<WritableMessage>) null);
    }
}

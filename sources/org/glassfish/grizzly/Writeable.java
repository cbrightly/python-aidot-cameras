package org.glassfish.grizzly;

import org.glassfish.grizzly.asyncqueue.PushBackHandler;

public interface Writeable<L> extends OutputSink {
    <M> GrizzlyFuture<WriteResult<M, L>> write(M m);

    <M> void write(L l, M m, CompletionHandler<WriteResult<M, L>> completionHandler);

    @Deprecated
    <M> void write(L l, M m, CompletionHandler<WriteResult<M, L>> completionHandler, PushBackHandler pushBackHandler);

    <M> void write(M m, CompletionHandler<WriteResult<M, L>> completionHandler);

    @Deprecated
    <M> void write(M m, CompletionHandler<WriteResult<M, L>> completionHandler, PushBackHandler pushBackHandler);
}

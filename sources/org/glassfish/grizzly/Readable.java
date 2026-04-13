package org.glassfish.grizzly;

public interface Readable<L> {
    <M> GrizzlyFuture<ReadResult<M, L>> read();

    <M> void read(CompletionHandler<ReadResult<M, L>> completionHandler);
}

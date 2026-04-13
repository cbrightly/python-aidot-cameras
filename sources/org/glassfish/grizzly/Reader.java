package org.glassfish.grizzly;

public interface Reader<L> {
    public static final int COMPLETE_EVENT = 2;
    public static final int INCOMPLETE_EVENT = 3;
    public static final int READ_EVENT = 1;

    GrizzlyFuture<ReadResult<Buffer, L>> read(Connection<L> connection);

    GrizzlyFuture<ReadResult<Buffer, L>> read(Connection<L> connection, Buffer buffer);

    void read(Connection<L> connection, Buffer buffer, CompletionHandler<ReadResult<Buffer, L>> completionHandler);

    void read(Connection<L> connection, Buffer buffer, CompletionHandler<ReadResult<Buffer, L>> completionHandler, Interceptor<ReadResult> interceptor);
}

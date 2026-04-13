package org.glassfish.grizzly;

import java.io.IOException;

public interface Closeable {
    void addCloseListener(CloseListener closeListener);

    void assertOpen();

    GrizzlyFuture<Closeable> close();

    @Deprecated
    void close(CompletionHandler<Closeable> completionHandler);

    GrizzlyFuture<CloseReason> closeFuture();

    void closeSilently();

    void closeWithReason(IOException iOException);

    boolean isOpen();

    boolean removeCloseListener(CloseListener closeListener);

    GrizzlyFuture<Closeable> terminate();

    void terminateSilently();

    void terminateWithReason(IOException iOException);
}

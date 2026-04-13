package org.glassfish.grizzly.asyncqueue;

import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.Writer;

public interface AsyncQueueWriter<L> extends Writer<L>, AsyncQueue {
    public static final int AUTO_SIZE = -2;
    public static final int UNLIMITED_SIZE = -1;

    @Deprecated
    boolean canWrite(Connection<L> connection, int i);

    int getMaxPendingBytesPerConnection();

    boolean isAllowDirectWrite();

    @Deprecated
    void notifyWritePossible(Connection<L> connection, WriteHandler writeHandler, int i);

    void setAllowDirectWrite(boolean z);

    void setMaxPendingBytesPerConnection(int i);

    @Deprecated
    void write(Connection<L> connection, L l, WritableMessage writableMessage, CompletionHandler<WriteResult<WritableMessage, L>> completionHandler, PushBackHandler pushBackHandler, MessageCloner<WritableMessage> messageCloner);
}

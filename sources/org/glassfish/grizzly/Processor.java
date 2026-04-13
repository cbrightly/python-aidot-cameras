package org.glassfish.grizzly;

import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;

public interface Processor<E extends Context> {
    boolean isInterested(IOEvent iOEvent);

    E obtainContext(Connection connection);

    ProcessorResult process(E e);

    void read(Connection connection, CompletionHandler<ReadResult> completionHandler);

    void setInterested(IOEvent iOEvent, boolean z);

    void write(Connection connection, Object obj, Object obj2, CompletionHandler<WriteResult> completionHandler);

    void write(Connection connection, Object obj, Object obj2, CompletionHandler<WriteResult> completionHandler, MessageCloner messageCloner);

    @Deprecated
    void write(Connection connection, Object obj, Object obj2, CompletionHandler<WriteResult> completionHandler, PushBackHandler pushBackHandler);
}

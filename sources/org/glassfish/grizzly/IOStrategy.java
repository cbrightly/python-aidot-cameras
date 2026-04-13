package org.glassfish.grizzly;

import java.util.concurrent.Executor;
import org.glassfish.grizzly.strategies.WorkerThreadPoolConfigProducer;

public interface IOStrategy extends WorkerThreadPoolConfigProducer {
    boolean executeIoEvent(Connection connection, IOEvent iOEvent);

    boolean executeIoEvent(Connection connection, IOEvent iOEvent, boolean z);

    Executor getThreadPoolFor(Connection connection, IOEvent iOEvent);
}

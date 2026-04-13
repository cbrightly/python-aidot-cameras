package org.glassfish.grizzly.strategies;

import java.util.concurrent.Executor;
import java.util.logging.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;

public final class WorkerThreadIOStrategy extends AbstractIOStrategy {
    private static final WorkerThreadIOStrategy INSTANCE = new WorkerThreadIOStrategy();
    private static final Logger logger = Grizzly.logger(WorkerThreadIOStrategy.class);

    private WorkerThreadIOStrategy() {
    }

    public static WorkerThreadIOStrategy getInstance() {
        return INSTANCE;
    }

    public boolean executeIoEvent(Connection connection, IOEvent ioEvent, boolean isIoEventEnabled) {
        IOEventLifeCycleListener listener;
        if (AbstractIOStrategy.isReadWrite(ioEvent)) {
            if (isIoEventEnabled) {
                connection.disableIOEvent(ioEvent);
            }
            listener = AbstractIOStrategy.ENABLE_INTEREST_LIFECYCLE_LISTENER;
        } else {
            listener = null;
        }
        Executor threadPool = getThreadPoolFor(connection, ioEvent);
        if (threadPool != null) {
            threadPool.execute(new WorkerThreadRunnable(connection, ioEvent, listener));
            return true;
        }
        run0(connection, ioEvent, listener);
        return true;
    }

    /* access modifiers changed from: private */
    public static void run0(Connection connection, IOEvent ioEvent, IOEventLifeCycleListener lifeCycleListener) {
        AbstractIOStrategy.fireIOEvent(connection, ioEvent, lifeCycleListener, logger);
    }

    public static final class WorkerThreadRunnable implements Runnable {
        final Connection connection;
        final IOEvent ioEvent;
        final IOEventLifeCycleListener lifeCycleListener;

        private WorkerThreadRunnable(Connection connection2, IOEvent ioEvent2, IOEventLifeCycleListener lifeCycleListener2) {
            this.connection = connection2;
            this.ioEvent = ioEvent2;
            this.lifeCycleListener = lifeCycleListener2;
        }

        public void run() {
            WorkerThreadIOStrategy.run0(this.connection, this.ioEvent, this.lifeCycleListener);
        }
    }
}

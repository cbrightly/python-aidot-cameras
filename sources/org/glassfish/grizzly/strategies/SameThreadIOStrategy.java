package org.glassfish.grizzly.strategies;

import java.util.concurrent.Executor;
import java.util.logging.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.asyncqueue.AsyncQueue;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;

public final class SameThreadIOStrategy extends AbstractIOStrategy {
    private static final SameThreadIOStrategy INSTANCE = new SameThreadIOStrategy();
    private static final InterestLifeCycleListenerWhenIoDisabled LIFECYCLE_LISTENER_WHEN_IO_DISABLED = new InterestLifeCycleListenerWhenIoDisabled();
    private static final InterestLifeCycleListenerWhenIoEnabled LIFECYCLE_LISTENER_WHEN_IO_ENABLED = new InterestLifeCycleListenerWhenIoEnabled();
    private static final Logger logger = Grizzly.logger(SameThreadIOStrategy.class);

    private SameThreadIOStrategy() {
    }

    public static SameThreadIOStrategy getInstance() {
        return INSTANCE;
    }

    public boolean executeIoEvent(Connection connection, IOEvent ioEvent, boolean isIoEventEnabled) {
        IOEventLifeCycleListener listener = null;
        if (AbstractIOStrategy.isReadWrite(ioEvent)) {
            listener = isIoEventEnabled ? LIFECYCLE_LISTENER_WHEN_IO_ENABLED : LIFECYCLE_LISTENER_WHEN_IO_DISABLED;
        }
        AbstractIOStrategy.fireIOEvent(connection, ioEvent, listener, logger);
        return true;
    }

    public Executor getThreadPoolFor(Connection connection, IOEvent ioEvent) {
        return null;
    }

    public ThreadPoolConfig createDefaultWorkerPoolConfig(Transport transport) {
        return null;
    }

    public static final class InterestLifeCycleListenerWhenIoEnabled extends IOEventLifeCycleListener.Adapter {
        private InterestLifeCycleListenerWhenIoEnabled() {
        }

        public void onReregister(Context context) {
            onComplete(context, (Object) null);
        }

        public void onComplete(Context context, Object data) {
            if (context.wasSuspended() || context.isManualIOEventControl()) {
                IOEvent ioEvent = context.getIoEvent();
                Connection connection = context.getConnection();
                if (AsyncQueue.EXPECTING_MORE_OPTION.equals(data)) {
                    connection.simulateIOEvent(ioEvent);
                } else {
                    connection.enableIOEvent(ioEvent);
                }
            }
        }

        public void onContextSuspend(Context context) {
            if (!context.wasSuspended() && !context.isManualIOEventControl()) {
                disableIOEvent(context);
            }
        }

        public void onContextManualIOEventControl(Context context) {
            if (!context.wasSuspended() && !context.isManualIOEventControl()) {
                disableIOEvent(context);
            }
        }

        private static void disableIOEvent(Context context) {
            context.getConnection().disableIOEvent(context.getIoEvent());
        }
    }

    public static final class InterestLifeCycleListenerWhenIoDisabled extends IOEventLifeCycleListener.Adapter {
        private InterestLifeCycleListenerWhenIoDisabled() {
        }

        public void onReregister(Context context) {
            onComplete(context, (Object) null);
        }

        public void onComplete(Context context, Object data) {
            IOEvent ioEvent = context.getIoEvent();
            Connection connection = context.getConnection();
            if (AsyncQueue.EXPECTING_MORE_OPTION.equals(data)) {
                connection.simulateIOEvent(ioEvent);
            } else {
                connection.enableIOEvent(ioEvent);
            }
        }
    }
}

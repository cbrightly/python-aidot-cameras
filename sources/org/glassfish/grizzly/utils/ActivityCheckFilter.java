package org.glassfish.grizzly.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.utils.DelayedExecutor;

public class ActivityCheckFilter extends BaseFilter {
    public static final String ACTIVE_ATTRIBUTE_NAME = "connection-active-attribute";
    /* access modifiers changed from: private */
    public static final Attribute<ActiveRecord> IDLE_ATTR = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute(ACTIVE_ATTRIBUTE_NAME, new NullaryFunction<ActiveRecord>() {
        public ActiveRecord evaluate() {
            return new ActiveRecord();
        }
    });
    private static final Logger LOGGER = Grizzly.logger(ActivityCheckFilter.class);
    private final DelayedExecutor.DelayQueue<Connection> queue;
    private final long timeoutMillis;

    public interface TimeoutHandler {
        void onTimeout(Connection connection);
    }

    public ActivityCheckFilter(DelayedExecutor executor, long timeout, TimeUnit timeoutUnit) {
        this(executor, timeout, timeoutUnit, (TimeoutHandler) null);
    }

    public ActivityCheckFilter(DelayedExecutor executor, long timeout, TimeUnit timeoutUnit, TimeoutHandler handler) {
        this(executor, (DelayedExecutor.Worker<Connection>) new DefaultWorker(handler), timeout, timeoutUnit);
    }

    protected ActivityCheckFilter(DelayedExecutor executor, DelayedExecutor.Worker<Connection> worker, long timeout, TimeUnit timeoutUnit) {
        if (executor != null) {
            this.timeoutMillis = TimeUnit.MILLISECONDS.convert(timeout, timeoutUnit);
            this.queue = executor.createDelayQueue(worker, new Resolver());
            return;
        }
        throw new IllegalArgumentException("executor cannot be null");
    }

    public NextAction handleAccept(FilterChainContext ctx) {
        this.queue.add(ctx.getConnection(), this.timeoutMillis, TimeUnit.MILLISECONDS);
        return ctx.getInvokeAction();
    }

    public NextAction handleConnect(FilterChainContext ctx) {
        this.queue.add(ctx.getConnection(), this.timeoutMillis, TimeUnit.MILLISECONDS);
        return ctx.getInvokeAction();
    }

    public NextAction handleRead(FilterChainContext ctx) {
        long unused = IDLE_ATTR.get((AttributeStorage) ctx.getConnection()).timeoutMillis = System.currentTimeMillis() + this.timeoutMillis;
        return ctx.getInvokeAction();
    }

    public NextAction handleWrite(FilterChainContext ctx) {
        long unused = IDLE_ATTR.get((AttributeStorage) ctx.getConnection()).timeoutMillis = System.currentTimeMillis() + this.timeoutMillis;
        return ctx.getInvokeAction();
    }

    public NextAction handleClose(FilterChainContext ctx) {
        this.queue.remove(ctx.getConnection());
        return ctx.getInvokeAction();
    }

    public static DelayedExecutor createDefaultIdleDelayedExecutor() {
        return createDefaultIdleDelayedExecutor(1000, TimeUnit.MILLISECONDS);
    }

    public static DelayedExecutor createDefaultIdleDelayedExecutor(long checkInterval, TimeUnit checkIntervalUnit) {
        return new DelayedExecutor(Executors.newSingleThreadExecutor(new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread newThread = new Thread(r);
                newThread.setName("Grizzly-ActiveTimeoutFilter-IdleCheck");
                newThread.setDaemon(true);
                return newThread;
            }
        }), checkInterval > 0 ? checkInterval : 1000, checkIntervalUnit != null ? checkIntervalUnit : TimeUnit.MILLISECONDS);
    }

    public long getTimeout(TimeUnit timeunit) {
        return timeunit.convert(this.timeoutMillis, TimeUnit.MILLISECONDS);
    }

    public static final class Resolver implements DelayedExecutor.Resolver<Connection> {
        private Resolver() {
        }

        public boolean removeTimeout(Connection connection) {
            long unused = ((ActiveRecord) ActivityCheckFilter.IDLE_ATTR.get((AttributeStorage) connection)).timeoutMillis = 0;
            return true;
        }

        public long getTimeoutMillis(Connection connection) {
            return ((ActiveRecord) ActivityCheckFilter.IDLE_ATTR.get((AttributeStorage) connection)).timeoutMillis;
        }

        public void setTimeoutMillis(Connection connection, long timeoutMillis) {
            long unused = ((ActiveRecord) ActivityCheckFilter.IDLE_ATTR.get((AttributeStorage) connection)).timeoutMillis = timeoutMillis;
        }
    }

    public static final class ActiveRecord {
        /* access modifiers changed from: private */
        public volatile long timeoutMillis;

        private ActiveRecord() {
        }
    }

    public static final class DefaultWorker implements DelayedExecutor.Worker<Connection> {
        private final TimeoutHandler handler;

        DefaultWorker(TimeoutHandler handler2) {
            this.handler = handler2;
        }

        public boolean doWork(Connection connection) {
            TimeoutHandler timeoutHandler = this.handler;
            if (timeoutHandler != null) {
                timeoutHandler.onTimeout(connection);
            }
            connection.closeSilently();
            return true;
        }
    }
}

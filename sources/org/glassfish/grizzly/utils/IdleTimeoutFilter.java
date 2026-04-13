package org.glassfish.grizzly.utils;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.utils.DelayedExecutor;

public class IdleTimeoutFilter extends BaseFilter {
    public static final Long FOREVER;
    public static final Long FOREVER_SPECIAL;
    /* access modifiers changed from: private */
    public static final Attribute<IdleRecord> IDLE_ATTR = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute(IDLE_ATTRIBUTE_NAME, new NullaryFunction<IdleRecord>() {
        public IdleRecord evaluate() {
            return new IdleRecord();
        }
    });
    public static final String IDLE_ATTRIBUTE_NAME = "connection-idle-attribute";
    private final FilterChainContext.CompletionListener contextCompletionListener;
    private final DelayedExecutor.DelayQueue<Connection> queue;
    private final DelayedExecutor.Resolver<Connection> resolver;
    /* access modifiers changed from: private */
    public final TimeoutResolver timeoutResolver;

    public interface TimeoutHandler {
        void onTimeout(Connection connection);
    }

    public interface TimeoutResolver {
        long getTimeout(FilterChainContext filterChainContext);
    }

    static {
        Long valueOf = Long.valueOf(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
        FOREVER = valueOf;
        FOREVER_SPECIAL = Long.valueOf(valueOf.longValue() - 1);
    }

    public IdleTimeoutFilter(DelayedExecutor executor, long timeout, TimeUnit timeoutUnit) {
        this(executor, timeout, timeoutUnit, (TimeoutHandler) null);
    }

    public IdleTimeoutFilter(DelayedExecutor executor, TimeoutResolver timeoutResolver2) {
        this(executor, timeoutResolver2, (TimeoutHandler) null);
    }

    public IdleTimeoutFilter(DelayedExecutor executor, long timeout, TimeUnit timeUnit, TimeoutHandler handler) {
        this(executor, (DelayedExecutor.Worker<Connection>) new DefaultWorker(handler), (TimeoutResolver) new IdleTimeoutResolver(convertToMillis(timeout, timeUnit)));
    }

    public IdleTimeoutFilter(DelayedExecutor executor, TimeoutResolver timeoutResolver2, TimeoutHandler handler) {
        this(executor, (DelayedExecutor.Worker<Connection>) new DefaultWorker(handler), timeoutResolver2);
    }

    protected IdleTimeoutFilter(DelayedExecutor executor, DelayedExecutor.Worker<Connection> worker, TimeoutResolver timeoutResolver2) {
        this.contextCompletionListener = new ContextCompletionListener();
        if (executor != null) {
            this.timeoutResolver = timeoutResolver2;
            Resolver resolver2 = new Resolver();
            this.resolver = resolver2;
            this.queue = executor.createDelayQueue(worker, resolver2);
            return;
        }
        throw new IllegalArgumentException("executor cannot be null");
    }

    public NextAction handleAccept(FilterChainContext ctx) {
        this.queue.add(ctx.getConnection(), FOREVER.longValue(), TimeUnit.MILLISECONDS);
        queueAction(ctx);
        return ctx.getInvokeAction();
    }

    public NextAction handleConnect(FilterChainContext ctx) {
        this.queue.add(ctx.getConnection(), FOREVER.longValue(), TimeUnit.MILLISECONDS);
        queueAction(ctx);
        return ctx.getInvokeAction();
    }

    public NextAction handleRead(FilterChainContext ctx) {
        queueAction(ctx);
        return ctx.getInvokeAction();
    }

    public NextAction handleWrite(FilterChainContext ctx) {
        queueAction(ctx);
        return ctx.getInvokeAction();
    }

    public NextAction handleClose(FilterChainContext ctx) {
        this.queue.remove(ctx.getConnection());
        return ctx.getInvokeAction();
    }

    public DelayedExecutor.Resolver<Connection> getResolver() {
        return this.resolver;
    }

    public static DelayedExecutor createDefaultIdleDelayedExecutor() {
        return createDefaultIdleDelayedExecutor(1000, TimeUnit.MILLISECONDS);
    }

    public static DelayedExecutor createDefaultIdleDelayedExecutor(long checkInterval, TimeUnit checkIntervalUnit) {
        return new DelayedExecutor(Executors.newSingleThreadExecutor(new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread newThread = new Thread(r);
                newThread.setName("Grizzly-IdleTimeoutFilter-IdleCheck");
                newThread.setDaemon(true);
                return newThread;
            }
        }), checkInterval > 0 ? checkInterval : 1000, checkIntervalUnit != null ? checkIntervalUnit : TimeUnit.MILLISECONDS);
    }

    public static void setCustomTimeout(Connection connection, long timeout, TimeUnit timeunit) {
        IDLE_ATTR.get((AttributeStorage) connection).setInitialTimeoutMillis(convertToMillis(timeout, timeunit));
    }

    /* access modifiers changed from: protected */
    public void queueAction(FilterChainContext ctx) {
        IdleRecord idleRecord = IDLE_ATTR.get((AttributeStorage) ctx.getConnection());
        if (IdleRecord.counterUpdater.getAndIncrement(idleRecord) == 0) {
            long unused = idleRecord.timeoutMillis = FOREVER.longValue();
        }
        ctx.addCompletionListener(this.contextCompletionListener);
    }

    private static long convertToMillis(long time, TimeUnit timeUnit) {
        return time >= 0 ? TimeUnit.MILLISECONDS.convert(time, timeUnit) : FOREVER.longValue();
    }

    public final class ContextCompletionListener implements FilterChainContext.CompletionListener {
        private ContextCompletionListener() {
        }

        public void onComplete(FilterChainContext ctx) {
            long timeoutToSet;
            IdleRecord idleRecord = (IdleRecord) IdleTimeoutFilter.IDLE_ATTR.get((AttributeStorage) ctx.getConnection());
            Long l = IdleTimeoutFilter.FOREVER_SPECIAL;
            long unused = idleRecord.timeoutMillis = l.longValue();
            if (idleRecord.isClosed || IdleRecord.counterUpdater.decrementAndGet(idleRecord) == 0) {
                if (idleRecord.isClosed) {
                    IdleRecord.counterUpdater.set(idleRecord, 0);
                    timeoutToSet = 0;
                } else {
                    long timeout = IdleTimeoutFilter.this.timeoutResolver.getTimeout(ctx);
                    Long l2 = IdleTimeoutFilter.FOREVER;
                    timeoutToSet = timeout == l2.longValue() ? l2.longValue() : System.currentTimeMillis() + timeout;
                }
                IdleRecord.timeoutMillisUpdater.compareAndSet(idleRecord, l.longValue(), timeoutToSet);
            }
        }
    }

    public static final class IdleTimeoutResolver implements TimeoutResolver {
        private final long defaultTimeoutMillis;

        IdleTimeoutResolver(long defaultTimeoutMillis2) {
            this.defaultTimeoutMillis = defaultTimeoutMillis2;
        }

        public long getTimeout(FilterChainContext ctx) {
            return ((IdleRecord) IdleTimeoutFilter.IDLE_ATTR.get((AttributeStorage) ctx.getConnection())).getInitialTimeoutMillis(this.defaultTimeoutMillis);
        }
    }

    public static final class Resolver implements DelayedExecutor.Resolver<Connection> {
        private Resolver() {
        }

        public boolean removeTimeout(Connection connection) {
            ((IdleRecord) IdleTimeoutFilter.IDLE_ATTR.get((AttributeStorage) connection)).close();
            return true;
        }

        public long getTimeoutMillis(Connection connection) {
            return ((IdleRecord) IdleTimeoutFilter.IDLE_ATTR.get((AttributeStorage) connection)).timeoutMillis;
        }

        public void setTimeoutMillis(Connection connection, long timeoutMillis) {
            long unused = ((IdleRecord) IdleTimeoutFilter.IDLE_ATTR.get((AttributeStorage) connection)).timeoutMillis = timeoutMillis;
        }
    }

    public static final class IdleRecord {
        /* access modifiers changed from: private */
        public static final AtomicIntegerFieldUpdater<IdleRecord> counterUpdater;
        /* access modifiers changed from: private */
        public static final AtomicLongFieldUpdater<IdleRecord> timeoutMillisUpdater;
        private volatile int counter;
        private long initialTimeoutMillis;
        /* access modifiers changed from: private */
        public boolean isClosed;
        private volatile boolean isInitialSet;
        /* access modifiers changed from: private */
        public volatile long timeoutMillis;

        private IdleRecord() {
        }

        static {
            Class<IdleRecord> cls = IdleRecord.class;
            timeoutMillisUpdater = AtomicLongFieldUpdater.newUpdater(cls, "timeoutMillis");
            counterUpdater = AtomicIntegerFieldUpdater.newUpdater(cls, "counter");
        }

        /* access modifiers changed from: private */
        public long getInitialTimeoutMillis(long defaultTimeoutMillis) {
            return this.isInitialSet ? this.initialTimeoutMillis : defaultTimeoutMillis;
        }

        /* access modifiers changed from: private */
        public void setInitialTimeoutMillis(long initialTimeoutMillis2) {
            this.initialTimeoutMillis = initialTimeoutMillis2;
            this.isInitialSet = true;
        }

        /* access modifiers changed from: private */
        public void close() {
            this.isClosed = true;
            this.timeoutMillis = 0;
        }
    }

    public static final class DefaultWorker implements DelayedExecutor.Worker<Connection> {
        private final TimeoutHandler handler;

        DefaultWorker(TimeoutHandler handler2) {
            this.handler = handler2;
        }

        public boolean doWork(Connection connection) {
            if (!connection.isOpen()) {
                return true;
            }
            TimeoutHandler timeoutHandler = this.handler;
            if (timeoutHandler != null) {
                timeoutHandler.onTimeout(connection);
            }
            connection.closeSilently();
            return true;
        }
    }
}

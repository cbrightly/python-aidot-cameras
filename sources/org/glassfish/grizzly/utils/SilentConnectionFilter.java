package org.glassfish.grizzly.utils;

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

public final class SilentConnectionFilter extends BaseFilter {
    private static final String ATTR_NAME;
    private static final Logger LOGGER;
    public static final long UNLIMITED_TIMEOUT = -1;
    public static final long UNSET_TIMEOUT = 0;
    /* access modifiers changed from: private */
    public static final Attribute<Long> silentConnectionAttr;
    private final DelayedExecutor.DelayQueue<Connection> queue;
    private final long timeoutMillis;

    static {
        Class<SilentConnectionFilter> cls = SilentConnectionFilter.class;
        LOGGER = Grizzly.logger(cls);
        String str = cls.getName() + ".silent-connection-attr";
        ATTR_NAME = str;
        silentConnectionAttr = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute(str);
    }

    public SilentConnectionFilter(DelayedExecutor executor, long timeout, TimeUnit timeunit) {
        this.timeoutMillis = TimeUnit.MILLISECONDS.convert(timeout, timeunit);
        this.queue = executor.createDelayQueue(new DelayedExecutor.Worker<Connection>() {
            public boolean doWork(Connection connection) {
                connection.closeSilently();
                return true;
            }
        }, new Resolver());
    }

    public long getTimeout(TimeUnit timeunit) {
        return timeunit.convert(this.timeoutMillis, TimeUnit.MILLISECONDS);
    }

    public NextAction handleAccept(FilterChainContext ctx) {
        this.queue.add(ctx.getConnection(), this.timeoutMillis, TimeUnit.MILLISECONDS);
        return ctx.getInvokeAction();
    }

    public NextAction handleRead(FilterChainContext ctx) {
        this.queue.remove(ctx.getConnection());
        return ctx.getInvokeAction();
    }

    public NextAction handleWrite(FilterChainContext ctx) {
        this.queue.remove(ctx.getConnection());
        return ctx.getInvokeAction();
    }

    public NextAction handleClose(FilterChainContext ctx) {
        this.queue.remove(ctx.getConnection());
        return ctx.getInvokeAction();
    }

    public static final class Resolver implements DelayedExecutor.Resolver<Connection> {
        private Resolver() {
        }

        public boolean removeTimeout(Connection connection) {
            return SilentConnectionFilter.silentConnectionAttr.remove((AttributeStorage) connection) != null;
        }

        public long getTimeoutMillis(Connection connection) {
            Long timeout = (Long) SilentConnectionFilter.silentConnectionAttr.get((AttributeStorage) connection);
            if (timeout != null) {
                return timeout.longValue();
            }
            return -1;
        }

        public void setTimeoutMillis(Connection connection, long timeoutMillis) {
            SilentConnectionFilter.silentConnectionAttr.set((AttributeStorage) connection, Long.valueOf(timeoutMillis));
        }
    }
}

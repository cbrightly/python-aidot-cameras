package org.glassfish.grizzly.filterchain;

import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Transport;

public class TransportFilter extends BaseFilter {
    private static final FlushEvent FLUSH_EVENT = new FlushEvent();

    public static FilterChainEvent createFlushEvent() {
        return FLUSH_EVENT;
    }

    public static FilterChainEvent createFlushEvent(CompletionHandler completionHandler) {
        if (completionHandler == null) {
            return FLUSH_EVENT;
        }
        return new FlushEvent(completionHandler);
    }

    public static final class FlushEvent implements FilterChainEvent {
        public static final Object TYPE = FlushEvent.class;
        final CompletionHandler completionHandler;

        private FlushEvent() {
            this((CompletionHandler) null);
        }

        private FlushEvent(CompletionHandler completionHandler2) {
            this.completionHandler = completionHandler2;
        }

        public Object type() {
            return TYPE;
        }

        public CompletionHandler getCompletionHandler() {
            return this.completionHandler;
        }
    }

    public NextAction handleAccept(FilterChainContext ctx) {
        Filter transportFilter0 = getTransportFilter0(ctx.getConnection().getTransport());
        if (transportFilter0 != null) {
            return transportFilter0.handleAccept(ctx);
        }
        return null;
    }

    public NextAction handleConnect(FilterChainContext ctx) {
        Filter transportFilter0 = getTransportFilter0(ctx.getConnection().getTransport());
        if (transportFilter0 != null) {
            return transportFilter0.handleConnect(ctx);
        }
        return null;
    }

    public NextAction handleRead(FilterChainContext ctx) {
        Filter transportFilter0 = getTransportFilter0(ctx.getConnection().getTransport());
        if (transportFilter0 != null) {
            return transportFilter0.handleRead(ctx);
        }
        return null;
    }

    public NextAction handleWrite(FilterChainContext ctx) {
        Filter transportFilter0 = getTransportFilter0(ctx.getConnection().getTransport());
        if (transportFilter0 != null) {
            return transportFilter0.handleWrite(ctx);
        }
        return null;
    }

    public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) {
        Filter transportFilter0 = getTransportFilter0(ctx.getConnection().getTransport());
        if (transportFilter0 != null) {
            return transportFilter0.handleEvent(ctx, event);
        }
        return null;
    }

    public NextAction handleClose(FilterChainContext ctx) {
        Filter transportFilter0 = getTransportFilter0(ctx.getConnection().getTransport());
        if (transportFilter0 != null) {
            return transportFilter0.handleClose(ctx);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public Filter getTransportFilter0(Transport transport) {
        if (transport instanceof FilterChainEnabledTransport) {
            return ((FilterChainEnabledTransport) transport).getTransportFilter();
        }
        return null;
    }
}

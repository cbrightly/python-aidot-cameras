package org.glassfish.grizzly.filterchain;

import java.util.EnumSet;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.IOEvent;

public abstract class AbstractFilterChain implements FilterChain {
    protected final EnumSet<IOEvent> interestedIoEventsMask = EnumSet.allOf(IOEvent.class);

    public int indexOfType(Class<? extends Filter> filterType) {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (filterType.isAssignableFrom(((Filter) get(i)).getClass())) {
                return i;
            }
        }
        return -1;
    }

    public boolean isInterested(IOEvent ioEvent) {
        return this.interestedIoEventsMask.contains(ioEvent);
    }

    public void setInterested(IOEvent ioEvent, boolean isInterested) {
        if (isInterested) {
            this.interestedIoEventsMask.add(ioEvent);
        } else {
            this.interestedIoEventsMask.remove(ioEvent);
        }
    }

    public final FilterChainContext obtainFilterChainContext(Connection connection) {
        FilterChainContext context = FilterChainContext.create(connection);
        context.internalContext.setProcessor(this);
        return context;
    }

    public FilterChainContext obtainFilterChainContext(Connection connection, Closeable closeable) {
        FilterChainContext context = FilterChainContext.create(connection, closeable);
        context.internalContext.setProcessor(this);
        return context;
    }

    public FilterChainContext obtainFilterChainContext(Connection connection, int startIdx, int endIdx, int currentIdx) {
        FilterChainContext ctx = obtainFilterChainContext(connection);
        ctx.setStartIdx(startIdx);
        ctx.setEndIdx(endIdx);
        ctx.setFilterIdx(currentIdx);
        return ctx;
    }

    public FilterChainContext obtainFilterChainContext(Connection connection, Closeable closeable, int startIdx, int endIdx, int currentIdx) {
        FilterChainContext ctx = obtainFilterChainContext(connection, closeable);
        ctx.setStartIdx(startIdx);
        ctx.setEndIdx(endIdx);
        ctx.setFilterIdx(currentIdx);
        return ctx;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(256);
        sb.append(getClass().getSimpleName());
        sb.append('@');
        sb.append(Integer.toHexString(hashCode()));
        sb.append(" {");
        int size = size();
        if (size > 0) {
            sb.append(((Filter) get(0)).toString());
            for (int i = 1; i < size; i++) {
                sb.append(" <-> ");
                sb.append(((Filter) get(i)).toString());
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public final Context obtainContext(Connection connection) {
        return obtainFilterChainContext(connection).internalContext;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        clear();
        super.finalize();
    }
}

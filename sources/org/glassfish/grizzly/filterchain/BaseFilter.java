package org.glassfish.grizzly.filterchain;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.filterchain.FilterChainContext;

public class BaseFilter implements Filter {
    public void onAdded(FilterChain filterChain) {
    }

    public void onFilterChainChanged(FilterChain filterChain) {
    }

    public void onRemoved(FilterChain filterChain) {
    }

    public NextAction handleRead(FilterChainContext ctx) {
        return ctx.getInvokeAction();
    }

    public NextAction handleWrite(FilterChainContext ctx) {
        return ctx.getInvokeAction();
    }

    public NextAction handleConnect(FilterChainContext ctx) {
        return ctx.getInvokeAction();
    }

    public NextAction handleAccept(FilterChainContext ctx) {
        return ctx.getInvokeAction();
    }

    public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) {
        return ctx.getInvokeAction();
    }

    public NextAction handleClose(FilterChainContext ctx) {
        return ctx.getInvokeAction();
    }

    public void exceptionOccurred(FilterChainContext ctx, Throwable error) {
    }

    public FilterChainContext createContext(Connection connection, FilterChainContext.Operation operation) {
        FilterChain filterChain = (FilterChain) connection.getProcessor();
        FilterChainContext ctx = filterChain.obtainFilterChainContext(connection);
        int idx = filterChain.indexOf(this);
        ctx.setOperation(operation);
        ctx.setFilterIdx(idx);
        ctx.setStartIdx(idx);
        return ctx;
    }

    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}

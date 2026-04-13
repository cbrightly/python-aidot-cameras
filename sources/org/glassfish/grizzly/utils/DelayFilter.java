package org.glassfish.grizzly.utils;

import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;

public class DelayFilter extends BaseFilter {
    private final long readTimeoutMillis;
    private final long writeTimeoutMillis;

    public DelayFilter(long readTimeoutMillis2, long writeTimeoutMillis2) {
        this.readTimeoutMillis = readTimeoutMillis2;
        this.writeTimeoutMillis = writeTimeoutMillis2;
    }

    public NextAction handleRead(FilterChainContext ctx) {
        try {
            Thread.sleep(this.readTimeoutMillis);
        } catch (Exception e) {
        }
        return ctx.getInvokeAction();
    }

    public NextAction handleWrite(FilterChainContext ctx) {
        try {
            Thread.sleep(this.writeTimeoutMillis);
        } catch (Exception e) {
        }
        return ctx.getInvokeAction();
    }
}

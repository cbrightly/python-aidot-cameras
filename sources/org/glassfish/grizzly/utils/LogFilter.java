package org.glassfish.grizzly.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;

public class LogFilter extends BaseFilter {
    private final Level level;
    private final Logger logger;

    public LogFilter() {
        this((Logger) null, (Level) null);
    }

    public LogFilter(Logger logger2) {
        this(logger2, (Level) null);
    }

    public LogFilter(Logger logger2, Level level2) {
        if (logger2 != null) {
            this.logger = logger2;
        } else {
            this.logger = Grizzly.logger(LogFilter.class);
        }
        if (level2 != null) {
            this.level = level2;
        } else {
            this.level = Level.INFO;
        }
    }

    public Logger getLogger() {
        return this.logger;
    }

    public Level getLevel() {
        return this.level;
    }

    public void onAdded(FilterChain filterChain) {
        this.logger.log(this.level, "LogFilter onAdded");
    }

    public void onRemoved(FilterChain filterChain) {
        this.logger.log(this.level, "LogFilter onRemoved");
    }

    public void onFilterChainChanged(FilterChain filterChain) {
        this.logger.log(this.level, "LogFilter onFilterChainChanged");
    }

    public NextAction handleRead(FilterChainContext ctx) {
        this.logger.log(this.level, "LogFilter handleRead. Connection={0} message={1}", new Object[]{ctx.getConnection(), ctx.getMessage()});
        return ctx.getInvokeAction();
    }

    public NextAction handleWrite(FilterChainContext ctx) {
        this.logger.log(this.level, "LogFilter handleWrite. Connection={0} message={1}", new Object[]{ctx.getConnection(), ctx.getMessage()});
        return ctx.getInvokeAction();
    }

    public NextAction handleConnect(FilterChainContext ctx) {
        this.logger.log(this.level, "LogFilter handleConnect. Connection={0} message={1}", new Object[]{ctx.getConnection(), ctx.getMessage()});
        return ctx.getInvokeAction();
    }

    public NextAction handleAccept(FilterChainContext ctx) {
        this.logger.log(this.level, "LogFilter handleAccept. Connection={0} message={1}", new Object[]{ctx.getConnection(), ctx.getMessage()});
        return ctx.getInvokeAction();
    }

    public NextAction handleClose(FilterChainContext ctx) {
        this.logger.log(this.level, "LogFilter handleClose. Connection={0} message={1}", new Object[]{ctx.getConnection(), ctx.getMessage()});
        return ctx.getInvokeAction();
    }

    public void exceptionOccurred(FilterChainContext ctx, Throwable error) {
        this.logger.log(this.level, "LogFilter exceptionOccured. Connection={0} message={1}", new Object[]{ctx.getConnection(), ctx.getMessage()});
    }
}

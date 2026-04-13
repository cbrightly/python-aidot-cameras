package org.glassfish.grizzly.filterchain;

import java.util.List;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.Processor;
import org.glassfish.grizzly.ProcessorResult;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.WriteResult;

public interface FilterChain extends Processor<Context>, List<Filter> {
    ProcessorResult execute(FilterChainContext filterChainContext);

    void fail(FilterChainContext filterChainContext, Throwable th);

    void fireEventDownstream(Connection connection, FilterChainEvent filterChainEvent, CompletionHandler<FilterChainContext> completionHandler);

    void fireEventUpstream(Connection connection, FilterChainEvent filterChainEvent, CompletionHandler<FilterChainContext> completionHandler);

    void flush(Connection connection, CompletionHandler<WriteResult> completionHandler);

    int indexOfType(Class<? extends Filter> cls);

    FilterChainContext obtainFilterChainContext(Connection connection);

    FilterChainContext obtainFilterChainContext(Connection connection, int i, int i2, int i3);

    FilterChainContext obtainFilterChainContext(Connection connection, Closeable closeable);

    FilterChainContext obtainFilterChainContext(Connection connection, Closeable closeable, int i, int i2, int i3);

    ReadResult read(FilterChainContext filterChainContext);
}

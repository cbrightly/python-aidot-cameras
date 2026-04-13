package org.glassfish.grizzly.filterchain;

public interface Filter {
    void exceptionOccurred(FilterChainContext filterChainContext, Throwable th);

    NextAction handleAccept(FilterChainContext filterChainContext);

    NextAction handleClose(FilterChainContext filterChainContext);

    NextAction handleConnect(FilterChainContext filterChainContext);

    NextAction handleEvent(FilterChainContext filterChainContext, FilterChainEvent filterChainEvent);

    NextAction handleRead(FilterChainContext filterChainContext);

    NextAction handleWrite(FilterChainContext filterChainContext);

    void onAdded(FilterChain filterChain);

    void onFilterChainChanged(FilterChain filterChain);

    void onRemoved(FilterChain filterChain);
}

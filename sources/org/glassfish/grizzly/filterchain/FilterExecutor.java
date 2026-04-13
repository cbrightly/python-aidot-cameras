package org.glassfish.grizzly.filterchain;

public interface FilterExecutor {
    int defaultEndIdx(FilterChainContext filterChainContext);

    int defaultStartIdx(FilterChainContext filterChainContext);

    NextAction execute(Filter filter, FilterChainContext filterChainContext);

    int getNextFilter(FilterChainContext filterChainContext);

    int getPreviousFilter(FilterChainContext filterChainContext);

    boolean hasNextFilter(FilterChainContext filterChainContext, int i);

    boolean hasPreviousFilter(FilterChainContext filterChainContext, int i);

    void initIndexes(FilterChainContext filterChainContext);

    boolean isDownstream();

    boolean isUpstream();
}

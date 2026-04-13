package org.glassfish.grizzly.filterchain;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.Processor;
import org.glassfish.grizzly.ProcessorSelector;

public class FilterChainProcessorSelector implements ProcessorSelector {
    protected final FilterChainBuilder builder;

    public FilterChainProcessorSelector(FilterChainBuilder builder2) {
        this.builder = builder2;
    }

    public Processor select(IOEvent ioEvent, Connection connection) {
        FilterChain chain = this.builder.build();
        if (chain.isInterested(ioEvent)) {
            return chain;
        }
        return null;
    }
}

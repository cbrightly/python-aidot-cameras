package org.glassfish.grizzly;

public interface ProcessorSelector {
    Processor select(IOEvent iOEvent, Connection connection);
}

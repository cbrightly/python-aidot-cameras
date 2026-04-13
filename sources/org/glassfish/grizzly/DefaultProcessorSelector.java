package org.glassfish.grizzly;

public class DefaultProcessorSelector implements ProcessorSelector {
    protected final Transport transport;

    public DefaultProcessorSelector(Transport transport2) {
        this.transport = transport2;
    }

    public Processor select(IOEvent ioEvent, Connection connection) {
        Processor eventProcessor = connection.getProcessor();
        if (eventProcessor != null && eventProcessor.isInterested(ioEvent)) {
            return eventProcessor;
        }
        ProcessorSelector processorSelector = connection.getProcessorSelector();
        if (processorSelector != null) {
            return processorSelector.select(ioEvent, connection);
        }
        return null;
    }
}

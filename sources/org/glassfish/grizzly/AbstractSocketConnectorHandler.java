package org.glassfish.grizzly;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.List;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;

public abstract class AbstractSocketConnectorHandler implements SocketConnectorHandler {
    protected final List<ConnectionProbe> probes = new LinkedList();
    private Processor processor;
    private ProcessorSelector processorSelector;
    protected final Transport transport;

    /* access modifiers changed from: protected */
    public abstract FutureImpl<Connection> connectAsync(SocketAddress socketAddress, SocketAddress socketAddress2, CompletionHandler<Connection> completionHandler, boolean z);

    public AbstractSocketConnectorHandler(Transport transport2) {
        this.transport = transport2;
        this.processor = transport2.getProcessor();
        this.processorSelector = transport2.getProcessorSelector();
    }

    public GrizzlyFuture<Connection> connect(String host, int port) {
        return connect((SocketAddress) new InetSocketAddress(host, port));
    }

    public GrizzlyFuture<Connection> connect(SocketAddress remoteAddress) {
        return connect(remoteAddress, (SocketAddress) null);
    }

    public void connect(SocketAddress remoteAddress, CompletionHandler<Connection> completionHandler) {
        connect(remoteAddress, (SocketAddress) null, completionHandler);
    }

    public GrizzlyFuture<Connection> connect(SocketAddress remoteAddress, SocketAddress localAddress) {
        return connectAsync(remoteAddress, localAddress, (CompletionHandler<Connection>) null, true);
    }

    public void connect(SocketAddress remoteAddress, SocketAddress localAddress, CompletionHandler<Connection> completionHandler) {
        connectAsync(remoteAddress, localAddress, completionHandler, false);
    }

    public Processor getProcessor() {
        return this.processor;
    }

    public void setProcessor(Processor processor2) {
        this.processor = processor2;
    }

    public ProcessorSelector getProcessorSelector() {
        return this.processorSelector;
    }

    public void setProcessorSelector(ProcessorSelector processorSelector2) {
        this.processorSelector = processorSelector2;
    }

    public void addMonitoringProbe(ConnectionProbe probe) {
        this.probes.add(probe);
    }

    public boolean removeMonitoringProbe(ConnectionProbe probe) {
        return this.probes.remove(probe);
    }

    public ConnectionProbe[] getMonitoringProbes() {
        List<ConnectionProbe> list = this.probes;
        return (ConnectionProbe[]) list.toArray(new ConnectionProbe[list.size()]);
    }

    /* access modifiers changed from: protected */
    public void preConfigure(Connection connection) {
    }

    /* access modifiers changed from: protected */
    public FutureImpl<Connection> makeCancellableFuture(final Connection connection) {
        return new SafeFutureImpl<Connection>() {
            /* access modifiers changed from: protected */
            public void onComplete() {
                try {
                    if (!isCancelled()) {
                        get();
                        return;
                    }
                } catch (Throwable th) {
                }
                try {
                    connection.closeSilently();
                } catch (Exception e) {
                }
            }
        };
    }

    public static abstract class Builder<E extends Builder> {
        protected ConnectionProbe connectionProbe;
        protected Processor processor;
        protected ProcessorSelector processorSelector;

        /* access modifiers changed from: protected */
        public abstract AbstractSocketConnectorHandler create();

        public E processor(Processor processor2) {
            this.processor = processor2;
            return this;
        }

        public E processorSelector(ProcessorSelector processorSelector2) {
            this.processorSelector = processorSelector2;
            return this;
        }

        public E probe(ConnectionProbe connectionProbe2) {
            this.connectionProbe = connectionProbe2;
            return this;
        }

        public AbstractSocketConnectorHandler build() {
            AbstractSocketConnectorHandler handler = create();
            Processor processor2 = this.processor;
            if (processor2 != null) {
                handler.setProcessor(processor2);
            }
            ProcessorSelector processorSelector2 = this.processorSelector;
            if (processorSelector2 != null) {
                handler.setProcessorSelector(processorSelector2);
            }
            ConnectionProbe connectionProbe2 = this.connectionProbe;
            if (connectionProbe2 != null) {
                handler.addMonitoringProbe(connectionProbe2);
            }
            return handler;
        }
    }
}

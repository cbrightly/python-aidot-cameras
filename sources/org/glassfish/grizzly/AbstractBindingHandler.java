package org.glassfish.grizzly;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.Channel;
import java.util.Random;
import org.glassfish.grizzly.nio.NIOTransport;

public abstract class AbstractBindingHandler implements SocketBinder {
    protected static final Random RANDOM = new Random();
    protected Processor processor;
    protected ProcessorSelector processorSelector;
    protected final NIOTransport transport;

    public AbstractBindingHandler(NIOTransport transport2) {
        this.transport = transport2;
        this.processor = transport2.getProcessor();
        this.processorSelector = transport2.getProcessorSelector();
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

    public Connection<?> bind(int port) {
        return bind((SocketAddress) new InetSocketAddress(port));
    }

    public Connection<?> bind(String host, int port) {
        return bind((SocketAddress) new InetSocketAddress(host, port));
    }

    public Connection<?> bind(String host, int port, int backlog) {
        return bind((SocketAddress) new InetSocketAddress(host, port), backlog);
    }

    public Connection<?> bind(String host, PortRange portRange, int backlog) {
        return bind(host, portRange, true, backlog);
    }

    public Connection<?> bind(String host, PortRange portRange, boolean randomStartPort, int backlog) {
        int initialOffset;
        int lower = portRange.getLower();
        int range = (portRange.getUpper() - lower) + 1;
        if (randomStartPort) {
            initialOffset = RANDOM.nextInt(range);
        } else {
            initialOffset = 0;
        }
        int offset = initialOffset;
        do {
            try {
                return bind(host, lower + offset, backlog);
            } catch (IOException e) {
                offset = (offset + 1) % range;
                if (offset == initialOffset) {
                    throw new BindException(String.format("Couldn't bind to any port in the range `%s`.", new Object[]{portRange.toString()}));
                }
            }
        } while (offset == initialOffset);
        throw new BindException(String.format("Couldn't bind to any port in the range `%s`.", new Object[]{portRange.toString()}));
    }

    public final void unbindAll() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public <T> T getSystemInheritedChannel(Class<?> channelType) {
        Channel inheritedChannel = System.inheritedChannel();
        if (inheritedChannel == null) {
            throw new IOException("Inherited channel is not set");
        } else if (channelType.isInstance(inheritedChannel)) {
            return inheritedChannel;
        } else {
            throw new IOException("Inherited channel is not " + channelType.getName() + ", but " + inheritedChannel.getClass().getName());
        }
    }

    public static abstract class Builder<E extends Builder> {
        protected Processor processor;
        protected ProcessorSelector processorSelector;

        /* access modifiers changed from: protected */
        public abstract AbstractBindingHandler create();

        public E processor(Processor processor2) {
            this.processor = processor2;
            return this;
        }

        public E processorSelector(ProcessorSelector processorSelector2) {
            this.processorSelector = processorSelector2;
            return this;
        }

        public AbstractBindingHandler build() {
            AbstractBindingHandler bindingHandler = create();
            Processor processor2 = this.processor;
            if (processor2 != null) {
                bindingHandler.setProcessor(processor2);
            }
            ProcessorSelector processorSelector2 = this.processorSelector;
            if (processorSelector2 != null) {
                bindingHandler.setProcessorSelector(processorSelector2);
            }
            return bindingHandler;
        }
    }
}

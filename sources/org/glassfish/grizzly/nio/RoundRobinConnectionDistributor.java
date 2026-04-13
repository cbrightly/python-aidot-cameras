package org.glassfish.grizzly.nio;

import java.nio.channels.SelectableChannel;
import java.util.concurrent.atomic.AtomicInteger;
import org.glassfish.grizzly.CompletionHandler;

public final class RoundRobinConnectionDistributor extends AbstractNIOConnectionDistributor {
    private final Iterator it;

    public interface Iterator {
        SelectorRunner next();

        SelectorRunner nextService();
    }

    public RoundRobinConnectionDistributor(NIOTransport transport) {
        this(transport, false, false);
    }

    public RoundRobinConnectionDistributor(NIOTransport transport, boolean useDedicatedAcceptor) {
        this(transport, useDedicatedAcceptor, false);
    }

    public RoundRobinConnectionDistributor(NIOTransport transport, boolean useDedicatedAcceptor, boolean isServerOnly) {
        super(transport);
        this.it = useDedicatedAcceptor ? isServerOnly ? new ServDedicatedIterator() : new DedicatedIterator() : isServerOnly ? new ServSharedIterator() : new SharedIterator();
    }

    public void registerChannel(SelectableChannel channel, int interestOps, Object attachment) {
        this.transport.getSelectorHandler().registerChannel(this.it.next(), channel, interestOps, attachment);
    }

    public void registerChannelAsync(SelectableChannel channel, int interestOps, Object attachment, CompletionHandler<RegisterChannelResult> completionHandler) {
        this.transport.getSelectorHandler().registerChannelAsync(this.it.next(), channel, interestOps, attachment, completionHandler);
    }

    public void registerServiceChannelAsync(SelectableChannel channel, int interestOps, Object attachment, CompletionHandler<RegisterChannelResult> completionHandler) {
        this.transport.getSelectorHandler().registerChannelAsync(this.it.nextService(), channel, interestOps, attachment, completionHandler);
    }

    public final class DedicatedIterator implements Iterator {
        private final AtomicInteger counter;

        private DedicatedIterator() {
            this.counter = new AtomicInteger();
        }

        public SelectorRunner next() {
            SelectorRunner[] runners = RoundRobinConnectionDistributor.this.getTransportSelectorRunners();
            if (runners.length == 1) {
                return runners[0];
            }
            return runners[((this.counter.getAndIncrement() & Integer.MAX_VALUE) % (runners.length - 1)) + 1];
        }

        public SelectorRunner nextService() {
            return RoundRobinConnectionDistributor.this.getTransportSelectorRunners()[0];
        }
    }

    public final class SharedIterator implements Iterator {
        private final AtomicInteger counter;

        private SharedIterator() {
            this.counter = new AtomicInteger();
        }

        public SelectorRunner next() {
            SelectorRunner[] runners = RoundRobinConnectionDistributor.this.getTransportSelectorRunners();
            if (runners.length == 1) {
                return runners[0];
            }
            return runners[(this.counter.getAndIncrement() & Integer.MAX_VALUE) % runners.length];
        }

        public SelectorRunner nextService() {
            return next();
        }
    }

    public final class ServDedicatedIterator implements Iterator {
        private int counter;

        private ServDedicatedIterator() {
        }

        public SelectorRunner next() {
            SelectorRunner[] runners = RoundRobinConnectionDistributor.this.getTransportSelectorRunners();
            if (runners.length == 1) {
                return runners[0];
            }
            int i = this.counter;
            this.counter = i + 1;
            return runners[((i & Integer.MAX_VALUE) % (runners.length - 1)) + 1];
        }

        public SelectorRunner nextService() {
            return RoundRobinConnectionDistributor.this.getTransportSelectorRunners()[0];
        }
    }

    public final class ServSharedIterator implements Iterator {
        private int counter;

        private ServSharedIterator() {
        }

        public SelectorRunner next() {
            SelectorRunner[] runners = RoundRobinConnectionDistributor.this.getTransportSelectorRunners();
            if (runners.length == 1) {
                return runners[0];
            }
            int i = this.counter;
            this.counter = i + 1;
            return runners[(i & Integer.MAX_VALUE) % runners.length];
        }

        public SelectorRunner nextService() {
            return next();
        }
    }
}

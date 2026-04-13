package org.glassfish.grizzly.nio;

import java.nio.channels.SelectableChannel;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.utils.Futures;

public abstract class AbstractNIOConnectionDistributor implements NIOChannelDistributor {
    protected final NIOTransport transport;

    public AbstractNIOConnectionDistributor(NIOTransport transport2) {
        this.transport = transport2;
    }

    public final void registerChannel(SelectableChannel channel) {
        registerChannel(channel, 0, (Object) null);
    }

    public final void registerChannel(SelectableChannel channel, int interestOps) {
        registerChannel(channel, interestOps, (Object) null);
    }

    public final GrizzlyFuture<RegisterChannelResult> registerChannelAsync(SelectableChannel channel) {
        return registerChannelAsync(channel, 0, (Object) null);
    }

    public final GrizzlyFuture<RegisterChannelResult> registerChannelAsync(SelectableChannel channel, int interestOps) {
        return registerChannelAsync(channel, interestOps, (Object) null);
    }

    public final GrizzlyFuture<RegisterChannelResult> registerChannelAsync(SelectableChannel channel, int interestOps, Object attachment) {
        FutureImpl<RegisterChannelResult> future = Futures.createSafeFuture();
        registerChannelAsync(channel, interestOps, attachment, Futures.toCompletionHandler(future));
        return future;
    }

    /* access modifiers changed from: protected */
    public SelectorRunner[] getTransportSelectorRunners() {
        return this.transport.getSelectorRunners();
    }
}

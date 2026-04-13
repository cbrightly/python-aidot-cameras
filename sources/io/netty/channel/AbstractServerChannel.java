package io.netty.channel;

import io.netty.channel.AbstractChannel;
import java.net.SocketAddress;

public abstract class AbstractServerChannel extends AbstractChannel implements ServerChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);

    protected AbstractServerChannel() {
        super((Channel) null);
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public SocketAddress remoteAddress() {
        return null;
    }

    /* access modifiers changed from: protected */
    public SocketAddress remoteAddress0() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void doDisconnect() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public AbstractChannel.AbstractUnsafe newUnsafe() {
        return new DefaultServerUnsafe();
    }

    /* access modifiers changed from: protected */
    public void doWrite(ChannelOutboundBuffer in) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public final Object filterOutboundMessage(Object msg) {
        throw new UnsupportedOperationException();
    }

    public final class DefaultServerUnsafe extends AbstractChannel.AbstractUnsafe {
        private DefaultServerUnsafe() {
            super();
        }

        public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
            safeSetFailure(promise, new UnsupportedOperationException());
        }
    }
}

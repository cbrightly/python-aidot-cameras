package io.netty.channel.oio;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.ThreadPerChannelEventLoop;
import java.net.SocketAddress;

public abstract class AbstractOioChannel extends AbstractChannel {
    protected static final int SO_TIMEOUT = 1000;
    private volatile boolean readPending;
    private final Runnable readTask = new Runnable() {
        public void run() {
            if (AbstractOioChannel.this.isReadPending() || AbstractOioChannel.this.config().isAutoRead()) {
                AbstractOioChannel.this.setReadPending(false);
                AbstractOioChannel.this.doRead();
            }
        }
    };

    /* access modifiers changed from: protected */
    public abstract void doConnect(SocketAddress socketAddress, SocketAddress socketAddress2);

    /* access modifiers changed from: protected */
    public abstract void doRead();

    protected AbstractOioChannel(Channel parent) {
        super(parent);
    }

    /* access modifiers changed from: protected */
    public AbstractChannel.AbstractUnsafe newUnsafe() {
        return new DefaultOioUnsafe();
    }

    public final class DefaultOioUnsafe extends AbstractChannel.AbstractUnsafe {
        private DefaultOioUnsafe() {
            super();
        }

        public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
            if (promise.setUncancellable() && ensureOpen(promise)) {
                try {
                    boolean wasActive = AbstractOioChannel.this.isActive();
                    AbstractOioChannel.this.doConnect(remoteAddress, localAddress);
                    boolean active = AbstractOioChannel.this.isActive();
                    safeSetSuccess(promise);
                    if (!wasActive && active) {
                        AbstractOioChannel.this.pipeline().fireChannelActive();
                    }
                } catch (Throwable t) {
                    safeSetFailure(promise, annotateConnectException(t, remoteAddress));
                    closeIfClosed();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isCompatible(EventLoop loop) {
        return loop instanceof ThreadPerChannelEventLoop;
    }

    /* access modifiers changed from: protected */
    public void doBeginRead() {
        if (!isReadPending()) {
            setReadPending(true);
            eventLoop().execute(this.readTask);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isReadPending() {
        return this.readPending;
    }

    /* access modifiers changed from: protected */
    public void setReadPending(boolean readPending2) {
        this.readPending = readPending2;
    }
}

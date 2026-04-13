package io.netty.channel.local;

import io.netty.channel.AbstractServerChannel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.EventLoop;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import java.net.SocketAddress;
import java.util.ArrayDeque;
import java.util.Queue;

public class LocalServerChannel extends AbstractServerChannel {
    private volatile boolean acceptInProgress;
    private final ChannelConfig config = new DefaultChannelConfig(this);
    private final Queue<Object> inboundBuffer = new ArrayDeque();
    private volatile LocalAddress localAddress;
    private final Runnable shutdownHook = new Runnable() {
        public void run() {
            LocalServerChannel.this.unsafe().close(LocalServerChannel.this.unsafe().voidPromise());
        }
    };
    private volatile int state;

    public ChannelConfig config() {
        return this.config;
    }

    public LocalAddress localAddress() {
        return (LocalAddress) super.localAddress();
    }

    public LocalAddress remoteAddress() {
        return (LocalAddress) super.remoteAddress();
    }

    public boolean isOpen() {
        return this.state < 2;
    }

    public boolean isActive() {
        return this.state == 1;
    }

    /* access modifiers changed from: protected */
    public boolean isCompatible(EventLoop loop) {
        return loop instanceof SingleThreadEventLoop;
    }

    /* access modifiers changed from: protected */
    public SocketAddress localAddress0() {
        return this.localAddress;
    }

    /* access modifiers changed from: protected */
    public void doRegister() {
        ((SingleThreadEventExecutor) eventLoop()).addShutdownHook(this.shutdownHook);
    }

    /* access modifiers changed from: protected */
    public void doBind(SocketAddress localAddress2) {
        this.localAddress = LocalChannelRegistry.register(this, this.localAddress, localAddress2);
        this.state = 1;
    }

    /* access modifiers changed from: protected */
    public void doClose() {
        if (this.state <= 1) {
            if (this.localAddress != null) {
                LocalChannelRegistry.unregister(this.localAddress);
                this.localAddress = null;
            }
            this.state = 2;
        }
    }

    /* access modifiers changed from: protected */
    public void doDeregister() {
        ((SingleThreadEventExecutor) eventLoop()).removeShutdownHook(this.shutdownHook);
    }

    /* access modifiers changed from: protected */
    public void doBeginRead() {
        if (!this.acceptInProgress) {
            Queue<Object> inboundBuffer2 = this.inboundBuffer;
            if (inboundBuffer2.isEmpty()) {
                this.acceptInProgress = true;
                return;
            }
            ChannelPipeline pipeline = pipeline();
            while (true) {
                Object m = inboundBuffer2.poll();
                if (m == null) {
                    pipeline.fireChannelReadComplete();
                    return;
                }
                pipeline.fireChannelRead(m);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public LocalChannel serve(LocalChannel peer) {
        final LocalChannel child = newLocalChannel(peer);
        if (eventLoop().inEventLoop()) {
            serve0(child);
        } else {
            eventLoop().execute(new Runnable() {
                public void run() {
                    LocalServerChannel.this.serve0(child);
                }
            });
        }
        return child;
    }

    /* access modifiers changed from: protected */
    public LocalChannel newLocalChannel(LocalChannel peer) {
        return new LocalChannel(this, peer);
    }

    /* access modifiers changed from: private */
    public void serve0(LocalChannel child) {
        this.inboundBuffer.add(child);
        if (this.acceptInProgress) {
            this.acceptInProgress = false;
            ChannelPipeline pipeline = pipeline();
            while (true) {
                Object m = this.inboundBuffer.poll();
                if (m == null) {
                    pipeline.fireChannelReadComplete();
                    return;
                }
                pipeline.fireChannelRead(m);
            }
        }
    }
}

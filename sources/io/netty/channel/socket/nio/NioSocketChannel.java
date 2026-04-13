package io.netty.channel.socket.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.FileRegion;
import io.netty.channel.nio.AbstractNioByteChannel;
import io.netty.channel.nio.AbstractNioChannel;
import io.netty.channel.socket.DefaultSocketChannelConfig;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SocketUtils;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.Executor;

public class NioSocketChannel extends AbstractNioByteChannel implements SocketChannel {
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private final SocketChannelConfig config;

    private static java.nio.channels.SocketChannel newSocket(SelectorProvider provider) {
        try {
            return provider.openSocketChannel();
        } catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }

    public NioSocketChannel() {
        this(newSocket(DEFAULT_SELECTOR_PROVIDER));
    }

    public NioSocketChannel(SelectorProvider provider) {
        this(newSocket(provider));
    }

    public NioSocketChannel(java.nio.channels.SocketChannel socket) {
        this((Channel) null, socket);
    }

    public NioSocketChannel(Channel parent, java.nio.channels.SocketChannel socket) {
        super(parent, socket);
        this.config = new NioSocketChannelConfig(this, socket.socket());
    }

    public ServerSocketChannel parent() {
        return (ServerSocketChannel) super.parent();
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public SocketChannelConfig config() {
        return this.config;
    }

    /* access modifiers changed from: protected */
    public java.nio.channels.SocketChannel javaChannel() {
        return (java.nio.channels.SocketChannel) super.javaChannel();
    }

    public boolean isActive() {
        java.nio.channels.SocketChannel ch = javaChannel();
        return ch.isOpen() && ch.isConnected();
    }

    public boolean isInputShutdown() {
        return super.isInputShutdown();
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    /* access modifiers changed from: protected */
    public final void doShutdownOutput() {
        if (PlatformDependent.javaVersion() >= 7) {
            javaChannel().shutdownOutput();
        } else {
            javaChannel().socket().shutdownOutput();
        }
    }

    public boolean isOutputShutdown() {
        return javaChannel().socket().isOutputShutdown() || !isActive();
    }

    public ChannelFuture shutdownOutput() {
        return shutdownOutput(newPromise());
    }

    public ChannelFuture shutdownOutput(final ChannelPromise promise) {
        EventLoop loop = eventLoop();
        if (loop.inEventLoop()) {
            ((AbstractChannel.AbstractUnsafe) unsafe()).shutdownOutput(promise);
        } else {
            loop.execute(new Runnable() {
                public void run() {
                    ((AbstractChannel.AbstractUnsafe) NioSocketChannel.this.unsafe()).shutdownOutput(promise);
                }
            });
        }
        return promise;
    }

    /* access modifiers changed from: protected */
    public SocketAddress localAddress0() {
        return javaChannel().socket().getLocalSocketAddress();
    }

    /* access modifiers changed from: protected */
    public SocketAddress remoteAddress0() {
        return javaChannel().socket().getRemoteSocketAddress();
    }

    /* access modifiers changed from: protected */
    public void doBind(SocketAddress localAddress) {
        doBind0(localAddress);
    }

    private void doBind0(SocketAddress localAddress) {
        if (PlatformDependent.javaVersion() >= 7) {
            SocketUtils.bind(javaChannel(), localAddress);
        } else {
            SocketUtils.bind(javaChannel().socket(), localAddress);
        }
    }

    /* access modifiers changed from: protected */
    public boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) {
        if (localAddress != null) {
            doBind0(localAddress);
        }
        boolean success = false;
        try {
            boolean connected = SocketUtils.connect(javaChannel(), remoteAddress);
            if (!connected) {
                selectionKey().interestOps(8);
            }
            success = true;
            return connected;
        } finally {
            if (!success) {
                doClose();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doFinishConnect() {
        if (!javaChannel().finishConnect()) {
            throw new Error();
        }
    }

    /* access modifiers changed from: protected */
    public void doDisconnect() {
        doClose();
    }

    /* access modifiers changed from: protected */
    public void doClose() {
        super.doClose();
        javaChannel().close();
    }

    /* access modifiers changed from: protected */
    public int doReadBytes(ByteBuf byteBuf) {
        return byteBuf.writeBytes((ScatteringByteChannel) javaChannel(), byteBuf.writableBytes());
    }

    /* access modifiers changed from: protected */
    public int doWriteBytes(ByteBuf buf) {
        return buf.readBytes((GatheringByteChannel) javaChannel(), buf.readableBytes());
    }

    /* access modifiers changed from: protected */
    public long doWriteFileRegion(FileRegion region) {
        return region.transferTo(javaChannel(), region.transfered());
    }

    /* access modifiers changed from: protected */
    public void doWrite(ChannelOutboundBuffer in) {
        while (in.size() != 0) {
            long writtenBytes = 0;
            boolean done = false;
            boolean setOpWrite = false;
            ByteBuffer[] nioBuffers = in.nioBuffers();
            int nioBufferCnt = in.nioBufferCount();
            long expectedWrittenBytes = in.nioBufferSize();
            java.nio.channels.SocketChannel ch = javaChannel();
            switch (nioBufferCnt) {
                case 0:
                    super.doWrite(in);
                    return;
                case 1:
                    ByteBuffer nioBuffer = nioBuffers[0];
                    int i = config().getWriteSpinCount() - 1;
                    while (true) {
                        if (i < 0) {
                            break;
                        } else {
                            int localWrittenBytes = ch.write(nioBuffer);
                            if (localWrittenBytes == 0) {
                                setOpWrite = true;
                                break;
                            } else {
                                expectedWrittenBytes -= (long) localWrittenBytes;
                                writtenBytes += (long) localWrittenBytes;
                                if (expectedWrittenBytes == 0) {
                                    done = true;
                                    break;
                                } else {
                                    i--;
                                }
                            }
                        }
                    }
                default:
                    int i2 = config().getWriteSpinCount() - 1;
                    while (true) {
                        if (i2 < 0) {
                            break;
                        } else {
                            long localWrittenBytes2 = ch.write(nioBuffers, 0, nioBufferCnt);
                            if (localWrittenBytes2 == 0) {
                                setOpWrite = true;
                                break;
                            } else {
                                expectedWrittenBytes -= localWrittenBytes2;
                                writtenBytes += localWrittenBytes2;
                                if (expectedWrittenBytes == 0) {
                                    done = true;
                                    break;
                                } else {
                                    i2--;
                                }
                            }
                        }
                    }
            }
            in.removeBytes(writtenBytes);
            if (!done) {
                incompleteWrite(setOpWrite);
                return;
            }
        }
        clearOpWrite();
        ChannelOutboundBuffer channelOutboundBuffer = in;
    }

    /* access modifiers changed from: protected */
    public AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
        return new NioSocketChannelUnsafe();
    }

    public final class NioSocketChannelUnsafe extends AbstractNioByteChannel.NioByteUnsafe {
        private NioSocketChannelUnsafe() {
            super();
        }

        /* access modifiers changed from: protected */
        public Executor prepareToClose() {
            try {
                if (!NioSocketChannel.this.javaChannel().isOpen() || NioSocketChannel.this.config().getSoLinger() <= 0) {
                    return null;
                }
                NioSocketChannel.this.doDeregister();
                return GlobalEventExecutor.INSTANCE;
            } catch (Throwable th) {
                return null;
            }
        }
    }

    public final class NioSocketChannelConfig extends DefaultSocketChannelConfig {
        private NioSocketChannelConfig(NioSocketChannel channel, Socket javaSocket) {
            super(channel, javaSocket);
        }

        /* access modifiers changed from: protected */
        public void autoReadCleared() {
            NioSocketChannel.this.setReadPending(false);
        }
    }
}

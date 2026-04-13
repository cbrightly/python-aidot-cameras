package io.netty.channel.socket.nio;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.socket.DefaultServerSocketChannelConfig;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.List;

public class NioServerSocketChannel extends AbstractNioMessageChannel implements ServerSocketChannel {
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) NioServerSocketChannel.class);
    private final ServerSocketChannelConfig config;

    private static java.nio.channels.ServerSocketChannel newSocket(SelectorProvider provider) {
        try {
            return provider.openServerSocketChannel();
        } catch (IOException e) {
            throw new ChannelException("Failed to open a server socket.", e);
        }
    }

    public NioServerSocketChannel() {
        this(newSocket(DEFAULT_SELECTOR_PROVIDER));
    }

    public NioServerSocketChannel(SelectorProvider provider) {
        this(newSocket(provider));
    }

    public NioServerSocketChannel(java.nio.channels.ServerSocketChannel channel) {
        super((Channel) null, channel, 16);
        this.config = new NioServerSocketChannelConfig(this, javaChannel().socket());
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public ServerSocketChannelConfig config() {
        return this.config;
    }

    public boolean isActive() {
        return javaChannel().socket().isBound();
    }

    public InetSocketAddress remoteAddress() {
        return null;
    }

    /* access modifiers changed from: protected */
    public java.nio.channels.ServerSocketChannel javaChannel() {
        return (java.nio.channels.ServerSocketChannel) super.javaChannel();
    }

    /* access modifiers changed from: protected */
    public SocketAddress localAddress0() {
        return SocketUtils.localSocketAddress(javaChannel().socket());
    }

    /* access modifiers changed from: protected */
    public void doBind(SocketAddress localAddress) {
        if (PlatformDependent.javaVersion() >= 7) {
            javaChannel().bind(localAddress, this.config.getBacklog());
        } else {
            javaChannel().socket().bind(localAddress, this.config.getBacklog());
        }
    }

    /* access modifiers changed from: protected */
    public void doClose() {
        javaChannel().close();
    }

    /* access modifiers changed from: protected */
    public int doReadMessages(List<Object> buf) {
        SocketChannel ch = SocketUtils.accept(javaChannel());
        if (ch == null) {
            return 0;
        }
        try {
            buf.add(new NioSocketChannel(this, ch));
            return 1;
        } catch (Throwable t2) {
            logger.warn("Failed to close a socket.", t2);
            return 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public void doFinishConnect() {
        throw new UnsupportedOperationException();
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
    public boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public final Object filterOutboundMessage(Object msg) {
        throw new UnsupportedOperationException();
    }

    public final class NioServerSocketChannelConfig extends DefaultServerSocketChannelConfig {
        private NioServerSocketChannelConfig(NioServerSocketChannel channel, ServerSocket javaSocket) {
            super(channel, javaSocket);
        }

        /* access modifiers changed from: protected */
        public void autoReadCleared() {
            NioServerSocketChannel.this.setReadPending(false);
        }
    }
}

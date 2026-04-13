package io.netty.channel.socket.oio;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.oio.AbstractOioMessageChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OioServerSocketChannel extends AbstractOioMessageChannel implements ServerSocketChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) OioServerSocketChannel.class);
    private final OioServerSocketChannelConfig config;
    final Lock shutdownLock;
    final ServerSocket socket;

    private static ServerSocket newServerSocket() {
        try {
            return new ServerSocket();
        } catch (IOException e) {
            throw new ChannelException("failed to create a server socket", e);
        }
    }

    public OioServerSocketChannel() {
        this(newServerSocket());
    }

    public OioServerSocketChannel(ServerSocket socket2) {
        super((Channel) null);
        this.shutdownLock = new ReentrantLock();
        if (socket2 != null) {
            try {
                socket2.setSoTimeout(1000);
                if (1 == 0) {
                    try {
                        socket2.close();
                    } catch (IOException e) {
                        if (logger.isWarnEnabled()) {
                            logger.warn("Failed to close a partially initialized socket.", (Throwable) e);
                        }
                    }
                }
                this.socket = socket2;
                this.config = new DefaultOioServerSocketChannelConfig(this, socket2);
            } catch (IOException e2) {
                throw new ChannelException("Failed to set the server socket timeout.", e2);
            } catch (Throwable th) {
                if (0 == 0) {
                    try {
                        socket2.close();
                    } catch (IOException e3) {
                        if (logger.isWarnEnabled()) {
                            logger.warn("Failed to close a partially initialized socket.", (Throwable) e3);
                        }
                    }
                }
                throw th;
            }
        } else {
            throw new NullPointerException("socket");
        }
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public OioServerSocketChannelConfig config() {
        return this.config;
    }

    public InetSocketAddress remoteAddress() {
        return null;
    }

    public boolean isOpen() {
        return !this.socket.isClosed();
    }

    public boolean isActive() {
        return isOpen() && this.socket.isBound();
    }

    /* access modifiers changed from: protected */
    public SocketAddress localAddress0() {
        return SocketUtils.localSocketAddress(this.socket);
    }

    /* access modifiers changed from: protected */
    public void doBind(SocketAddress localAddress) {
        this.socket.bind(localAddress, this.config.getBacklog());
    }

    /* access modifiers changed from: protected */
    public void doClose() {
        this.socket.close();
    }

    /* access modifiers changed from: protected */
    public int doReadMessages(List<Object> buf) {
        if (this.socket.isClosed()) {
            return -1;
        }
        try {
            Socket s = this.socket.accept();
            try {
                buf.add(new OioSocketChannel(this, s));
                return 1;
            } catch (Throwable t2) {
                logger.warn("Failed to close a socket.", t2);
                return 0;
            }
        } catch (SocketTimeoutException e) {
            return 0;
        }
    }

    /* access modifiers changed from: protected */
    public void doWrite(ChannelOutboundBuffer in) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public Object filterOutboundMessage(Object msg) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) {
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
    public void setReadPending(boolean readPending) {
        super.setReadPending(readPending);
    }
}

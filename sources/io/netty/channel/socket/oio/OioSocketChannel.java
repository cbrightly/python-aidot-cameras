package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ConnectTimeoutException;
import io.netty.channel.EventLoop;
import io.netty.channel.oio.OioByteStreamChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

public class OioSocketChannel extends OioByteStreamChannel implements SocketChannel {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) OioSocketChannel.class);
    private final OioSocketChannelConfig config;
    private final Socket socket;

    public OioSocketChannel() {
        this(new Socket());
    }

    public OioSocketChannel(Socket socket2) {
        this((Channel) null, socket2);
    }

    public OioSocketChannel(Channel parent, Socket socket2) {
        super(parent);
        this.socket = socket2;
        this.config = new DefaultOioSocketChannelConfig(this, socket2);
        try {
            if (socket2.isConnected()) {
                activate(socket2.getInputStream(), socket2.getOutputStream());
            }
            socket2.setSoTimeout(1000);
            if (1 == 0) {
                try {
                    socket2.close();
                } catch (IOException e) {
                    logger.warn("Failed to close a socket.", (Throwable) e);
                }
            }
        } catch (Exception e2) {
            throw new ChannelException("failed to initialize a socket", e2);
        } catch (Throwable th) {
            if (0 == 0) {
                try {
                    socket2.close();
                } catch (IOException e3) {
                    logger.warn("Failed to close a socket.", (Throwable) e3);
                }
            }
            throw th;
        }
    }

    public ServerSocketChannel parent() {
        return (ServerSocketChannel) super.parent();
    }

    public OioSocketChannelConfig config() {
        return this.config;
    }

    public boolean isOpen() {
        return !this.socket.isClosed();
    }

    public boolean isActive() {
        return !this.socket.isClosed() && this.socket.isConnected();
    }

    public boolean isInputShutdown() {
        return super.isInputShutdown();
    }

    public boolean isOutputShutdown() {
        return this.socket.isOutputShutdown() || !isActive();
    }

    /* access modifiers changed from: protected */
    public final void doShutdownOutput() {
        shutdownOutput0();
    }

    public ChannelFuture shutdownOutput() {
        return shutdownOutput(newPromise());
    }

    /* access modifiers changed from: protected */
    public int doReadBytes(ByteBuf buf) {
        if (this.socket.isClosed()) {
            return -1;
        }
        try {
            return super.doReadBytes(buf);
        } catch (SocketTimeoutException e) {
            return 0;
        }
    }

    public ChannelFuture shutdownOutput(final ChannelPromise promise) {
        EventLoop loop = eventLoop();
        if (loop.inEventLoop()) {
            shutdownOutput0(promise);
        } else {
            loop.execute(new Runnable() {
                public void run() {
                    OioSocketChannel.this.shutdownOutput0(promise);
                }
            });
        }
        return promise;
    }

    /* access modifiers changed from: private */
    public void shutdownOutput0(ChannelPromise promise) {
        try {
            shutdownOutput0();
            promise.setSuccess();
        } catch (Throwable t) {
            promise.setFailure(t);
        }
    }

    private void shutdownOutput0() {
        this.socket.shutdownOutput();
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    /* access modifiers changed from: protected */
    public SocketAddress localAddress0() {
        return this.socket.getLocalSocketAddress();
    }

    /* access modifiers changed from: protected */
    public SocketAddress remoteAddress0() {
        return this.socket.getRemoteSocketAddress();
    }

    /* access modifiers changed from: protected */
    public void doBind(SocketAddress localAddress) {
        SocketUtils.bind(this.socket, localAddress);
    }

    /* access modifiers changed from: protected */
    public void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) {
        if (localAddress != null) {
            SocketUtils.bind(this.socket, localAddress);
        }
        try {
            SocketUtils.connect(this.socket, remoteAddress, config().getConnectTimeoutMillis());
            activate(this.socket.getInputStream(), this.socket.getOutputStream());
            if (1 == 0) {
                doClose();
            }
        } catch (SocketTimeoutException e) {
            ConnectTimeoutException cause = new ConnectTimeoutException("connection timed out: " + remoteAddress);
            cause.setStackTrace(e.getStackTrace());
            throw cause;
        } catch (Throwable th) {
            if (0 == 0) {
                doClose();
            }
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void doDisconnect() {
        doClose();
    }

    /* access modifiers changed from: protected */
    public void doClose() {
        this.socket.close();
    }

    /* access modifiers changed from: protected */
    public boolean checkInputShutdown() {
        if (!isInputShutdown()) {
            return false;
        }
        try {
            Thread.sleep((long) config().getSoTimeout());
            return true;
        } catch (Throwable th) {
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void setReadPending(boolean readPending) {
        super.setReadPending(readPending);
    }
}

package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CloseReason;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.SelectorRunner;
import org.glassfish.grizzly.utils.Holder;
import org.glassfish.grizzly.utils.NullaryFunction;

public class TCPNIOConnection extends NIOConnection {
    private static final Logger LOGGER = Grizzly.logger(TCPNIOConnection.class);
    private AtomicReference<ConnectResultHandler> connectHandlerRef;
    Holder<SocketAddress> localSocketAddressHolder;
    Holder<SocketAddress> peerSocketAddressHolder;
    private int readBufferSize = -1;
    private int writeBufferSize = -1;

    public interface ConnectResultHandler {
        void connected();

        void failed(Throwable th);
    }

    public TCPNIOConnection(TCPNIOTransport transport, SelectableChannel channel) {
        super(transport);
        this.channel = channel;
    }

    /* access modifiers changed from: protected */
    public void setSelectionKey(SelectionKey selectionKey) {
        super.setSelectionKey(selectionKey);
    }

    /* access modifiers changed from: protected */
    public void setSelectorRunner(SelectorRunner selectorRunner) {
        super.setSelectorRunner(selectorRunner);
    }

    /* access modifiers changed from: protected */
    public void preClose() {
        checkConnectFailed((Throwable) null);
        super.preClose();
    }

    /* access modifiers changed from: protected */
    public boolean notifyReady() {
        return NIOConnection.connectCloseSemaphoreUpdater.compareAndSet(this, (Object) null, NIOConnection.NOTIFICATION_INITIALIZED);
    }

    public SocketAddress getPeerAddress() {
        return this.peerSocketAddressHolder.get();
    }

    public SocketAddress getLocalAddress() {
        return this.localSocketAddressHolder.get();
    }

    /* access modifiers changed from: protected */
    public void resetProperties() {
        if (this.channel != null) {
            setReadBufferSize(this.transport.getReadBufferSize());
            setWriteBufferSize(this.transport.getWriteBufferSize());
            int transportMaxAsyncWriteQueueSize = ((TCPNIOTransport) this.transport).getAsyncQueueIO().getWriter().getMaxPendingBytesPerConnection();
            setMaxAsyncWriteQueueSize(transportMaxAsyncWriteQueueSize == -2 ? getWriteBufferSize() * 4 : transportMaxAsyncWriteQueueSize);
            this.localSocketAddressHolder = Holder.lazyHolder(new NullaryFunction<SocketAddress>() {
                public SocketAddress evaluate() {
                    return ((SocketChannel) TCPNIOConnection.this.channel).socket().getLocalSocketAddress();
                }
            });
            this.peerSocketAddressHolder = Holder.lazyHolder(new NullaryFunction<SocketAddress>() {
                public SocketAddress evaluate() {
                    return ((SocketChannel) TCPNIOConnection.this.channel).socket().getRemoteSocketAddress();
                }
            });
        }
    }

    public int getReadBufferSize() {
        int i = this.readBufferSize;
        if (i >= 0) {
            return i;
        }
        try {
            this.readBufferSize = ((SocketChannel) this.channel).socket().getReceiveBufferSize();
        } catch (IOException e) {
            LOGGER.log(Level.FINE, LogMessages.WARNING_GRIZZLY_CONNECTION_GET_READBUFFER_SIZE_EXCEPTION(), e);
            this.readBufferSize = 0;
        }
        return this.readBufferSize;
    }

    public void setReadBufferSize(int readBufferSize2) {
        if (readBufferSize2 > 0) {
            try {
                if (readBufferSize2 > ((SocketChannel) this.channel).socket().getReceiveBufferSize()) {
                    ((SocketChannel) this.channel).socket().setReceiveBufferSize(readBufferSize2);
                }
                this.readBufferSize = readBufferSize2;
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_CONNECTION_SET_READBUFFER_SIZE_EXCEPTION(), e);
            }
        }
    }

    public int getWriteBufferSize() {
        int i = this.writeBufferSize;
        if (i >= 0) {
            return i;
        }
        try {
            this.writeBufferSize = ((SocketChannel) this.channel).socket().getSendBufferSize();
        } catch (IOException e) {
            LOGGER.log(Level.FINE, LogMessages.WARNING_GRIZZLY_CONNECTION_GET_WRITEBUFFER_SIZE_EXCEPTION(), e);
            this.writeBufferSize = 0;
        }
        return this.writeBufferSize;
    }

    public void setWriteBufferSize(int writeBufferSize2) {
        if (writeBufferSize2 > 0) {
            try {
                if (writeBufferSize2 > ((SocketChannel) this.channel).socket().getSendBufferSize()) {
                    ((SocketChannel) this.channel).socket().setSendBufferSize(writeBufferSize2);
                }
                this.writeBufferSize = writeBufferSize2;
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_CONNECTION_SET_WRITEBUFFER_SIZE_EXCEPTION(), e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void setConnectResultHandler(ConnectResultHandler connectHandler) {
        this.connectHandlerRef = new AtomicReference<>(connectHandler);
    }

    /* access modifiers changed from: protected */
    public final void onConnect() {
        AtomicReference<ConnectResultHandler> localRef = this.connectHandlerRef;
        if (localRef != null) {
            ConnectResultHandler andSet = localRef.getAndSet((Object) null);
            ConnectResultHandler localConnectHandler = andSet;
            if (andSet != null) {
                localConnectHandler.connected();
                this.connectHandlerRef = null;
            }
        }
        NIOConnection.notifyProbesConnect(this);
    }

    /* access modifiers changed from: protected */
    public final void checkConnectFailed(Throwable failure) {
        AtomicReference<ConnectResultHandler> localRef = this.connectHandlerRef;
        if (localRef != null) {
            ConnectResultHandler andSet = localRef.getAndSet((Object) null);
            ConnectResultHandler localConnectHandler = andSet;
            if (andSet != null) {
                if (failure == null) {
                    failure = new IOException("closed");
                }
                localConnectHandler.failed(failure);
                this.connectHandlerRef = null;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void terminate0(CompletionHandler<Closeable> completionHandler, CloseReason closeReason) {
        super.terminate0(completionHandler, closeReason);
    }

    /* access modifiers changed from: protected */
    public final void onRead(Buffer data, int size) {
        if (size > 0) {
            NIOConnection.notifyProbesRead(this, data, size);
        }
        checkEmptyRead(size);
    }

    /* access modifiers changed from: protected */
    public void enableInitialOpRead() {
        super.enableInitialOpRead();
    }

    /* access modifiers changed from: protected */
    public final void onWrite(Buffer data, long size) {
        NIOConnection.notifyProbesWrite(this, data, size);
    }

    public boolean canWrite() {
        return this.transport.getWriter((Connection) this).canWrite(this);
    }

    @Deprecated
    public boolean canWrite(int length) {
        return this.transport.getWriter((Connection) this).canWrite(this);
    }

    public void notifyCanWrite(WriteHandler writeHandler) {
        this.transport.getWriter((Connection) this).notifyWritePossible(this, writeHandler);
    }

    @Deprecated
    public void notifyCanWrite(WriteHandler handler, int length) {
        this.transport.getWriter((Connection) this).notifyWritePossible(this, handler);
    }

    public String toString() {
        return "TCPNIOConnection" + "{localSocketAddress=" + this.localSocketAddressHolder + ", peerSocketAddress=" + this.peerSocketAddressHolder + '}';
    }
}

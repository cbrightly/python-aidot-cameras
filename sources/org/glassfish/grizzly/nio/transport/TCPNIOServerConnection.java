package org.glassfish.grizzly.nio.transport;

import com.tencent.bugly.Bugly;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CloseReason;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.RegisterChannelResult;
import org.glassfish.grizzly.utils.CompletionHandlerAdapter;
import org.glassfish.grizzly.utils.Exceptions;
import org.glassfish.grizzly.utils.Holder;
import org.glassfish.grizzly.utils.NullaryFunction;

public final class TCPNIOServerConnection extends TCPNIOConnection {
    private static boolean DISABLE_INTERRUPT_CLEAR;
    /* access modifiers changed from: private */
    public static final Logger LOGGER;
    private FutureImpl<Connection> acceptListener;
    private final Object acceptSync = new Object();
    private final RegisterAcceptedChannelCompletionHandler defaultCompletionHandler = new RegisterAcceptedChannelCompletionHandler(this);

    static {
        Class<TCPNIOServerConnection> cls = TCPNIOServerConnection.class;
        DISABLE_INTERRUPT_CLEAR = Boolean.valueOf(System.getProperty(cls.getName() + "_DISABLE_INTERRUPT_CLEAR", Bugly.SDK_IS_DEV)).booleanValue();
        LOGGER = Grizzly.logger(cls);
    }

    public TCPNIOServerConnection(TCPNIOTransport transport, ServerSocketChannel serverSocketChannel) {
        super(transport, serverSocketChannel);
    }

    public void listen() {
        CompletionHandler<RegisterChannelResult> registerCompletionHandler = ((TCPNIOTransport) this.transport).selectorRegistrationHandler;
        FutureImpl<RegisterChannelResult> future = SafeFutureImpl.create();
        this.transport.getNIOChannelDistributor().registerServiceChannelAsync(this.channel, 16, this, new CompletionHandlerAdapter(future, registerCompletionHandler));
        try {
            future.get(10, TimeUnit.SECONDS);
            notifyReady();
            NIOConnection.notifyProbesBind(this);
        } catch (ExecutionException e) {
            throw Exceptions.makeIOException(e.getCause());
        } catch (Exception e2) {
            throw Exceptions.makeIOException(e2);
        }
    }

    public boolean isBlocking() {
        return this.transport.isBlocking();
    }

    public boolean isStandalone() {
        return this.transport.isStandalone();
    }

    public GrizzlyFuture<Connection> accept() {
        if (isStandalone()) {
            GrizzlyFuture<Connection> future = acceptAsync();
            if (isBlocking()) {
                try {
                    future.get();
                } catch (Exception e) {
                }
            }
            return future;
        }
        throw new IllegalStateException("Accept could be used in standalone mode only");
    }

    /* access modifiers changed from: protected */
    public GrizzlyFuture<Connection> acceptAsync() {
        FutureImpl<Connection> future;
        if (isOpen()) {
            synchronized (this.acceptSync) {
                future = SafeFutureImpl.create();
                SocketChannel acceptedChannel = doAccept();
                if (acceptedChannel != null) {
                    configureAcceptedChannel(acceptedChannel);
                    registerAcceptedChannel(createClientConnection(acceptedChannel), new RegisterAcceptedChannelCompletionHandler(future), 0);
                } else {
                    this.acceptListener = future;
                    enableIOEvent(IOEvent.SERVER_ACCEPT);
                }
            }
            return future;
        }
        throw new IOException("Connection is closed");
    }

    private SocketChannel doAccept() {
        if (!DISABLE_INTERRUPT_CLEAR && Thread.currentThread().isInterrupted()) {
            Thread.interrupted();
        }
        return ((ServerSocketChannel) getChannel()).accept();
    }

    private void configureAcceptedChannel(SocketChannel acceptedChannel) {
        TCPNIOTransport tcpNIOTransport = (TCPNIOTransport) this.transport;
        tcpNIOTransport.getChannelConfigurator().preConfigure(this.transport, acceptedChannel);
        tcpNIOTransport.getChannelConfigurator().postConfigure(this.transport, acceptedChannel);
    }

    private TCPNIOConnection createClientConnection(SocketChannel acceptedChannel) {
        TCPNIOConnection connection = ((TCPNIOTransport) this.transport).obtainNIOConnection(acceptedChannel);
        if (this.processor != null) {
            connection.setProcessor(this.processor);
        }
        if (this.processorSelector != null) {
            connection.setProcessorSelector(this.processorSelector);
        }
        connection.resetProperties();
        return connection;
    }

    private void registerAcceptedChannel(TCPNIOConnection acceptedConnection, CompletionHandler<RegisterChannelResult> completionHandler, int initialSelectionKeyInterest) {
        ((TCPNIOTransport) this.transport).getNIOChannelDistributor().registerChannelAsync(acceptedConnection.getChannel(), initialSelectionKeyInterest, acceptedConnection, completionHandler);
    }

    public void preClose() {
        FutureImpl<Connection> futureImpl = this.acceptListener;
        if (futureImpl != null) {
            futureImpl.failure(new IOException("Connection is closed"));
        }
        this.transport.unbind(this);
        super.preClose();
    }

    public void onAccept() {
        if (!isStandalone()) {
            SocketChannel acceptedChannel = doAccept();
            if (acceptedChannel != null) {
                configureAcceptedChannel(acceptedChannel);
                TCPNIOConnection acceptedConnection = createClientConnection(acceptedChannel);
                NIOConnection.notifyProbesAccept(this, acceptedConnection);
                registerAcceptedChannel(acceptedConnection, this.defaultCompletionHandler, 1);
                return;
            }
            return;
        }
        synchronized (this.acceptSync) {
            if (this.acceptListener == null) {
                disableIOEvent(IOEvent.SERVER_ACCEPT);
                return;
            }
            SocketChannel acceptedChannel2 = doAccept();
            if (acceptedChannel2 != null) {
                configureAcceptedChannel(acceptedChannel2);
                TCPNIOConnection acceptedConnection2 = createClientConnection(acceptedChannel2);
                NIOConnection.notifyProbesAccept(this, acceptedConnection2);
                registerAcceptedChannel(acceptedConnection2, new RegisterAcceptedChannelCompletionHandler(this.acceptListener), 0);
                this.acceptListener = null;
                TCPNIOConnection tCPNIOConnection = acceptedConnection2;
            }
        }
    }

    public void setReadBufferSize(int readBufferSize) {
        throw new IllegalStateException("Use TCPNIOTransport.setReadBufferSize()");
    }

    public void setWriteBufferSize(int writeBufferSize) {
        throw new IllegalStateException("Use TCPNIOTransport.setWriteBufferSize()");
    }

    public int getReadBufferSize() {
        return this.transport.getReadBufferSize();
    }

    public int getWriteBufferSize() {
        return this.transport.getWriteBufferSize();
    }

    /* access modifiers changed from: protected */
    public void closeGracefully0(CompletionHandler<Closeable> completionHandler, CloseReason closeReason) {
        terminate0(completionHandler, closeReason);
    }

    /* access modifiers changed from: protected */
    public void resetProperties() {
        this.localSocketAddressHolder = Holder.lazyHolder(new NullaryFunction<SocketAddress>() {
            public SocketAddress evaluate() {
                return ((ServerSocketChannel) TCPNIOServerConnection.this.channel).socket().getLocalSocketAddress();
            }
        });
        this.peerSocketAddressHolder = Holder.staticHolder(null);
    }

    public final class RegisterAcceptedChannelCompletionHandler extends EmptyCompletionHandler<RegisterChannelResult> {
        private final FutureImpl<Connection> listener;

        public RegisterAcceptedChannelCompletionHandler(TCPNIOServerConnection this$02) {
            this((FutureImpl<Connection>) null);
        }

        public RegisterAcceptedChannelCompletionHandler(FutureImpl<Connection> listener2) {
            this.listener = listener2;
        }

        public void completed(RegisterChannelResult result) {
            try {
                TCPNIOTransport nioTransport = (TCPNIOTransport) TCPNIOServerConnection.this.transport;
                nioTransport.selectorRegistrationHandler.completed(result);
                TCPNIOConnection connection = (TCPNIOConnection) nioTransport.getSelectionKeyHandler().getConnectionForKey(result.getSelectionKey());
                FutureImpl<Connection> futureImpl = this.listener;
                if (futureImpl != null) {
                    futureImpl.result(connection);
                }
                if (connection.notifyReady()) {
                    TCPNIOServerConnection.this.transport.fireIOEvent(IOEvent.ACCEPTED, connection, (IOEventLifeCycleListener) null);
                }
            } catch (Exception e) {
                TCPNIOServerConnection.LOGGER.log(Level.FINE, "Exception happened, when trying to accept the connection", e);
            }
        }
    }
}

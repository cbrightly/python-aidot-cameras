package org.glassfish.grizzly.nio.transport;

import androidx.work.WorkRequest;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import org.glassfish.grizzly.AbstractSocketConnectorHandler;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.ReadyFutureImpl;
import org.glassfish.grizzly.nio.NIOChannelDistributor;
import org.glassfish.grizzly.nio.RegisterChannelResult;
import org.glassfish.grizzly.utils.Futures;

public class UDPNIOConnectorHandler extends AbstractSocketConnectorHandler {
    private static final Logger LOGGER = Grizzly.logger(UDPNIOConnectorHandler.class);
    protected volatile long connectionTimeoutMillis = WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS;
    protected boolean isReuseAddress;

    protected UDPNIOConnectorHandler(UDPNIOTransport transport) {
        super(transport);
        this.connectionTimeoutMillis = (long) transport.getConnectionTimeout();
        this.isReuseAddress = transport.isReuseAddress();
    }

    public GrizzlyFuture<Connection> connect() {
        return connectAsync((SocketAddress) null, (SocketAddress) null, (CompletionHandler<Connection>) null, true);
    }

    public void connect(SocketAddress remoteAddress, SocketAddress localAddress, CompletionHandler<Connection> completionHandler) {
        if (!this.transport.isBlocking()) {
            connectAsync(remoteAddress, localAddress, completionHandler, false);
        } else {
            connectSync(remoteAddress, localAddress, completionHandler);
        }
    }

    /* access modifiers changed from: protected */
    public void connectSync(SocketAddress remoteAddress, SocketAddress localAddress, CompletionHandler<Connection> completionHandler) {
        waitNIOFuture(connectAsync(remoteAddress, localAddress, completionHandler, true), completionHandler);
    }

    /* access modifiers changed from: protected */
    public FutureImpl<Connection> connectAsync(SocketAddress remoteAddress, SocketAddress localAddress, CompletionHandler<Connection> completionHandler, boolean needFuture) {
        CompletionHandler<Connection> completionHandlerToPass;
        FutureImpl<Connection> futureToReturn;
        UDPNIOTransport nioTransport = (UDPNIOTransport) this.transport;
        UDPNIOConnection newConnection = null;
        try {
            DatagramChannel datagramChannel = nioTransport.getSelectorProvider().openDatagramChannel();
            nioTransport.getChannelConfigurator().preConfigure(nioTransport, datagramChannel);
            DatagramSocket socket = datagramChannel.socket();
            UDPNIOConnection newConnection2 = nioTransport.obtainNIOConnection(datagramChannel);
            boolean reuseAddr = this.isReuseAddress;
            if (reuseAddr != nioTransport.isReuseAddress()) {
                socket.setReuseAddress(reuseAddr);
            }
            socket.bind(localAddress);
            if (remoteAddress != null) {
                datagramChannel.connect(remoteAddress);
            }
            nioTransport.getChannelConfigurator().postConfigure(nioTransport, datagramChannel);
            preConfigure(newConnection2);
            newConnection2.setProcessor(getProcessor());
            newConnection2.setProcessorSelector(getProcessorSelector());
            NIOChannelDistributor nioChannelDistributor = nioTransport.getNIOChannelDistributor();
            if (nioChannelDistributor != null) {
                if (needFuture) {
                    futureToReturn = makeCancellableFuture(newConnection2);
                    completionHandlerToPass = Futures.toCompletionHandler(futureToReturn, completionHandler);
                } else {
                    completionHandlerToPass = completionHandler;
                    futureToReturn = null;
                }
                nioChannelDistributor.registerChannelAsync(datagramChannel, 0, newConnection2, new ConnectHandler(newConnection2, completionHandlerToPass));
                return futureToReturn;
            }
            throw new IllegalStateException("NIOChannelDistributor is null. Is Transport running?");
        } catch (Exception e) {
            if (newConnection != null) {
                newConnection.closeSilently();
            }
            if (completionHandler != null) {
                completionHandler.failed(e);
            }
            if (needFuture) {
                return ReadyFutureImpl.create((Throwable) e);
            }
            return null;
        }
    }

    public boolean isReuseAddress() {
        return this.isReuseAddress;
    }

    public void setReuseAddress(boolean isReuseAddress2) {
        this.isReuseAddress = isReuseAddress2;
    }

    public long getSyncConnectTimeout(TimeUnit timeUnit) {
        return timeUnit.convert(this.connectionTimeoutMillis, TimeUnit.MILLISECONDS);
    }

    public void setSyncConnectTimeout(long timeout, TimeUnit timeUnit) {
        this.connectionTimeoutMillis = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
    }

    /* access modifiers changed from: protected */
    public void waitNIOFuture(FutureImpl<Connection> future, CompletionHandler<Connection> completionHandler) {
        try {
            future.get(this.connectionTimeoutMillis, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Futures.notifyFailure(future, completionHandler, e);
        } catch (TimeoutException e2) {
            Futures.notifyFailure(future, completionHandler, new IOException("Channel registration on Selector timeout!"));
        } catch (Exception e3) {
        }
    }

    /* access modifiers changed from: private */
    public static void abortConnection(UDPNIOConnection connection, CompletionHandler<Connection> completionHandler, Throwable failure) {
        connection.closeSilently();
        if (completionHandler != null) {
            completionHandler.failed(failure);
        }
    }

    public final class ConnectHandler extends EmptyCompletionHandler<RegisterChannelResult> {
        private final CompletionHandler<Connection> completionHandler;
        private final UDPNIOConnection connection;

        private ConnectHandler(UDPNIOConnection connection2, CompletionHandler<Connection> completionHandler2) {
            this.connection = connection2;
            this.completionHandler = completionHandler2;
        }

        public void completed(RegisterChannelResult result) {
            UDPNIOTransport transport = (UDPNIOTransport) UDPNIOConnectorHandler.this.transport;
            transport.registerChannelCompletionHandler.completed(result);
            try {
                this.connection.onConnect();
            } catch (Exception e) {
                UDPNIOConnectorHandler.abortConnection(this.connection, this.completionHandler, e);
            }
            if (this.connection.notifyReady()) {
                transport.fireIOEvent(IOEvent.CONNECTED, this.connection, new EnableReadHandler(this.completionHandler));
            }
        }

        public void failed(Throwable throwable) {
            UDPNIOConnectorHandler.abortConnection(this.connection, this.completionHandler, throwable);
        }
    }

    public static class EnableReadHandler extends IOEventLifeCycleListener.Adapter {
        private final CompletionHandler<Connection> completionHandler;

        private EnableReadHandler(CompletionHandler<Connection> completionHandler2) {
            this.completionHandler = completionHandler2;
        }

        public void onReregister(Context context) {
            onComplete(context, (Object) null);
        }

        public void onNotRun(Context context) {
            onComplete(context, (Object) null);
        }

        public void onComplete(Context context, Object data) {
            UDPNIOConnection connection = (UDPNIOConnection) context.getConnection();
            CompletionHandler<Connection> completionHandler2 = this.completionHandler;
            if (completionHandler2 != null) {
                completionHandler2.completed(connection);
            }
            if (!connection.isStandalone()) {
                connection.enableInitialOpRead();
            }
        }

        public void onError(Context context, Object description) {
            context.getConnection().closeSilently();
        }
    }

    public static Builder builder(UDPNIOTransport transport) {
        return new Builder().setTransport(transport);
    }

    public static class Builder extends AbstractSocketConnectorHandler.Builder<Builder> {
        private Boolean reuseAddress;
        private Long timeout;
        private TimeUnit timeoutTimeunit;
        private UDPNIOTransport transport;

        public UDPNIOConnectorHandler build() {
            UDPNIOConnectorHandler handler = (UDPNIOConnectorHandler) super.build();
            Boolean bool = this.reuseAddress;
            if (bool != null) {
                handler.setReuseAddress(bool.booleanValue());
            }
            Long l = this.timeout;
            if (l != null) {
                handler.setSyncConnectTimeout(l.longValue(), this.timeoutTimeunit);
            }
            return handler;
        }

        public Builder setTransport(UDPNIOTransport transport2) {
            this.transport = transport2;
            return this;
        }

        public Builder setReuseAddress(boolean reuseAddress2) {
            this.reuseAddress = Boolean.valueOf(reuseAddress2);
            return this;
        }

        public Builder setSyncConnectTimeout(long timeout2, TimeUnit timeunit) {
            this.timeout = Long.valueOf(timeout2);
            this.timeoutTimeunit = timeunit;
            return this;
        }

        /* access modifiers changed from: protected */
        public AbstractSocketConnectorHandler create() {
            if (this.transport != null) {
                return new UDPNIOConnectorHandler(this.transport);
            }
            throw new IllegalStateException("Unable to create UDPNIOConnectorHandler - transport is null");
        }
    }
}

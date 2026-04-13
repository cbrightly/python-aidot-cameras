package org.glassfish.grizzly.nio.transport;

import androidx.work.WorkRequest;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.AbstractSocketConnectorHandler;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.nio.RegisterChannelResult;
import org.glassfish.grizzly.utils.Exceptions;
import org.glassfish.grizzly.utils.Futures;

public class TCPNIOConnectorHandler extends AbstractSocketConnectorHandler {
    protected static final int DEFAULT_CONNECTION_TIMEOUT = 30000;
    /* access modifiers changed from: private */
    public static final Logger LOGGER = Grizzly.logger(TCPNIOConnectorHandler.class);
    protected volatile long connectionTimeoutMillis = WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS;
    private final InstantConnectHandler instantConnectHandler;
    protected boolean isReuseAddress;

    protected TCPNIOConnectorHandler(TCPNIOTransport transport) {
        super(transport);
        this.connectionTimeoutMillis = (long) transport.getConnectionTimeout();
        this.isReuseAddress = transport.isReuseAddress();
        this.instantConnectHandler = new InstantConnectHandler();
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
    /* JADX WARNING: Removed duplicated region for block: B:26:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0090  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x009a A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.glassfish.grizzly.impl.FutureImpl<org.glassfish.grizzly.Connection> connectAsync(java.net.SocketAddress r17, java.net.SocketAddress r18, org.glassfish.grizzly.CompletionHandler<org.glassfish.grizzly.Connection> r19, boolean r20) {
        /*
            r16 = this;
            r1 = r16
            r2 = r18
            r3 = r19
            org.glassfish.grizzly.Transport r0 = r1.transport
            r4 = r0
            org.glassfish.grizzly.nio.transport.TCPNIOTransport r4 = (org.glassfish.grizzly.nio.transport.TCPNIOTransport) r4
            r5 = 0
            java.nio.channels.spi.SelectorProvider r0 = r4.getSelectorProvider()     // Catch:{ Exception -> 0x0086 }
            java.nio.channels.SocketChannel r0 = r0.openSocketChannel()     // Catch:{ Exception -> 0x0086 }
            org.glassfish.grizzly.nio.transport.TCPNIOConnection r6 = r4.obtainNIOConnection(r0)     // Catch:{ Exception -> 0x0086 }
            r5 = r6
            java.net.Socket r7 = r0.socket()     // Catch:{ Exception -> 0x0086 }
            org.glassfish.grizzly.nio.ChannelConfigurator r8 = r4.getChannelConfigurator()     // Catch:{ Exception -> 0x0086 }
            r8.preConfigure(r4, r0)     // Catch:{ Exception -> 0x0086 }
            boolean r8 = r1.isReuseAddress     // Catch:{ Exception -> 0x0086 }
            boolean r9 = r4.isReuseAddress()     // Catch:{ Exception -> 0x0086 }
            if (r8 == r9) goto L_0x0030
            r7.setReuseAddress(r8)     // Catch:{ Exception -> 0x0086 }
        L_0x0030:
            if (r2 == 0) goto L_0x0035
            r7.bind(r2)     // Catch:{ Exception -> 0x0086 }
        L_0x0035:
            r1.preConfigure(r6)     // Catch:{ Exception -> 0x0086 }
            org.glassfish.grizzly.Processor r9 = r16.getProcessor()     // Catch:{ Exception -> 0x0086 }
            r6.setProcessor(r9)     // Catch:{ Exception -> 0x0086 }
            org.glassfish.grizzly.ProcessorSelector r9 = r16.getProcessorSelector()     // Catch:{ Exception -> 0x0086 }
            r6.setProcessorSelector(r9)     // Catch:{ Exception -> 0x0086 }
            r9 = r17
            boolean r10 = r0.connect(r9)     // Catch:{ Exception -> 0x0084 }
            if (r20 == 0) goto L_0x0057
            org.glassfish.grizzly.impl.FutureImpl r11 = r1.makeCancellableFuture(r6)     // Catch:{ Exception -> 0x0084 }
            org.glassfish.grizzly.CompletionHandler r12 = org.glassfish.grizzly.utils.Futures.toCompletionHandler(r11, r3)     // Catch:{ Exception -> 0x0084 }
            goto L_0x005a
        L_0x0057:
            r12 = r19
            r11 = 0
        L_0x005a:
            org.glassfish.grizzly.nio.transport.TCPNIOConnectorHandler$1 r13 = new org.glassfish.grizzly.nio.transport.TCPNIOConnectorHandler$1     // Catch:{ Exception -> 0x0084 }
            r13.<init>(r6, r12)     // Catch:{ Exception -> 0x0084 }
            r5.setConnectResultHandler(r13)     // Catch:{ Exception -> 0x0084 }
            org.glassfish.grizzly.nio.NIOChannelDistributor r13 = r4.getNIOChannelDistributor()     // Catch:{ Exception -> 0x0084 }
            if (r13 == 0) goto L_0x007c
            if (r10 == 0) goto L_0x0071
            r14 = 0
            org.glassfish.grizzly.nio.transport.TCPNIOConnectorHandler$InstantConnectHandler r15 = r1.instantConnectHandler     // Catch:{ Exception -> 0x0084 }
            r13.registerChannelAsync(r0, r14, r5, r15)     // Catch:{ Exception -> 0x0084 }
            goto L_0x007b
        L_0x0071:
            r14 = 8
            org.glassfish.grizzly.nio.transport.TCPNIOConnectorHandler$RegisterChannelCompletionHandler r15 = new org.glassfish.grizzly.nio.transport.TCPNIOConnectorHandler$RegisterChannelCompletionHandler     // Catch:{ Exception -> 0x0084 }
            r15.<init>(r5)     // Catch:{ Exception -> 0x0084 }
            r13.registerChannelAsync(r0, r14, r5, r15)     // Catch:{ Exception -> 0x0084 }
        L_0x007b:
            return r11
        L_0x007c:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException     // Catch:{ Exception -> 0x0084 }
            java.lang.String r15 = "NIOChannelDistributor is null. Is Transport running?"
            r14.<init>(r15)     // Catch:{ Exception -> 0x0084 }
            throw r14     // Catch:{ Exception -> 0x0084 }
        L_0x0084:
            r0 = move-exception
            goto L_0x0089
        L_0x0086:
            r0 = move-exception
            r9 = r17
        L_0x0089:
            if (r5 == 0) goto L_0x008e
            r5.closeSilently()
        L_0x008e:
            if (r3 == 0) goto L_0x0093
            r3.failed(r0)
        L_0x0093:
            if (r20 == 0) goto L_0x009a
            org.glassfish.grizzly.impl.ReadyFutureImpl r6 = org.glassfish.grizzly.impl.ReadyFutureImpl.create((java.lang.Throwable) r0)
            goto L_0x009b
        L_0x009a:
            r6 = 0
        L_0x009b:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.nio.transport.TCPNIOConnectorHandler.connectAsync(java.net.SocketAddress, java.net.SocketAddress, org.glassfish.grizzly.CompletionHandler, boolean):org.glassfish.grizzly.impl.FutureImpl");
    }

    protected static void onConnectedAsync(TCPNIOConnection connection, CompletionHandler<Connection> completionHandler) {
        TCPNIOTransport tcpTransport = (TCPNIOTransport) connection.getTransport();
        SocketChannel channel = (SocketChannel) connection.getChannel();
        try {
            if (!channel.isConnected()) {
                channel.finishConnect();
            }
            connection.resetProperties();
            connection.disableIOEvent(IOEvent.CLIENT_CONNECTED);
            tcpTransport.getChannelConfigurator().postConfigure(tcpTransport, channel);
            if (connection.notifyReady()) {
                tcpTransport.fireIOEvent(IOEvent.CONNECTED, connection, new EnableReadHandler(completionHandler));
            }
        } catch (Exception e) {
            abortConnection(connection, completionHandler, e);
            throw Exceptions.makeIOException(e);
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
    public static void abortConnection(TCPNIOConnection connection, CompletionHandler<Connection> completionHandler, Throwable failure) {
        connection.closeSilently();
        if (completionHandler != null) {
            completionHandler.failed(failure);
        }
    }

    public class InstantConnectHandler extends EmptyCompletionHandler<RegisterChannelResult> {
        private InstantConnectHandler() {
        }

        public void completed(RegisterChannelResult result) {
            TCPNIOTransport transport = (TCPNIOTransport) TCPNIOConnectorHandler.this.transport;
            transport.selectorRegistrationHandler.completed(result);
            try {
                ((TCPNIOConnection) transport.getSelectionKeyHandler().getConnectionForKey(result.getSelectionKey())).onConnect();
            } catch (Exception e) {
                TCPNIOConnectorHandler.LOGGER.log(Level.FINE, "Exception happened, when trying to connect the channel", e);
            }
        }
    }

    public static class RegisterChannelCompletionHandler extends EmptyCompletionHandler<RegisterChannelResult> {
        private final TCPNIOConnection connection;

        public RegisterChannelCompletionHandler(TCPNIOConnection connection2) {
            this.connection = connection2;
        }

        public void completed(RegisterChannelResult result) {
            ((TCPNIOTransport) this.connection.getTransport()).selectorRegistrationHandler.completed(result);
        }

        public void failed(Throwable throwable) {
            this.connection.checkConnectFailed(throwable);
        }
    }

    public static final class EnableReadHandler extends IOEventLifeCycleListener.Adapter {
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
            TCPNIOConnection connection = (TCPNIOConnection) context.getConnection();
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

    public static Builder builder(TCPNIOTransport transport) {
        return new Builder().setTransport(transport);
    }

    public static class Builder extends AbstractSocketConnectorHandler.Builder<Builder> {
        private Boolean reuseAddress;
        private Long timeout;
        private TimeUnit timeoutTimeunit;
        private TCPNIOTransport transport;

        public TCPNIOConnectorHandler build() {
            TCPNIOConnectorHandler handler = (TCPNIOConnectorHandler) super.build();
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

        public Builder setTransport(TCPNIOTransport transport2) {
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
                return new TCPNIOConnectorHandler(this.transport);
            }
            throw new IllegalStateException("Unable to create TCPNIOConnectorHandler - transport is null");
        }
    }
}

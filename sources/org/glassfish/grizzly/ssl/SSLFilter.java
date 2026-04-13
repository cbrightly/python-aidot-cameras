package org.glassfish.grizzly.ssl;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CloseListener;
import org.glassfish.grizzly.CloseType;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.GenericCloseListener;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.PendingWriteQueueLimitExceededException;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.utils.Exceptions;
import org.glassfish.grizzly.utils.JdkVersion;

public class SSLFilter extends SSLBaseFilter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean IS_JDK7_OR_HIGHER = (JdkVersion.getJdkVersion().compareTo("1.7.0") >= 0);
    /* access modifiers changed from: private */
    public static final Logger LOGGER = Grizzly.logger(SSLFilter.class);
    private final SSLEngineConfigurator clientSSLEngineConfigurator;
    private final ConnectionCloseListener closeListener;
    /* access modifiers changed from: private */
    public final Attribute<SSLHandshakeContext> handshakeContextAttr;
    protected volatile int maxPendingBytes;

    public SSLFilter() {
        this((SSLEngineConfigurator) null, (SSLEngineConfigurator) null);
    }

    public SSLFilter(SSLEngineConfigurator serverSSLEngineConfigurator, SSLEngineConfigurator clientSSLEngineConfigurator2) {
        this(serverSSLEngineConfigurator, clientSSLEngineConfigurator2, true);
    }

    public SSLFilter(SSLEngineConfigurator serverSSLEngineConfigurator, SSLEngineConfigurator clientSSLEngineConfigurator2, boolean renegotiateOnClientAuthWant) {
        super(serverSSLEngineConfigurator, renegotiateOnClientAuthWant);
        this.closeListener = new ConnectionCloseListener();
        this.maxPendingBytes = Integer.MAX_VALUE;
        if (clientSSLEngineConfigurator2 == null) {
            this.clientSSLEngineConfigurator = new SSLEngineConfigurator(SSLContextConfigurator.DEFAULT_CONFIG.createSSLContext(true), true, false, false);
        } else {
            this.clientSSLEngineConfigurator = clientSSLEngineConfigurator2;
        }
        this.handshakeContextAttr = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute("SSLFilter-SSLHandshakeContextAttr");
    }

    public SSLEngineConfigurator getClientSSLEngineConfigurator() {
        return this.clientSSLEngineConfigurator;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0030, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.glassfish.grizzly.filterchain.NextAction handleWrite(org.glassfish.grizzly.filterchain.FilterChainContext r11) {
        /*
            r10 = this;
            org.glassfish.grizzly.Connection r7 = r11.getConnection()
            java.lang.Object r0 = r11.getMessage()
            boolean r0 = r0 instanceof org.glassfish.grizzly.FileTransfer
            if (r0 != 0) goto L_0x0050
            monitor-enter(r7)
            org.glassfish.grizzly.ssl.SSLConnectionContext r0 = r10.obtainSslConnectionContext(r7)     // Catch:{ all -> 0x004d }
            r8 = r0
            javax.net.ssl.SSLEngine r0 = r8.getSslEngine()     // Catch:{ all -> 0x004d }
            r9 = r0
            if (r9 == 0) goto L_0x0031
            boolean r0 = org.glassfish.grizzly.ssl.SSLUtils.isHandshaking(r9)     // Catch:{ all -> 0x004d }
            if (r0 != 0) goto L_0x0031
            boolean r0 = r8.isServerMode()     // Catch:{ all -> 0x004d }
            if (r0 == 0) goto L_0x002a
            org.glassfish.grizzly.filterchain.NextAction r0 = super.handleWrite(r11)     // Catch:{ all -> 0x004d }
            goto L_0x002f
        L_0x002a:
            r0 = 1
            org.glassfish.grizzly.filterchain.NextAction r0 = r10.accurateWrite(r11, r0)     // Catch:{ all -> 0x004d }
        L_0x002f:
            monitor-exit(r7)     // Catch:{ all -> 0x004d }
            return r0
        L_0x0031:
            if (r9 == 0) goto L_0x003b
            org.glassfish.grizzly.attributes.Attribute<org.glassfish.grizzly.ssl.SSLFilter$SSLHandshakeContext> r0 = r10.handshakeContextAttr     // Catch:{ all -> 0x004d }
            boolean r0 = r0.isSet((org.glassfish.grizzly.attributes.AttributeStorage) r7)     // Catch:{ all -> 0x004d }
            if (r0 != 0) goto L_0x0046
        L_0x003b:
            r2 = 0
            r3 = 0
            org.glassfish.grizzly.ssl.SSLEngineConfigurator r4 = r10.clientSSLEngineConfigurator     // Catch:{ all -> 0x004d }
            r6 = 0
            r0 = r10
            r1 = r7
            r5 = r11
            r0.handshake(r1, r2, r3, r4, r5, r6)     // Catch:{ all -> 0x004d }
        L_0x0046:
            r0 = 0
            org.glassfish.grizzly.filterchain.NextAction r0 = r10.accurateWrite(r11, r0)     // Catch:{ all -> 0x004d }
            monitor-exit(r7)     // Catch:{ all -> 0x004d }
            return r0
        L_0x004d:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x004d }
            throw r0
        L_0x0050:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "TLS operations not supported with SendFile messages"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.ssl.SSLFilter.handleWrite(org.glassfish.grizzly.filterchain.FilterChainContext):org.glassfish.grizzly.filterchain.NextAction");
    }

    public int getMaxPendingBytesPerConnection() {
        return this.maxPendingBytes;
    }

    public void setMaxPendingBytesPerConnection(int maxPendingBytes2) {
        this.maxPendingBytes = maxPendingBytes2;
    }

    public void handshake(Connection connection, CompletionHandler<SSLEngine> completionHandler) {
        handshake(connection, completionHandler, (Object) null, this.clientSSLEngineConfigurator);
    }

    public void handshake(Connection connection, CompletionHandler<SSLEngine> completionHandler, Object dstAddress) {
        handshake(connection, completionHandler, dstAddress, this.clientSSLEngineConfigurator);
    }

    public void handshake(Connection connection, CompletionHandler<SSLEngine> completionHandler, Object dstAddress, SSLEngineConfigurator sslEngineConfigurator) {
        handshake(connection, completionHandler, dstAddress, sslEngineConfigurator, createContext(connection, FilterChainContext.Operation.WRITE), true);
    }

    /* access modifiers changed from: protected */
    public void handshake(Connection<?> connection, CompletionHandler<SSLEngine> completionHandler, Object dstAddress, SSLEngineConfigurator sslEngineConfigurator, FilterChainContext context, boolean forceBeginHandshake) {
        SSLConnectionContext sslCtx = obtainSslConnectionContext(connection);
        SSLEngine sslEngine = sslCtx.getSslEngine();
        if (sslEngine == null) {
            sslEngine = createClientSSLEngine(sslCtx, sslEngineConfigurator);
            sslCtx.configure(sslEngine);
        } else if (!SSLUtils.isHandshaking(sslEngine)) {
            sslEngineConfigurator.configure(sslEngine);
        }
        notifyHandshakeStart(connection);
        if (forceBeginHandshake || !sslEngine.getSession().isValid()) {
            sslEngine.beginHandshake();
        }
        this.handshakeContextAttr.set((AttributeStorage) connection, new SSLHandshakeContext(connection, completionHandler));
        connection.addCloseListener((CloseListener) this.closeListener);
        synchronized (connection) {
            if (doHandshakeStep(sslCtx, context, (Buffer) null) != null) {
                throw new AssertionError();
            }
        }
    }

    private NextAction accurateWrite(FilterChainContext ctx, boolean isHandshakeComplete) {
        Connection connection = ctx.getConnection();
        SSLHandshakeContext handshakeContext = this.handshakeContextAttr.get((AttributeStorage) connection);
        if (isHandshakeComplete && handshakeContext == null) {
            return super.handleWrite(ctx);
        }
        if (handshakeContext == null) {
            handshakeContext = new SSLHandshakeContext(connection, (CompletionHandler<SSLEngine>) null);
            this.handshakeContextAttr.set((AttributeStorage) connection, handshakeContext);
        }
        if (!handshakeContext.add(ctx)) {
            return super.handleWrite(ctx);
        }
        return ctx.getSuspendAction();
    }

    /* access modifiers changed from: protected */
    public void notifyHandshakeComplete(Connection<?> connection, SSLEngine sslEngine) {
        SSLHandshakeContext handshakeContext = this.handshakeContextAttr.get((AttributeStorage) connection);
        if (handshakeContext != null) {
            connection.removeCloseListener((CloseListener) this.closeListener);
            handshakeContext.completed(sslEngine);
            this.handshakeContextAttr.remove((AttributeStorage) connection);
        }
        super.notifyHandshakeComplete(connection, sslEngine);
    }

    /* access modifiers changed from: protected */
    public void notifyHandshakeFailed(Connection connection, Throwable t) {
        SSLHandshakeContext handshakeContext = this.handshakeContextAttr.get((AttributeStorage) connection);
        if (handshakeContext != null) {
            connection.removeCloseListener((CloseListener) this.closeListener);
            handshakeContext.failed(t);
        }
        super.notifyHandshakeFailed(connection, t);
    }

    /* access modifiers changed from: protected */
    public Buffer doHandshakeStep(SSLConnectionContext sslCtx, FilterChainContext ctx, Buffer inputBuffer, Buffer tmpAppBuffer0) {
        try {
            return super.doHandshakeStep(sslCtx, ctx, inputBuffer, tmpAppBuffer0);
        } catch (IOException ioe) {
            SSLHandshakeContext context = this.handshakeContextAttr.get((AttributeStorage) ctx.getConnection());
            if (context != null) {
                context.failed(ioe);
            }
            throw ioe;
        }
    }

    /* access modifiers changed from: protected */
    public SSLEngine createClientSSLEngine(SSLConnectionContext sslCtx, SSLEngineConfigurator sslEngineConfigurator) {
        if (IS_JDK7_OR_HIGHER) {
            return sslEngineConfigurator.createSSLEngine(HostNameResolver.getPeerHostName(sslCtx.getConnection()), -1);
        }
        return sslEngineConfigurator.createSSLEngine();
    }

    public final class SSLHandshakeContext {
        private CompletionHandler<SSLEngine> completionHandler;
        private final Connection connection;
        private Throwable error;
        private boolean isComplete;
        private List<FilterChainContext> pendingWriteContexts;
        private int sizeInBytes = 0;

        public SSLHandshakeContext(Connection connection2, CompletionHandler<SSLEngine> completionHandler2) {
            this.connection = connection2;
            this.completionHandler = completionHandler2;
        }

        public boolean add(FilterChainContext context) {
            Throwable th = this.error;
            if (th != null) {
                throw Exceptions.makeIOException(th);
            } else if (this.isComplete) {
                return false;
            } else {
                int newSize = this.sizeInBytes + ((Buffer) context.getMessage()).remaining();
                if (newSize <= SSLFilter.this.maxPendingBytes) {
                    this.sizeInBytes = newSize;
                    if (this.pendingWriteContexts == null) {
                        this.pendingWriteContexts = new LinkedList();
                    }
                    this.pendingWriteContexts.add(context);
                    return true;
                }
                throw new PendingWriteQueueLimitExceededException("Max queued data limit exceeded: " + newSize + '>' + SSLFilter.this.maxPendingBytes);
            }
        }

        public void completed(SSLEngine engine) {
            try {
                synchronized (this.connection) {
                    this.isComplete = true;
                    CompletionHandler<SSLEngine> completionHandlerLocal = this.completionHandler;
                    this.completionHandler = null;
                    if (completionHandlerLocal != null) {
                        completionHandlerLocal.completed(engine);
                    }
                    resumePendingWrites();
                }
            } catch (Exception e) {
                SSLFilter.LOGGER.log(Level.FINE, "Unexpected SSLHandshakeContext.completed() error", e);
                failed(e);
            }
        }

        public void failed(Throwable throwable) {
            synchronized (this.connection) {
                if (this.error == null) {
                    this.error = throwable;
                    CompletionHandler<SSLEngine> completionHandlerLocal = this.completionHandler;
                    this.completionHandler = null;
                    if (completionHandlerLocal != null) {
                        completionHandlerLocal.failed(throwable);
                    }
                    this.connection.closeWithReason(Exceptions.makeIOException(throwable));
                    resumePendingWrites();
                }
            }
        }

        private void resumePendingWrites() {
            List<FilterChainContext> pendingWriteContextsLocal = this.pendingWriteContexts;
            this.pendingWriteContexts = null;
            if (pendingWriteContextsLocal != null) {
                for (FilterChainContext ctx : pendingWriteContextsLocal) {
                    try {
                        ctx.resume();
                    } catch (Exception e) {
                    }
                }
                pendingWriteContextsLocal.clear();
                this.sizeInBytes = 0;
            }
        }
    }

    public final class ConnectionCloseListener implements GenericCloseListener {
        private ConnectionCloseListener() {
        }

        public void onClosed(Closeable closeable, CloseType type) {
            Connection connection = (Connection) closeable;
            SSLHandshakeContext handshakeContext = (SSLHandshakeContext) SSLFilter.this.handshakeContextAttr.get((AttributeStorage) connection);
            if (handshakeContext != null) {
                handshakeContext.failed(new EOFException());
                SSLFilter.this.handshakeContextAttr.remove((AttributeStorage) connection);
            }
        }
    }

    public static class HostNameResolver {
        private HostNameResolver() {
        }

        public static String getPeerHostName(Connection<?> connection) {
            Object peerAddress = connection.getPeerAddress();
            if (peerAddress instanceof InetSocketAddress) {
                return ((InetSocketAddress) peerAddress).getHostString();
            }
            return null;
        }
    }
}

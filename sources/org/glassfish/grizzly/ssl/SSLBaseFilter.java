package org.glassfish.grizzly.ssl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.FileTransfer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.ProcessorExecutor;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.FilterChainEvent;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.ssl.SSLConnectionContext;
import org.glassfish.grizzly.utils.Futures;

public class SSLBaseFilter extends BaseFilter {
    protected static final MessageCloner<Buffer> COPY_CLONER = new OnWriteCopyCloner();
    private static final Logger LOGGER = Grizzly.logger(SSLBaseFilter.class);
    private static final SSLConnectionContext.Allocator MM_ALLOCATOR = new SSLConnectionContext.Allocator() {
        public Buffer grow(SSLConnectionContext sslCtx, Buffer oldBuffer, int newSize) {
            MemoryManager mm = sslCtx.getConnection().getMemoryManager();
            return oldBuffer == null ? mm.allocate(newSize) : mm.reallocate(oldBuffer, newSize);
        }
    };
    private static final SSLConnectionContext.Allocator OUTPUT_BUFFER_ALLOCATOR = new SSLConnectionContext.Allocator() {
        public Buffer grow(SSLConnectionContext sslCtx, Buffer oldBuffer, int newSize) {
            return SSLUtils.allocateOutputBuffer(newSize);
        }
    };
    protected final Set<HandshakeListener> handshakeListeners;
    private long handshakeTimeoutMillis;
    private SSLTransportFilterWrapper optimizedTransportFilter;
    private final boolean renegotiateOnClientAuthWant;
    private volatile boolean renegotiationDisabled;
    /* access modifiers changed from: private */
    public final SSLEngineConfigurator serverSSLEngineConfigurator;

    public interface HandshakeListener {
        void onComplete(Connection<?> connection);

        void onFailure(Connection<?> connection, Throwable th);

        void onInit(Connection<?> connection, SSLEngine sSLEngine);

        void onStart(Connection<?> connection);
    }

    public SSLBaseFilter() {
        this((SSLEngineConfigurator) null);
    }

    public SSLBaseFilter(SSLEngineConfigurator serverSSLEngineConfigurator2) {
        this(serverSSLEngineConfigurator2, true);
    }

    public SSLBaseFilter(SSLEngineConfigurator serverSSLEngineConfigurator2, boolean renegotiateOnClientAuthWant2) {
        SSLEngineConfigurator sSLEngineConfigurator;
        this.handshakeListeners = Collections.newSetFromMap(new ConcurrentHashMap(2));
        this.handshakeTimeoutMillis = -1;
        this.renegotiateOnClientAuthWant = renegotiateOnClientAuthWant2;
        if (serverSSLEngineConfigurator2 != null) {
            sSLEngineConfigurator = serverSSLEngineConfigurator2;
        } else {
            sSLEngineConfigurator = new SSLEngineConfigurator(SSLContextConfigurator.DEFAULT_CONFIG.createSSLContext(true), false, false, false);
        }
        this.serverSSLEngineConfigurator = sSLEngineConfigurator;
    }

    public boolean isRenegotiateOnClientAuthWant() {
        return this.renegotiateOnClientAuthWant;
    }

    public SSLEngineConfigurator getServerSSLEngineConfigurator() {
        return this.serverSSLEngineConfigurator;
    }

    public void addHandshakeListener(HandshakeListener listener) {
        this.handshakeListeners.add(listener);
    }

    public void removeHandshakeListener(HandshakeListener listener) {
        this.handshakeListeners.remove(listener);
    }

    public long getHandshakeTimeout(TimeUnit timeUnit) {
        long j = this.handshakeTimeoutMillis;
        if (j < 0) {
            return -1;
        }
        return timeUnit.convert(j, TimeUnit.MILLISECONDS);
    }

    public void setHandshakeTimeout(long handshakeTimeout, TimeUnit timeUnit) {
        if (handshakeTimeout < 0) {
            this.handshakeTimeoutMillis = -1;
        } else {
            this.handshakeTimeoutMillis = TimeUnit.MILLISECONDS.convert(handshakeTimeout, timeUnit);
        }
    }

    public void setRenegotiationDisabled(boolean renegotiationDisabled2) {
        this.renegotiationDisabled = renegotiationDisabled2;
    }

    /* access modifiers changed from: protected */
    public SSLTransportFilterWrapper getOptimizedTransportFilter(TransportFilter childFilter) {
        SSLTransportFilterWrapper sSLTransportFilterWrapper = this.optimizedTransportFilter;
        if (sSLTransportFilterWrapper == null || sSLTransportFilterWrapper.wrappedFilter != childFilter) {
            this.optimizedTransportFilter = createOptimizedTransportFilter(childFilter);
        }
        return this.optimizedTransportFilter;
    }

    /* access modifiers changed from: protected */
    public SSLTransportFilterWrapper createOptimizedTransportFilter(TransportFilter childFilter) {
        return new SSLTransportFilterWrapper(childFilter, this);
    }

    public void onRemoved(FilterChain filterChain) {
        int sslTransportFilterIdx;
        SSLTransportFilterWrapper sSLTransportFilterWrapper = this.optimizedTransportFilter;
        if (sSLTransportFilterWrapper != null && (sslTransportFilterIdx = filterChain.indexOf(sSLTransportFilterWrapper)) >= 0) {
            filterChain.set(sslTransportFilterIdx, ((SSLTransportFilterWrapper) filterChain.get(sslTransportFilterIdx)).wrappedFilter);
        }
    }

    public void onAdded(FilterChain filterChain) {
        int transportFilterIdx;
        if (filterChain.indexOfType(SSLTransportFilterWrapper.class) == -1 && (transportFilterIdx = filterChain.indexOfType(TransportFilter.class)) >= 0) {
            filterChain.set(transportFilterIdx, getOptimizedTransportFilter((TransportFilter) filterChain.get(transportFilterIdx)));
        }
    }

    public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) {
        if (event.type() != "CERT_EVENT") {
            return ctx.getInvokeAction();
        }
        CertificateEvent ce = (CertificateEvent) event;
        try {
            return ctx.getSuspendAction();
        } finally {
            getPeerCertificateChain(obtainSslConnectionContext(ctx.getConnection()), ctx, ce.needClientAuth, ce.certsFuture);
        }
    }

    public NextAction handleRead(FilterChainContext ctx) {
        SSLEngine sslEngine;
        Buffer buffer;
        Connection connection = ctx.getConnection();
        SSLConnectionContext sslCtx = obtainSslConnectionContext(connection);
        SSLEngine sslEngine2 = sslCtx.getSslEngine();
        if (sslEngine2 != null && !SSLUtils.isHandshaking(sslEngine2)) {
            return unwrapAll(ctx, sslCtx);
        }
        if (sslEngine2 == null) {
            SSLEngine sslEngine3 = this.serverSSLEngineConfigurator.createSSLEngine();
            sslEngine3.beginHandshake();
            sslCtx.configure(sslEngine3);
            notifyHandshakeStart(connection);
            sslEngine = sslEngine3;
        } else {
            sslEngine = sslEngine2;
        }
        if (this.handshakeTimeoutMillis >= 0) {
            buffer = doHandshakeSync(sslCtx, ctx, (Buffer) ctx.getMessage(), this.handshakeTimeoutMillis);
        } else {
            buffer = SSLUtils.makeInputRemainder(sslCtx, ctx, doHandshakeStep(sslCtx, ctx, (Buffer) ctx.getMessage()));
        }
        boolean hasRemaining = buffer != null && buffer.hasRemaining();
        if (!SSLUtils.isHandshaking(sslEngine)) {
            notifyHandshakeComplete(connection, sslEngine);
            FilterChain connectionFilterChain = sslCtx.getNewConnectionFilterChain();
            sslCtx.setNewConnectionFilterChain((FilterChain) null);
            if (connectionFilterChain != null) {
                Logger logger = LOGGER;
                Level level = Level.FINE;
                if (logger.isLoggable(level)) {
                    logger.log(level, "Applying new FilterChain afterSSLHandshake. Connection={0} filterchain={1}", new Object[]{connection, connectionFilterChain});
                }
                connection.setProcessor(connectionFilterChain);
                if (!hasRemaining) {
                    return ctx.getStopAction();
                }
                NextAction suspendAction = ctx.getSuspendAction();
                ctx.setMessage(buffer);
                ctx.suspend();
                ProcessorExecutor.execute(obtainProtocolChainContext(ctx, connectionFilterChain).getInternalContext());
                return suspendAction;
            } else if (hasRemaining) {
                ctx.setMessage(buffer);
                return unwrapAll(ctx, sslCtx);
            }
        }
        return ctx.getStopAction(buffer);
    }

    public NextAction handleWrite(FilterChainContext ctx) {
        NextAction stopAction;
        if (!(ctx.getMessage() instanceof FileTransfer)) {
            Connection connection = ctx.getConnection();
            synchronized (connection) {
                Buffer output = wrapAll(ctx, obtainSslConnectionContext(connection));
                FilterChainContext.TransportContext transportContext = ctx.getTransportContext();
                ctx.write((Object) null, output, transportContext.getCompletionHandler(), transportContext.getPushBackHandler(), COPY_CLONER, transportContext.isBlocking());
                stopAction = ctx.getStopAction();
            }
            return stopAction;
        }
        throw new IllegalStateException("TLS operations not supported with SendFile messages");
    }

    /* access modifiers changed from: protected */
    public NextAction unwrapAll(FilterChainContext ctx, SSLConnectionContext sslCtx) {
        Buffer input = (Buffer) ctx.getMessage();
        Buffer output = null;
        boolean isClosed = false;
        while (true) {
            int len = SSLUtils.getSSLPacketSize(input);
            if (len != -1 && input.remaining() >= len) {
                SSLConnectionContext.SslResult result = sslCtx.unwrap(len, input, output, MM_ALLOCATOR);
                output = result.getOutput();
                if (!result.isError()) {
                    if (SSLUtils.isHandshaking(sslCtx.getSslEngine())) {
                        if (result.getSslEngineResult().getStatus() != SSLEngineResult.Status.CLOSED) {
                            input = rehandshake(ctx, sslCtx);
                        } else {
                            input = silentRehandshake(ctx, sslCtx);
                            isClosed = true;
                        }
                        if (input == null) {
                        }
                    }
                    switch (AnonymousClass4.$SwitchMap$javax$net$ssl$SSLEngineResult$Status[result.getSslEngineResult().getStatus().ordinal()]) {
                        case 1:
                            if (!input.hasRemaining()) {
                                break;
                            }
                        case 2:
                            isClosed = true;
                            break;
                        default:
                            throw new IllegalStateException("Unexpected status: " + result.getSslEngineResult().getStatus());
                    }
                } else {
                    output.dispose();
                    throw result.getError();
                }
            }
        }
        if (output != null) {
            output.trim();
            if (output.hasRemaining() || isClosed) {
                ctx.setMessage(output);
                if (!isClosed) {
                    return ctx.getInvokeAction(SSLUtils.makeInputRemainder(sslCtx, ctx, input));
                }
                LOGGER.finer("Closed SSL connection detected, terminating chain.");
            }
        }
        return ctx.getStopAction(SSLUtils.makeInputRemainder(sslCtx, ctx, input));
    }

    /* access modifiers changed from: protected */
    public Buffer wrapAll(FilterChainContext ctx, SSLConnectionContext sslCtx) {
        Buffer input = (Buffer) ctx.getMessage();
        Buffer output = sslCtx.wrapAll(input, OUTPUT_BUFFER_ALLOCATOR);
        input.tryDispose();
        return output;
    }

    /* access modifiers changed from: protected */
    public Buffer doHandshakeSync(SSLConnectionContext sslCtx, FilterChainContext ctx, Buffer inputBuffer, long timeoutMillis) {
        Connection connection = ctx.getConnection();
        SSLEngine sslEngine = sslCtx.getSslEngine();
        Buffer tmpAppBuffer = SSLUtils.allocateOutputBuffer(sslCtx.getAppBufferSize());
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        long oldReadTimeout = connection.getReadTimeout(timeUnit);
        try {
            connection.setReadTimeout(timeoutMillis, timeUnit);
            Buffer inputBuffer2 = SSLUtils.makeInputRemainder(sslCtx, ctx, doHandshakeStep(sslCtx, ctx, inputBuffer, tmpAppBuffer));
            while (SSLUtils.isHandshaking(sslEngine)) {
                inputBuffer2 = SSLUtils.makeInputRemainder(sslCtx, ctx, doHandshakeStep(sslCtx, ctx, Buffers.appendBuffers(ctx.getMemoryManager(), inputBuffer2, (Buffer) ctx.read().getMessage()), tmpAppBuffer));
            }
            return inputBuffer2;
        } finally {
            tmpAppBuffer.dispose();
            connection.setReadTimeout(oldReadTimeout, TimeUnit.MILLISECONDS);
        }
    }

    /* access modifiers changed from: protected */
    public Buffer doHandshakeStep(SSLConnectionContext sslCtx, FilterChainContext ctx, Buffer inputBuffer) {
        return doHandshakeStep(sslCtx, ctx, inputBuffer, (Buffer) null);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00f9, code lost:
        throw new javax.net.ssl.SSLException("SSL unwrap error: " + r11);
     */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0142  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0147  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x014e  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x00ff A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.glassfish.grizzly.Buffer doHandshakeStep(org.glassfish.grizzly.ssl.SSLConnectionContext r16, org.glassfish.grizzly.filterchain.FilterChainContext r17, org.glassfish.grizzly.Buffer r18, org.glassfish.grizzly.Buffer r19) {
        /*
            r15 = this;
            r1 = r16
            r2 = r17
            org.glassfish.grizzly.Connection r3 = r17.getConnection()
            java.util.logging.Logger r0 = LOGGER
            java.util.logging.Level r4 = java.util.logging.Level.FINEST
            boolean r4 = r0.isLoggable(r4)
            r5 = 0
            r6 = 0
            r7 = r19
            javax.net.ssl.SSLEngine r0 = r16.getSslEngine()     // Catch:{ IOException -> 0x012c, all -> 0x0124 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r0 = r0.getHandshakeStatus()     // Catch:{ IOException -> 0x012c, all -> 0x0124 }
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r18
        L_0x0021:
            if (r4 == 0) goto L_0x0041
            java.util.logging.Logger r9 = LOGGER     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            java.util.logging.Level r10 = java.util.logging.Level.FINEST     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            java.lang.String r11 = "Loop Engine: {0} handshakeStatus={1}"
            r12 = 2
            java.lang.Object[] r12 = new java.lang.Object[r12]     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r13 = 0
            javax.net.ssl.SSLEngine r14 = r16.getSslEngine()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r12[r13] = r14     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r13 = 1
            javax.net.ssl.SSLEngine r14 = r16.getSslEngine()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r14 = r14.getHandshakeStatus()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r12[r13] = r14     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r9.log(r10, r11, r12)     // Catch:{ IOException -> 0x0122, all -> 0x011f }
        L_0x0041:
            int[] r9 = org.glassfish.grizzly.ssl.SSLBaseFilter.AnonymousClass4.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            int r10 = r0.ordinal()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r9 = r9[r10]     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            switch(r9) {
                case 1: goto L_0x0090;
                case 2: goto L_0x0071;
                case 3: goto L_0x0050;
                case 4: goto L_0x004e;
                case 5: goto L_0x004e;
                default: goto L_0x004c;
            }     // Catch:{ IOException -> 0x0122, all -> 0x011f }
        L_0x004c:
            goto L_0x00fa
        L_0x004e:
            goto L_0x00ff
        L_0x0050:
            if (r4 == 0) goto L_0x005f
            java.util.logging.Logger r9 = LOGGER     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            java.util.logging.Level r10 = java.util.logging.Level.FINEST     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            java.lang.String r11 = "NEED_TASK Engine: {0}"
            javax.net.ssl.SSLEngine r12 = r16.getSslEngine()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r9.log(r10, r11, r12)     // Catch:{ IOException -> 0x0122, all -> 0x011f }
        L_0x005f:
            javax.net.ssl.SSLEngine r9 = r16.getSslEngine()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            org.glassfish.grizzly.ssl.SSLUtils.executeDelegatedTask(r9)     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            javax.net.ssl.SSLEngine r9 = r16.getSslEngine()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r9 = r9.getHandshakeStatus()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r0 = r9
            goto L_0x00fa
        L_0x0071:
            if (r4 == 0) goto L_0x0080
            java.util.logging.Logger r9 = LOGGER     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            java.util.logging.Level r10 = java.util.logging.Level.FINEST     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            java.lang.String r11 = "NEED_WRAP Engine: {0}"
            javax.net.ssl.SSLEngine r12 = r16.getSslEngine()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r9.log(r10, r11, r12)     // Catch:{ IOException -> 0x0122, all -> 0x011f }
        L_0x0080:
            org.glassfish.grizzly.Buffer r9 = org.glassfish.grizzly.ssl.SSLUtils.handshakeWrap(r3, r1, r7)     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r7 = r9
            javax.net.ssl.SSLEngine r9 = r16.getSslEngine()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r9 = r9.getHandshakeStatus()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r0 = r9
            goto L_0x00fa
        L_0x0090:
            if (r4 == 0) goto L_0x009f
            java.util.logging.Logger r9 = LOGGER     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            java.util.logging.Level r10 = java.util.logging.Level.FINEST     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            java.lang.String r11 = "NEED_UNWRAP Engine: {0}"
            javax.net.ssl.SSLEngine r12 = r16.getSslEngine()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r9.log(r10, r11, r12)     // Catch:{ IOException -> 0x0122, all -> 0x011f }
        L_0x009f:
            if (r5 == 0) goto L_0x00ff
            boolean r9 = r5.hasRemaining()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            if (r9 != 0) goto L_0x00a8
            goto L_0x00ff
        L_0x00a8:
            int r9 = org.glassfish.grizzly.ssl.SSLUtils.getSSLPacketSize(r5)     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r10 = -1
            if (r9 == r10) goto L_0x00ff
            int r10 = r5.remaining()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            if (r10 >= r9) goto L_0x00b6
            goto L_0x00ff
        L_0x00b6:
            if (r8 != 0) goto L_0x00c1
            int r10 = r16.getAppBufferSize()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            org.glassfish.grizzly.Buffer r10 = org.glassfish.grizzly.ssl.SSLUtils.allocateOutputBuffer(r10)     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r8 = r10
        L_0x00c1:
            javax.net.ssl.SSLEngineResult r10 = org.glassfish.grizzly.ssl.SSLUtils.handshakeUnwrap(r9, r1, r5, r8)     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            boolean r11 = r5.hasRemaining()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            if (r11 != 0) goto L_0x00cd
            r6 = r5
            r5 = 0
        L_0x00cd:
            javax.net.ssl.SSLEngineResult$Status r11 = r10.getStatus()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            javax.net.ssl.SSLEngineResult$Status r12 = javax.net.ssl.SSLEngineResult.Status.BUFFER_UNDERFLOW     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            if (r11 == r12) goto L_0x00e3
            javax.net.ssl.SSLEngineResult$Status r12 = javax.net.ssl.SSLEngineResult.Status.BUFFER_OVERFLOW     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            if (r11 == r12) goto L_0x00e3
            javax.net.ssl.SSLEngine r12 = r16.getSslEngine()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r12 = r12.getHandshakeStatus()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r0 = r12
            goto L_0x00fa
        L_0x00e3:
            javax.net.ssl.SSLException r12 = new javax.net.ssl.SSLException     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r13.<init>()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            java.lang.String r14 = "SSL unwrap error: "
            r13.append(r14)     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r13.append(r11)     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            java.lang.String r13 = r13.toString()     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            r12.<init>(r13)     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            throw r12     // Catch:{ IOException -> 0x0122, all -> 0x011f }
        L_0x00fa:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r9 = javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED     // Catch:{ IOException -> 0x0122, all -> 0x011f }
            if (r0 != r9) goto L_0x0021
        L_0x00ff:
            if (r19 != 0) goto L_0x0106
            if (r8 == 0) goto L_0x0106
            r8.dispose()
        L_0x0106:
            if (r6 == 0) goto L_0x010d
            r6.tryDispose()
            r5 = 0
            goto L_0x0112
        L_0x010d:
            if (r5 == 0) goto L_0x0112
            r5.shrink()
        L_0x0112:
            if (r7 == 0) goto L_0x011e
            if (r5 == 0) goto L_0x011b
            org.glassfish.grizzly.Buffer r0 = org.glassfish.grizzly.ssl.SSLUtils.makeInputRemainder(r1, r2, r5)
            r5 = r0
        L_0x011b:
            r2.write(r7)
        L_0x011e:
            return r5
        L_0x011f:
            r0 = move-exception
            r9 = r15
            goto L_0x0139
        L_0x0122:
            r0 = move-exception
            goto L_0x0132
        L_0x0124:
            r0 = move-exception
            r9 = r15
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r18
            goto L_0x0139
        L_0x012c:
            r0 = move-exception
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r18
        L_0x0132:
            r9 = r15
            r15.notifyHandshakeFailed(r3, r0)     // Catch:{ all -> 0x0138 }
            throw r0     // Catch:{ all -> 0x0138 }
        L_0x0138:
            r0 = move-exception
        L_0x0139:
            if (r19 != 0) goto L_0x0140
            if (r8 == 0) goto L_0x0140
            r8.dispose()
        L_0x0140:
            if (r6 == 0) goto L_0x0147
            r6.tryDispose()
            r5 = 0
            goto L_0x014c
        L_0x0147:
            if (r5 == 0) goto L_0x014c
            r5.shrink()
        L_0x014c:
            if (r7 == 0) goto L_0x0157
            if (r5 == 0) goto L_0x0154
            org.glassfish.grizzly.Buffer r5 = org.glassfish.grizzly.ssl.SSLUtils.makeInputRemainder(r1, r2, r5)
        L_0x0154:
            r2.write(r7)
        L_0x0157:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.ssl.SSLBaseFilter.doHandshakeStep(org.glassfish.grizzly.ssl.SSLConnectionContext, org.glassfish.grizzly.filterchain.FilterChainContext, org.glassfish.grizzly.Buffer, org.glassfish.grizzly.Buffer):org.glassfish.grizzly.Buffer");
    }

    /* renamed from: org.glassfish.grizzly.ssl.SSLBaseFilter$4  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus;
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$Status;

        static {
            int[] iArr = new int[SSLEngineResult.HandshakeStatus.values().length];
            $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus = iArr;
            try {
                iArr[SSLEngineResult.HandshakeStatus.NEED_UNWRAP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_WRAP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_TASK.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.FINISHED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            int[] iArr2 = new int[SSLEngineResult.Status.values().length];
            $SwitchMap$javax$net$ssl$SSLEngineResult$Status = iArr2;
            try {
                iArr2[SSLEngineResult.Status.OK.ordinal()] = 1;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$Status[SSLEngineResult.Status.CLOSED.ordinal()] = 2;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public void renegotiate(SSLConnectionContext sslCtx, FilterChainContext context) {
        if (!this.renegotiationDisabled) {
            SSLEngine sslEngine = sslCtx.getSslEngine();
            if (!sslEngine.getWantClientAuth() || this.renegotiateOnClientAuthWant) {
                boolean authConfigured = sslEngine.getWantClientAuth() || sslEngine.getNeedClientAuth();
                if (!authConfigured) {
                    sslEngine.setNeedClientAuth(true);
                }
                sslEngine.getSession().invalidate();
                try {
                    sslEngine.beginHandshake();
                    try {
                        rehandshake(context, sslCtx);
                    } finally {
                        if (!authConfigured) {
                            sslEngine.setNeedClientAuth(false);
                        }
                    }
                } catch (SSLHandshakeException e) {
                    if (e.toString().toLowerCase().contains("insecure renegotiation")) {
                        Logger logger = LOGGER;
                        if (logger.isLoggable(Level.SEVERE)) {
                            logger.severe("Secure SSL/TLS renegotiation is not supported by the peer.  This is most likely due to the peer using an older SSL/TLS implementation that does not implement RFC 5746.");
                        }
                    }
                    throw e;
                }
            }
        }
    }

    private Buffer silentRehandshake(FilterChainContext context, SSLConnectionContext sslCtx) {
        try {
            return doHandshakeSync(sslCtx, context, (Buffer) null, this.handshakeTimeoutMillis);
        } catch (Throwable th) {
            Logger logger = LOGGER;
            Level level = Level.FINE;
            if (logger.isLoggable(level)) {
                logger.log(level, "Error during graceful ssl connection close", th);
            }
            if (th instanceof SSLException) {
                throw th;
            }
            throw new SSLException("Error during re-handshaking", th);
        }
    }

    private Buffer rehandshake(FilterChainContext context, SSLConnectionContext sslCtx) {
        Connection c = context.getConnection();
        notifyHandshakeStart(c);
        try {
            Buffer buffer = doHandshakeSync(sslCtx, context, (Buffer) null, this.handshakeTimeoutMillis);
            notifyHandshakeComplete(c, sslCtx.getSslEngine());
            return buffer;
        } catch (Throwable th) {
            notifyHandshakeFailed(c, th);
            Logger logger = LOGGER;
            Level level = Level.FINE;
            if (logger.isLoggable(level)) {
                logger.log(level, "Error during re-handshaking", th);
            }
            if (th instanceof SSLException) {
                throw th;
            }
            throw new SSLException("Error during re-handshaking", th);
        }
    }

    /* access modifiers changed from: protected */
    public void getPeerCertificateChain(final SSLConnectionContext sslCtx, final FilterChainContext context, boolean needClientAuth, final FutureImpl<Object[]> certFuture) {
        Certificate[] certs = getPeerCertificates(sslCtx);
        if (certs != null) {
            certFuture.result(certs);
        } else if (needClientAuth) {
            Transport transport = context.getConnection().getTransport();
            ExecutorService threadPool = transport.getWorkerThreadPool();
            if (threadPool == null) {
                threadPool = transport.getKernelThreadPool();
            }
            threadPool.submit(new Runnable() {
                public void run() {
                    try {
                        SSLBaseFilter.this.renegotiate(sslCtx, context);
                        Certificate[] certs = SSLBaseFilter.getPeerCertificates(sslCtx);
                        if (certs == null) {
                            certFuture.result(null);
                            return;
                        }
                        X509Certificate[] x509Certs = SSLBaseFilter.extractX509Certs(certs);
                        if (x509Certs != null) {
                            if (x509Certs.length >= 1) {
                                certFuture.result(x509Certs);
                                FilterChainContext filterChainContext = context;
                                filterChainContext.resume(filterChainContext.getStopAction());
                                return;
                            }
                        }
                        certFuture.result(null);
                    } catch (IOException ioe) {
                        certFuture.failure(ioe);
                    } finally {
                        FilterChainContext filterChainContext2 = context;
                        filterChainContext2.resume(filterChainContext2.getStopAction());
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public SSLConnectionContext obtainSslConnectionContext(Connection connection) {
        Attribute<SSLConnectionContext> attribute = SSLUtils.SSL_CTX_ATTR;
        SSLConnectionContext sslCtx = attribute.get((AttributeStorage) connection);
        if (sslCtx != null) {
            return sslCtx;
        }
        SSLConnectionContext sslCtx2 = createSslConnectionContext(connection);
        attribute.set((AttributeStorage) connection, sslCtx2);
        return sslCtx2;
    }

    /* access modifiers changed from: protected */
    public SSLConnectionContext createSslConnectionContext(Connection connection) {
        return new SSLConnectionContext(connection);
    }

    private static FilterChainContext obtainProtocolChainContext(FilterChainContext ctx, FilterChain completeProtocolFilterChain) {
        FilterChainContext newFilterChainContext = completeProtocolFilterChain.obtainFilterChainContext(ctx.getConnection(), ctx.getStartIdx(), completeProtocolFilterChain.size(), ctx.getFilterIdx());
        newFilterChainContext.setAddressHolder(ctx.getAddressHolder());
        newFilterChainContext.setMessage(ctx.getMessage());
        newFilterChainContext.getInternalContext().setIoEvent(IOEvent.READ);
        newFilterChainContext.getInternalContext().addLifeCycleListener(new InternalProcessingHandler(ctx));
        return newFilterChainContext;
    }

    /* access modifiers changed from: private */
    public static X509Certificate[] extractX509Certs(Certificate[] certs) {
        X509Certificate[] x509Certs = new X509Certificate[certs.length];
        int len = certs.length;
        for (int i = 0; i < len; i++) {
            if (certs[i] instanceof X509Certificate) {
                x509Certs[i] = certs[i];
            } else {
                try {
                    x509Certs[i] = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(certs[i].getEncoded()));
                } catch (Exception ex) {
                    Logger logger = LOGGER;
                    Level level = Level.INFO;
                    logger.log(level, "Error translating cert " + certs[i], ex);
                    return null;
                }
            }
            Logger logger2 = LOGGER;
            Level level2 = Level.FINE;
            if (logger2.isLoggable(level2)) {
                logger2.log(level2, "Cert #{0} = {1}", new Object[]{Integer.valueOf(i), x509Certs[i]});
            }
        }
        return x509Certs;
    }

    /* access modifiers changed from: private */
    public static Certificate[] getPeerCertificates(SSLConnectionContext sslCtx) {
        try {
            return sslCtx.getSslEngine().getSession().getPeerCertificates();
        } catch (Throwable t) {
            LOGGER.log(Level.FINE, "Error getting client certs", t);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void notifyHandshakeInit(Connection<?> connection, SSLEngine sslEngine) {
        if (!this.handshakeListeners.isEmpty()) {
            for (HandshakeListener listener : this.handshakeListeners) {
                listener.onInit(connection, sslEngine);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void notifyHandshakeStart(Connection connection) {
        if (!this.handshakeListeners.isEmpty()) {
            for (HandshakeListener listener : this.handshakeListeners) {
                listener.onStart(connection);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void notifyHandshakeComplete(Connection<?> connection, SSLEngine sslEngine) {
        if (!this.handshakeListeners.isEmpty()) {
            for (HandshakeListener listener : this.handshakeListeners) {
                listener.onComplete(connection);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void notifyHandshakeFailed(Connection connection, Throwable t) {
        if (!this.handshakeListeners.isEmpty()) {
            for (HandshakeListener listener : this.handshakeListeners) {
                listener.onFailure(connection, t);
            }
        }
    }

    public static class CertificateEvent implements FilterChainEvent {
        static final String TYPE = "CERT_EVENT";
        final FutureImpl<Object[]> certsFuture = Futures.createSafeFuture();
        final boolean needClientAuth;

        public CertificateEvent(boolean needClientAuth2) {
            this.needClientAuth = needClientAuth2;
        }

        public final Object type() {
            return TYPE;
        }

        public GrizzlyFuture<Object[]> trigger(FilterChainContext ctx) {
            ctx.getFilterChain().fireEventDownstream(ctx.getConnection(), this, (CompletionHandler<FilterChainContext>) null);
            return this.certsFuture;
        }
    }

    public static class InternalProcessingHandler extends IOEventLifeCycleListener.Adapter {
        private final FilterChainContext parentContext;

        private InternalProcessingHandler(FilterChainContext parentContext2) {
            this.parentContext = parentContext2;
        }

        public void onComplete(Context context, Object data) {
            FilterChainContext filterChainContext = this.parentContext;
            filterChainContext.resume(filterChainContext.getStopAction());
        }
    }

    public static class SSLTransportFilterWrapper extends TransportFilter {
        protected final SSLBaseFilter sslBaseFilter;
        protected final TransportFilter wrappedFilter;

        public SSLTransportFilterWrapper(TransportFilter transportFilter, SSLBaseFilter sslBaseFilter2) {
            this.wrappedFilter = transportFilter;
            this.sslBaseFilter = sslBaseFilter2;
        }

        public NextAction handleAccept(FilterChainContext ctx) {
            return this.wrappedFilter.handleAccept(ctx);
        }

        public NextAction handleConnect(FilterChainContext ctx) {
            return this.wrappedFilter.handleConnect(ctx);
        }

        public NextAction handleRead(FilterChainContext ctx) {
            Connection connection = ctx.getConnection();
            SSLConnectionContext sslCtx = this.sslBaseFilter.obtainSslConnectionContext(connection);
            if (sslCtx.getSslEngine() == null) {
                SSLEngine sslEngine = this.sslBaseFilter.serverSSLEngineConfigurator.createSSLEngine();
                this.sslBaseFilter.notifyHandshakeInit(connection, sslEngine);
                sslEngine.beginHandshake();
                sslCtx.configure(sslEngine);
                this.sslBaseFilter.notifyHandshakeStart(connection);
            }
            ctx.setMessage(SSLUtils.allowDispose(SSLUtils.allocateInputBuffer(sslCtx)));
            return this.wrappedFilter.handleRead(ctx);
        }

        public NextAction handleWrite(FilterChainContext ctx) {
            return this.wrappedFilter.handleWrite(ctx);
        }

        public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) {
            return this.wrappedFilter.handleEvent(ctx, event);
        }

        public NextAction handleClose(FilterChainContext ctx) {
            return this.wrappedFilter.handleClose(ctx);
        }

        public void onAdded(FilterChain filterChain) {
            this.wrappedFilter.onAdded(filterChain);
        }

        public void onFilterChainChanged(FilterChain filterChain) {
            this.wrappedFilter.onFilterChainChanged(filterChain);
        }

        public void onRemoved(FilterChain filterChain) {
            this.wrappedFilter.onRemoved(filterChain);
        }

        public void exceptionOccurred(FilterChainContext ctx, Throwable error) {
            this.wrappedFilter.exceptionOccurred(ctx, error);
        }

        public FilterChainContext createContext(Connection connection, FilterChainContext.Operation operation) {
            return this.wrappedFilter.createContext(connection, operation);
        }
    }

    public static final class OnWriteCopyCloner implements MessageCloner<Buffer> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        private OnWriteCopyCloner() {
        }

        public Buffer clone(Connection connection, Buffer originalMessage) {
            SSLConnectionContext sslCtx = SSLUtils.getSslConnectionContext(connection);
            int copyThreshold = sslCtx.getNetBufferSize() / 2;
            Buffer lastOutputBuffer = sslCtx.resetLastOutputBuffer();
            if (originalMessage.remaining() < copyThreshold) {
                return SSLUtils.move(connection.getMemoryManager(), originalMessage);
            }
            if (lastOutputBuffer.remaining() >= copyThreshold) {
                return originalMessage;
            }
            Buffer tmpBuf = SSLUtils.copy(connection.getMemoryManager(), originalMessage);
            if (originalMessage.isComposite()) {
                ((CompositeBuffer) originalMessage).replace(lastOutputBuffer, tmpBuf);
            } else if (originalMessage != lastOutputBuffer) {
                throw new AssertionError();
            }
            lastOutputBuffer.tryDispose();
            return tmpBuf;
        }
    }
}

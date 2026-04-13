package io.netty.handler.ssl;

import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.internal.tcnative.Buffer;
import io.netty.internal.tcnative.SSL;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCounted;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetectorFactory;
import io.netty.util.ResourceLeakTracker;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.locks.Lock;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionBindingEvent;
import javax.net.ssl.SSLSessionBindingListener;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;

public class ReferenceCountedOpenSslEngine extends SSLEngine implements ReferenceCounted, ApplicationProtocolAccessor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final SSLException BEGIN_HANDSHAKE_ENGINE_CLOSED = ((SSLException) ThrowableUtil.unknownStackTrace(new SSLException("engine closed"), ReferenceCountedOpenSslEngine.class, "beginHandshake()"));
    private static final SSLEngineResult CLOSED_NOT_HANDSHAKING = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
    private static final int DEFAULT_HOSTNAME_VALIDATION_FLAGS = 0;
    private static final AtomicIntegerFieldUpdater<ReferenceCountedOpenSslEngine> DESTROYED_UPDATER = AtomicIntegerFieldUpdater.newUpdater(ReferenceCountedOpenSslEngine.class, "destroyed");
    private static final SSLException HANDSHAKE_ENGINE_CLOSED = ((SSLException) ThrowableUtil.unknownStackTrace(new SSLException("engine closed"), ReferenceCountedOpenSslEngine.class, "handshake()"));
    private static final String INVALID_CIPHER = "SSL_NULL_WITH_NULL_NULL";
    static final int MAX_PLAINTEXT_LENGTH = SSL.SSL_MAX_PLAINTEXT_LENGTH;
    /* access modifiers changed from: private */
    public static final int MAX_RECORD_SIZE = SSL.SSL_MAX_RECORD_LENGTH;
    private static final SSLEngineResult NEED_UNWRAP_CLOSED = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
    private static final SSLEngineResult NEED_UNWRAP_OK = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
    private static final SSLEngineResult NEED_WRAP_CLOSED = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
    private static final SSLEngineResult NEED_WRAP_OK = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
    private static final int[] OPENSSL_OP_NO_PROTOCOLS = {SSL.SSL_OP_NO_SSLv2, SSL.SSL_OP_NO_SSLv3, SSL.SSL_OP_NO_TLSv1, SSL.SSL_OP_NO_TLSv1_1, SSL.SSL_OP_NO_TLSv1_2};
    private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_SSLV2 = 0;
    private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_SSLV3 = 1;
    private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_TLSv1 = 2;
    private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_TLSv1_1 = 3;
    private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_TLSv1_2 = 4;
    private static final SSLException RENEGOTIATION_UNSUPPORTED = ((SSLException) ThrowableUtil.unknownStackTrace(new SSLException("renegotiation unsupported"), ReferenceCountedOpenSslEngine.class, "beginHandshake()"));
    private static final ResourceLeakDetector<ReferenceCountedOpenSslEngine> leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(ReferenceCountedOpenSslEngine.class);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) ReferenceCountedOpenSslEngine.class);
    private Object algorithmConstraints;
    private final ByteBufAllocator alloc;
    /* access modifiers changed from: private */
    public final OpenSslApplicationProtocolNegotiator apn;
    /* access modifiers changed from: private */
    public volatile String applicationProtocol;
    private boolean certificateSet;
    private volatile ClientAuth clientAuth;
    /* access modifiers changed from: private */
    public final boolean clientMode;
    private volatile int destroyed;
    private final boolean enableOcsp;
    private String endPointIdentificationAlgorithm;
    private final OpenSslEngineMap engineMap;
    SSLHandshakeException handshakeException;
    /* access modifiers changed from: private */
    public HandshakeState handshakeState = HandshakeState.NOT_STARTED;
    private boolean isInboundDone;
    final boolean jdkCompatibilityMode;
    private final OpenSslKeyMaterialManager keyMaterialManager;
    /* access modifiers changed from: private */
    public volatile long lastAccessed;
    /* access modifiers changed from: private */
    public final ResourceLeakTracker<ReferenceCountedOpenSslEngine> leak;
    /* access modifiers changed from: private */
    public final Certificate[] localCerts;
    private volatile Collection<?> matchers;
    private int maxWrapBufferSize;
    private int maxWrapOverhead;
    private long networkBIO;
    private boolean outboundClosed;
    private boolean receivedShutdown;
    private final AbstractReferenceCounted refCnt = new AbstractReferenceCounted() {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class<ReferenceCountedOpenSslEngine> cls = ReferenceCountedOpenSslEngine.class;
        }

        /* access modifiers changed from: protected */
        public void deallocate() {
            ReferenceCountedOpenSslEngine.this.shutdown();
            if (ReferenceCountedOpenSslEngine.this.leak != null && !ReferenceCountedOpenSslEngine.this.leak.close(ReferenceCountedOpenSslEngine.this)) {
                throw new AssertionError();
            }
        }
    };
    private final OpenSslSession session;
    private final ByteBuffer[] singleDstBuffer;
    private final ByteBuffer[] singleSrcBuffer;
    private List<String> sniHostNames;
    /* access modifiers changed from: private */
    public long ssl;

    public enum HandshakeState {
        NOT_STARTED,
        STARTED_IMPLICITLY,
        STARTED_EXPLICITLY,
        FINISHED
    }

    static {
        Class<ReferenceCountedOpenSslEngine> cls = ReferenceCountedOpenSslEngine.class;
    }

    ReferenceCountedOpenSslEngine(ReferenceCountedOpenSslContext context, ByteBufAllocator alloc2, String peerHost, int peerPort, boolean jdkCompatibilityMode2, boolean leakDetection) {
        super(peerHost, peerPort);
        ClientAuth clientAuth2 = ClientAuth.NONE;
        this.clientAuth = clientAuth2;
        this.lastAccessed = -1;
        boolean z = true;
        this.singleSrcBuffer = new ByteBuffer[1];
        this.singleDstBuffer = new ByteBuffer[1];
        OpenSsl.ensureAvailability();
        this.alloc = (ByteBufAllocator) ObjectUtil.checkNotNull(alloc2, "alloc");
        this.apn = (OpenSslApplicationProtocolNegotiator) context.applicationProtocolNegotiator();
        this.session = new OpenSslSession(context.sessionContext());
        boolean isClient = context.isClient();
        this.clientMode = isClient;
        this.engineMap = context.engineMap;
        this.localCerts = context.keyCertChain;
        this.keyMaterialManager = context.keyMaterialManager();
        boolean z2 = context.enableOcsp;
        this.enableOcsp = z2;
        this.jdkCompatibilityMode = jdkCompatibilityMode2;
        Lock readerLock = context.ctxLock.readLock();
        readerLock.lock();
        try {
            long j = context.ctx;
            if (context.isClient()) {
                z = false;
            }
            long finalSsl = SSL.newSSL(j, z);
            synchronized (this) {
                this.ssl = finalSsl;
                try {
                    this.networkBIO = SSL.bioNewByteBuffer(finalSsl, context.getBioNonApplicationBufferSize());
                    if (!isClient) {
                        clientAuth2 = context.clientAuth;
                    }
                    setClientAuth(clientAuth2);
                    String[] strArr = context.protocols;
                    if (strArr != null) {
                        setEnabledProtocols(strArr);
                    }
                    if (isClient && peerHost != null) {
                        SSL.setTlsExtHostName(this.ssl, peerHost);
                    }
                    if (z2) {
                        SSL.enableOcsp(this.ssl);
                    }
                    if (!jdkCompatibilityMode2) {
                        long j2 = this.ssl;
                        SSL.setMode(j2, SSL.getMode(j2) | SSL.SSL_MODE_ENABLE_PARTIAL_WRITE);
                    }
                    calculateMaxWrapOverhead();
                } catch (Throwable cause) {
                    SSL.freeSSL(this.ssl);
                    PlatformDependent.throwException(cause);
                }
            }
            this.leak = leakDetection ? leakDetector.track(this) : null;
        } finally {
            readerLock.unlock();
        }
    }

    public void setOcspResponse(byte[] response) {
        if (!this.enableOcsp) {
            throw new IllegalStateException("OCSP stapling is not enabled");
        } else if (!this.clientMode) {
            synchronized (this) {
                SSL.setOcspResponse(this.ssl, response);
            }
        } else {
            throw new IllegalStateException("Not a server SSLEngine");
        }
    }

    public byte[] getOcspResponse() {
        byte[] ocspResponse;
        if (!this.enableOcsp) {
            throw new IllegalStateException("OCSP stapling is not enabled");
        } else if (this.clientMode) {
            synchronized (this) {
                ocspResponse = SSL.getOcspResponse(this.ssl);
            }
            return ocspResponse;
        } else {
            throw new IllegalStateException("Not a client SSLEngine");
        }
    }

    public final int refCnt() {
        return this.refCnt.refCnt();
    }

    public final ReferenceCounted retain() {
        this.refCnt.retain();
        return this;
    }

    public final ReferenceCounted retain(int increment) {
        this.refCnt.retain(increment);
        return this;
    }

    public final boolean release() {
        return this.refCnt.release();
    }

    public final boolean release(int decrement) {
        return this.refCnt.release(decrement);
    }

    public final synchronized SSLSession getHandshakeSession() {
        switch (AnonymousClass2.$SwitchMap$io$netty$handler$ssl$ReferenceCountedOpenSslEngine$HandshakeState[this.handshakeState.ordinal()]) {
            case 1:
            case 2:
                return null;
            default:
                return this.session;
        }
    }

    public final synchronized long sslPointer() {
        return this.ssl;
    }

    public final synchronized void shutdown() {
        if (DESTROYED_UPDATER.compareAndSet(this, 0, 1)) {
            this.engineMap.remove(this.ssl);
            SSL.freeSSL(this.ssl);
            this.networkBIO = 0;
            this.ssl = 0;
            this.outboundClosed = true;
            this.isInboundDone = true;
        }
        SSL.clearError();
    }

    /* JADX INFO: finally extract failed */
    private int writePlaintextData(ByteBuffer src, int len) {
        int pos = src.position();
        int limit = src.limit();
        if (src.isDirect()) {
            int sslWrote = SSL.writeToSSL(this.ssl, Buffer.address(src) + ((long) pos), len);
            if (sslWrote <= 0) {
                return sslWrote;
            }
            src.position(pos + sslWrote);
            return sslWrote;
        }
        ByteBuf buf = this.alloc.directBuffer(len);
        try {
            src.limit(pos + len);
            buf.setBytes(0, src);
            src.limit(limit);
            int sslWrote2 = SSL.writeToSSL(this.ssl, OpenSsl.memoryAddress(buf), len);
            if (sslWrote2 > 0) {
                src.position(pos + sslWrote2);
            } else {
                src.position(pos);
            }
            buf.release();
            return sslWrote2;
        } catch (Throwable th) {
            buf.release();
            throw th;
        }
    }

    private ByteBuf writeEncryptedData(ByteBuffer src, int len) {
        int pos = src.position();
        if (src.isDirect()) {
            SSL.bioSetByteBuffer(this.networkBIO, Buffer.address(src) + ((long) pos), len, false);
            return null;
        }
        ByteBuf buf = this.alloc.directBuffer(len);
        try {
            int limit = src.limit();
            src.limit(pos + len);
            buf.writeBytes(src);
            src.position(pos);
            src.limit(limit);
            SSL.bioSetByteBuffer(this.networkBIO, OpenSsl.memoryAddress(buf), len, false);
            return buf;
        } catch (Throwable cause) {
            buf.release();
            PlatformDependent.throwException(cause);
            return null;
        }
    }

    /* JADX INFO: finally extract failed */
    private int readPlaintextData(ByteBuffer dst) {
        int pos = dst.position();
        if (dst.isDirect()) {
            int sslRead = SSL.readFromSSL(this.ssl, Buffer.address(dst) + ((long) pos), dst.limit() - pos);
            if (sslRead <= 0) {
                return sslRead;
            }
            dst.position(pos + sslRead);
            return sslRead;
        }
        int limit = dst.limit();
        int len = Math.min(maxEncryptedPacketLength0(), limit - pos);
        ByteBuf buf = this.alloc.directBuffer(len);
        try {
            int sslRead2 = SSL.readFromSSL(this.ssl, OpenSsl.memoryAddress(buf), len);
            if (sslRead2 > 0) {
                dst.limit(pos + sslRead2);
                buf.getBytes(buf.readerIndex(), dst);
                dst.limit(limit);
            }
            buf.release();
            return sslRead2;
        } catch (Throwable th) {
            buf.release();
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public final synchronized int maxWrapOverhead() {
        return this.maxWrapOverhead;
    }

    /* access modifiers changed from: package-private */
    public final synchronized int maxEncryptedPacketLength() {
        return maxEncryptedPacketLength0();
    }

    /* access modifiers changed from: package-private */
    public final int maxEncryptedPacketLength0() {
        return this.maxWrapOverhead + MAX_PLAINTEXT_LENGTH;
    }

    /* access modifiers changed from: package-private */
    public final int calculateMaxLengthForWrap(int plaintextLength, int numComponents) {
        return (int) Math.min((long) this.maxWrapBufferSize, ((long) plaintextLength) + (((long) this.maxWrapOverhead) * ((long) numComponents)));
    }

    /* access modifiers changed from: package-private */
    public final synchronized int sslPending() {
        return sslPending0();
    }

    /* access modifiers changed from: private */
    public void calculateMaxWrapOverhead() {
        this.maxWrapOverhead = SSL.getMaxWrapOverhead(this.ssl);
        this.maxWrapBufferSize = this.jdkCompatibilityMode ? maxEncryptedPacketLength0() : maxEncryptedPacketLength0() << 4;
    }

    private int sslPending0() {
        if (this.handshakeState != HandshakeState.FINISHED) {
            return 0;
        }
        return SSL.sslPending(this.ssl);
    }

    private boolean isBytesAvailableEnoughForWrap(int bytesAvailable, int plaintextLength, int numComponents) {
        return ((long) bytesAvailable) - (((long) this.maxWrapOverhead) * ((long) numComponents)) >= ((long) plaintextLength);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:109:0x023e, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x0296, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x02ef, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x038d, code lost:
        return r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:204:0x041c, code lost:
        return r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0036, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:233:0x049e, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:261:0x0529, code lost:
        return r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:275:0x057b, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:291:0x05d1, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:305:0x0624, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:324:0x0683, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b0, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0105, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x015c, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x01c9, code lost:
        return r9;
     */
    /* JADX WARNING: Removed duplicated region for block: B:335:0x06b1 A[Catch:{ all -> 0x06f3, all -> 0x06f6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:340:0x06e9 A[Catch:{ all -> 0x06f3, all -> 0x06f6 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final javax.net.ssl.SSLEngineResult wrap(java.nio.ByteBuffer[] r18, int r19, int r20, java.nio.ByteBuffer r21) {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = r21
            if (r2 == 0) goto L_0x0736
            if (r5 == 0) goto L_0x072d
            int r0 = r2.length
            if (r3 >= r0) goto L_0x06fe
            int r0 = r3 + r4
            int r6 = r2.length
            if (r0 > r6) goto L_0x06fe
            boolean r0 = r21.isReadOnly()
            if (r0 != 0) goto L_0x06f8
            monitor-enter(r17)
            boolean r0 = r17.isOutboundDone()     // Catch:{ all -> 0x06f3 }
            if (r0 == 0) goto L_0x0037
            boolean r0 = r17.isInboundDone()     // Catch:{ all -> 0x06f3 }
            if (r0 != 0) goto L_0x0033
            boolean r0 = r17.isDestroyed()     // Catch:{ all -> 0x06f3 }
            if (r0 == 0) goto L_0x0030
            goto L_0x0033
        L_0x0030:
            javax.net.ssl.SSLEngineResult r0 = NEED_UNWRAP_CLOSED     // Catch:{ all -> 0x06f3 }
            goto L_0x0035
        L_0x0033:
            javax.net.ssl.SSLEngineResult r0 = CLOSED_NOT_HANDSHAKING     // Catch:{ all -> 0x06f3 }
        L_0x0035:
            monitor-exit(r17)     // Catch:{ all -> 0x06f3 }
            return r0
        L_0x0037:
            r6 = 0
            r7 = 0
            boolean r0 = r21.isDirect()     // Catch:{ all -> 0x06a9 }
            if (r0 == 0) goto L_0x0054
            long r8 = r1.networkBIO     // Catch:{ all -> 0x06a9 }
            long r10 = io.netty.internal.tcnative.Buffer.address(r21)     // Catch:{ all -> 0x06a9 }
            int r0 = r21.position()     // Catch:{ all -> 0x06a9 }
            long r12 = (long) r0     // Catch:{ all -> 0x06a9 }
            long r10 = r10 + r12
            int r12 = r21.remaining()     // Catch:{ all -> 0x06a9 }
            r13 = 1
            io.netty.internal.tcnative.SSL.bioSetByteBuffer(r8, r10, r12, r13)     // Catch:{ all -> 0x06a9 }
            goto L_0x006d
        L_0x0054:
            io.netty.buffer.ByteBufAllocator r0 = r1.alloc     // Catch:{ all -> 0x06a9 }
            int r8 = r21.remaining()     // Catch:{ all -> 0x06a9 }
            io.netty.buffer.ByteBuf r0 = r0.directBuffer(r8)     // Catch:{ all -> 0x06a9 }
            r7 = r0
            long r8 = r1.networkBIO     // Catch:{ all -> 0x06a9 }
            long r10 = io.netty.handler.ssl.OpenSsl.memoryAddress(r7)     // Catch:{ all -> 0x06a9 }
            int r12 = r7.writableBytes()     // Catch:{ all -> 0x06a9 }
            r13 = 1
            io.netty.internal.tcnative.SSL.bioSetByteBuffer(r8, r10, r12, r13)     // Catch:{ all -> 0x06a9 }
        L_0x006d:
            long r8 = r1.networkBIO     // Catch:{ all -> 0x06a9 }
            int r0 = io.netty.internal.tcnative.SSL.bioLengthByteBuffer(r8)     // Catch:{ all -> 0x06a9 }
            boolean r8 = r1.outboundClosed     // Catch:{ all -> 0x06a9 }
            r9 = 0
            if (r8 == 0) goto L_0x017d
            long r10 = r1.networkBIO     // Catch:{ all -> 0x06a9 }
            int r8 = io.netty.internal.tcnative.SSL.bioFlushByteBuffer(r10)     // Catch:{ all -> 0x06a9 }
            r6 = r8
            if (r6 > 0) goto L_0x00d0
            javax.net.ssl.SSLEngineResult$HandshakeStatus r8 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING     // Catch:{ all -> 0x06a9 }
            javax.net.ssl.SSLEngineResult r8 = r1.newResultMayFinishHandshake(r8, r9, r9)     // Catch:{ all -> 0x06a9 }
            long r9 = r1.networkBIO     // Catch:{ all -> 0x06f3 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r9)     // Catch:{ all -> 0x06f3 }
            if (r7 != 0) goto L_0x0097
            int r9 = r21.position()     // Catch:{ all -> 0x06f3 }
            int r9 = r9 + r6
            r5.position(r9)     // Catch:{ all -> 0x06f3 }
            goto L_0x00af
        L_0x0097:
            int r9 = r7.readableBytes()     // Catch:{ all -> 0x06f3 }
            int r10 = r21.remaining()     // Catch:{ all -> 0x06f3 }
            if (r9 > r10) goto L_0x00b1
            int r9 = r7.readerIndex()     // Catch:{ all -> 0x06f3 }
            java.nio.ByteBuffer r9 = r7.internalNioBuffer(r9, r6)     // Catch:{ all -> 0x06f3 }
            r5.put(r9)     // Catch:{ all -> 0x06f3 }
            r7.release()     // Catch:{ all -> 0x06f3 }
        L_0x00af:
            monitor-exit(r17)     // Catch:{ all -> 0x06f3 }
            return r8
        L_0x00b1:
            java.lang.AssertionError r8 = new java.lang.AssertionError     // Catch:{ all -> 0x06f3 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x06f3 }
            r9.<init>()     // Catch:{ all -> 0x06f3 }
            java.lang.String r10 = "The destination buffer "
            r9.append(r10)     // Catch:{ all -> 0x06f3 }
            r9.append(r5)     // Catch:{ all -> 0x06f3 }
            java.lang.String r10 = " didn't have enough remaining space to hold the encrypted content in "
            r9.append(r10)     // Catch:{ all -> 0x06f3 }
            r9.append(r7)     // Catch:{ all -> 0x06f3 }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x06f3 }
            r8.<init>(r9)     // Catch:{ all -> 0x06f3 }
        L_0x00cf:
            throw r8     // Catch:{ all -> 0x06f3 }
        L_0x00d0:
            boolean r8 = r17.doSSLShutdown()     // Catch:{ all -> 0x06a9 }
            if (r8 != 0) goto L_0x0125
            javax.net.ssl.SSLEngineResult$HandshakeStatus r8 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING     // Catch:{ all -> 0x06a9 }
            javax.net.ssl.SSLEngineResult r8 = r1.newResultMayFinishHandshake(r8, r9, r6)     // Catch:{ all -> 0x06a9 }
            long r9 = r1.networkBIO     // Catch:{ all -> 0x06f3 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r9)     // Catch:{ all -> 0x06f3 }
            if (r7 != 0) goto L_0x00ec
            int r9 = r21.position()     // Catch:{ all -> 0x06f3 }
            int r9 = r9 + r6
            r5.position(r9)     // Catch:{ all -> 0x06f3 }
            goto L_0x0104
        L_0x00ec:
            int r9 = r7.readableBytes()     // Catch:{ all -> 0x06f3 }
            int r10 = r21.remaining()     // Catch:{ all -> 0x06f3 }
            if (r9 > r10) goto L_0x0106
            int r9 = r7.readerIndex()     // Catch:{ all -> 0x06f3 }
            java.nio.ByteBuffer r9 = r7.internalNioBuffer(r9, r6)     // Catch:{ all -> 0x06f3 }
            r5.put(r9)     // Catch:{ all -> 0x06f3 }
            r7.release()     // Catch:{ all -> 0x06f3 }
        L_0x0104:
            monitor-exit(r17)     // Catch:{ all -> 0x06f3 }
            return r8
        L_0x0106:
            java.lang.AssertionError r8 = new java.lang.AssertionError     // Catch:{ all -> 0x06f3 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x06f3 }
            r9.<init>()     // Catch:{ all -> 0x06f3 }
            java.lang.String r10 = "The destination buffer "
            r9.append(r10)     // Catch:{ all -> 0x06f3 }
            r9.append(r5)     // Catch:{ all -> 0x06f3 }
            java.lang.String r10 = " didn't have enough remaining space to hold the encrypted content in "
            r9.append(r10)     // Catch:{ all -> 0x06f3 }
            r9.append(r7)     // Catch:{ all -> 0x06f3 }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x06f3 }
            r8.<init>(r9)     // Catch:{ all -> 0x06f3 }
            goto L_0x00cf
        L_0x0125:
            long r10 = r1.networkBIO     // Catch:{ all -> 0x06a9 }
            int r8 = io.netty.internal.tcnative.SSL.bioLengthByteBuffer(r10)     // Catch:{ all -> 0x06a9 }
            int r6 = r0 - r8
            javax.net.ssl.SSLEngineResult$HandshakeStatus r8 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_WRAP     // Catch:{ all -> 0x06a9 }
            javax.net.ssl.SSLEngineResult r8 = r1.newResultMayFinishHandshake(r8, r9, r6)     // Catch:{ all -> 0x06a9 }
            long r9 = r1.networkBIO     // Catch:{ all -> 0x06f3 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r9)     // Catch:{ all -> 0x06f3 }
            if (r7 != 0) goto L_0x0143
            int r9 = r21.position()     // Catch:{ all -> 0x06f3 }
            int r9 = r9 + r6
            r5.position(r9)     // Catch:{ all -> 0x06f3 }
            goto L_0x015b
        L_0x0143:
            int r9 = r7.readableBytes()     // Catch:{ all -> 0x06f3 }
            int r10 = r21.remaining()     // Catch:{ all -> 0x06f3 }
            if (r9 > r10) goto L_0x015d
            int r9 = r7.readerIndex()     // Catch:{ all -> 0x06f3 }
            java.nio.ByteBuffer r9 = r7.internalNioBuffer(r9, r6)     // Catch:{ all -> 0x06f3 }
            r5.put(r9)     // Catch:{ all -> 0x06f3 }
            r7.release()     // Catch:{ all -> 0x06f3 }
        L_0x015b:
            monitor-exit(r17)     // Catch:{ all -> 0x06f3 }
            return r8
        L_0x015d:
            java.lang.AssertionError r8 = new java.lang.AssertionError     // Catch:{ all -> 0x06f3 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x06f3 }
            r9.<init>()     // Catch:{ all -> 0x06f3 }
            java.lang.String r10 = "The destination buffer "
            r9.append(r10)     // Catch:{ all -> 0x06f3 }
            r9.append(r5)     // Catch:{ all -> 0x06f3 }
            java.lang.String r10 = " didn't have enough remaining space to hold the encrypted content in "
            r9.append(r10)     // Catch:{ all -> 0x06f3 }
            r9.append(r7)     // Catch:{ all -> 0x06f3 }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x06f3 }
            r8.<init>(r9)     // Catch:{ all -> 0x06f3 }
            goto L_0x00cf
        L_0x017d:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r8 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING     // Catch:{ all -> 0x06a9 }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r10 = r1.handshakeState     // Catch:{ all -> 0x06a9 }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r11 = io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.FINISHED     // Catch:{ all -> 0x06a9 }
            if (r10 == r11) goto L_0x0310
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r11 = io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY     // Catch:{ all -> 0x06a9 }
            if (r10 == r11) goto L_0x018d
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r10 = io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.STARTED_IMPLICITLY     // Catch:{ all -> 0x06a9 }
            r1.handshakeState = r10     // Catch:{ all -> 0x06a9 }
        L_0x018d:
            long r10 = r1.networkBIO     // Catch:{ all -> 0x06a9 }
            int r10 = io.netty.internal.tcnative.SSL.bioFlushByteBuffer(r10)     // Catch:{ all -> 0x06a9 }
            r6 = r10
            if (r6 <= 0) goto L_0x01e9
            javax.net.ssl.SSLHandshakeException r10 = r1.handshakeException     // Catch:{ all -> 0x06a9 }
            if (r10 == 0) goto L_0x01e9
            javax.net.ssl.SSLEngineResult$HandshakeStatus r10 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_WRAP     // Catch:{ all -> 0x06a9 }
            javax.net.ssl.SSLEngineResult r9 = r1.newResult(r10, r9, r6)     // Catch:{ all -> 0x06a9 }
            long r10 = r1.networkBIO     // Catch:{ all -> 0x06f3 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r10)     // Catch:{ all -> 0x06f3 }
            if (r7 != 0) goto L_0x01b0
            int r10 = r21.position()     // Catch:{ all -> 0x06f3 }
            int r10 = r10 + r6
            r5.position(r10)     // Catch:{ all -> 0x06f3 }
            goto L_0x01c8
        L_0x01b0:
            int r10 = r7.readableBytes()     // Catch:{ all -> 0x06f3 }
            int r11 = r21.remaining()     // Catch:{ all -> 0x06f3 }
            if (r10 > r11) goto L_0x01ca
            int r10 = r7.readerIndex()     // Catch:{ all -> 0x06f3 }
            java.nio.ByteBuffer r10 = r7.internalNioBuffer(r10, r6)     // Catch:{ all -> 0x06f3 }
            r5.put(r10)     // Catch:{ all -> 0x06f3 }
            r7.release()     // Catch:{ all -> 0x06f3 }
        L_0x01c8:
            monitor-exit(r17)     // Catch:{ all -> 0x06f3 }
            return r9
        L_0x01ca:
            java.lang.AssertionError r9 = new java.lang.AssertionError     // Catch:{ all -> 0x06f3 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x06f3 }
            r10.<init>()     // Catch:{ all -> 0x06f3 }
            java.lang.String r11 = "The destination buffer "
            r10.append(r11)     // Catch:{ all -> 0x06f3 }
            r10.append(r5)     // Catch:{ all -> 0x06f3 }
            java.lang.String r11 = " didn't have enough remaining space to hold the encrypted content in "
            r10.append(r11)     // Catch:{ all -> 0x06f3 }
            r10.append(r7)     // Catch:{ all -> 0x06f3 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x06f3 }
            r9.<init>(r10)     // Catch:{ all -> 0x06f3 }
        L_0x01e8:
            throw r9     // Catch:{ all -> 0x06f3 }
        L_0x01e9:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r10 = r17.handshake()     // Catch:{ all -> 0x06a9 }
            r8 = r10
            long r10 = r1.networkBIO     // Catch:{ all -> 0x06a9 }
            int r10 = io.netty.internal.tcnative.SSL.bioLengthByteBuffer(r10)     // Catch:{ all -> 0x06a9 }
            int r6 = r0 - r10
            if (r6 <= 0) goto L_0x025e
            javax.net.ssl.SSLEngineResult$HandshakeStatus r10 = javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED     // Catch:{ all -> 0x06a9 }
            if (r8 == r10) goto L_0x020c
            if (r6 != r0) goto L_0x0201
            javax.net.ssl.SSLEngineResult$HandshakeStatus r10 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_WRAP     // Catch:{ all -> 0x06a9 }
            goto L_0x020d
        L_0x0201:
            long r10 = r1.networkBIO     // Catch:{ all -> 0x06a9 }
            int r10 = io.netty.internal.tcnative.SSL.bioLengthNonApplication(r10)     // Catch:{ all -> 0x06a9 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r10 = r1.getHandshakeStatus(r10)     // Catch:{ all -> 0x06a9 }
            goto L_0x020d
        L_0x020c:
        L_0x020d:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r10 = r1.mayFinishHandshake(r10)     // Catch:{ all -> 0x06a9 }
            javax.net.ssl.SSLEngineResult r9 = r1.newResult(r10, r9, r6)     // Catch:{ all -> 0x06a9 }
            long r10 = r1.networkBIO     // Catch:{ all -> 0x06f3 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r10)     // Catch:{ all -> 0x06f3 }
            if (r7 != 0) goto L_0x0225
            int r10 = r21.position()     // Catch:{ all -> 0x06f3 }
            int r10 = r10 + r6
            r5.position(r10)     // Catch:{ all -> 0x06f3 }
            goto L_0x023d
        L_0x0225:
            int r10 = r7.readableBytes()     // Catch:{ all -> 0x06f3 }
            int r11 = r21.remaining()     // Catch:{ all -> 0x06f3 }
            if (r10 > r11) goto L_0x023f
            int r10 = r7.readerIndex()     // Catch:{ all -> 0x06f3 }
            java.nio.ByteBuffer r10 = r7.internalNioBuffer(r10, r6)     // Catch:{ all -> 0x06f3 }
            r5.put(r10)     // Catch:{ all -> 0x06f3 }
            r7.release()     // Catch:{ all -> 0x06f3 }
        L_0x023d:
            monitor-exit(r17)     // Catch:{ all -> 0x06f3 }
            return r9
        L_0x023f:
            java.lang.AssertionError r9 = new java.lang.AssertionError     // Catch:{ all -> 0x06f3 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x06f3 }
            r10.<init>()     // Catch:{ all -> 0x06f3 }
            java.lang.String r11 = "The destination buffer "
            r10.append(r11)     // Catch:{ all -> 0x06f3 }
            r10.append(r5)     // Catch:{ all -> 0x06f3 }
            java.lang.String r11 = " didn't have enough remaining space to hold the encrypted content in "
            r10.append(r11)     // Catch:{ all -> 0x06f3 }
            r10.append(r7)     // Catch:{ all -> 0x06f3 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x06f3 }
            r9.<init>(r10)     // Catch:{ all -> 0x06f3 }
            goto L_0x01e8
        L_0x025e:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r10 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_UNWRAP     // Catch:{ all -> 0x06a9 }
            if (r8 != r10) goto L_0x02b7
            boolean r9 = r17.isOutboundDone()     // Catch:{ all -> 0x06a9 }
            if (r9 == 0) goto L_0x026b
            javax.net.ssl.SSLEngineResult r9 = NEED_UNWRAP_CLOSED     // Catch:{ all -> 0x06a9 }
            goto L_0x026d
        L_0x026b:
            javax.net.ssl.SSLEngineResult r9 = NEED_UNWRAP_OK     // Catch:{ all -> 0x06a9 }
        L_0x026d:
            long r10 = r1.networkBIO     // Catch:{ all -> 0x06f3 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r10)     // Catch:{ all -> 0x06f3 }
            if (r7 != 0) goto L_0x027d
            int r10 = r21.position()     // Catch:{ all -> 0x06f3 }
            int r10 = r10 + r6
            r5.position(r10)     // Catch:{ all -> 0x06f3 }
            goto L_0x0295
        L_0x027d:
            int r10 = r7.readableBytes()     // Catch:{ all -> 0x06f3 }
            int r11 = r21.remaining()     // Catch:{ all -> 0x06f3 }
            if (r10 > r11) goto L_0x0297
            int r10 = r7.readerIndex()     // Catch:{ all -> 0x06f3 }
            java.nio.ByteBuffer r10 = r7.internalNioBuffer(r10, r6)     // Catch:{ all -> 0x06f3 }
            r5.put(r10)     // Catch:{ all -> 0x06f3 }
            r7.release()     // Catch:{ all -> 0x06f3 }
        L_0x0295:
            monitor-exit(r17)     // Catch:{ all -> 0x06f3 }
            return r9
        L_0x0297:
            java.lang.AssertionError r9 = new java.lang.AssertionError     // Catch:{ all -> 0x06f3 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x06f3 }
            r10.<init>()     // Catch:{ all -> 0x06f3 }
            java.lang.String r11 = "The destination buffer "
            r10.append(r11)     // Catch:{ all -> 0x06f3 }
            r10.append(r5)     // Catch:{ all -> 0x06f3 }
            java.lang.String r11 = " didn't have enough remaining space to hold the encrypted content in "
            r10.append(r11)     // Catch:{ all -> 0x06f3 }
            r10.append(r7)     // Catch:{ all -> 0x06f3 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x06f3 }
            r9.<init>(r10)     // Catch:{ all -> 0x06f3 }
            goto L_0x01e8
        L_0x02b7:
            boolean r10 = r1.outboundClosed     // Catch:{ all -> 0x06a9 }
            if (r10 == 0) goto L_0x0310
            long r10 = r1.networkBIO     // Catch:{ all -> 0x06a9 }
            int r10 = io.netty.internal.tcnative.SSL.bioFlushByteBuffer(r10)     // Catch:{ all -> 0x06a9 }
            r6 = r10
            javax.net.ssl.SSLEngineResult r9 = r1.newResultMayFinishHandshake(r8, r9, r6)     // Catch:{ all -> 0x06a9 }
            long r10 = r1.networkBIO     // Catch:{ all -> 0x06f3 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r10)     // Catch:{ all -> 0x06f3 }
            if (r7 != 0) goto L_0x02d6
            int r10 = r21.position()     // Catch:{ all -> 0x06f3 }
            int r10 = r10 + r6
            r5.position(r10)     // Catch:{ all -> 0x06f3 }
            goto L_0x02ee
        L_0x02d6:
            int r10 = r7.readableBytes()     // Catch:{ all -> 0x06f3 }
            int r11 = r21.remaining()     // Catch:{ all -> 0x06f3 }
            if (r10 > r11) goto L_0x02f0
            int r10 = r7.readerIndex()     // Catch:{ all -> 0x06f3 }
            java.nio.ByteBuffer r10 = r7.internalNioBuffer(r10, r6)     // Catch:{ all -> 0x06f3 }
            r5.put(r10)     // Catch:{ all -> 0x06f3 }
            r7.release()     // Catch:{ all -> 0x06f3 }
        L_0x02ee:
            monitor-exit(r17)     // Catch:{ all -> 0x06f3 }
            return r9
        L_0x02f0:
            java.lang.AssertionError r9 = new java.lang.AssertionError     // Catch:{ all -> 0x06f3 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x06f3 }
            r10.<init>()     // Catch:{ all -> 0x06f3 }
            java.lang.String r11 = "The destination buffer "
            r10.append(r11)     // Catch:{ all -> 0x06f3 }
            r10.append(r5)     // Catch:{ all -> 0x06f3 }
            java.lang.String r11 = " didn't have enough remaining space to hold the encrypted content in "
            r10.append(r11)     // Catch:{ all -> 0x06f3 }
            r10.append(r7)     // Catch:{ all -> 0x06f3 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x06f3 }
            r9.<init>(r10)     // Catch:{ all -> 0x06f3 }
            goto L_0x01e8
        L_0x0310:
            int r10 = r3 + r4
            boolean r11 = r1.jdkCompatibilityMode     // Catch:{ all -> 0x06a9 }
            if (r11 == 0) goto L_0x03ad
            r11 = 0
            r12 = r19
        L_0x0319:
            if (r12 >= r10) goto L_0x034e
            r13 = r2[r12]     // Catch:{ all -> 0x06a9 }
            if (r13 == 0) goto L_0x0331
            int r14 = MAX_PLAINTEXT_LENGTH     // Catch:{ all -> 0x06a9 }
            if (r11 != r14) goto L_0x0324
            goto L_0x032e
        L_0x0324:
            int r15 = r13.remaining()     // Catch:{ all -> 0x06a9 }
            int r11 = r11 + r15
            if (r11 > r14) goto L_0x032d
            if (r11 >= 0) goto L_0x032e
        L_0x032d:
            r11 = r14
        L_0x032e:
            int r12 = r12 + 1
            goto L_0x0319
        L_0x0331:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x06a9 }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x06a9 }
            r14.<init>()     // Catch:{ all -> 0x06a9 }
            java.lang.String r15 = "srcs["
            r14.append(r15)     // Catch:{ all -> 0x06a9 }
            r14.append(r12)     // Catch:{ all -> 0x06a9 }
            java.lang.String r15 = "] is null"
            r14.append(r15)     // Catch:{ all -> 0x06a9 }
            java.lang.String r14 = r14.toString()     // Catch:{ all -> 0x06a9 }
            r9.<init>(r14)     // Catch:{ all -> 0x06a9 }
            throw r9     // Catch:{ all -> 0x06a9 }
        L_0x034e:
            int r12 = r21.remaining()     // Catch:{ all -> 0x06a9 }
            r13 = 1
            boolean r12 = r1.isBytesAvailableEnoughForWrap(r12, r11, r13)     // Catch:{ all -> 0x06a9 }
            if (r12 != 0) goto L_0x03ad
            javax.net.ssl.SSLEngineResult r12 = new javax.net.ssl.SSLEngineResult     // Catch:{ all -> 0x06a9 }
            javax.net.ssl.SSLEngineResult$Status r13 = javax.net.ssl.SSLEngineResult.Status.BUFFER_OVERFLOW     // Catch:{ all -> 0x06a9 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r14 = r17.getHandshakeStatus()     // Catch:{ all -> 0x06a9 }
            r12.<init>(r13, r14, r9, r9)     // Catch:{ all -> 0x06a9 }
            long r13 = r1.networkBIO     // Catch:{ all -> 0x06f3 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r13)     // Catch:{ all -> 0x06f3 }
            if (r7 != 0) goto L_0x0374
            int r9 = r21.position()     // Catch:{ all -> 0x06f3 }
            int r9 = r9 + r6
            r5.position(r9)     // Catch:{ all -> 0x06f3 }
            goto L_0x038c
        L_0x0374:
            int r9 = r7.readableBytes()     // Catch:{ all -> 0x06f3 }
            int r13 = r21.remaining()     // Catch:{ all -> 0x06f3 }
            if (r9 > r13) goto L_0x038e
            int r9 = r7.readerIndex()     // Catch:{ all -> 0x06f3 }
            java.nio.ByteBuffer r9 = r7.internalNioBuffer(r9, r6)     // Catch:{ all -> 0x06f3 }
            r5.put(r9)     // Catch:{ all -> 0x06f3 }
            r7.release()     // Catch:{ all -> 0x06f3 }
        L_0x038c:
            monitor-exit(r17)     // Catch:{ all -> 0x06f3 }
            return r12
        L_0x038e:
            java.lang.AssertionError r9 = new java.lang.AssertionError     // Catch:{ all -> 0x06f3 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x06f3 }
            r12.<init>()     // Catch:{ all -> 0x06f3 }
            java.lang.String r13 = "The destination buffer "
            r12.append(r13)     // Catch:{ all -> 0x06f3 }
            r12.append(r5)     // Catch:{ all -> 0x06f3 }
            java.lang.String r13 = " didn't have enough remaining space to hold the encrypted content in "
            r12.append(r13)     // Catch:{ all -> 0x06f3 }
            r12.append(r7)     // Catch:{ all -> 0x06f3 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x06f3 }
            r9.<init>(r12)     // Catch:{ all -> 0x06f3 }
            throw r9     // Catch:{ all -> 0x06f3 }
        L_0x03ad:
            r9 = 0
            long r11 = r1.networkBIO     // Catch:{ all -> 0x06a9 }
            int r11 = io.netty.internal.tcnative.SSL.bioFlushByteBuffer(r11)     // Catch:{ all -> 0x06a9 }
            r6 = r11
        L_0x03b5:
            if (r3 >= r10) goto L_0x0653
            r11 = r2[r3]     // Catch:{ all -> 0x0650 }
            int r12 = r11.remaining()     // Catch:{ all -> 0x0650 }
            if (r12 != 0) goto L_0x03c4
            r16 = r10
            r10 = r3
            goto L_0x0469
        L_0x03c4:
            boolean r13 = r1.jdkCompatibilityMode     // Catch:{ all -> 0x0650 }
            if (r13 == 0) goto L_0x03db
            int r13 = MAX_PLAINTEXT_LENGTH     // Catch:{ all -> 0x03d8 }
            int r13 = r13 - r9
            int r13 = java.lang.Math.min(r12, r13)     // Catch:{ all -> 0x03d8 }
            int r13 = r1.writePlaintextData(r11, r13)     // Catch:{ all -> 0x03d8 }
            r16 = r10
            r10 = r3
            goto L_0x0450
        L_0x03d8:
            r0 = move-exception
            goto L_0x06aa
        L_0x03db:
            int r13 = r21.remaining()     // Catch:{ all -> 0x0650 }
            int r13 = r13 - r6
            int r14 = r1.maxWrapOverhead     // Catch:{ all -> 0x0650 }
            int r13 = r13 - r14
            if (r13 > 0) goto L_0x0444
            javax.net.ssl.SSLEngineResult r14 = new javax.net.ssl.SSLEngineResult     // Catch:{ all -> 0x0440 }
            javax.net.ssl.SSLEngineResult$Status r15 = javax.net.ssl.SSLEngineResult.Status.BUFFER_OVERFLOW     // Catch:{ all -> 0x0440 }
            r16 = r10
            javax.net.ssl.SSLEngineResult$HandshakeStatus r10 = r17.getHandshakeStatus()     // Catch:{ all -> 0x0440 }
            r14.<init>(r15, r10, r9, r6)     // Catch:{ all -> 0x0440 }
            r10 = r3
            long r2 = r1.networkBIO     // Catch:{ all -> 0x043c }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r2)     // Catch:{ all -> 0x043c }
            if (r7 != 0) goto L_0x0403
            int r2 = r21.position()     // Catch:{ all -> 0x043c }
            int r2 = r2 + r6
            r5.position(r2)     // Catch:{ all -> 0x043c }
            goto L_0x041b
        L_0x0403:
            int r2 = r7.readableBytes()     // Catch:{ all -> 0x043c }
            int r3 = r21.remaining()     // Catch:{ all -> 0x043c }
            if (r2 > r3) goto L_0x041d
            int r2 = r7.readerIndex()     // Catch:{ all -> 0x043c }
            java.nio.ByteBuffer r2 = r7.internalNioBuffer(r2, r6)     // Catch:{ all -> 0x043c }
            r5.put(r2)     // Catch:{ all -> 0x043c }
            r7.release()     // Catch:{ all -> 0x043c }
        L_0x041b:
            monitor-exit(r17)     // Catch:{ all -> 0x043c }
            return r14
        L_0x041d:
            java.lang.AssertionError r2 = new java.lang.AssertionError     // Catch:{ all -> 0x043c }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x043c }
            r3.<init>()     // Catch:{ all -> 0x043c }
            java.lang.String r14 = "The destination buffer "
            r3.append(r14)     // Catch:{ all -> 0x043c }
            r3.append(r5)     // Catch:{ all -> 0x043c }
            java.lang.String r14 = " didn't have enough remaining space to hold the encrypted content in "
            r3.append(r14)     // Catch:{ all -> 0x043c }
            r3.append(r7)     // Catch:{ all -> 0x043c }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x043c }
            r2.<init>(r3)     // Catch:{ all -> 0x043c }
            throw r2     // Catch:{ all -> 0x043c }
        L_0x043c:
            r0 = move-exception
            r3 = r10
            goto L_0x06f4
        L_0x0440:
            r0 = move-exception
            r10 = r3
            goto L_0x06aa
        L_0x0444:
            r16 = r10
            r10 = r3
            int r2 = java.lang.Math.min(r12, r13)     // Catch:{ all -> 0x064c }
            int r2 = r1.writePlaintextData(r11, r2)     // Catch:{ all -> 0x064c }
            r13 = r2
        L_0x0450:
            if (r13 <= 0) goto L_0x04c2
            int r9 = r9 + r13
            long r2 = r1.networkBIO     // Catch:{ all -> 0x04be }
            int r2 = io.netty.internal.tcnative.SSL.bioLengthByteBuffer(r2)     // Catch:{ all -> 0x04be }
            int r3 = r0 - r2
            int r6 = r6 + r3
            r0 = r2
            boolean r3 = r1.jdkCompatibilityMode     // Catch:{ all -> 0x04be }
            if (r3 != 0) goto L_0x0471
            int r3 = r21.remaining()     // Catch:{ all -> 0x04be }
            if (r6 != r3) goto L_0x0468
            goto L_0x0471
        L_0x0468:
        L_0x0469:
            int r3 = r10 + 1
            r2 = r18
            r10 = r16
            goto L_0x03b5
        L_0x0471:
            javax.net.ssl.SSLEngineResult r3 = r1.newResultMayFinishHandshake(r8, r9, r6)     // Catch:{ all -> 0x04be }
            long r14 = r1.networkBIO     // Catch:{ all -> 0x043c }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r14)     // Catch:{ all -> 0x043c }
            if (r7 != 0) goto L_0x0485
            int r14 = r21.position()     // Catch:{ all -> 0x043c }
            int r14 = r14 + r6
            r5.position(r14)     // Catch:{ all -> 0x043c }
            goto L_0x049d
        L_0x0485:
            int r14 = r7.readableBytes()     // Catch:{ all -> 0x043c }
            int r15 = r21.remaining()     // Catch:{ all -> 0x043c }
            if (r14 > r15) goto L_0x049f
            int r14 = r7.readerIndex()     // Catch:{ all -> 0x043c }
            java.nio.ByteBuffer r14 = r7.internalNioBuffer(r14, r6)     // Catch:{ all -> 0x043c }
            r5.put(r14)     // Catch:{ all -> 0x043c }
            r7.release()     // Catch:{ all -> 0x043c }
        L_0x049d:
            monitor-exit(r17)     // Catch:{ all -> 0x043c }
            return r3
        L_0x049f:
            java.lang.AssertionError r3 = new java.lang.AssertionError     // Catch:{ all -> 0x043c }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x043c }
            r14.<init>()     // Catch:{ all -> 0x043c }
            java.lang.String r15 = "The destination buffer "
            r14.append(r15)     // Catch:{ all -> 0x043c }
            r14.append(r5)     // Catch:{ all -> 0x043c }
            java.lang.String r15 = " didn't have enough remaining space to hold the encrypted content in "
            r14.append(r15)     // Catch:{ all -> 0x043c }
            r14.append(r7)     // Catch:{ all -> 0x043c }
            java.lang.String r14 = r14.toString()     // Catch:{ all -> 0x043c }
            r3.<init>(r14)     // Catch:{ all -> 0x043c }
            throw r3     // Catch:{ all -> 0x043c }
        L_0x04be:
            r0 = move-exception
            r3 = r10
            goto L_0x06aa
        L_0x04c2:
            long r2 = r1.ssl     // Catch:{ all -> 0x064c }
            int r2 = io.netty.internal.tcnative.SSL.getError(r2, r13)     // Catch:{ all -> 0x064c }
            int r3 = io.netty.internal.tcnative.SSL.SSL_ERROR_ZERO_RETURN     // Catch:{ all -> 0x064c }
            if (r2 != r3) goto L_0x059b
            boolean r3 = r1.receivedShutdown     // Catch:{ all -> 0x064c }
            if (r3 != 0) goto L_0x0549
            r17.closeAll()     // Catch:{ all -> 0x064c }
            long r14 = r1.networkBIO     // Catch:{ all -> 0x064c }
            int r3 = io.netty.internal.tcnative.SSL.bioLengthByteBuffer(r14)     // Catch:{ all -> 0x064c }
            int r3 = r0 - r3
            int r6 = r6 + r3
            javax.net.ssl.SSLEngineResult$HandshakeStatus r3 = javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED     // Catch:{ all -> 0x064c }
            if (r8 == r3) goto L_0x04f4
            int r3 = r21.remaining()     // Catch:{ all -> 0x04be }
            if (r6 != r3) goto L_0x04e9
            javax.net.ssl.SSLEngineResult$HandshakeStatus r3 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_WRAP     // Catch:{ all -> 0x04be }
            goto L_0x04f5
        L_0x04e9:
            long r14 = r1.networkBIO     // Catch:{ all -> 0x04be }
            int r3 = io.netty.internal.tcnative.SSL.bioLengthNonApplication(r14)     // Catch:{ all -> 0x04be }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r3 = r1.getHandshakeStatus(r3)     // Catch:{ all -> 0x04be }
            goto L_0x04f5
        L_0x04f4:
        L_0x04f5:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r3 = r1.mayFinishHandshake(r3)     // Catch:{ all -> 0x064c }
            javax.net.ssl.SSLEngineResult r14 = r1.newResult(r3, r9, r6)     // Catch:{ all -> 0x064c }
            r15 = r10
            r19 = r11
            long r10 = r1.networkBIO     // Catch:{ all -> 0x06a3 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r10)     // Catch:{ all -> 0x06a3 }
            if (r7 != 0) goto L_0x0510
            int r10 = r21.position()     // Catch:{ all -> 0x06a3 }
            int r10 = r10 + r6
            r5.position(r10)     // Catch:{ all -> 0x06a3 }
            goto L_0x0528
        L_0x0510:
            int r10 = r7.readableBytes()     // Catch:{ all -> 0x06a3 }
            int r11 = r21.remaining()     // Catch:{ all -> 0x06a3 }
            if (r10 > r11) goto L_0x052a
            int r10 = r7.readerIndex()     // Catch:{ all -> 0x06a3 }
            java.nio.ByteBuffer r10 = r7.internalNioBuffer(r10, r6)     // Catch:{ all -> 0x06a3 }
            r5.put(r10)     // Catch:{ all -> 0x06a3 }
            r7.release()     // Catch:{ all -> 0x06a3 }
        L_0x0528:
            monitor-exit(r17)     // Catch:{ all -> 0x06a3 }
            return r14
        L_0x052a:
            java.lang.AssertionError r10 = new java.lang.AssertionError     // Catch:{ all -> 0x06a3 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x06a3 }
            r11.<init>()     // Catch:{ all -> 0x06a3 }
            java.lang.String r14 = "The destination buffer "
            r11.append(r14)     // Catch:{ all -> 0x06a3 }
            r11.append(r5)     // Catch:{ all -> 0x06a3 }
            java.lang.String r14 = " didn't have enough remaining space to hold the encrypted content in "
            r11.append(r14)     // Catch:{ all -> 0x06a3 }
            r11.append(r7)     // Catch:{ all -> 0x06a3 }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x06a3 }
            r10.<init>(r11)     // Catch:{ all -> 0x06a3 }
            throw r10     // Catch:{ all -> 0x06a3 }
        L_0x0549:
            r15 = r10
            r19 = r11
            javax.net.ssl.SSLEngineResult$HandshakeStatus r3 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING     // Catch:{ all -> 0x06a6 }
            javax.net.ssl.SSLEngineResult r3 = r1.newResult(r3, r9, r6)     // Catch:{ all -> 0x06a6 }
            long r10 = r1.networkBIO     // Catch:{ all -> 0x06a3 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r10)     // Catch:{ all -> 0x06a3 }
            if (r7 != 0) goto L_0x0562
            int r10 = r21.position()     // Catch:{ all -> 0x06a3 }
            int r10 = r10 + r6
            r5.position(r10)     // Catch:{ all -> 0x06a3 }
            goto L_0x057a
        L_0x0562:
            int r10 = r7.readableBytes()     // Catch:{ all -> 0x06a3 }
            int r11 = r21.remaining()     // Catch:{ all -> 0x06a3 }
            if (r10 > r11) goto L_0x057c
            int r10 = r7.readerIndex()     // Catch:{ all -> 0x06a3 }
            java.nio.ByteBuffer r10 = r7.internalNioBuffer(r10, r6)     // Catch:{ all -> 0x06a3 }
            r5.put(r10)     // Catch:{ all -> 0x06a3 }
            r7.release()     // Catch:{ all -> 0x06a3 }
        L_0x057a:
            monitor-exit(r17)     // Catch:{ all -> 0x06a3 }
            return r3
        L_0x057c:
            java.lang.AssertionError r3 = new java.lang.AssertionError     // Catch:{ all -> 0x06a3 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x06a3 }
            r10.<init>()     // Catch:{ all -> 0x06a3 }
            java.lang.String r11 = "The destination buffer "
            r10.append(r11)     // Catch:{ all -> 0x06a3 }
            r10.append(r5)     // Catch:{ all -> 0x06a3 }
            java.lang.String r11 = " didn't have enough remaining space to hold the encrypted content in "
            r10.append(r11)     // Catch:{ all -> 0x06a3 }
            r10.append(r7)     // Catch:{ all -> 0x06a3 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x06a3 }
            r3.<init>(r10)     // Catch:{ all -> 0x06a3 }
        L_0x059a:
            throw r3     // Catch:{ all -> 0x06a3 }
        L_0x059b:
            r15 = r10
            r19 = r11
            int r3 = io.netty.internal.tcnative.SSL.SSL_ERROR_WANT_READ     // Catch:{ all -> 0x06a6 }
            if (r2 != r3) goto L_0x05f1
            javax.net.ssl.SSLEngineResult$HandshakeStatus r3 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_UNWRAP     // Catch:{ all -> 0x06a6 }
            javax.net.ssl.SSLEngineResult r3 = r1.newResult(r3, r9, r6)     // Catch:{ all -> 0x06a6 }
            long r10 = r1.networkBIO     // Catch:{ all -> 0x06a3 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r10)     // Catch:{ all -> 0x06a3 }
            if (r7 != 0) goto L_0x05b8
            int r10 = r21.position()     // Catch:{ all -> 0x06a3 }
            int r10 = r10 + r6
            r5.position(r10)     // Catch:{ all -> 0x06a3 }
            goto L_0x05d0
        L_0x05b8:
            int r10 = r7.readableBytes()     // Catch:{ all -> 0x06a3 }
            int r11 = r21.remaining()     // Catch:{ all -> 0x06a3 }
            if (r10 > r11) goto L_0x05d2
            int r10 = r7.readerIndex()     // Catch:{ all -> 0x06a3 }
            java.nio.ByteBuffer r10 = r7.internalNioBuffer(r10, r6)     // Catch:{ all -> 0x06a3 }
            r5.put(r10)     // Catch:{ all -> 0x06a3 }
            r7.release()     // Catch:{ all -> 0x06a3 }
        L_0x05d0:
            monitor-exit(r17)     // Catch:{ all -> 0x06a3 }
            return r3
        L_0x05d2:
            java.lang.AssertionError r3 = new java.lang.AssertionError     // Catch:{ all -> 0x06a3 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x06a3 }
            r10.<init>()     // Catch:{ all -> 0x06a3 }
            java.lang.String r11 = "The destination buffer "
            r10.append(r11)     // Catch:{ all -> 0x06a3 }
            r10.append(r5)     // Catch:{ all -> 0x06a3 }
            java.lang.String r11 = " didn't have enough remaining space to hold the encrypted content in "
            r10.append(r11)     // Catch:{ all -> 0x06a3 }
            r10.append(r7)     // Catch:{ all -> 0x06a3 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x06a3 }
            r3.<init>(r10)     // Catch:{ all -> 0x06a3 }
            goto L_0x059a
        L_0x05f1:
            int r3 = io.netty.internal.tcnative.SSL.SSL_ERROR_WANT_WRITE     // Catch:{ all -> 0x06a6 }
            if (r2 != r3) goto L_0x0645
            javax.net.ssl.SSLEngineResult$Status r3 = javax.net.ssl.SSLEngineResult.Status.BUFFER_OVERFLOW     // Catch:{ all -> 0x06a6 }
            javax.net.ssl.SSLEngineResult r3 = r1.newResult(r3, r8, r9, r6)     // Catch:{ all -> 0x06a6 }
            long r10 = r1.networkBIO     // Catch:{ all -> 0x06a3 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r10)     // Catch:{ all -> 0x06a3 }
            if (r7 != 0) goto L_0x060b
            int r10 = r21.position()     // Catch:{ all -> 0x06a3 }
            int r10 = r10 + r6
            r5.position(r10)     // Catch:{ all -> 0x06a3 }
            goto L_0x0623
        L_0x060b:
            int r10 = r7.readableBytes()     // Catch:{ all -> 0x06a3 }
            int r11 = r21.remaining()     // Catch:{ all -> 0x06a3 }
            if (r10 > r11) goto L_0x0625
            int r10 = r7.readerIndex()     // Catch:{ all -> 0x06a3 }
            java.nio.ByteBuffer r10 = r7.internalNioBuffer(r10, r6)     // Catch:{ all -> 0x06a3 }
            r5.put(r10)     // Catch:{ all -> 0x06a3 }
            r7.release()     // Catch:{ all -> 0x06a3 }
        L_0x0623:
            monitor-exit(r17)     // Catch:{ all -> 0x06a3 }
            return r3
        L_0x0625:
            java.lang.AssertionError r3 = new java.lang.AssertionError     // Catch:{ all -> 0x06a3 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x06a3 }
            r10.<init>()     // Catch:{ all -> 0x06a3 }
            java.lang.String r11 = "The destination buffer "
            r10.append(r11)     // Catch:{ all -> 0x06a3 }
            r10.append(r5)     // Catch:{ all -> 0x06a3 }
            java.lang.String r11 = " didn't have enough remaining space to hold the encrypted content in "
            r10.append(r11)     // Catch:{ all -> 0x06a3 }
            r10.append(r7)     // Catch:{ all -> 0x06a3 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x06a3 }
            r3.<init>(r10)     // Catch:{ all -> 0x06a3 }
            goto L_0x059a
        L_0x0645:
            java.lang.String r3 = "SSL_write"
            javax.net.ssl.SSLException r3 = r1.shutdownWithError(r3)     // Catch:{ all -> 0x06a6 }
            throw r3     // Catch:{ all -> 0x06a6 }
        L_0x064c:
            r0 = move-exception
            r15 = r10
            r3 = r15
            goto L_0x06aa
        L_0x0650:
            r0 = move-exception
            r15 = r3
            goto L_0x06aa
        L_0x0653:
            r15 = r3
            r16 = r10
            javax.net.ssl.SSLEngineResult r2 = r1.newResultMayFinishHandshake(r8, r9, r6)     // Catch:{ all -> 0x06a6 }
            long r10 = r1.networkBIO     // Catch:{ all -> 0x06a3 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r10)     // Catch:{ all -> 0x06a3 }
            if (r7 != 0) goto L_0x066a
            int r3 = r21.position()     // Catch:{ all -> 0x06a3 }
            int r3 = r3 + r6
            r5.position(r3)     // Catch:{ all -> 0x06a3 }
            goto L_0x0682
        L_0x066a:
            int r3 = r7.readableBytes()     // Catch:{ all -> 0x06a3 }
            int r10 = r21.remaining()     // Catch:{ all -> 0x06a3 }
            if (r3 > r10) goto L_0x0684
            int r3 = r7.readerIndex()     // Catch:{ all -> 0x06a3 }
            java.nio.ByteBuffer r3 = r7.internalNioBuffer(r3, r6)     // Catch:{ all -> 0x06a3 }
            r5.put(r3)     // Catch:{ all -> 0x06a3 }
            r7.release()     // Catch:{ all -> 0x06a3 }
        L_0x0682:
            monitor-exit(r17)     // Catch:{ all -> 0x06a3 }
            return r2
        L_0x0684:
            java.lang.AssertionError r2 = new java.lang.AssertionError     // Catch:{ all -> 0x06a3 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x06a3 }
            r3.<init>()     // Catch:{ all -> 0x06a3 }
            java.lang.String r10 = "The destination buffer "
            r3.append(r10)     // Catch:{ all -> 0x06a3 }
            r3.append(r5)     // Catch:{ all -> 0x06a3 }
            java.lang.String r10 = " didn't have enough remaining space to hold the encrypted content in "
            r3.append(r10)     // Catch:{ all -> 0x06a3 }
            r3.append(r7)     // Catch:{ all -> 0x06a3 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x06a3 }
            r2.<init>(r3)     // Catch:{ all -> 0x06a3 }
            throw r2     // Catch:{ all -> 0x06a3 }
        L_0x06a3:
            r0 = move-exception
            r3 = r15
            goto L_0x06f4
        L_0x06a6:
            r0 = move-exception
            r3 = r15
            goto L_0x06aa
        L_0x06a9:
            r0 = move-exception
        L_0x06aa:
            long r8 = r1.networkBIO     // Catch:{ all -> 0x06f6 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r8)     // Catch:{ all -> 0x06f6 }
            if (r7 == 0) goto L_0x06e9
            int r2 = r7.readableBytes()     // Catch:{ all -> 0x06f6 }
            int r8 = r21.remaining()     // Catch:{ all -> 0x06f6 }
            if (r2 <= r8) goto L_0x06da
            java.lang.AssertionError r0 = new java.lang.AssertionError     // Catch:{ all -> 0x06f6 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x06f6 }
            r2.<init>()     // Catch:{ all -> 0x06f6 }
            java.lang.String r8 = "The destination buffer "
            r2.append(r8)     // Catch:{ all -> 0x06f6 }
            r2.append(r5)     // Catch:{ all -> 0x06f6 }
            java.lang.String r8 = " didn't have enough remaining space to hold the encrypted content in "
            r2.append(r8)     // Catch:{ all -> 0x06f6 }
            r2.append(r7)     // Catch:{ all -> 0x06f6 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x06f6 }
            r0.<init>(r2)     // Catch:{ all -> 0x06f6 }
            throw r0     // Catch:{ all -> 0x06f6 }
        L_0x06da:
            int r2 = r7.readerIndex()     // Catch:{ all -> 0x06f6 }
            java.nio.ByteBuffer r2 = r7.internalNioBuffer(r2, r6)     // Catch:{ all -> 0x06f6 }
            r5.put(r2)     // Catch:{ all -> 0x06f6 }
            r7.release()     // Catch:{ all -> 0x06f6 }
            goto L_0x06f1
        L_0x06e9:
            int r2 = r21.position()     // Catch:{ all -> 0x06f6 }
            int r2 = r2 + r6
            r5.position(r2)     // Catch:{ all -> 0x06f6 }
        L_0x06f1:
            throw r0     // Catch:{ all -> 0x06f6 }
        L_0x06f3:
            r0 = move-exception
        L_0x06f4:
            monitor-exit(r17)     // Catch:{ all -> 0x06f6 }
            throw r0
        L_0x06f6:
            r0 = move-exception
            goto L_0x06f4
        L_0x06f8:
            java.nio.ReadOnlyBufferException r0 = new java.nio.ReadOnlyBufferException
            r0.<init>()
            throw r0
        L_0x06fe:
            java.lang.IndexOutOfBoundsException r0 = new java.lang.IndexOutOfBoundsException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r6 = "offset: "
            r2.append(r6)
            r2.append(r3)
            java.lang.String r6 = ", length: "
            r2.append(r6)
            r2.append(r4)
            java.lang.String r6 = " (expected: offset <= offset + length <= srcs.length ("
            r2.append(r6)
            r6 = r18
            int r7 = r6.length
            r2.append(r7)
            java.lang.String r7 = "))"
            r2.append(r7)
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            throw r0
        L_0x072d:
            r6 = r2
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "dst is null"
            r0.<init>(r2)
            throw r0
        L_0x0736:
            r6 = r2
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "srcs is null"
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.ReferenceCountedOpenSslEngine.wrap(java.nio.ByteBuffer[], int, int, java.nio.ByteBuffer):javax.net.ssl.SSLEngineResult");
    }

    private SSLEngineResult newResult(SSLEngineResult.HandshakeStatus hs, int bytesConsumed, int bytesProduced) {
        return newResult(SSLEngineResult.Status.OK, hs, bytesConsumed, bytesProduced);
    }

    private SSLEngineResult newResult(SSLEngineResult.Status status, SSLEngineResult.HandshakeStatus hs, int bytesConsumed, int bytesProduced) {
        if (!isOutboundDone()) {
            return new SSLEngineResult(status, hs, bytesConsumed, bytesProduced);
        }
        if (isInboundDone()) {
            hs = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
            shutdown();
        }
        return new SSLEngineResult(SSLEngineResult.Status.CLOSED, hs, bytesConsumed, bytesProduced);
    }

    private SSLEngineResult newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus hs, int bytesConsumed, int bytesProduced) {
        SSLEngineResult.HandshakeStatus handshakeStatus = SSLEngineResult.HandshakeStatus.FINISHED;
        if (hs != handshakeStatus) {
            handshakeStatus = getHandshakeStatus();
        }
        return newResult(mayFinishHandshake(handshakeStatus), bytesConsumed, bytesProduced);
    }

    private SSLEngineResult newResultMayFinishHandshake(SSLEngineResult.Status status, SSLEngineResult.HandshakeStatus hs, int bytesConsumed, int bytesProduced) {
        SSLEngineResult.HandshakeStatus handshakeStatus = SSLEngineResult.HandshakeStatus.FINISHED;
        if (hs != handshakeStatus) {
            handshakeStatus = getHandshakeStatus();
        }
        return newResult(status, mayFinishHandshake(handshakeStatus), bytesConsumed, bytesProduced);
    }

    private SSLException shutdownWithError(String operations) {
        return shutdownWithError(operations, SSL.getLastError());
    }

    private SSLException shutdownWithError(String operation, String err) {
        InternalLogger internalLogger = logger;
        if (internalLogger.isDebugEnabled()) {
            internalLogger.debug("{} failed: OpenSSL error: {}", operation, err);
        }
        shutdown();
        if (this.handshakeState == HandshakeState.FINISHED) {
            return new SSLException(err);
        }
        return new SSLHandshakeException(err);
    }

    /* JADX WARNING: type inference failed for: r19v18 */
    /* JADX WARNING: type inference failed for: r19v19 */
    /* JADX WARNING: type inference failed for: r19v22 */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x021e, code lost:
        if (r7 == null) goto L_0x0232;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:?, code lost:
        r7.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x0224, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x0225, code lost:
        r7 = r30;
        r16 = r6;
        r21 = r12;
        r13 = r10;
        r10 = r14;
        r14 = r15;
        r15 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x0232, code lost:
        r7 = r30;
        r16 = r6;
        r21 = r12;
        r13 = r10;
        r10 = r14;
        r14 = r15;
        r15 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x027b, code lost:
        if (r13 <= 0) goto L_0x0291;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x027d, code lost:
        r23 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:?, code lost:
        r0 = newResult(javax.net.ssl.SSLEngineResult.Status.BUFFER_OVERFLOW, r4, r15, r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x0286, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x0287, code lost:
        r16 = r8;
        r6 = r13;
        r13 = r10;
        r10 = r14;
        r14 = r15;
        r15 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x0291, code lost:
        r23 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x0297, code lost:
        if (isInboundDone() == false) goto L_0x029c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:?, code lost:
        r0 = javax.net.ssl.SSLEngineResult.Status.CLOSED;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:185:?, code lost:
        r0 = javax.net.ssl.SSLEngineResult.Status.OK;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x029e, code lost:
        r0 = newResultMayFinishHandshake(r0, r4, r15, r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x02a3, code lost:
        if (r7 == null) goto L_0x02b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:189:?, code lost:
        r7.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:190:0x02a9, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:191:0x02aa, code lost:
        r16 = r8;
        r7 = r13;
        r13 = r10;
        r10 = r14;
        r14 = r15;
        r15 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:192:0x02b4, code lost:
        r24 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:194:?, code lost:
        io.netty.internal.tcnative.SSL.bioClearByteBuffer(r1.networkBIO);
        rejectRemoteInitiatedRenegotiation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:196:0x02c1, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:197:0x02c2, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:198:0x02c3, code lost:
        r16 = r8;
        r13 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:199:0x02c8, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:200:0x02c9, code lost:
        r24 = r9;
        r16 = r8;
        r6 = r13;
        r13 = r10;
        r10 = r14;
        r14 = r15;
        r15 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:219:0x032f, code lost:
        r23 = r0;
        r19 = r8;
        r24 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:221:?, code lost:
        r0 = io.netty.internal.tcnative.SSL.getError(r1.ssl, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:222:0x033d, code lost:
        if (r0 == io.netty.internal.tcnative.SSL.SSL_ERROR_WANT_READ) goto L_0x0392;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:224:0x0341, code lost:
        if (r0 != io.netty.internal.tcnative.SSL.SSL_ERROR_WANT_WRITE) goto L_0x0345;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:225:0x0343, code lost:
        r13 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:0x0347, code lost:
        if (r0 != io.netty.internal.tcnative.SSL.SSL_ERROR_ZERO_RETURN) goto L_0x036f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:229:0x034b, code lost:
        if (r1.receivedShutdown != false) goto L_0x0350;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:231:?, code lost:
        closeAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:234:0x0354, code lost:
        if (isInboundDone() == false) goto L_0x0359;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:236:?, code lost:
        r8 = javax.net.ssl.SSLEngineResult.Status.CLOSED;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:238:?, code lost:
        r8 = javax.net.ssl.SSLEngineResult.Status.OK;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:239:0x035b, code lost:
        r8 = newResultMayFinishHandshake(r8, r4, r15, r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:240:0x035f, code lost:
        if (r7 == null) goto L_0x0364;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:242:?, code lost:
        r7.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:243:0x0364, code lost:
        r13 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:245:?, code lost:
        io.netty.internal.tcnative.SSL.bioClearByteBuffer(r1.networkBIO);
        rejectRemoteInitiatedRenegotiation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:247:0x036e, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:248:0x036f, code lost:
        r13 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:250:?, code lost:
        r8 = sslReadErrorResult(io.netty.internal.tcnative.SSL.getLastErrorNumber(), r15, r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:251:0x0378, code lost:
        if (r7 == null) goto L_0x037d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:253:?, code lost:
        r7.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:255:?, code lost:
        io.netty.internal.tcnative.SSL.bioClearByteBuffer(r1.networkBIO);
        rejectRemoteInitiatedRenegotiation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:257:0x0386, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:258:0x0387, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:259:0x0388, code lost:
        r6 = r30;
        r10 = r14;
        r14 = r15;
        r15 = r20;
        r9 = r24;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:260:0x0392, code lost:
        r13 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:261:0x0393, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:262:0x0395, code lost:
        if (r3 < r11) goto L_0x03d0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:263:0x0397, code lost:
        if (r7 == null) goto L_0x039c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:265:?, code lost:
        r7.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:266:0x039c, code lost:
        r7 = r30;
        r10 = r14;
        r14 = r15;
        r15 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:280:0x03d0, code lost:
        if (r7 == null) goto L_0x03df;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:282:?, code lost:
        r7.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:283:0x03d6, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:284:0x03d7, code lost:
        r7 = r30;
        r10 = r14;
        r14 = r15;
        r15 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:285:0x03df, code lost:
        r7 = r30;
        r19 = r13;
        r10 = r14;
        r14 = r15;
        r6 = r16;
        r8 = r17;
        r15 = r20;
        r12 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:286:0x03ef, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:287:0x03f0, code lost:
        r13 = r10;
        r6 = r30;
        r10 = r14;
        r14 = r15;
        r15 = r20;
        r9 = r24;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:324:0x0486, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00af, code lost:
        return r0;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x0214  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0248  */
    /* JADX WARNING: Removed duplicated region for block: B:297:0x0432 A[SYNTHETIC, Splitter:B:297:0x0432] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:267:0x03a2=Splitter:B:267:0x03a2, B:321:0x0484=Splitter:B:321:0x0484, B:254:0x037d=Splitter:B:254:0x037d, B:306:0x044d=Splitter:B:306:0x044d} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final javax.net.ssl.SSLEngineResult unwrap(java.nio.ByteBuffer[] r26, int r27, int r28, java.nio.ByteBuffer[] r29, int r30, int r31) {
        /*
            r25 = this;
            r1 = r25
            r2 = r26
            r3 = r27
            r4 = r28
            r5 = r29
            r6 = r30
            r7 = r31
            if (r2 == 0) goto L_0x04f0
            int r0 = r2.length
            if (r3 >= r0) goto L_0x04c1
            int r0 = r3 + r4
            int r8 = r2.length
            if (r0 > r8) goto L_0x04c1
            if (r5 == 0) goto L_0x04b9
            int r0 = r5.length
            if (r6 >= r0) goto L_0x0488
            int r0 = r6 + r7
            int r8 = r5.length
            if (r0 > r8) goto L_0x0488
            r8 = 0
            int r10 = r6 + r7
            r0 = r30
        L_0x0028:
            if (r0 >= r10) goto L_0x005f
            r11 = r5[r0]
            if (r11 == 0) goto L_0x0043
            boolean r12 = r11.isReadOnly()
            if (r12 != 0) goto L_0x003d
            int r12 = r11.remaining()
            long r12 = (long) r12
            long r8 = r8 + r12
            int r0 = r0 + 1
            goto L_0x0028
        L_0x003d:
            java.nio.ReadOnlyBufferException r12 = new java.nio.ReadOnlyBufferException
            r12.<init>()
            throw r12
        L_0x0043:
            java.lang.IllegalArgumentException r12 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = "dsts["
            r13.append(r14)
            r13.append(r0)
            java.lang.String r14 = "] is null"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r12.<init>(r13)
            throw r12
        L_0x005f:
            int r11 = r3 + r4
            r12 = 0
            r0 = r27
        L_0x0065:
            if (r0 >= r11) goto L_0x0095
            r14 = r2[r0]
            if (r14 == 0) goto L_0x0076
            int r15 = r14.remaining()
            r16 = r14
            long r14 = (long) r15
            long r12 = r12 + r14
            int r0 = r0 + 1
            goto L_0x0065
        L_0x0076:
            r16 = r14
            java.lang.IllegalArgumentException r14 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r4 = "srcs["
            r15.append(r4)
            r15.append(r0)
            java.lang.String r4 = "] is null"
            r15.append(r4)
            java.lang.String r4 = r15.toString()
            r14.<init>(r4)
            throw r14
        L_0x0095:
            monitor-enter(r25)
            boolean r0 = r25.isInboundDone()     // Catch:{ all -> 0x047c }
            if (r0 == 0) goto L_0x00ba
            boolean r0 = r25.isOutboundDone()     // Catch:{ all -> 0x00b0 }
            if (r0 != 0) goto L_0x00ac
            boolean r0 = r25.isDestroyed()     // Catch:{ all -> 0x00b0 }
            if (r0 == 0) goto L_0x00a9
            goto L_0x00ac
        L_0x00a9:
            javax.net.ssl.SSLEngineResult r0 = NEED_WRAP_CLOSED     // Catch:{ all -> 0x00b0 }
            goto L_0x00ae
        L_0x00ac:
            javax.net.ssl.SSLEngineResult r0 = CLOSED_NOT_HANDSHAKING     // Catch:{ all -> 0x00b0 }
        L_0x00ae:
            monitor-exit(r25)     // Catch:{ all -> 0x00b0 }
            return r0
        L_0x00b0:
            r0 = move-exception
            r16 = r6
            r17 = r8
            r21 = r12
            r13 = r10
            goto L_0x0484
        L_0x00ba:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r0 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING     // Catch:{ all -> 0x047c }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r4 = r1.handshakeState     // Catch:{ all -> 0x047c }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r14 = io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.FINISHED     // Catch:{ all -> 0x047c }
            if (r4 == r14) goto L_0x00e1
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r14 = io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY     // Catch:{ all -> 0x00b0 }
            if (r4 == r14) goto L_0x00ca
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r4 = io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.STARTED_IMPLICITLY     // Catch:{ all -> 0x00b0 }
            r1.handshakeState = r4     // Catch:{ all -> 0x00b0 }
        L_0x00ca:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r4 = r25.handshake()     // Catch:{ all -> 0x00b0 }
            r0 = r4
            javax.net.ssl.SSLEngineResult$HandshakeStatus r4 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_WRAP     // Catch:{ all -> 0x00b0 }
            if (r0 != r4) goto L_0x00d7
            javax.net.ssl.SSLEngineResult r4 = NEED_WRAP_OK     // Catch:{ all -> 0x00b0 }
            monitor-exit(r25)     // Catch:{ all -> 0x00b0 }
            return r4
        L_0x00d7:
            boolean r4 = r1.isInboundDone     // Catch:{ all -> 0x00b0 }
            if (r4 == 0) goto L_0x00df
            javax.net.ssl.SSLEngineResult r4 = NEED_WRAP_CLOSED     // Catch:{ all -> 0x00b0 }
            monitor-exit(r25)     // Catch:{ all -> 0x00b0 }
            return r4
        L_0x00df:
            r4 = r0
            goto L_0x00e2
        L_0x00e1:
            r4 = r0
        L_0x00e2:
            int r0 = r25.sslPending0()     // Catch:{ all -> 0x047c }
            boolean r14 = r1.jdkCompatibilityMode     // Catch:{ all -> 0x047c }
            r15 = 0
            if (r14 == 0) goto L_0x016f
            r19 = 5
            int r14 = (r12 > r19 ? 1 : (r12 == r19 ? 0 : -1))
            if (r14 >= 0) goto L_0x00f9
            javax.net.ssl.SSLEngineResult$Status r14 = javax.net.ssl.SSLEngineResult.Status.BUFFER_UNDERFLOW     // Catch:{ all -> 0x00b0 }
            javax.net.ssl.SSLEngineResult r14 = r1.newResultMayFinishHandshake(r14, r4, r15, r15)     // Catch:{ all -> 0x00b0 }
            monitor-exit(r25)     // Catch:{ all -> 0x00b0 }
            return r14
        L_0x00f9:
            int r14 = io.netty.handler.ssl.SslUtils.getEncryptedPacketLength((java.nio.ByteBuffer[]) r26, (int) r27)     // Catch:{ all -> 0x0165 }
            r15 = -2
            if (r14 == r15) goto L_0x015b
            int r15 = r14 + -5
            long r6 = (long) r15     // Catch:{ all -> 0x0165 }
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 <= 0) goto L_0x014a
            int r6 = MAX_RECORD_SIZE     // Catch:{ all -> 0x0165 }
            if (r15 > r6) goto L_0x0123
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$OpenSslSession r6 = r1.session     // Catch:{ all -> 0x0119 }
            r6.tryExpandApplicationBufferSize(r15)     // Catch:{ all -> 0x0119 }
            javax.net.ssl.SSLEngineResult$Status r6 = javax.net.ssl.SSLEngineResult.Status.BUFFER_OVERFLOW     // Catch:{ all -> 0x0119 }
            r7 = 0
            javax.net.ssl.SSLEngineResult r6 = r1.newResultMayFinishHandshake(r6, r4, r7, r7)     // Catch:{ all -> 0x0119 }
            monitor-exit(r25)     // Catch:{ all -> 0x0119 }
            return r6
        L_0x0119:
            r0 = move-exception
            r16 = r30
            r17 = r8
            r21 = r12
            r13 = r10
            goto L_0x0484
        L_0x0123:
            javax.net.ssl.SSLException r6 = new javax.net.ssl.SSLException     // Catch:{ all -> 0x0165 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0165 }
            r7.<init>()     // Catch:{ all -> 0x0165 }
            r19 = r10
            java.lang.String r10 = "Illegal packet length: "
            r7.append(r10)     // Catch:{ all -> 0x0191 }
            r7.append(r15)     // Catch:{ all -> 0x0191 }
            java.lang.String r10 = " > "
            r7.append(r10)     // Catch:{ all -> 0x0191 }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$OpenSslSession r10 = r1.session     // Catch:{ all -> 0x0191 }
            int r10 = r10.getApplicationBufferSize()     // Catch:{ all -> 0x0191 }
            r7.append(r10)     // Catch:{ all -> 0x0191 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0191 }
            r6.<init>(r7)     // Catch:{ all -> 0x0191 }
            throw r6     // Catch:{ all -> 0x0191 }
        L_0x014a:
            r19 = r10
            long r6 = (long) r14     // Catch:{ all -> 0x0191 }
            int r6 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1))
            if (r6 >= 0) goto L_0x015a
            javax.net.ssl.SSLEngineResult$Status r6 = javax.net.ssl.SSLEngineResult.Status.BUFFER_UNDERFLOW     // Catch:{ all -> 0x0191 }
            r7 = 0
            javax.net.ssl.SSLEngineResult r6 = r1.newResultMayFinishHandshake(r6, r4, r7, r7)     // Catch:{ all -> 0x0191 }
            monitor-exit(r25)     // Catch:{ all -> 0x0191 }
            return r6
        L_0x015a:
            goto L_0x01a5
        L_0x015b:
            r19 = r10
            io.netty.handler.ssl.NotSslRecordException r6 = new io.netty.handler.ssl.NotSslRecordException     // Catch:{ all -> 0x0191 }
            java.lang.String r7 = "not an SSL/TLS record"
            r6.<init>((java.lang.String) r7)     // Catch:{ all -> 0x0191 }
            throw r6     // Catch:{ all -> 0x0191 }
        L_0x0165:
            r0 = move-exception
            r16 = r30
            r17 = r8
            r21 = r12
            r13 = r10
            goto L_0x0484
        L_0x016f:
            r19 = r10
            r6 = 0
            int r10 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1))
            if (r10 != 0) goto L_0x0182
            if (r0 > 0) goto L_0x0182
            javax.net.ssl.SSLEngineResult$Status r6 = javax.net.ssl.SSLEngineResult.Status.BUFFER_UNDERFLOW     // Catch:{ all -> 0x0191 }
            r7 = 0
            javax.net.ssl.SSLEngineResult r6 = r1.newResultMayFinishHandshake(r6, r4, r7, r7)     // Catch:{ all -> 0x0191 }
            monitor-exit(r25)     // Catch:{ all -> 0x0191 }
            return r6
        L_0x0182:
            r6 = 0
            int r10 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r10 != 0) goto L_0x019c
            javax.net.ssl.SSLEngineResult$Status r6 = javax.net.ssl.SSLEngineResult.Status.BUFFER_OVERFLOW     // Catch:{ all -> 0x0191 }
            r7 = 0
            javax.net.ssl.SSLEngineResult r6 = r1.newResultMayFinishHandshake(r6, r4, r7, r7)     // Catch:{ all -> 0x0191 }
            monitor-exit(r25)     // Catch:{ all -> 0x0191 }
            return r6
        L_0x0191:
            r0 = move-exception
            r16 = r30
            r17 = r8
            r21 = r12
            r13 = r19
            goto L_0x0484
        L_0x019c:
            r6 = 2147483647(0x7fffffff, double:1.060997895E-314)
            long r6 = java.lang.Math.min(r6, r12)     // Catch:{ all -> 0x0472 }
            int r6 = (int) r6
            r14 = r6
        L_0x01a5:
            if (r3 >= r11) goto L_0x0462
            r6 = 0
            int r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x0456
            r6 = 0
            r7 = 0
            r10 = r6
            r15 = r14
            r6 = r30
            r14 = r7
            r7 = r0
        L_0x01b5:
            r0 = r2[r3]     // Catch:{ all -> 0x0442 }
            r27 = r0
            int r0 = r27.remaining()     // Catch:{ all -> 0x0442 }
            r30 = r0
            r16 = r6
            r6 = r30
            if (r6 != 0) goto L_0x01f5
            if (r7 > 0) goto L_0x01d6
            int r3 = r3 + 1
            if (r3 < r11) goto L_0x01d3
            r17 = r8
            r21 = r12
            r13 = r19
            goto L_0x03a2
        L_0x01d3:
            r6 = r16
            goto L_0x01b5
        L_0x01d6:
            r0 = 0
            r30 = r7
            r17 = r8
            long r7 = r1.networkBIO     // Catch:{ all -> 0x01ec }
            int r7 = io.netty.internal.tcnative.SSL.bioLengthByteBuffer(r7)     // Catch:{ all -> 0x01ec }
            r8 = r27
            r9 = r7
            r20 = r15
            r7 = r0
            r15 = r14
            r14 = r10
            r10 = r30
            goto L_0x020c
        L_0x01ec:
            r0 = move-exception
            r7 = r30
            r21 = r12
            r13 = r19
            goto L_0x044d
        L_0x01f5:
            r30 = r7
            r17 = r8
            int r0 = java.lang.Math.min(r15, r6)     // Catch:{ all -> 0x043a }
            r7 = r0
            r8 = r27
            io.netty.buffer.ByteBuf r0 = r1.writeEncryptedData(r8, r7)     // Catch:{ all -> 0x043a }
            r9 = r7
            r20 = r15
            r7 = r0
            r15 = r14
            r14 = r10
            r10 = r30
        L_0x020c:
            r0 = r5[r16]     // Catch:{ all -> 0x041f }
            boolean r21 = r0.hasRemaining()     // Catch:{ all -> 0x041f }
            if (r21 != 0) goto L_0x0248
            r27 = r6
            int r6 = r16 + 1
            r30 = r10
            r10 = r19
            if (r6 < r10) goto L_0x023f
            if (r7 == 0) goto L_0x0232
            r7.release()     // Catch:{ all -> 0x0224 }
            goto L_0x0232
        L_0x0224:
            r0 = move-exception
            r7 = r30
            r16 = r6
            r21 = r12
            r13 = r10
            r10 = r14
            r14 = r15
            r15 = r20
            goto L_0x044d
        L_0x0232:
            r7 = r30
            r16 = r6
            r21 = r12
            r13 = r10
            r10 = r14
            r14 = r15
            r15 = r20
            goto L_0x03a2
        L_0x023f:
            r16 = r6
            r19 = r10
            r6 = r27
            r10 = r30
            goto L_0x020c
        L_0x0248:
            r27 = r6
            r30 = r10
            r10 = r19
            int r6 = r1.readPlaintextData(r0)     // Catch:{ all -> 0x0412 }
            r21 = r12
            long r12 = r1.networkBIO     // Catch:{ all -> 0x0407 }
            int r12 = io.netty.internal.tcnative.SSL.bioLengthByteBuffer(r12)     // Catch:{ all -> 0x0407 }
            int r12 = r9 - r12
            int r15 = r15 + r12
            int r20 = r20 - r12
            int r9 = r9 - r12
            int r13 = r8.position()     // Catch:{ all -> 0x03fa }
            int r13 = r13 + r12
            r8.position(r13)     // Catch:{ all -> 0x03fa }
            if (r6 <= 0) goto L_0x032f
            int r14 = r14 + r6
            boolean r13 = r0.hasRemaining()     // Catch:{ all -> 0x0321 }
            if (r13 != 0) goto L_0x02e0
            int r13 = r25.sslPending0()     // Catch:{ all -> 0x0321 }
            r19 = r8
            int r8 = r16 + 1
            if (r8 < r10) goto L_0x02d7
            if (r13 <= 0) goto L_0x0291
            r23 = r0
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.BUFFER_OVERFLOW     // Catch:{ all -> 0x0286 }
            javax.net.ssl.SSLEngineResult r0 = r1.newResult(r0, r4, r15, r14)     // Catch:{ all -> 0x0286 }
            goto L_0x02a2
        L_0x0286:
            r0 = move-exception
            r16 = r8
            r6 = r13
            r13 = r10
            r10 = r14
            r14 = r15
            r15 = r20
            goto L_0x0430
        L_0x0291:
            r23 = r0
            boolean r0 = r25.isInboundDone()     // Catch:{ all -> 0x02c8 }
            if (r0 == 0) goto L_0x029c
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.CLOSED     // Catch:{ all -> 0x0286 }
            goto L_0x029e
        L_0x029c:
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.OK     // Catch:{ all -> 0x02c8 }
        L_0x029e:
            javax.net.ssl.SSLEngineResult r0 = r1.newResultMayFinishHandshake(r0, r4, r15, r14)     // Catch:{ all -> 0x02c8 }
        L_0x02a2:
            if (r7 == 0) goto L_0x02b4
            r7.release()     // Catch:{ all -> 0x02a9 }
            goto L_0x02b4
        L_0x02a9:
            r0 = move-exception
            r16 = r8
            r7 = r13
            r13 = r10
            r10 = r14
            r14 = r15
            r15 = r20
            goto L_0x044d
        L_0x02b4:
            r30 = r8
            r24 = r9
            long r8 = r1.networkBIO     // Catch:{ all -> 0x02c2 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r8)     // Catch:{ all -> 0x02c2 }
            r25.rejectRemoteInitiatedRenegotiation()     // Catch:{ all -> 0x02c2 }
            monitor-exit(r25)     // Catch:{ all -> 0x02c2 }
            return r0
        L_0x02c2:
            r0 = move-exception
            r16 = r30
            r13 = r10
            goto L_0x0484
        L_0x02c8:
            r0 = move-exception
            r30 = r8
            r24 = r9
            r16 = r30
            r6 = r13
            r13 = r10
            r10 = r14
            r14 = r15
            r15 = r20
            goto L_0x0430
        L_0x02d7:
            r23 = r0
            r30 = r8
            r24 = r9
            r16 = r30
            goto L_0x02ef
        L_0x02e0:
            r23 = r0
            r19 = r8
            r24 = r9
            if (r20 == 0) goto L_0x0308
            boolean r0 = r1.jdkCompatibilityMode     // Catch:{ all -> 0x02fc }
            if (r0 == 0) goto L_0x02ed
            goto L_0x0308
        L_0x02ed:
            r13 = r30
        L_0x02ef:
            r6 = r27
            r8 = r19
            r9 = r24
            r19 = r10
            r10 = r13
            r12 = r21
            goto L_0x020c
        L_0x02fc:
            r0 = move-exception
            r6 = r30
            r13 = r10
            r10 = r14
            r14 = r15
            r15 = r20
            r9 = r24
            goto L_0x0430
        L_0x0308:
            if (r7 == 0) goto L_0x0318
            r7.release()     // Catch:{ all -> 0x030e }
            goto L_0x0318
        L_0x030e:
            r0 = move-exception
            r7 = r30
            r13 = r10
            r10 = r14
            r14 = r15
            r15 = r20
            goto L_0x044d
        L_0x0318:
            r7 = r30
            r13 = r10
            r10 = r14
            r14 = r15
            r15 = r20
            goto L_0x03a2
        L_0x0321:
            r0 = move-exception
            r19 = r8
            r24 = r9
            r6 = r30
            r13 = r10
            r10 = r14
            r14 = r15
            r15 = r20
            goto L_0x0430
        L_0x032f:
            r23 = r0
            r19 = r8
            r24 = r9
            long r8 = r1.ssl     // Catch:{ all -> 0x03ef }
            int r0 = io.netty.internal.tcnative.SSL.getError(r8, r6)     // Catch:{ all -> 0x03ef }
            int r8 = io.netty.internal.tcnative.SSL.SSL_ERROR_WANT_READ     // Catch:{ all -> 0x03ef }
            if (r0 == r8) goto L_0x0392
            int r8 = io.netty.internal.tcnative.SSL.SSL_ERROR_WANT_WRITE     // Catch:{ all -> 0x03ef }
            if (r0 != r8) goto L_0x0345
            r13 = r10
            goto L_0x0393
        L_0x0345:
            int r8 = io.netty.internal.tcnative.SSL.SSL_ERROR_ZERO_RETURN     // Catch:{ all -> 0x03ef }
            if (r0 != r8) goto L_0x036f
            boolean r8 = r1.receivedShutdown     // Catch:{ all -> 0x03ef }
            if (r8 != 0) goto L_0x0350
            r25.closeAll()     // Catch:{ all -> 0x02fc }
        L_0x0350:
            boolean r8 = r25.isInboundDone()     // Catch:{ all -> 0x03ef }
            if (r8 == 0) goto L_0x0359
            javax.net.ssl.SSLEngineResult$Status r8 = javax.net.ssl.SSLEngineResult.Status.CLOSED     // Catch:{ all -> 0x02fc }
            goto L_0x035b
        L_0x0359:
            javax.net.ssl.SSLEngineResult$Status r8 = javax.net.ssl.SSLEngineResult.Status.OK     // Catch:{ all -> 0x03ef }
        L_0x035b:
            javax.net.ssl.SSLEngineResult r8 = r1.newResultMayFinishHandshake(r8, r4, r15, r14)     // Catch:{ all -> 0x03ef }
            if (r7 == 0) goto L_0x0364
            r7.release()     // Catch:{ all -> 0x030e }
        L_0x0364:
            r13 = r10
            long r9 = r1.networkBIO     // Catch:{ all -> 0x0486 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r9)     // Catch:{ all -> 0x0486 }
            r25.rejectRemoteInitiatedRenegotiation()     // Catch:{ all -> 0x0486 }
            monitor-exit(r25)     // Catch:{ all -> 0x0486 }
            return r8
        L_0x036f:
            r13 = r10
            int r8 = io.netty.internal.tcnative.SSL.getLastErrorNumber()     // Catch:{ all -> 0x0387 }
            javax.net.ssl.SSLEngineResult r8 = r1.sslReadErrorResult(r8, r15, r14)     // Catch:{ all -> 0x0387 }
            if (r7 == 0) goto L_0x037d
            r7.release()     // Catch:{ all -> 0x03d6 }
        L_0x037d:
            long r9 = r1.networkBIO     // Catch:{ all -> 0x0486 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r9)     // Catch:{ all -> 0x0486 }
            r25.rejectRemoteInitiatedRenegotiation()     // Catch:{ all -> 0x0486 }
            monitor-exit(r25)     // Catch:{ all -> 0x0486 }
            return r8
        L_0x0387:
            r0 = move-exception
            r6 = r30
            r10 = r14
            r14 = r15
            r15 = r20
            r9 = r24
            goto L_0x0430
        L_0x0392:
            r13 = r10
        L_0x0393:
            int r3 = r3 + 1
            if (r3 < r11) goto L_0x03d0
            if (r7 == 0) goto L_0x039c
            r7.release()     // Catch:{ all -> 0x03d6 }
        L_0x039c:
            r7 = r30
            r10 = r14
            r14 = r15
            r15 = r20
        L_0x03a2:
            long r8 = r1.networkBIO     // Catch:{ all -> 0x0486 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r8)     // Catch:{ all -> 0x0486 }
            r25.rejectRemoteInitiatedRenegotiation()     // Catch:{ all -> 0x0486 }
            boolean r0 = r1.receivedShutdown     // Catch:{ all -> 0x0486 }
            if (r0 != 0) goto L_0x03bf
            long r8 = r1.ssl     // Catch:{ all -> 0x0486 }
            int r0 = io.netty.internal.tcnative.SSL.getShutdown(r8)     // Catch:{ all -> 0x0486 }
            int r6 = io.netty.internal.tcnative.SSL.SSL_RECEIVED_SHUTDOWN     // Catch:{ all -> 0x0486 }
            r0 = r0 & r6
            int r6 = io.netty.internal.tcnative.SSL.SSL_RECEIVED_SHUTDOWN     // Catch:{ all -> 0x0486 }
            if (r0 != r6) goto L_0x03bf
            r25.closeAll()     // Catch:{ all -> 0x0486 }
        L_0x03bf:
            boolean r0 = r25.isInboundDone()     // Catch:{ all -> 0x0486 }
            if (r0 == 0) goto L_0x03c8
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.CLOSED     // Catch:{ all -> 0x0486 }
            goto L_0x03ca
        L_0x03c8:
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.OK     // Catch:{ all -> 0x0486 }
        L_0x03ca:
            javax.net.ssl.SSLEngineResult r0 = r1.newResultMayFinishHandshake(r0, r4, r14, r10)     // Catch:{ all -> 0x0486 }
            monitor-exit(r25)     // Catch:{ all -> 0x0486 }
            return r0
        L_0x03d0:
            if (r7 == 0) goto L_0x03df
            r7.release()     // Catch:{ all -> 0x03d6 }
            goto L_0x03df
        L_0x03d6:
            r0 = move-exception
            r7 = r30
            r10 = r14
            r14 = r15
            r15 = r20
            goto L_0x044d
        L_0x03df:
            r7 = r30
            r19 = r13
            r10 = r14
            r14 = r15
            r6 = r16
            r8 = r17
            r15 = r20
            r12 = r21
            goto L_0x01b5
        L_0x03ef:
            r0 = move-exception
            r13 = r10
            r6 = r30
            r10 = r14
            r14 = r15
            r15 = r20
            r9 = r24
            goto L_0x0430
        L_0x03fa:
            r0 = move-exception
            r19 = r8
            r24 = r9
            r13 = r10
            r6 = r30
            r10 = r14
            r14 = r15
            r15 = r20
            goto L_0x0430
        L_0x0407:
            r0 = move-exception
            r19 = r8
            r13 = r10
            r6 = r30
            r10 = r14
            r14 = r15
            r15 = r20
            goto L_0x0430
        L_0x0412:
            r0 = move-exception
            r19 = r8
            r21 = r12
            r13 = r10
            r6 = r30
            r10 = r14
            r14 = r15
            r15 = r20
            goto L_0x0430
        L_0x041f:
            r0 = move-exception
            r27 = r6
            r30 = r10
            r21 = r12
            r13 = r19
            r19 = r8
            r6 = r30
            r10 = r14
            r14 = r15
            r15 = r20
        L_0x0430:
            if (r7 == 0) goto L_0x0435
            r7.release()     // Catch:{ all -> 0x0437 }
        L_0x0435:
            throw r0     // Catch:{ all -> 0x0437 }
        L_0x0437:
            r0 = move-exception
            r7 = r6
            goto L_0x044d
        L_0x043a:
            r0 = move-exception
            r21 = r12
            r13 = r19
            r7 = r30
            goto L_0x044d
        L_0x0442:
            r0 = move-exception
            r16 = r6
            r30 = r7
            r17 = r8
            r21 = r12
            r13 = r19
        L_0x044d:
            long r8 = r1.networkBIO     // Catch:{ all -> 0x0486 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r8)     // Catch:{ all -> 0x0486 }
            r25.rejectRemoteInitiatedRenegotiation()     // Catch:{ all -> 0x0486 }
            throw r0     // Catch:{ all -> 0x0486 }
        L_0x0456:
            r17 = r8
            r21 = r12
            r13 = r19
            java.lang.AssertionError r6 = new java.lang.AssertionError     // Catch:{ all -> 0x046e }
            r6.<init>()     // Catch:{ all -> 0x046e }
            throw r6     // Catch:{ all -> 0x046e }
        L_0x0462:
            r17 = r8
            r21 = r12
            r13 = r19
            java.lang.AssertionError r6 = new java.lang.AssertionError     // Catch:{ all -> 0x046e }
            r6.<init>()     // Catch:{ all -> 0x046e }
            throw r6     // Catch:{ all -> 0x046e }
        L_0x046e:
            r0 = move-exception
            r16 = r30
            goto L_0x0484
        L_0x0472:
            r0 = move-exception
            r17 = r8
            r21 = r12
            r13 = r19
            r16 = r30
            goto L_0x0484
        L_0x047c:
            r0 = move-exception
            r17 = r8
            r21 = r12
            r13 = r10
            r16 = r30
        L_0x0484:
            monitor-exit(r25)     // Catch:{ all -> 0x0486 }
            throw r0
        L_0x0486:
            r0 = move-exception
            goto L_0x0484
        L_0x0488:
            java.lang.IndexOutOfBoundsException r0 = new java.lang.IndexOutOfBoundsException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "offset: "
            r4.append(r6)
            r6 = r30
            r4.append(r6)
            java.lang.String r7 = ", length: "
            r4.append(r7)
            r7 = r31
            r4.append(r7)
            java.lang.String r8 = " (expected: offset <= offset + length <= dsts.length ("
            r4.append(r8)
            int r8 = r5.length
            r4.append(r8)
            java.lang.String r8 = "))"
            r4.append(r8)
            java.lang.String r4 = r4.toString()
            r0.<init>(r4)
            throw r0
        L_0x04b9:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r4 = "dsts is null"
            r0.<init>(r4)
            throw r0
        L_0x04c1:
            java.lang.IndexOutOfBoundsException r0 = new java.lang.IndexOutOfBoundsException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r8 = "offset: "
            r4.append(r8)
            r4.append(r3)
            java.lang.String r8 = ", length: "
            r4.append(r8)
            r8 = r28
            r4.append(r8)
            java.lang.String r9 = " (expected: offset <= offset + length <= srcs.length ("
            r4.append(r9)
            int r9 = r2.length
            r4.append(r9)
            java.lang.String r9 = "))"
            r4.append(r9)
            java.lang.String r4 = r4.toString()
            r0.<init>(r4)
            throw r0
        L_0x04f0:
            r8 = r4
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r4 = "srcs"
            r0.<init>(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.ReferenceCountedOpenSslEngine.unwrap(java.nio.ByteBuffer[], int, int, java.nio.ByteBuffer[], int, int):javax.net.ssl.SSLEngineResult");
    }

    private SSLEngineResult sslReadErrorResult(int err, int bytesConsumed, int bytesProduced) {
        String errStr = SSL.getErrorString((long) err);
        if (SSL.bioLengthNonApplication(this.networkBIO) > 0) {
            if (this.handshakeException == null && this.handshakeState != HandshakeState.FINISHED) {
                this.handshakeException = new SSLHandshakeException(errStr);
            }
            return new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_WRAP, bytesConsumed, bytesProduced);
        }
        throw shutdownWithError("SSL_read", errStr);
    }

    private void closeAll() {
        this.receivedShutdown = true;
        closeOutbound();
        closeInbound();
    }

    private void rejectRemoteInitiatedRenegotiation() {
        if (!isDestroyed() && SSL.getHandshakeCount(this.ssl) > 1) {
            shutdown();
            throw new SSLHandshakeException("remote-initiated renegotiation not allowed");
        }
    }

    public final SSLEngineResult unwrap(ByteBuffer[] srcs, ByteBuffer[] dsts) {
        return unwrap(srcs, 0, srcs.length, dsts, 0, dsts.length);
    }

    private ByteBuffer[] singleSrcBuffer(ByteBuffer src) {
        ByteBuffer[] byteBufferArr = this.singleSrcBuffer;
        byteBufferArr[0] = src;
        return byteBufferArr;
    }

    private void resetSingleSrcBuffer() {
        this.singleSrcBuffer[0] = null;
    }

    private ByteBuffer[] singleDstBuffer(ByteBuffer src) {
        ByteBuffer[] byteBufferArr = this.singleDstBuffer;
        byteBufferArr[0] = src;
        return byteBufferArr;
    }

    private void resetSingleDstBuffer() {
        this.singleDstBuffer[0] = null;
    }

    public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts, int offset, int length) {
        SSLEngineResult unwrap;
        try {
            unwrap = unwrap(singleSrcBuffer(src), 0, 1, dsts, offset, length);
            resetSingleSrcBuffer();
        } catch (Throwable th) {
            resetSingleSrcBuffer();
            throw th;
        }
        return unwrap;
    }

    public final synchronized SSLEngineResult wrap(ByteBuffer src, ByteBuffer dst) {
        SSLEngineResult wrap;
        try {
            wrap = wrap(singleSrcBuffer(src), dst);
            resetSingleSrcBuffer();
        } catch (Throwable th) {
            resetSingleSrcBuffer();
            throw th;
        }
        return wrap;
    }

    public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer dst) {
        SSLEngineResult unwrap;
        try {
            unwrap = unwrap(singleSrcBuffer(src), singleDstBuffer(dst));
            resetSingleSrcBuffer();
            resetSingleDstBuffer();
        } catch (Throwable th) {
            resetSingleSrcBuffer();
            resetSingleDstBuffer();
            throw th;
        }
        return unwrap;
    }

    public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts) {
        SSLEngineResult unwrap;
        try {
            unwrap = unwrap(singleSrcBuffer(src), dsts);
            resetSingleSrcBuffer();
        } catch (Throwable th) {
            resetSingleSrcBuffer();
            throw th;
        }
        return unwrap;
    }

    public final Runnable getDelegatedTask() {
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0027, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void closeInbound() {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.isInboundDone     // Catch:{ all -> 0x0028 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r2)
            return
        L_0x0007:
            r0 = 1
            r2.isInboundDone = r0     // Catch:{ all -> 0x0028 }
            boolean r0 = r2.isOutboundDone()     // Catch:{ all -> 0x0028 }
            if (r0 == 0) goto L_0x0013
            r2.shutdown()     // Catch:{ all -> 0x0028 }
        L_0x0013:
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r0 = r2.handshakeState     // Catch:{ all -> 0x0028 }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r1 = io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.NOT_STARTED     // Catch:{ all -> 0x0028 }
            if (r0 == r1) goto L_0x0026
            boolean r0 = r2.receivedShutdown     // Catch:{ all -> 0x0028 }
            if (r0 == 0) goto L_0x001e
            goto L_0x0026
        L_0x001e:
            javax.net.ssl.SSLException r0 = new javax.net.ssl.SSLException     // Catch:{ all -> 0x0028 }
            java.lang.String r1 = "Inbound closed before receiving peer's close_notify: possible truncation attack?"
            r0.<init>(r1)     // Catch:{ all -> 0x0028 }
            throw r0     // Catch:{ all -> 0x0028 }
        L_0x0026:
            monitor-exit(r2)
            return
        L_0x0028:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.ReferenceCountedOpenSslEngine.closeInbound():void");
    }

    public final synchronized boolean isInboundDone() {
        return this.isInboundDone;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void closeOutbound() {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.outboundClosed     // Catch:{ all -> 0x002c }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r3)
            return
        L_0x0007:
            r0 = 1
            r3.outboundClosed = r0     // Catch:{ all -> 0x002c }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r0 = r3.handshakeState     // Catch:{ all -> 0x002c }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r1 = io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.NOT_STARTED     // Catch:{ all -> 0x002c }
            if (r0 == r1) goto L_0x0027
            boolean r0 = r3.isDestroyed()     // Catch:{ all -> 0x002c }
            if (r0 != 0) goto L_0x0027
            long r0 = r3.ssl     // Catch:{ all -> 0x002c }
            int r0 = io.netty.internal.tcnative.SSL.getShutdown(r0)     // Catch:{ all -> 0x002c }
            int r1 = io.netty.internal.tcnative.SSL.SSL_SENT_SHUTDOWN     // Catch:{ all -> 0x002c }
            r1 = r1 & r0
            int r2 = io.netty.internal.tcnative.SSL.SSL_SENT_SHUTDOWN     // Catch:{ all -> 0x002c }
            if (r1 == r2) goto L_0x0026
            r3.doSSLShutdown()     // Catch:{ all -> 0x002c }
        L_0x0026:
            goto L_0x002a
        L_0x0027:
            r3.shutdown()     // Catch:{ all -> 0x002c }
        L_0x002a:
            monitor-exit(r3)
            return
        L_0x002c:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.ReferenceCountedOpenSslEngine.closeOutbound():void");
    }

    private boolean doSSLShutdown() {
        if (SSL.isInInit(this.ssl) != 0) {
            return false;
        }
        int err = SSL.shutdownSSL(this.ssl);
        if (err >= 0) {
            return true;
        }
        int sslErr = SSL.getError(this.ssl, err);
        if (sslErr == SSL.SSL_ERROR_SYSCALL || sslErr == SSL.SSL_ERROR_SSL) {
            InternalLogger internalLogger = logger;
            if (internalLogger.isDebugEnabled()) {
                internalLogger.debug("SSL_shutdown failed: OpenSSL error: {}", (Object) SSL.getLastError());
            }
            shutdown();
            return false;
        }
        SSL.clearError();
        return true;
    }

    public final synchronized boolean isOutboundDone() {
        boolean z;
        if (this.outboundClosed) {
            long j = this.networkBIO;
            if (j == 0 || SSL.bioLengthNonApplication(j) == 0) {
                z = true;
            }
        }
        z = false;
        return z;
    }

    public final String[] getSupportedCipherSuites() {
        Set<String> set = OpenSsl.AVAILABLE_CIPHER_SUITES;
        return (String[]) set.toArray(new String[set.size()]);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0014, code lost:
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0016, code lost:
        if (r1 >= r0.length) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0018, code lost:
        r2 = toJavaCipherSuite(r0[r1]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001e, code lost:
        if (r2 == null) goto L_0x0022;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0020, code lost:
        r0[r1] = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0022, code lost:
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0025, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0026, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000e, code lost:
        if (r0 != null) goto L_0x0013;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0012, code lost:
        return io.netty.util.internal.EmptyArrays.EMPTY_STRINGS;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0013, code lost:
        monitor-enter(r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String[] getEnabledCipherSuites() {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.isDestroyed()     // Catch:{ all -> 0x002e }
            if (r0 != 0) goto L_0x002a
            long r0 = r3.ssl     // Catch:{ all -> 0x002e }
            java.lang.String[] r0 = io.netty.internal.tcnative.SSL.getCiphers(r0)     // Catch:{ all -> 0x002e }
            monitor-exit(r3)     // Catch:{ all -> 0x002e }
            if (r0 != 0) goto L_0x0013
            java.lang.String[] r1 = io.netty.util.internal.EmptyArrays.EMPTY_STRINGS
            return r1
        L_0x0013:
            monitor-enter(r3)
            r1 = 0
        L_0x0015:
            int r2 = r0.length     // Catch:{ all -> 0x0027 }
            if (r1 >= r2) goto L_0x0025
            r2 = r0[r1]     // Catch:{ all -> 0x0027 }
            java.lang.String r2 = r3.toJavaCipherSuite(r2)     // Catch:{ all -> 0x0027 }
            if (r2 == 0) goto L_0x0022
            r0[r1] = r2     // Catch:{ all -> 0x0027 }
        L_0x0022:
            int r1 = r1 + 1
            goto L_0x0015
        L_0x0025:
            monitor-exit(r3)     // Catch:{ all -> 0x0027 }
            return r0
        L_0x0027:
            r1 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0027 }
            throw r1
        L_0x002a:
            java.lang.String[] r0 = io.netty.util.internal.EmptyArrays.EMPTY_STRINGS     // Catch:{ all -> 0x002e }
            monitor-exit(r3)     // Catch:{ all -> 0x002e }
            return r0
        L_0x002e:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x002e }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.ReferenceCountedOpenSslEngine.getEnabledCipherSuites():java.lang.String[]");
    }

    public final void setEnabledCipherSuites(String[] cipherSuites) {
        ObjectUtil.checkNotNull(cipherSuites, "cipherSuites");
        StringBuilder buf = new StringBuilder();
        int length = cipherSuites.length;
        int i = 0;
        while (i < length) {
            String c = cipherSuites[i];
            if (c == null) {
                break;
            }
            String converted = CipherSuiteConverter.toOpenSsl(c);
            if (converted == null) {
                converted = c;
            }
            if (OpenSsl.isCipherSuiteAvailable(converted)) {
                buf.append(converted);
                buf.append(':');
                i++;
            } else {
                throw new IllegalArgumentException("unsupported cipher suite: " + c + '(' + converted + ')');
            }
        }
        if (buf.length() != 0) {
            buf.setLength(buf.length() - 1);
            String cipherSuiteSpec = buf.toString();
            synchronized (this) {
                if (!isDestroyed()) {
                    try {
                        SSL.setCipherSuites(this.ssl, cipherSuiteSpec);
                    } catch (Exception e) {
                        throw new IllegalStateException("failed to enable cipher suites: " + cipherSuiteSpec, e);
                    }
                } else {
                    throw new IllegalStateException("failed to enable cipher suites: " + cipherSuiteSpec);
                }
            }
            return;
        }
        throw new IllegalArgumentException("empty cipher suites");
    }

    public final String[] getSupportedProtocols() {
        Set<String> set = OpenSsl.SUPPORTED_PROTOCOLS_SET;
        return (String[]) set.toArray(new String[set.size()]);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0030, code lost:
        if (isProtocolEnabled(r1, io.netty.internal.tcnative.SSL.SSL_OP_NO_TLSv1_1, "TLSv1.1") == false) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0032, code lost:
        r0.add("TLSv1.1");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003f, code lost:
        if (isProtocolEnabled(r1, io.netty.internal.tcnative.SSL.SSL_OP_NO_TLSv1_2, "TLSv1.2") == false) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0041, code lost:
        r0.add("TLSv1.2");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004e, code lost:
        if (isProtocolEnabled(r1, io.netty.internal.tcnative.SSL.SSL_OP_NO_SSLv2, "SSLv2") == false) goto L_0x0055;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0050, code lost:
        r0.add("SSLv2");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005d, code lost:
        if (isProtocolEnabled(r1, io.netty.internal.tcnative.SSL.SSL_OP_NO_SSLv3, "SSLv3") == false) goto L_0x0064;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005f, code lost:
        r0.add("SSLv3");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0070, code lost:
        return (java.lang.String[]) r0.toArray(new java.lang.String[r0.size()]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0021, code lost:
        if (isProtocolEnabled(r1, io.netty.internal.tcnative.SSL.SSL_OP_NO_TLSv1, "TLSv1") == false) goto L_0x0028;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0023, code lost:
        r0.add("TLSv1");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String[] getEnabledProtocols() {
        /*
            r4 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = 6
            r0.<init>(r1)
            java.lang.String r1 = "SSLv2Hello"
            r0.add(r1)
            monitor-enter(r4)
            boolean r1 = r4.isDestroyed()     // Catch:{ all -> 0x007c }
            if (r1 != 0) goto L_0x0071
            long r1 = r4.ssl     // Catch:{ all -> 0x007c }
            int r1 = io.netty.internal.tcnative.SSL.getOptions(r1)     // Catch:{ all -> 0x007c }
            monitor-exit(r4)     // Catch:{ all -> 0x007c }
            int r2 = io.netty.internal.tcnative.SSL.SSL_OP_NO_TLSv1
            java.lang.String r3 = "TLSv1"
            boolean r2 = isProtocolEnabled(r1, r2, r3)
            if (r2 == 0) goto L_0x0028
            java.lang.String r2 = "TLSv1"
            r0.add(r2)
        L_0x0028:
            int r2 = io.netty.internal.tcnative.SSL.SSL_OP_NO_TLSv1_1
            java.lang.String r3 = "TLSv1.1"
            boolean r2 = isProtocolEnabled(r1, r2, r3)
            if (r2 == 0) goto L_0x0037
            java.lang.String r2 = "TLSv1.1"
            r0.add(r2)
        L_0x0037:
            int r2 = io.netty.internal.tcnative.SSL.SSL_OP_NO_TLSv1_2
            java.lang.String r3 = "TLSv1.2"
            boolean r2 = isProtocolEnabled(r1, r2, r3)
            if (r2 == 0) goto L_0x0046
            java.lang.String r2 = "TLSv1.2"
            r0.add(r2)
        L_0x0046:
            int r2 = io.netty.internal.tcnative.SSL.SSL_OP_NO_SSLv2
            java.lang.String r3 = "SSLv2"
            boolean r2 = isProtocolEnabled(r1, r2, r3)
            if (r2 == 0) goto L_0x0055
            java.lang.String r2 = "SSLv2"
            r0.add(r2)
        L_0x0055:
            int r2 = io.netty.internal.tcnative.SSL.SSL_OP_NO_SSLv3
            java.lang.String r3 = "SSLv3"
            boolean r2 = isProtocolEnabled(r1, r2, r3)
            if (r2 == 0) goto L_0x0064
            java.lang.String r2 = "SSLv3"
            r0.add(r2)
        L_0x0064:
            int r2 = r0.size()
            java.lang.String[] r2 = new java.lang.String[r2]
            java.lang.Object[] r2 = r0.toArray(r2)
            java.lang.String[] r2 = (java.lang.String[]) r2
            return r2
        L_0x0071:
            r1 = 1
            java.lang.String[] r1 = new java.lang.String[r1]     // Catch:{ all -> 0x007c }
            java.lang.Object[] r1 = r0.toArray(r1)     // Catch:{ all -> 0x007c }
            java.lang.String[] r1 = (java.lang.String[]) r1     // Catch:{ all -> 0x007c }
            monitor-exit(r4)     // Catch:{ all -> 0x007c }
            return r1
        L_0x007c:
            r1 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x007c }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.ReferenceCountedOpenSslEngine.getEnabledProtocols():java.lang.String[]");
    }

    private static boolean isProtocolEnabled(int opts, int disableMask, String protocolString) {
        return (opts & disableMask) == 0 && OpenSsl.SUPPORTED_PROTOCOLS_SET.contains(protocolString);
    }

    public final void setEnabledProtocols(String[] protocols) {
        if (protocols != null) {
            int minProtocolIndex = OPENSSL_OP_NO_PROTOCOLS.length;
            int maxProtocolIndex = 0;
            int length = protocols.length;
            int i = 0;
            while (i < length) {
                String p = protocols[i];
                if (OpenSsl.SUPPORTED_PROTOCOLS_SET.contains(p)) {
                    if (p.equals("SSLv2")) {
                        if (minProtocolIndex > 0) {
                            minProtocolIndex = 0;
                        }
                        if (maxProtocolIndex < 0) {
                            maxProtocolIndex = 0;
                        }
                    } else if (p.equals("SSLv3")) {
                        if (minProtocolIndex > 1) {
                            minProtocolIndex = 1;
                        }
                        if (maxProtocolIndex < 1) {
                            maxProtocolIndex = 1;
                        }
                    } else if (p.equals("TLSv1")) {
                        if (minProtocolIndex > 2) {
                            minProtocolIndex = 2;
                        }
                        if (maxProtocolIndex < 2) {
                            maxProtocolIndex = 2;
                        }
                    } else if (p.equals("TLSv1.1")) {
                        if (minProtocolIndex > 3) {
                            minProtocolIndex = 3;
                        }
                        if (maxProtocolIndex < 3) {
                            maxProtocolIndex = 3;
                        }
                    } else if (p.equals("TLSv1.2")) {
                        if (minProtocolIndex > 4) {
                            minProtocolIndex = 4;
                        }
                        if (maxProtocolIndex < 4) {
                            maxProtocolIndex = 4;
                        }
                    }
                    i++;
                } else {
                    throw new IllegalArgumentException("Protocol " + p + " is not supported.");
                }
            }
            synchronized (this) {
                if (!isDestroyed()) {
                    SSL.clearOptions(this.ssl, SSL.SSL_OP_NO_SSLv2 | SSL.SSL_OP_NO_SSLv3 | SSL.SSL_OP_NO_TLSv1 | SSL.SSL_OP_NO_TLSv1_1 | SSL.SSL_OP_NO_TLSv1_2);
                    int opts = 0;
                    for (int i2 = 0; i2 < minProtocolIndex; i2++) {
                        opts |= OPENSSL_OP_NO_PROTOCOLS[i2];
                    }
                    if (maxProtocolIndex != Integer.MAX_VALUE) {
                        int i3 = maxProtocolIndex + 1;
                        while (true) {
                            int[] iArr = OPENSSL_OP_NO_PROTOCOLS;
                            if (i3 < iArr.length) {
                                opts |= iArr[i3];
                                i3++;
                            } else {
                                SSL.setOptions(this.ssl, opts);
                            }
                        }
                    } else {
                        throw new AssertionError();
                    }
                } else {
                    throw new IllegalStateException("failed to enable protocols: " + Arrays.asList(protocols));
                }
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    public final SSLSession getSession() {
        return this.session;
    }

    /* Debug info: failed to restart local var, previous not found, register: 2 */
    public final synchronized void beginHandshake() {
        switch (AnonymousClass2.$SwitchMap$io$netty$handler$ssl$ReferenceCountedOpenSslEngine$HandshakeState[this.handshakeState.ordinal()]) {
            case 1:
                this.handshakeState = HandshakeState.STARTED_EXPLICITLY;
                handshake();
                calculateMaxWrapOverhead();
                break;
            case 2:
                throw RENEGOTIATION_UNSUPPORTED;
            case 3:
                checkEngineClosed(BEGIN_HANDSHAKE_ENGINE_CLOSED);
                this.handshakeState = HandshakeState.STARTED_EXPLICITLY;
                calculateMaxWrapOverhead();
                break;
            case 4:
                break;
            default:
                throw new Error();
        }
    }

    private void checkEngineClosed(SSLException cause) {
        if (isDestroyed()) {
            throw cause;
        }
    }

    private static SSLEngineResult.HandshakeStatus pendingStatus(int pendingStatus) {
        return pendingStatus > 0 ? SSLEngineResult.HandshakeStatus.NEED_WRAP : SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
    }

    /* access modifiers changed from: private */
    public static boolean isEmpty(Object[] arr) {
        return arr == null || arr.length == 0;
    }

    /* access modifiers changed from: private */
    public static boolean isEmpty(byte[] cert) {
        return cert == null || cert.length == 0;
    }

    private SSLEngineResult.HandshakeStatus handshake() {
        OpenSslKeyMaterialManager openSslKeyMaterialManager;
        if (this.handshakeState == HandshakeState.FINISHED) {
            return SSLEngineResult.HandshakeStatus.FINISHED;
        }
        checkEngineClosed(HANDSHAKE_ENGINE_CLOSED);
        SSLHandshakeException exception = this.handshakeException;
        if (exception == null) {
            this.engineMap.add(this);
            if (this.lastAccessed == -1) {
                this.lastAccessed = System.currentTimeMillis();
            }
            if (!this.certificateSet && (openSslKeyMaterialManager = this.keyMaterialManager) != null) {
                this.certificateSet = true;
                openSslKeyMaterialManager.setKeyMaterial(this);
            }
            int code = SSL.doHandshake(this.ssl);
            if (code > 0) {
                this.session.handshakeFinished();
                this.engineMap.remove(this.ssl);
                return SSLEngineResult.HandshakeStatus.FINISHED;
            } else if (this.handshakeException == null) {
                int sslError = SSL.getError(this.ssl, code);
                if (sslError == SSL.SSL_ERROR_WANT_READ || sslError == SSL.SSL_ERROR_WANT_WRITE) {
                    return pendingStatus(SSL.bioLengthNonApplication(this.networkBIO));
                }
                throw shutdownWithError("SSL_do_handshake");
            } else {
                SSLHandshakeException exception2 = this.handshakeException;
                this.handshakeException = null;
                shutdown();
                throw exception2;
            }
        } else if (SSL.bioLengthNonApplication(this.networkBIO) > 0) {
            return SSLEngineResult.HandshakeStatus.NEED_WRAP;
        } else {
            this.handshakeException = null;
            shutdown();
            throw exception;
        }
    }

    private SSLEngineResult.HandshakeStatus mayFinishHandshake(SSLEngineResult.HandshakeStatus status) {
        if (status != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING || this.handshakeState == HandshakeState.FINISHED) {
            return status;
        }
        return handshake();
    }

    public final synchronized SSLEngineResult.HandshakeStatus getHandshakeStatus() {
        return needPendingStatus() ? pendingStatus(SSL.bioLengthNonApplication(this.networkBIO)) : SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
    }

    private SSLEngineResult.HandshakeStatus getHandshakeStatus(int pending) {
        return needPendingStatus() ? pendingStatus(pending) : SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
    }

    private boolean needPendingStatus() {
        return this.handshakeState != HandshakeState.NOT_STARTED && !isDestroyed() && (this.handshakeState != HandshakeState.FINISHED || isInboundDone() || isOutboundDone());
    }

    /* access modifiers changed from: private */
    public String toJavaCipherSuite(String openSslCipherSuite) {
        if (openSslCipherSuite == null) {
            return null;
        }
        return CipherSuiteConverter.toJava(openSslCipherSuite, toJavaCipherSuitePrefix(SSL.getVersion(this.ssl)));
    }

    private static String toJavaCipherSuitePrefix(String protocolVersion) {
        char c;
        if (protocolVersion == null || protocolVersion.isEmpty()) {
            c = 0;
        } else {
            c = protocolVersion.charAt(0);
        }
        switch (c) {
            case 'S':
                return "SSL";
            case 'T':
                return "TLS";
            default:
                return LDNetUtil.NETWORKTYPE_INVALID;
        }
    }

    public final void setUseClientMode(boolean clientMode2) {
        if (clientMode2 != this.clientMode) {
            throw new UnsupportedOperationException();
        }
    }

    public final boolean getUseClientMode() {
        return this.clientMode;
    }

    public final void setNeedClientAuth(boolean b) {
        setClientAuth(b ? ClientAuth.REQUIRE : ClientAuth.NONE);
    }

    public final boolean getNeedClientAuth() {
        return this.clientAuth == ClientAuth.REQUIRE;
    }

    public final void setWantClientAuth(boolean b) {
        setClientAuth(b ? ClientAuth.OPTIONAL : ClientAuth.NONE);
    }

    public final boolean getWantClientAuth() {
        return this.clientAuth == ClientAuth.OPTIONAL;
    }

    public final synchronized void setVerify(int verifyMode, int depth) {
        SSL.setVerify(this.ssl, verifyMode, depth);
    }

    private void setClientAuth(ClientAuth mode) {
        if (!this.clientMode) {
            synchronized (this) {
                if (this.clientAuth != mode) {
                    switch (AnonymousClass2.$SwitchMap$io$netty$handler$ssl$ClientAuth[mode.ordinal()]) {
                        case 1:
                            SSL.setVerify(this.ssl, 0, 10);
                            break;
                        case 2:
                            SSL.setVerify(this.ssl, 2, 10);
                            break;
                        case 3:
                            SSL.setVerify(this.ssl, 1, 10);
                            break;
                        default:
                            throw new Error(mode.toString());
                    }
                    this.clientAuth = mode;
                }
            }
        }
    }

    public final void setEnableSessionCreation(boolean b) {
        if (b) {
            throw new UnsupportedOperationException();
        }
    }

    public final boolean getEnableSessionCreation() {
        return false;
    }

    public final synchronized SSLParameters getSSLParameters() {
        SSLParameters sslParameters;
        sslParameters = super.getSSLParameters();
        int version = PlatformDependent.javaVersion();
        if (version >= 7) {
            sslParameters.setEndpointIdentificationAlgorithm(this.endPointIdentificationAlgorithm);
            Java7SslParametersUtils.setAlgorithmConstraints(sslParameters, this.algorithmConstraints);
            if (version >= 8) {
                List<String> list = this.sniHostNames;
                if (list != null) {
                    Java8SslUtils.setSniHostNames(sslParameters, list);
                }
                if (!isDestroyed()) {
                    Java8SslUtils.setUseCipherSuitesOrder(sslParameters, (SSL.getOptions(this.ssl) & SSL.SSL_OP_CIPHER_SERVER_PREFERENCE) != 0);
                }
                Java8SslUtils.setSNIMatchers(sslParameters, this.matchers);
            }
        }
        return sslParameters;
    }

    public final synchronized void setSSLParameters(SSLParameters sslParameters) {
        int version = PlatformDependent.javaVersion();
        if (version >= 7) {
            if (sslParameters.getAlgorithmConstraints() == null) {
                if (version >= 8) {
                    if (!isDestroyed()) {
                        if (this.clientMode) {
                            List<String> sniHostNames2 = Java8SslUtils.getSniHostNames(sslParameters);
                            for (String name : sniHostNames2) {
                                SSL.setTlsExtHostName(this.ssl, name);
                            }
                            this.sniHostNames = sniHostNames2;
                        }
                        if (Java8SslUtils.getUseCipherSuitesOrder(sslParameters)) {
                            SSL.setOptions(this.ssl, SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
                        } else {
                            SSL.clearOptions(this.ssl, SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
                        }
                    }
                    this.matchers = sslParameters.getSNIMatchers();
                }
                String endPointIdentificationAlgorithm2 = sslParameters.getEndpointIdentificationAlgorithm();
                boolean endPointVerificationEnabled = endPointIdentificationAlgorithm2 != null && !endPointIdentificationAlgorithm2.isEmpty();
                SSL.setHostNameValidation(this.ssl, 0, endPointVerificationEnabled ? getPeerHost() : null);
                if (this.clientMode && endPointVerificationEnabled) {
                    SSL.setVerify(this.ssl, 2, -1);
                }
                this.endPointIdentificationAlgorithm = endPointIdentificationAlgorithm2;
                this.algorithmConstraints = sslParameters.getAlgorithmConstraints();
            } else {
                throw new IllegalArgumentException("AlgorithmConstraints are not supported.");
            }
        }
        super.setSSLParameters(sslParameters);
    }

    /* access modifiers changed from: private */
    public boolean isDestroyed() {
        return this.destroyed != 0;
    }

    /* access modifiers changed from: package-private */
    public final boolean checkSniHostnameMatch(String hostname) {
        return Java8SslUtils.checkSniHostnameMatch(this.matchers, hostname);
    }

    public String getNegotiatedApplicationProtocol() {
        return this.applicationProtocol;
    }

    public final class OpenSslSession implements SSLSession {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private volatile int applicationBufferSize = ReferenceCountedOpenSslEngine.MAX_PLAINTEXT_LENGTH;
        private String cipher;
        private long creationTime;
        private byte[] id;
        private Certificate[] peerCerts;
        private String protocol;
        private final OpenSslSessionContext sessionContext;
        private Map<String, Object> values;
        private X509Certificate[] x509PeerCerts;

        static {
            Class<ReferenceCountedOpenSslEngine> cls = ReferenceCountedOpenSslEngine.class;
        }

        OpenSslSession(OpenSslSessionContext sessionContext2) {
            this.sessionContext = sessionContext2;
        }

        public byte[] getId() {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                byte[] bArr = this.id;
                if (bArr == null) {
                    byte[] bArr2 = EmptyArrays.EMPTY_BYTES;
                    return bArr2;
                }
                byte[] bArr3 = (byte[]) bArr.clone();
                return bArr3;
            }
        }

        public SSLSessionContext getSessionContext() {
            return this.sessionContext;
        }

        public long getCreationTime() {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (this.creationTime == 0 && !ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    this.creationTime = SSL.getTime(ReferenceCountedOpenSslEngine.this.ssl) * 1000;
                }
            }
            return this.creationTime;
        }

        public long getLastAccessedTime() {
            long lastAccessed = ReferenceCountedOpenSslEngine.this.lastAccessed;
            return lastAccessed == -1 ? getCreationTime() : lastAccessed;
        }

        public void invalidate() {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    SSL.setTimeout(ReferenceCountedOpenSslEngine.this.ssl, 0);
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:9:0x002f, code lost:
            return r2;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean isValid() {
            /*
                r9 = this;
                io.netty.handler.ssl.ReferenceCountedOpenSslEngine r0 = io.netty.handler.ssl.ReferenceCountedOpenSslEngine.this
                monitor-enter(r0)
                io.netty.handler.ssl.ReferenceCountedOpenSslEngine r1 = io.netty.handler.ssl.ReferenceCountedOpenSslEngine.this     // Catch:{ all -> 0x0032 }
                boolean r1 = r1.isDestroyed()     // Catch:{ all -> 0x0032 }
                r2 = 0
                if (r1 != 0) goto L_0x0030
                long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0032 }
                io.netty.handler.ssl.ReferenceCountedOpenSslEngine r1 = io.netty.handler.ssl.ReferenceCountedOpenSslEngine.this     // Catch:{ all -> 0x0032 }
                long r5 = r1.ssl     // Catch:{ all -> 0x0032 }
                long r5 = io.netty.internal.tcnative.SSL.getTimeout(r5)     // Catch:{ all -> 0x0032 }
                r7 = 1000(0x3e8, double:4.94E-321)
                long r5 = r5 * r7
                long r3 = r3 - r5
                io.netty.handler.ssl.ReferenceCountedOpenSslEngine r1 = io.netty.handler.ssl.ReferenceCountedOpenSslEngine.this     // Catch:{ all -> 0x0032 }
                long r5 = r1.ssl     // Catch:{ all -> 0x0032 }
                long r5 = io.netty.internal.tcnative.SSL.getTime(r5)     // Catch:{ all -> 0x0032 }
                long r5 = r5 * r7
                int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r1 >= 0) goto L_0x002e
                r2 = 1
            L_0x002e:
                monitor-exit(r0)     // Catch:{ all -> 0x0032 }
                return r2
            L_0x0030:
                monitor-exit(r0)     // Catch:{ all -> 0x0032 }
                return r2
            L_0x0032:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0032 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.ReferenceCountedOpenSslEngine.OpenSslSession.isValid():boolean");
        }

        public void putValue(String name, Object value) {
            if (name == null) {
                throw new NullPointerException("name");
            } else if (value != null) {
                Map<String, Object> values2 = this.values;
                if (values2 == null) {
                    HashMap hashMap = new HashMap(2);
                    this.values = hashMap;
                    values2 = hashMap;
                }
                Object old = values2.put(name, value);
                if (value instanceof SSLSessionBindingListener) {
                    ((SSLSessionBindingListener) value).valueBound(new SSLSessionBindingEvent(this, name));
                }
                notifyUnbound(old, name);
            } else {
                throw new NullPointerException("value");
            }
        }

        public Object getValue(String name) {
            if (name != null) {
                Map<String, Object> map = this.values;
                if (map == null) {
                    return null;
                }
                return map.get(name);
            }
            throw new NullPointerException("name");
        }

        public void removeValue(String name) {
            if (name != null) {
                Map<String, Object> values2 = this.values;
                if (values2 != null) {
                    notifyUnbound(values2.remove(name), name);
                    return;
                }
                return;
            }
            throw new NullPointerException("name");
        }

        public String[] getValueNames() {
            Map<String, Object> values2 = this.values;
            if (values2 == null || values2.isEmpty()) {
                return EmptyArrays.EMPTY_STRINGS;
            }
            return (String[]) values2.keySet().toArray(new String[values2.size()]);
        }

        private void notifyUnbound(Object value, String name) {
            if (value instanceof SSLSessionBindingListener) {
                ((SSLSessionBindingListener) value).valueUnbound(new SSLSessionBindingEvent(this, name));
            }
        }

        /* access modifiers changed from: package-private */
        public void handshakeFinished() {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    this.id = SSL.getSessionId(ReferenceCountedOpenSslEngine.this.ssl);
                    ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = ReferenceCountedOpenSslEngine.this;
                    this.cipher = referenceCountedOpenSslEngine.toJavaCipherSuite(SSL.getCipherForSSL(referenceCountedOpenSslEngine.ssl));
                    this.protocol = SSL.getVersion(ReferenceCountedOpenSslEngine.this.ssl);
                    initPeerCerts();
                    selectApplicationProtocol();
                    ReferenceCountedOpenSslEngine.this.calculateMaxWrapOverhead();
                    HandshakeState unused = ReferenceCountedOpenSslEngine.this.handshakeState = HandshakeState.FINISHED;
                } else {
                    throw new SSLException("Already closed");
                }
            }
        }

        private void initPeerCerts() {
            byte[][] chain = SSL.getPeerCertChain(ReferenceCountedOpenSslEngine.this.ssl);
            if (!ReferenceCountedOpenSslEngine.this.clientMode) {
                byte[] clientCert = SSL.getPeerCertificate(ReferenceCountedOpenSslEngine.this.ssl);
                if (ReferenceCountedOpenSslEngine.isEmpty(clientCert)) {
                    this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
                    this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
                } else if (ReferenceCountedOpenSslEngine.isEmpty((Object[]) chain)) {
                    this.peerCerts = new Certificate[]{new OpenSslX509Certificate(clientCert)};
                    this.x509PeerCerts = new X509Certificate[]{new OpenSslJavaxX509Certificate(clientCert)};
                } else {
                    Certificate[] certificateArr = new Certificate[(chain.length + 1)];
                    this.peerCerts = certificateArr;
                    this.x509PeerCerts = new X509Certificate[(chain.length + 1)];
                    certificateArr[0] = new OpenSslX509Certificate(clientCert);
                    this.x509PeerCerts[0] = new OpenSslJavaxX509Certificate(clientCert);
                    initCerts(chain, 1);
                }
            } else if (ReferenceCountedOpenSslEngine.isEmpty((Object[]) chain)) {
                this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
                this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
            } else {
                this.peerCerts = new Certificate[chain.length];
                this.x509PeerCerts = new X509Certificate[chain.length];
                initCerts(chain, 0);
            }
        }

        private void initCerts(byte[][] chain, int startPos) {
            for (int i = 0; i < chain.length; i++) {
                int certPos = startPos + i;
                this.peerCerts[certPos] = new OpenSslX509Certificate(chain[i]);
                this.x509PeerCerts[certPos] = new OpenSslJavaxX509Certificate(chain[i]);
            }
        }

        private void selectApplicationProtocol() {
            ApplicationProtocolConfig.SelectedListenerFailureBehavior behavior = ReferenceCountedOpenSslEngine.this.apn.selectedListenerFailureBehavior();
            List<String> protocols = ReferenceCountedOpenSslEngine.this.apn.protocols();
            switch (AnonymousClass2.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[ReferenceCountedOpenSslEngine.this.apn.protocol().ordinal()]) {
                case 1:
                    return;
                case 2:
                    String applicationProtocol = SSL.getAlpnSelected(ReferenceCountedOpenSslEngine.this.ssl);
                    if (applicationProtocol != null) {
                        String unused = ReferenceCountedOpenSslEngine.this.applicationProtocol = selectApplicationProtocol(protocols, behavior, applicationProtocol);
                        return;
                    }
                    return;
                case 3:
                    String applicationProtocol2 = SSL.getNextProtoNegotiated(ReferenceCountedOpenSslEngine.this.ssl);
                    if (applicationProtocol2 != null) {
                        String unused2 = ReferenceCountedOpenSslEngine.this.applicationProtocol = selectApplicationProtocol(protocols, behavior, applicationProtocol2);
                        return;
                    }
                    return;
                case 4:
                    String applicationProtocol3 = SSL.getAlpnSelected(ReferenceCountedOpenSslEngine.this.ssl);
                    if (applicationProtocol3 == null) {
                        applicationProtocol3 = SSL.getNextProtoNegotiated(ReferenceCountedOpenSslEngine.this.ssl);
                    }
                    if (applicationProtocol3 != null) {
                        String unused3 = ReferenceCountedOpenSslEngine.this.applicationProtocol = selectApplicationProtocol(protocols, behavior, applicationProtocol3);
                        return;
                    }
                    return;
                default:
                    throw new Error();
            }
        }

        private String selectApplicationProtocol(List<String> protocols, ApplicationProtocolConfig.SelectedListenerFailureBehavior behavior, String applicationProtocol) {
            if (behavior == ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT) {
                return applicationProtocol;
            }
            int size = protocols.size();
            if (size <= 0) {
                throw new AssertionError();
            } else if (protocols.contains(applicationProtocol)) {
                return applicationProtocol;
            } else {
                if (behavior == ApplicationProtocolConfig.SelectedListenerFailureBehavior.CHOOSE_MY_LAST_PROTOCOL) {
                    return protocols.get(size - 1);
                }
                throw new SSLException("unknown protocol " + applicationProtocol);
            }
        }

        public Certificate[] getPeerCertificates() {
            Certificate[] certificateArr;
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (!ReferenceCountedOpenSslEngine.isEmpty((Object[]) this.peerCerts)) {
                    certificateArr = (Certificate[]) this.peerCerts.clone();
                } else {
                    throw new SSLPeerUnverifiedException("peer not verified");
                }
            }
            return certificateArr;
        }

        public Certificate[] getLocalCertificates() {
            if (ReferenceCountedOpenSslEngine.this.localCerts == null) {
                return null;
            }
            return (Certificate[]) ReferenceCountedOpenSslEngine.this.localCerts.clone();
        }

        public X509Certificate[] getPeerCertificateChain() {
            X509Certificate[] x509CertificateArr;
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (!ReferenceCountedOpenSslEngine.isEmpty((Object[]) this.x509PeerCerts)) {
                    x509CertificateArr = (X509Certificate[]) this.x509PeerCerts.clone();
                } else {
                    throw new SSLPeerUnverifiedException("peer not verified");
                }
            }
            return x509CertificateArr;
        }

        public Principal getPeerPrincipal() {
            return ((java.security.cert.X509Certificate) getPeerCertificates()[0]).getSubjectX500Principal();
        }

        public Principal getLocalPrincipal() {
            Certificate[] local = ReferenceCountedOpenSslEngine.this.localCerts;
            if (local == null || local.length == 0) {
                return null;
            }
            return ((java.security.cert.X509Certificate) local[0]).getIssuerX500Principal();
        }

        public String getCipherSuite() {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                String str = this.cipher;
                if (str == null) {
                    return ReferenceCountedOpenSslEngine.INVALID_CIPHER;
                }
                return str;
            }
        }

        public String getProtocol() {
            String protocol2 = this.protocol;
            if (protocol2 == null) {
                synchronized (ReferenceCountedOpenSslEngine.this) {
                    if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                        protocol2 = SSL.getVersion(ReferenceCountedOpenSslEngine.this.ssl);
                    } else {
                        protocol2 = "";
                    }
                }
            }
            return protocol2;
        }

        public String getPeerHost() {
            return ReferenceCountedOpenSslEngine.this.getPeerHost();
        }

        public int getPeerPort() {
            return ReferenceCountedOpenSslEngine.this.getPeerPort();
        }

        public int getPacketBufferSize() {
            return ReferenceCountedOpenSslEngine.this.maxEncryptedPacketLength();
        }

        public int getApplicationBufferSize() {
            return this.applicationBufferSize;
        }

        /* access modifiers changed from: package-private */
        public void tryExpandApplicationBufferSize(int packetLengthDataOnly) {
            if (packetLengthDataOnly > ReferenceCountedOpenSslEngine.MAX_PLAINTEXT_LENGTH && this.applicationBufferSize != ReferenceCountedOpenSslEngine.MAX_RECORD_SIZE) {
                this.applicationBufferSize = ReferenceCountedOpenSslEngine.MAX_RECORD_SIZE;
            }
        }
    }

    /* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslEngine$2  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol;
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ClientAuth;
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ReferenceCountedOpenSslEngine$HandshakeState;

        static {
            int[] iArr = new int[ApplicationProtocolConfig.Protocol.values().length];
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol = iArr;
            try {
                iArr[ApplicationProtocolConfig.Protocol.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[ApplicationProtocolConfig.Protocol.ALPN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[ApplicationProtocolConfig.Protocol.NPN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[ApplicationProtocolConfig.Protocol.NPN_AND_ALPN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            int[] iArr2 = new int[ClientAuth.values().length];
            $SwitchMap$io$netty$handler$ssl$ClientAuth = iArr2;
            try {
                iArr2[ClientAuth.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ClientAuth[ClientAuth.REQUIRE.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ClientAuth[ClientAuth.OPTIONAL.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
            int[] iArr3 = new int[HandshakeState.values().length];
            $SwitchMap$io$netty$handler$ssl$ReferenceCountedOpenSslEngine$HandshakeState = iArr3;
            try {
                iArr3[HandshakeState.NOT_STARTED.ordinal()] = 1;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ReferenceCountedOpenSslEngine$HandshakeState[HandshakeState.FINISHED.ordinal()] = 2;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ReferenceCountedOpenSslEngine$HandshakeState[HandshakeState.STARTED_IMPLICITLY.ordinal()] = 3;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ReferenceCountedOpenSslEngine$HandshakeState[HandshakeState.STARTED_EXPLICITLY.ordinal()] = 4;
            } catch (NoSuchFieldError e11) {
            }
        }
    }
}

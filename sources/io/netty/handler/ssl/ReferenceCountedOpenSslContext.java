package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.internal.tcnative.CertificateVerifier;
import io.netty.internal.tcnative.SSL;
import io.netty.internal.tcnative.SSLContext;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCounted;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetectorFactory;
import io.netty.util.ResourceLeakTracker;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.AccessController;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateRevokedException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

public abstract class ReferenceCountedOpenSslContext extends SslContext implements ReferenceCounted {
    private static final int DEFAULT_BIO_NON_APPLICATION_BUFFER_SIZE = ((Integer) AccessController.doPrivileged(new PrivilegedAction<Integer>() {
        public Integer run() {
            return Integer.valueOf(Math.max(1, SystemPropertyUtil.getInt("io.netty.handler.ssl.openssl.bioNonApplicationBufferSize", 2048)));
        }
    })).intValue();
    private static final Integer DH_KEY_LENGTH;
    static final OpenSslApplicationProtocolNegotiator NONE_PROTOCOL_NEGOTIATOR = new OpenSslApplicationProtocolNegotiator() {
        public ApplicationProtocolConfig.Protocol protocol() {
            return ApplicationProtocolConfig.Protocol.NONE;
        }

        public List<String> protocols() {
            return Collections.emptyList();
        }

        public ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior() {
            return ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
        }

        public ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
            return ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT;
        }
    };
    protected static final int VERIFY_DEPTH = 10;
    private static final ResourceLeakDetector<ReferenceCountedOpenSslContext> leakDetector;
    /* access modifiers changed from: private */
    public static final InternalLogger logger;
    private final OpenSslApplicationProtocolNegotiator apn;
    private volatile int bioNonApplicationBufferSize;
    final ClientAuth clientAuth;
    protected long ctx;
    final ReadWriteLock ctxLock;
    final boolean enableOcsp;
    final OpenSslEngineMap engineMap;
    final Certificate[] keyCertChain;
    /* access modifiers changed from: private */
    public final ResourceLeakTracker<ReferenceCountedOpenSslContext> leak;
    private final int mode;
    final String[] protocols;
    private final AbstractReferenceCounted refCnt;
    private final long sessionCacheSize;
    private final long sessionTimeout;
    private final List<String> unmodifiableCiphers;

    /* access modifiers changed from: package-private */
    public abstract OpenSslKeyMaterialManager keyMaterialManager();

    public abstract OpenSslSessionContext sessionContext();

    static {
        String dhKeySize;
        Class<ReferenceCountedOpenSslContext> cls = ReferenceCountedOpenSslContext.class;
        logger = InternalLoggerFactory.getInstance((Class<?>) cls);
        leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(cls);
        Integer dhLen = null;
        try {
            dhKeySize = (String) AccessController.doPrivileged(new PrivilegedAction<String>() {
                public String run() {
                    return SystemPropertyUtil.get("jdk.tls.ephemeralDHKeySize");
                }
            });
            if (dhKeySize != null) {
                dhLen = Integer.valueOf(dhKeySize);
            }
        } catch (NumberFormatException e) {
            InternalLogger internalLogger = logger;
            internalLogger.debug("ReferenceCountedOpenSslContext supports -Djdk.tls.ephemeralDHKeySize={int}, but got: " + dhKeySize);
        } catch (Throwable th) {
        }
        DH_KEY_LENGTH = dhLen;
    }

    ReferenceCountedOpenSslContext(Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apnCfg, long sessionCacheSize2, long sessionTimeout2, int mode2, Certificate[] keyCertChain2, ClientAuth clientAuth2, String[] protocols2, boolean startTls, boolean enableOcsp2, boolean leakDetection) {
        this(ciphers, cipherFilter, toNegotiator(apnCfg), sessionCacheSize2, sessionTimeout2, mode2, keyCertChain2, clientAuth2, protocols2, startTls, enableOcsp2, leakDetection);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: java.security.cert.Certificate[]} */
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01ba  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    ReferenceCountedOpenSslContext(java.lang.Iterable<java.lang.String> r18, io.netty.handler.ssl.CipherSuiteFilter r19, io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator r20, long r21, long r23, int r25, java.security.cert.Certificate[] r26, io.netty.handler.ssl.ClientAuth r27, java.lang.String[] r28, boolean r29, boolean r30, boolean r31) {
        /*
            r17 = this;
            r1 = r17
            r2 = r21
            r4 = r23
            r6 = r25
            r7 = r30
            r8 = r29
            r1.<init>(r8)
            io.netty.handler.ssl.ReferenceCountedOpenSslContext$2 r0 = new io.netty.handler.ssl.ReferenceCountedOpenSslContext$2
            r0.<init>()
            r1.refCnt = r0
            io.netty.handler.ssl.ReferenceCountedOpenSslContext$DefaultOpenSslEngineMap r0 = new io.netty.handler.ssl.ReferenceCountedOpenSslContext$DefaultOpenSslEngineMap
            r9 = 0
            r0.<init>()
            r1.engineMap = r0
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = new java.util.concurrent.locks.ReentrantReadWriteLock
            r0.<init>()
            r1.ctxLock = r0
            int r0 = DEFAULT_BIO_NON_APPLICATION_BUFFER_SIZE
            r1.bioNonApplicationBufferSize = r0
            io.netty.handler.ssl.OpenSsl.ensureAvailability()
            if (r7 == 0) goto L_0x003d
            boolean r0 = io.netty.handler.ssl.OpenSsl.isOcspSupported()
            if (r0 == 0) goto L_0x0035
            goto L_0x003d
        L_0x0035:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r9 = "OCSP is not supported."
            r0.<init>(r9)
            throw r0
        L_0x003d:
            r0 = 1
            if (r6 == r0) goto L_0x004b
            if (r6 != 0) goto L_0x0043
            goto L_0x004b
        L_0x0043:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r9 = "mode most be either SSL.SSL_MODE_SERVER or SSL.SSL_MODE_CLIENT"
            r0.<init>(r9)
            throw r0
        L_0x004b:
            if (r31 == 0) goto L_0x0054
            io.netty.util.ResourceLeakDetector<io.netty.handler.ssl.ReferenceCountedOpenSslContext> r0 = leakDetector
            io.netty.util.ResourceLeakTracker r0 = r0.track(r1)
            goto L_0x0055
        L_0x0054:
            r0 = r9
        L_0x0055:
            r1.leak = r0
            r1.mode = r6
            boolean r0 = r17.isServer()
            if (r0 == 0) goto L_0x006a
            java.lang.String r0 = "clientAuth"
            r10 = r27
            java.lang.Object r0 = io.netty.util.internal.ObjectUtil.checkNotNull(r10, r0)
            io.netty.handler.ssl.ClientAuth r0 = (io.netty.handler.ssl.ClientAuth) r0
            goto L_0x006e
        L_0x006a:
            r10 = r27
            io.netty.handler.ssl.ClientAuth r0 = io.netty.handler.ssl.ClientAuth.NONE
        L_0x006e:
            r1.clientAuth = r0
            r11 = r28
            r1.protocols = r11
            r1.enableOcsp = r7
            if (r26 != 0) goto L_0x0079
            goto L_0x0080
        L_0x0079:
            java.lang.Object r0 = r26.clone()
            r9 = r0
            java.security.cert.Certificate[] r9 = (java.security.cert.Certificate[]) r9
        L_0x0080:
            r1.keyCertChain = r9
            java.lang.String r0 = "cipherFilter"
            r9 = r19
            java.lang.Object r0 = io.netty.util.internal.ObjectUtil.checkNotNull(r9, r0)
            io.netty.handler.ssl.CipherSuiteFilter r0 = (io.netty.handler.ssl.CipherSuiteFilter) r0
            java.util.List<java.lang.String> r12 = io.netty.handler.ssl.OpenSsl.DEFAULT_CIPHERS
            java.util.Set r13 = io.netty.handler.ssl.OpenSsl.availableJavaCipherSuites()
            r14 = r18
            java.lang.String[] r0 = r0.filterCipherSuites(r14, r12, r13)
            java.util.List r0 = java.util.Arrays.asList(r0)
            r1.unmodifiableCiphers = r0
            java.lang.String r12 = "apn"
            r13 = r20
            java.lang.Object r12 = io.netty.util.internal.ObjectUtil.checkNotNull(r13, r12)
            io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator r12 = (io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator) r12
            r1.apn = r12
            r12 = 0
            r15 = 31
            long r8 = io.netty.internal.tcnative.SSLContext.make(r15, r6)     // Catch:{ Exception -> 0x01af }
            r1.ctx = r8     // Catch:{ Exception -> 0x01af }
            int r15 = io.netty.internal.tcnative.SSLContext.getOptions(r8)     // Catch:{ all -> 0x01ad }
            int r16 = io.netty.internal.tcnative.SSL.SSL_OP_NO_SSLv2     // Catch:{ all -> 0x01ad }
            r15 = r15 | r16
            int r16 = io.netty.internal.tcnative.SSL.SSL_OP_NO_SSLv3     // Catch:{ all -> 0x01ad }
            r15 = r15 | r16
            int r16 = io.netty.internal.tcnative.SSL.SSL_OP_CIPHER_SERVER_PREFERENCE     // Catch:{ all -> 0x01ad }
            r15 = r15 | r16
            int r16 = io.netty.internal.tcnative.SSL.SSL_OP_NO_COMPRESSION     // Catch:{ all -> 0x01ad }
            r15 = r15 | r16
            int r16 = io.netty.internal.tcnative.SSL.SSL_OP_NO_TICKET     // Catch:{ all -> 0x01ad }
            r15 = r15 | r16
            io.netty.internal.tcnative.SSLContext.setOptions(r8, r15)     // Catch:{ all -> 0x01ad }
            long r8 = r1.ctx     // Catch:{ all -> 0x01ad }
            int r15 = io.netty.internal.tcnative.SSLContext.getMode(r8)     // Catch:{ all -> 0x01ad }
            int r16 = io.netty.internal.tcnative.SSL.SSL_MODE_ACCEPT_MOVING_WRITE_BUFFER     // Catch:{ all -> 0x01ad }
            r15 = r15 | r16
            io.netty.internal.tcnative.SSLContext.setMode(r8, r15)     // Catch:{ all -> 0x01ad }
            java.lang.Integer r8 = DH_KEY_LENGTH     // Catch:{ all -> 0x01ad }
            if (r8 == 0) goto L_0x00e9
            long r9 = r1.ctx     // Catch:{ all -> 0x01ad }
            int r8 = r8.intValue()     // Catch:{ all -> 0x01ad }
            io.netty.internal.tcnative.SSLContext.setTmpDHLength(r9, r8)     // Catch:{ all -> 0x01ad }
        L_0x00e9:
            long r8 = r1.ctx     // Catch:{ SSLException -> 0x01aa, Exception -> 0x0190 }
            java.lang.String r0 = io.netty.handler.ssl.CipherSuiteConverter.toOpenSsl((java.lang.Iterable<java.lang.String>) r0)     // Catch:{ SSLException -> 0x01aa, Exception -> 0x0190 }
            io.netty.internal.tcnative.SSLContext.setCipherSuite(r8, r0)     // Catch:{ SSLException -> 0x01aa, Exception -> 0x0190 }
            java.util.List r0 = r20.protocols()     // Catch:{ all -> 0x01ad }
            boolean r8 = r0.isEmpty()     // Catch:{ all -> 0x01ad }
            if (r8 != 0) goto L_0x013e
            int r8 = r0.size()     // Catch:{ all -> 0x01ad }
            java.lang.String[] r8 = new java.lang.String[r8]     // Catch:{ all -> 0x01ad }
            java.lang.Object[] r8 = r0.toArray(r8)     // Catch:{ all -> 0x01ad }
            java.lang.String[] r8 = (java.lang.String[]) r8     // Catch:{ all -> 0x01ad }
            io.netty.handler.ssl.ApplicationProtocolConfig$SelectorFailureBehavior r9 = r20.selectorFailureBehavior()     // Catch:{ all -> 0x01ad }
            int r9 = opensslSelectorFailureBehavior(r9)     // Catch:{ all -> 0x01ad }
            int[] r10 = io.netty.handler.ssl.ReferenceCountedOpenSslContext.AnonymousClass5.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol     // Catch:{ all -> 0x01ad }
            io.netty.handler.ssl.ApplicationProtocolConfig$Protocol r15 = r20.protocol()     // Catch:{ all -> 0x01ad }
            int r15 = r15.ordinal()     // Catch:{ all -> 0x01ad }
            r10 = r10[r15]     // Catch:{ all -> 0x01ad }
            switch(r10) {
                case 1: goto L_0x0134;
                case 2: goto L_0x012e;
                case 3: goto L_0x0123;
                default: goto L_0x0120;
            }     // Catch:{ all -> 0x01ad }
        L_0x0120:
            java.lang.Error r10 = new java.lang.Error     // Catch:{ all -> 0x01ad }
            goto L_0x013a
        L_0x0123:
            long r10 = r1.ctx     // Catch:{ all -> 0x01ad }
            io.netty.internal.tcnative.SSLContext.setNpnProtos(r10, r8, r9)     // Catch:{ all -> 0x01ad }
            long r10 = r1.ctx     // Catch:{ all -> 0x01ad }
            io.netty.internal.tcnative.SSLContext.setAlpnProtos(r10, r8, r9)     // Catch:{ all -> 0x01ad }
            goto L_0x013e
        L_0x012e:
            long r10 = r1.ctx     // Catch:{ all -> 0x01ad }
            io.netty.internal.tcnative.SSLContext.setAlpnProtos(r10, r8, r9)     // Catch:{ all -> 0x01ad }
            goto L_0x013e
        L_0x0134:
            long r10 = r1.ctx     // Catch:{ all -> 0x01ad }
            io.netty.internal.tcnative.SSLContext.setNpnProtos(r10, r8, r9)     // Catch:{ all -> 0x01ad }
            goto L_0x013e
        L_0x013a:
            r10.<init>()     // Catch:{ all -> 0x01ad }
            throw r10     // Catch:{ all -> 0x01ad }
        L_0x013e:
            r8 = 0
            int r10 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r10 <= 0) goto L_0x014c
            r1.sessionCacheSize = r2     // Catch:{ all -> 0x01ad }
            long r10 = r1.ctx     // Catch:{ all -> 0x01ad }
            io.netty.internal.tcnative.SSLContext.setSessionCacheSize(r10, r2)     // Catch:{ all -> 0x01ad }
            goto L_0x015c
        L_0x014c:
            long r10 = r1.ctx     // Catch:{ all -> 0x01ad }
            r8 = 20480(0x5000, double:1.01185E-319)
            long r8 = io.netty.internal.tcnative.SSLContext.setSessionCacheSize(r10, r8)     // Catch:{ all -> 0x01ad }
            r2 = r8
            r1.sessionCacheSize = r8     // Catch:{ all -> 0x018e }
            long r8 = r1.ctx     // Catch:{ all -> 0x018e }
            io.netty.internal.tcnative.SSLContext.setSessionCacheSize(r8, r2)     // Catch:{ all -> 0x018e }
        L_0x015c:
            r8 = 0
            int r8 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r8 <= 0) goto L_0x016a
            r1.sessionTimeout = r4     // Catch:{ all -> 0x018e }
            long r8 = r1.ctx     // Catch:{ all -> 0x018e }
            io.netty.internal.tcnative.SSLContext.setSessionCacheTimeout(r8, r4)     // Catch:{ all -> 0x018e }
            goto L_0x017a
        L_0x016a:
            long r8 = r1.ctx     // Catch:{ all -> 0x018e }
            r10 = 300(0x12c, double:1.48E-321)
            long r8 = io.netty.internal.tcnative.SSLContext.setSessionCacheTimeout(r8, r10)     // Catch:{ all -> 0x018e }
            r4 = r8
            r1.sessionTimeout = r8     // Catch:{ all -> 0x018c }
            long r8 = r1.ctx     // Catch:{ all -> 0x018c }
            io.netty.internal.tcnative.SSLContext.setSessionCacheTimeout(r8, r4)     // Catch:{ all -> 0x018c }
        L_0x017a:
            if (r7 == 0) goto L_0x0185
            long r8 = r1.ctx     // Catch:{ all -> 0x018c }
            boolean r10 = r17.isClient()     // Catch:{ all -> 0x018c }
            io.netty.internal.tcnative.SSLContext.enableOcsp(r8, r10)     // Catch:{ all -> 0x018c }
        L_0x0185:
            r0 = 1
            if (r0 != 0) goto L_0x018b
            r17.release()
        L_0x018b:
            return
        L_0x018c:
            r0 = move-exception
            goto L_0x01b8
        L_0x018e:
            r0 = move-exception
            goto L_0x01b8
        L_0x0190:
            r0 = move-exception
            javax.net.ssl.SSLException r8 = new javax.net.ssl.SSLException     // Catch:{ all -> 0x01ad }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x01ad }
            r9.<init>()     // Catch:{ all -> 0x01ad }
            java.lang.String r10 = "failed to set cipher suite: "
            r9.append(r10)     // Catch:{ all -> 0x01ad }
            java.util.List<java.lang.String> r10 = r1.unmodifiableCiphers     // Catch:{ all -> 0x01ad }
            r9.append(r10)     // Catch:{ all -> 0x01ad }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x01ad }
            r8.<init>(r9, r0)     // Catch:{ all -> 0x01ad }
            throw r8     // Catch:{ all -> 0x01ad }
        L_0x01aa:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x01ad }
        L_0x01ad:
            r0 = move-exception
            goto L_0x01b8
        L_0x01af:
            r0 = move-exception
            javax.net.ssl.SSLException r8 = new javax.net.ssl.SSLException     // Catch:{ all -> 0x01ad }
            java.lang.String r9 = "failed to create an SSL_CTX"
            r8.<init>(r9, r0)     // Catch:{ all -> 0x01ad }
            throw r8     // Catch:{ all -> 0x01ad }
        L_0x01b8:
            if (r12 != 0) goto L_0x01bd
            r17.release()
        L_0x01bd:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.ReferenceCountedOpenSslContext.<init>(java.lang.Iterable, io.netty.handler.ssl.CipherSuiteFilter, io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator, long, long, int, java.security.cert.Certificate[], io.netty.handler.ssl.ClientAuth, java.lang.String[], boolean, boolean, boolean):void");
    }

    private static int opensslSelectorFailureBehavior(ApplicationProtocolConfig.SelectorFailureBehavior behavior) {
        switch (AnonymousClass5.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior[behavior.ordinal()]) {
            case 1:
                return 0;
            case 2:
                return 1;
            default:
                throw new Error();
        }
    }

    public final List<String> cipherSuites() {
        return this.unmodifiableCiphers;
    }

    public final long sessionCacheSize() {
        return this.sessionCacheSize;
    }

    public final long sessionTimeout() {
        return this.sessionTimeout;
    }

    public ApplicationProtocolNegotiator applicationProtocolNegotiator() {
        return this.apn;
    }

    public final boolean isClient() {
        return this.mode == 0;
    }

    public final SSLEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
        return newEngine0(alloc, peerHost, peerPort, true);
    }

    /* access modifiers changed from: protected */
    public final SslHandler newHandler(ByteBufAllocator alloc, boolean startTls) {
        return new SslHandler(newEngine0(alloc, (String) null, -1, false), startTls);
    }

    /* access modifiers changed from: protected */
    public final SslHandler newHandler(ByteBufAllocator alloc, String peerHost, int peerPort, boolean startTls) {
        return new SslHandler(newEngine0(alloc, peerHost, peerPort, false), startTls);
    }

    /* access modifiers changed from: package-private */
    public SSLEngine newEngine0(ByteBufAllocator alloc, String peerHost, int peerPort, boolean jdkCompatibilityMode) {
        return new ReferenceCountedOpenSslEngine(this, alloc, peerHost, peerPort, jdkCompatibilityMode, true);
    }

    public final SSLEngine newEngine(ByteBufAllocator alloc) {
        return newEngine(alloc, (String) null, -1);
    }

    @Deprecated
    public final long context() {
        Lock readerLock = this.ctxLock.readLock();
        readerLock.lock();
        try {
            return this.ctx;
        } finally {
            readerLock.unlock();
        }
    }

    @Deprecated
    public final OpenSslSessionStats stats() {
        return sessionContext().stats();
    }

    @Deprecated
    public void setRejectRemoteInitiatedRenegotiation(boolean rejectRemoteInitiatedRenegotiation) {
        if (!rejectRemoteInitiatedRenegotiation) {
            throw new UnsupportedOperationException("Renegotiation is not supported");
        }
    }

    @Deprecated
    public boolean getRejectRemoteInitiatedRenegotiation() {
        return true;
    }

    public void setBioNonApplicationBufferSize(int bioNonApplicationBufferSize2) {
        this.bioNonApplicationBufferSize = ObjectUtil.checkPositiveOrZero(bioNonApplicationBufferSize2, "bioNonApplicationBufferSize");
    }

    public int getBioNonApplicationBufferSize() {
        return this.bioNonApplicationBufferSize;
    }

    @Deprecated
    public final void setTicketKeys(byte[] keys) {
        sessionContext().setTicketKeys(keys);
    }

    @Deprecated
    public final long sslCtxPointer() {
        Lock readerLock = this.ctxLock.readLock();
        readerLock.lock();
        try {
            return this.ctx;
        } finally {
            readerLock.unlock();
        }
    }

    /* access modifiers changed from: private */
    public void destroy() {
        Lock writerLock = this.ctxLock.writeLock();
        writerLock.lock();
        try {
            long j = this.ctx;
            if (j != 0) {
                if (this.enableOcsp) {
                    SSLContext.disableOcsp(j);
                }
                SSLContext.free(this.ctx);
                this.ctx = 0;
            }
        } finally {
            writerLock.unlock();
        }
    }

    protected static X509Certificate[] certificates(byte[][] chain) {
        X509Certificate[] peerCerts = new X509Certificate[chain.length];
        for (int i = 0; i < peerCerts.length; i++) {
            peerCerts[i] = new OpenSslX509Certificate(chain[i]);
        }
        return peerCerts;
    }

    protected static X509TrustManager chooseTrustManager(TrustManager[] managers) {
        for (X509TrustManager x509TrustManager : managers) {
            if (x509TrustManager instanceof X509TrustManager) {
                return x509TrustManager;
            }
        }
        throw new IllegalStateException("no X509TrustManager found");
    }

    protected static X509KeyManager chooseX509KeyManager(KeyManager[] kms) {
        for (X509KeyManager x509KeyManager : kms) {
            if (x509KeyManager instanceof X509KeyManager) {
                return x509KeyManager;
            }
        }
        throw new IllegalStateException("no X509KeyManager found");
    }

    static OpenSslApplicationProtocolNegotiator toNegotiator(ApplicationProtocolConfig config) {
        if (config == null) {
            return NONE_PROTOCOL_NEGOTIATOR;
        }
        switch (AnonymousClass5.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[config.protocol().ordinal()]) {
            case 1:
            case 2:
            case 3:
                switch (AnonymousClass5.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior[config.selectedListenerFailureBehavior().ordinal()]) {
                    case 1:
                    case 2:
                        switch (AnonymousClass5.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior[config.selectorFailureBehavior().ordinal()]) {
                            case 1:
                            case 2:
                                return new OpenSslDefaultApplicationProtocolNegotiator(config);
                            default:
                                throw new UnsupportedOperationException("OpenSSL provider does not support " + config.selectorFailureBehavior() + " behavior");
                        }
                    default:
                        throw new UnsupportedOperationException("OpenSSL provider does not support " + config.selectedListenerFailureBehavior() + " behavior");
                }
            case 4:
                return NONE_PROTOCOL_NEGOTIATOR;
            default:
                throw new Error();
        }
    }

    /* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslContext$5  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol;
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior;
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior;

        static {
            int[] iArr = new int[ApplicationProtocolConfig.SelectedListenerFailureBehavior.values().length];
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior = iArr;
            try {
                iArr[ApplicationProtocolConfig.SelectedListenerFailureBehavior.CHOOSE_MY_LAST_PROTOCOL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior[ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            int[] iArr2 = new int[ApplicationProtocolConfig.SelectorFailureBehavior.values().length];
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior = iArr2;
            try {
                iArr2[ApplicationProtocolConfig.SelectorFailureBehavior.NO_ADVERTISE.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior[ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            int[] iArr3 = new int[ApplicationProtocolConfig.Protocol.values().length];
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol = iArr3;
            try {
                iArr3[ApplicationProtocolConfig.Protocol.NPN.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[ApplicationProtocolConfig.Protocol.ALPN.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[ApplicationProtocolConfig.Protocol.NPN_AND_ALPN.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[ApplicationProtocolConfig.Protocol.NONE.ordinal()] = 4;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    static boolean useExtendedTrustManager(X509TrustManager trustManager) {
        return PlatformDependent.javaVersion() >= 7 && (trustManager instanceof X509ExtendedTrustManager);
    }

    static boolean useExtendedKeyManager(X509KeyManager keyManager) {
        return PlatformDependent.javaVersion() >= 7 && (keyManager instanceof X509ExtendedKeyManager);
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

    public static abstract class AbstractCertificateVerifier extends CertificateVerifier {
        private final OpenSslEngineMap engineMap;

        /* access modifiers changed from: package-private */
        public abstract void verify(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, X509Certificate[] x509CertificateArr, String str);

        AbstractCertificateVerifier(OpenSslEngineMap engineMap2) {
            this.engineMap = engineMap2;
        }

        public final int verify(long ssl, byte[][] chain, String auth) {
            X509Certificate[] peerCerts = ReferenceCountedOpenSslContext.certificates(chain);
            ReferenceCountedOpenSslEngine engine = this.engineMap.get(ssl);
            try {
                verify(engine, peerCerts, auth);
                return CertificateVerifier.X509_V_OK;
            } catch (Throwable th) {
                ReferenceCountedOpenSslContext.logger.debug("verification of certificate failed", (Throwable) th);
                SSLHandshakeException e = new SSLHandshakeException("General OpenSslEngine problem");
                e.initCause(th);
                engine.handshakeException = e;
                if (th instanceof OpenSslCertificateException) {
                    return th.errorCode();
                }
                if (th instanceof CertificateExpiredException) {
                    return CertificateVerifier.X509_V_ERR_CERT_HAS_EXPIRED;
                }
                if (th instanceof CertificateNotYetValidException) {
                    return CertificateVerifier.X509_V_ERR_CERT_NOT_YET_VALID;
                }
                if (PlatformDependent.javaVersion() >= 7) {
                    if (th instanceof CertificateRevokedException) {
                        return CertificateVerifier.X509_V_ERR_CERT_REVOKED;
                    }
                    for (Throwable wrapped = th.getCause(); wrapped != null; wrapped = wrapped.getCause()) {
                        if (wrapped instanceof CertPathValidatorException) {
                            CertPathValidatorException.Reason reason = ((CertPathValidatorException) wrapped).getReason();
                            if (reason == CertPathValidatorException.BasicReason.EXPIRED) {
                                return CertificateVerifier.X509_V_ERR_CERT_HAS_EXPIRED;
                            }
                            if (reason == CertPathValidatorException.BasicReason.NOT_YET_VALID) {
                                return CertificateVerifier.X509_V_ERR_CERT_NOT_YET_VALID;
                            }
                            if (reason == CertPathValidatorException.BasicReason.REVOKED) {
                                return CertificateVerifier.X509_V_ERR_CERT_REVOKED;
                            }
                        }
                    }
                }
                return CertificateVerifier.X509_V_ERR_UNSPECIFIED;
            }
        }
    }

    public static final class DefaultOpenSslEngineMap implements OpenSslEngineMap {
        private final Map<Long, ReferenceCountedOpenSslEngine> engines;

        private DefaultOpenSslEngineMap() {
            this.engines = PlatformDependent.newConcurrentHashMap();
        }

        public ReferenceCountedOpenSslEngine remove(long ssl) {
            return this.engines.remove(Long.valueOf(ssl));
        }

        public void add(ReferenceCountedOpenSslEngine engine) {
            this.engines.put(Long.valueOf(engine.sslPointer()), engine);
        }

        public ReferenceCountedOpenSslEngine get(long ssl) {
            return this.engines.get(Long.valueOf(ssl));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x0092  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void setKeyMaterial(long r18, java.security.cert.X509Certificate[] r20, java.security.PrivateKey r21, java.lang.String r22) {
        /*
            r1 = 0
            r3 = 0
            r5 = 0
            r7 = 0
            io.netty.buffer.ByteBufAllocator r0 = io.netty.buffer.ByteBufAllocator.DEFAULT     // Catch:{ SSLException -> 0x007f, Exception -> 0x0072, all -> 0x006c }
            r8 = 1
            r9 = r20
            io.netty.handler.ssl.PemEncoded r10 = io.netty.handler.ssl.PemX509Certificate.toPEM(r0, r8, r9)     // Catch:{ SSLException -> 0x0068, Exception -> 0x0064, all -> 0x0060 }
            r7 = r10
            io.netty.handler.ssl.PemEncoded r10 = r7.retain()     // Catch:{ SSLException -> 0x0068, Exception -> 0x0064, all -> 0x0060 }
            long r13 = toBIO(r0, r10)     // Catch:{ SSLException -> 0x0068, Exception -> 0x0064, all -> 0x0060 }
            io.netty.handler.ssl.PemEncoded r3 = r7.retain()     // Catch:{ SSLException -> 0x005b, Exception -> 0x0056, all -> 0x0051 }
            long r3 = toBIO(r0, r3)     // Catch:{ SSLException -> 0x005b, Exception -> 0x0056, all -> 0x0051 }
            r5 = r3
            if (r21 == 0) goto L_0x0029
            long r3 = toBIO((java.security.PrivateKey) r21)     // Catch:{ SSLException -> 0x005b, Exception -> 0x0056, all -> 0x0051 }
            r1 = r3
        L_0x0029:
            if (r22 != 0) goto L_0x0030
            java.lang.String r0 = ""
            r17 = r0
            goto L_0x0032
        L_0x0030:
            r17 = r22
        L_0x0032:
            r11 = r18
            r15 = r1
            io.netty.internal.tcnative.SSLContext.setCertificateBio(r11, r13, r15, r17)     // Catch:{ SSLException -> 0x005b, Exception -> 0x0056, all -> 0x0051 }
            r10 = r18
            io.netty.internal.tcnative.SSLContext.setCertificateChainBio(r10, r5, r8)     // Catch:{ SSLException -> 0x004f, Exception -> 0x004d, all -> 0x004b }
            freeBio(r1)
            freeBio(r13)
            freeBio(r5)
            r7.release()
            return
        L_0x004b:
            r0 = move-exception
            goto L_0x0054
        L_0x004d:
            r0 = move-exception
            goto L_0x0059
        L_0x004f:
            r0 = move-exception
            goto L_0x005e
        L_0x0051:
            r0 = move-exception
            r10 = r18
        L_0x0054:
            r3 = r13
            goto L_0x0087
        L_0x0056:
            r0 = move-exception
            r10 = r18
        L_0x0059:
            r3 = r13
            goto L_0x0077
        L_0x005b:
            r0 = move-exception
            r10 = r18
        L_0x005e:
            r3 = r13
            goto L_0x0084
        L_0x0060:
            r0 = move-exception
            r10 = r18
            goto L_0x0087
        L_0x0064:
            r0 = move-exception
            r10 = r18
            goto L_0x0077
        L_0x0068:
            r0 = move-exception
            r10 = r18
            goto L_0x0084
        L_0x006c:
            r0 = move-exception
            r10 = r18
            r9 = r20
            goto L_0x0087
        L_0x0072:
            r0 = move-exception
            r10 = r18
            r9 = r20
        L_0x0077:
            javax.net.ssl.SSLException r8 = new javax.net.ssl.SSLException     // Catch:{ all -> 0x0086 }
            java.lang.String r12 = "failed to set certificate and key"
            r8.<init>(r12, r0)     // Catch:{ all -> 0x0086 }
            throw r8     // Catch:{ all -> 0x0086 }
        L_0x007f:
            r0 = move-exception
            r10 = r18
            r9 = r20
        L_0x0084:
            throw r0     // Catch:{ all -> 0x0086 }
        L_0x0086:
            r0 = move-exception
        L_0x0087:
            freeBio(r1)
            freeBio(r3)
            freeBio(r5)
            if (r7 == 0) goto L_0x0095
            r7.release()
        L_0x0095:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.ReferenceCountedOpenSslContext.setKeyMaterial(long, java.security.cert.X509Certificate[], java.security.PrivateKey, java.lang.String):void");
    }

    static void freeBio(long bio) {
        if (bio != 0) {
            SSL.freeBIO(bio);
        }
    }

    static long toBIO(PrivateKey key) {
        if (key == null) {
            return 0;
        }
        ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
        PemEncoded pem = PemPrivateKey.toPEM(allocator, true, key);
        try {
            return toBIO(allocator, pem.retain());
        } finally {
            pem.release();
        }
    }

    static long toBIO(X509Certificate... certChain) {
        if (certChain == null) {
            return 0;
        }
        if (certChain.length != 0) {
            ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
            PemEncoded pem = PemX509Certificate.toPEM(allocator, true, certChain);
            try {
                return toBIO(allocator, pem.retain());
            } finally {
                pem.release();
            }
        } else {
            throw new IllegalArgumentException("certChain can't be empty");
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=io.netty.handler.ssl.PemEncoded, code=io.netty.buffer.ByteBuf, for r6v0, types: [io.netty.handler.ssl.PemEncoded, io.netty.util.ReferenceCounted, io.netty.buffer.ByteBufHolder] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static long toBIO(io.netty.buffer.ByteBufAllocator r5, io.netty.buffer.ByteBuf r6) {
        /*
            io.netty.buffer.ByteBuf r0 = r6.content()     // Catch:{ all -> 0x0063 }
            boolean r1 = r0.isDirect()     // Catch:{ all -> 0x0063 }
            if (r1 == 0) goto L_0x001a
            io.netty.buffer.ByteBuf r1 = r0.slice()     // Catch:{ all -> 0x0063 }
            io.netty.buffer.ByteBuf r1 = r1.retain()     // Catch:{ all -> 0x0063 }
            long r1 = newBIO(r1)     // Catch:{ all -> 0x0063 }
            r6.release()
            return r1
        L_0x001a:
            int r1 = r0.readableBytes()     // Catch:{ all -> 0x0063 }
            io.netty.buffer.ByteBuf r1 = r5.directBuffer(r1)     // Catch:{ all -> 0x0063 }
            int r2 = r0.readerIndex()     // Catch:{ all -> 0x0050 }
            int r3 = r0.readableBytes()     // Catch:{ all -> 0x0050 }
            r1.writeBytes((io.netty.buffer.ByteBuf) r0, (int) r2, (int) r3)     // Catch:{ all -> 0x0050 }
            io.netty.buffer.ByteBuf r2 = r1.slice()     // Catch:{ all -> 0x0050 }
            io.netty.buffer.ByteBuf r2 = r2.retain()     // Catch:{ all -> 0x0050 }
            long r2 = newBIO(r2)     // Catch:{ all -> 0x0050 }
            boolean r4 = r6.isSensitive()     // Catch:{ all -> 0x004a }
            if (r4 == 0) goto L_0x0042
            io.netty.handler.ssl.SslUtils.zeroout(r1)     // Catch:{ all -> 0x004a }
        L_0x0042:
            r1.release()     // Catch:{ all -> 0x0063 }
            r6.release()
            return r2
        L_0x004a:
            r2 = move-exception
            r1.release()     // Catch:{ all -> 0x0063 }
        L_0x004e:
            throw r2     // Catch:{ all -> 0x0063 }
        L_0x0050:
            r2 = move-exception
            boolean r3 = r6.isSensitive()     // Catch:{ all -> 0x005e }
            if (r3 == 0) goto L_0x005a
            io.netty.handler.ssl.SslUtils.zeroout(r1)     // Catch:{ all -> 0x005e }
        L_0x005a:
            r1.release()     // Catch:{ all -> 0x0063 }
            goto L_0x004e
        L_0x005e:
            r2 = move-exception
            r1.release()     // Catch:{ all -> 0x0063 }
            goto L_0x004e
        L_0x0063:
            r0 = move-exception
            r6.release()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.ReferenceCountedOpenSslContext.toBIO(io.netty.buffer.ByteBufAllocator, io.netty.handler.ssl.PemEncoded):long");
    }

    private static long newBIO(ByteBuf buffer) {
        try {
            long bio = SSL.newMemBIO();
            int readable = buffer.readableBytes();
            if (SSL.bioWrite(bio, OpenSsl.memoryAddress(buffer) + ((long) buffer.readerIndex()), readable) == readable) {
                return bio;
            }
            SSL.freeBIO(bio);
            throw new IllegalStateException("Could not write data to memory BIO");
        } finally {
            buffer.release();
        }
    }
}

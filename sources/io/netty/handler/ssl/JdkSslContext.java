package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManager;

public class JdkSslContext extends SslContext {
    private static final List<String> DEFAULT_CIPHERS;
    private static final String[] DEFAULT_PROTOCOLS;
    static final String PROTOCOL = "TLS";
    private static final Set<String> SUPPORTED_CIPHERS;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) JdkSslContext.class);
    private final JdkApplicationProtocolNegotiator apn;
    private final String[] cipherSuites;
    private final ClientAuth clientAuth;
    private final boolean isClient;
    private final String[] protocols;
    private final SSLContext sslContext;
    private final List<String> unmodifiableCipherSuites;

    static {
        try {
            SSLContext context = SSLContext.getInstance(PROTOCOL);
            context.init((KeyManager[]) null, (TrustManager[]) null, (SecureRandom) null);
            SSLEngine engine = context.createSSLEngine();
            String[] supportedProtocols = engine.getSupportedProtocols();
            Set<String> supportedProtocolsSet = new HashSet<>(supportedProtocols.length);
            for (String add : supportedProtocols) {
                supportedProtocolsSet.add(add);
            }
            List<String> protocols2 = new ArrayList<>();
            SslUtils.addIfSupported(supportedProtocolsSet, protocols2, "TLSv1.2", "TLSv1.1", "TLSv1");
            if (!protocols2.isEmpty()) {
                DEFAULT_PROTOCOLS = (String[]) protocols2.toArray(new String[protocols2.size()]);
            } else {
                DEFAULT_PROTOCOLS = engine.getEnabledProtocols();
            }
            String[] supportedCiphers = engine.getSupportedCipherSuites();
            SUPPORTED_CIPHERS = new HashSet(supportedCiphers.length);
            for (String supportedCipher : supportedCiphers) {
                Set<String> set = SUPPORTED_CIPHERS;
                set.add(supportedCipher);
                if (supportedCipher.startsWith("SSL_")) {
                    String tlsPrefixedCipherName = "TLS_" + supportedCipher.substring("SSL_".length());
                    try {
                        engine.setEnabledCipherSuites(new String[]{tlsPrefixedCipherName});
                        set.add(tlsPrefixedCipherName);
                    } catch (IllegalArgumentException e) {
                    }
                }
            }
            List<String> ciphers = new ArrayList<>();
            SslUtils.addIfSupported(SUPPORTED_CIPHERS, ciphers, SslUtils.DEFAULT_CIPHER_SUITES);
            SslUtils.useFallbackCiphersIfDefaultIsEmpty(ciphers, engine.getEnabledCipherSuites());
            List<T> unmodifiableList = Collections.unmodifiableList(ciphers);
            DEFAULT_CIPHERS = unmodifiableList;
            InternalLogger internalLogger = logger;
            if (internalLogger.isDebugEnabled()) {
                internalLogger.debug("Default protocols (JDK): {} ", (Object) Arrays.asList(DEFAULT_PROTOCOLS));
                internalLogger.debug("Default cipher suites (JDK): {}", (Object) unmodifiableList);
            }
        } catch (Exception e2) {
            throw new Error("failed to initialize the default SSL context", e2);
        }
    }

    public JdkSslContext(SSLContext sslContext2, boolean isClient2, ClientAuth clientAuth2) {
        this(sslContext2, isClient2, (Iterable<String>) null, IdentityCipherSuiteFilter.INSTANCE, JdkDefaultApplicationProtocolNegotiator.INSTANCE, clientAuth2, (String[]) null, false);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public JdkSslContext(SSLContext sslContext2, boolean isClient2, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn2, ClientAuth clientAuth2) {
        this(sslContext2, isClient2, ciphers, cipherFilter, toNegotiator(apn2, !isClient2), clientAuth2, (String[]) null, false);
        ApplicationProtocolConfig applicationProtocolConfig = apn2;
    }

    JdkSslContext(SSLContext sslContext2, boolean isClient2, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, JdkApplicationProtocolNegotiator apn2, ClientAuth clientAuth2, String[] protocols2, boolean startTls) {
        super(startTls);
        this.apn = (JdkApplicationProtocolNegotiator) ObjectUtil.checkNotNull(apn2, "apn");
        this.clientAuth = (ClientAuth) ObjectUtil.checkNotNull(clientAuth2, "clientAuth");
        String[] filterCipherSuites = ((CipherSuiteFilter) ObjectUtil.checkNotNull(cipherFilter, "cipherFilter")).filterCipherSuites(ciphers, DEFAULT_CIPHERS, SUPPORTED_CIPHERS);
        this.cipherSuites = filterCipherSuites;
        this.protocols = protocols2 == null ? DEFAULT_PROTOCOLS : protocols2;
        this.unmodifiableCipherSuites = Collections.unmodifiableList(Arrays.asList(filterCipherSuites));
        this.sslContext = (SSLContext) ObjectUtil.checkNotNull(sslContext2, "sslContext");
        this.isClient = isClient2;
    }

    public final SSLContext context() {
        return this.sslContext;
    }

    public final boolean isClient() {
        return this.isClient;
    }

    public final SSLSessionContext sessionContext() {
        if (isServer()) {
            return context().getServerSessionContext();
        }
        return context().getClientSessionContext();
    }

    public final List<String> cipherSuites() {
        return this.unmodifiableCipherSuites;
    }

    public final long sessionCacheSize() {
        return (long) sessionContext().getSessionCacheSize();
    }

    public final long sessionTimeout() {
        return (long) sessionContext().getSessionTimeout();
    }

    public final SSLEngine newEngine(ByteBufAllocator alloc) {
        return configureAndWrapEngine(context().createSSLEngine(), alloc);
    }

    public final SSLEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
        return configureAndWrapEngine(context().createSSLEngine(peerHost, peerPort), alloc);
    }

    private SSLEngine configureAndWrapEngine(SSLEngine engine, ByteBufAllocator alloc) {
        engine.setEnabledCipherSuites(this.cipherSuites);
        engine.setEnabledProtocols(this.protocols);
        engine.setUseClientMode(isClient());
        if (isServer()) {
            switch (AnonymousClass1.$SwitchMap$io$netty$handler$ssl$ClientAuth[this.clientAuth.ordinal()]) {
                case 1:
                    engine.setWantClientAuth(true);
                    break;
                case 2:
                    engine.setNeedClientAuth(true);
                    break;
                case 3:
                    break;
                default:
                    throw new Error("Unknown auth " + this.clientAuth);
            }
        }
        JdkApplicationProtocolNegotiator.SslEngineWrapperFactory factory = this.apn.wrapperFactory();
        if (factory instanceof JdkApplicationProtocolNegotiator.AllocatorAwareSslEngineWrapperFactory) {
            return ((JdkApplicationProtocolNegotiator.AllocatorAwareSslEngineWrapperFactory) factory).wrapSslEngine(engine, alloc, this.apn, isServer());
        }
        return factory.wrapSslEngine(engine, this.apn, isServer());
    }

    public final JdkApplicationProtocolNegotiator applicationProtocolNegotiator() {
        return this.apn;
    }

    static JdkApplicationProtocolNegotiator toNegotiator(ApplicationProtocolConfig config, boolean isServer) {
        if (config == null) {
            return JdkDefaultApplicationProtocolNegotiator.INSTANCE;
        }
        switch (AnonymousClass1.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[config.protocol().ordinal()]) {
            case 1:
                return JdkDefaultApplicationProtocolNegotiator.INSTANCE;
            case 2:
                if (isServer) {
                    switch (AnonymousClass1.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior[config.selectorFailureBehavior().ordinal()]) {
                        case 1:
                            return new JdkAlpnApplicationProtocolNegotiator(true, (Iterable<String>) config.supportedProtocols());
                        case 2:
                            return new JdkAlpnApplicationProtocolNegotiator(false, (Iterable<String>) config.supportedProtocols());
                        default:
                            throw new UnsupportedOperationException("JDK provider does not support " + config.selectorFailureBehavior() + " failure behavior");
                    }
                } else {
                    switch (AnonymousClass1.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior[config.selectedListenerFailureBehavior().ordinal()]) {
                        case 1:
                            return new JdkAlpnApplicationProtocolNegotiator(false, (Iterable<String>) config.supportedProtocols());
                        case 2:
                            return new JdkAlpnApplicationProtocolNegotiator(true, (Iterable<String>) config.supportedProtocols());
                        default:
                            throw new UnsupportedOperationException("JDK provider does not support " + config.selectedListenerFailureBehavior() + " failure behavior");
                    }
                }
            case 3:
                if (isServer) {
                    switch (AnonymousClass1.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior[config.selectedListenerFailureBehavior().ordinal()]) {
                        case 1:
                            return new JdkNpnApplicationProtocolNegotiator(false, (Iterable<String>) config.supportedProtocols());
                        case 2:
                            return new JdkNpnApplicationProtocolNegotiator(true, (Iterable<String>) config.supportedProtocols());
                        default:
                            throw new UnsupportedOperationException("JDK provider does not support " + config.selectedListenerFailureBehavior() + " failure behavior");
                    }
                } else {
                    switch (AnonymousClass1.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior[config.selectorFailureBehavior().ordinal()]) {
                        case 1:
                            return new JdkNpnApplicationProtocolNegotiator(true, (Iterable<String>) config.supportedProtocols());
                        case 2:
                            return new JdkNpnApplicationProtocolNegotiator(false, (Iterable<String>) config.supportedProtocols());
                        default:
                            throw new UnsupportedOperationException("JDK provider does not support " + config.selectorFailureBehavior() + " failure behavior");
                    }
                }
            default:
                throw new UnsupportedOperationException("JDK provider does not support " + config.protocol() + " protocol");
        }
    }

    /* renamed from: io.netty.handler.ssl.JdkSslContext$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol;
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior;
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior;
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ClientAuth;

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
            int[] iArr2 = new int[ApplicationProtocolConfig.SelectedListenerFailureBehavior.values().length];
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior = iArr2;
            try {
                iArr2[ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior[ApplicationProtocolConfig.SelectedListenerFailureBehavior.FATAL_ALERT.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            int[] iArr3 = new int[ApplicationProtocolConfig.SelectorFailureBehavior.values().length];
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior = iArr3;
            try {
                iArr3[ApplicationProtocolConfig.SelectorFailureBehavior.FATAL_ALERT.ordinal()] = 1;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior[ApplicationProtocolConfig.SelectorFailureBehavior.NO_ADVERTISE.ordinal()] = 2;
            } catch (NoSuchFieldError e7) {
            }
            int[] iArr4 = new int[ClientAuth.values().length];
            $SwitchMap$io$netty$handler$ssl$ClientAuth = iArr4;
            try {
                iArr4[ClientAuth.OPTIONAL.ordinal()] = 1;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ClientAuth[ClientAuth.REQUIRE.ordinal()] = 2;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$ClientAuth[ClientAuth.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    @Deprecated
    protected static KeyManagerFactory buildKeyManagerFactory(File certChainFile, File keyFile, String keyPassword, KeyManagerFactory kmf) {
        String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
        if (algorithm == null) {
            algorithm = "SunX509";
        }
        return buildKeyManagerFactory(certChainFile, algorithm, keyFile, keyPassword, kmf);
    }

    @Deprecated
    protected static KeyManagerFactory buildKeyManagerFactory(File certChainFile, String keyAlgorithm, File keyFile, String keyPassword, KeyManagerFactory kmf) {
        return SslContext.buildKeyManagerFactory(SslContext.toX509Certificates(certChainFile), keyAlgorithm, SslContext.toPrivateKey(keyFile, keyPassword), keyPassword, kmf);
    }
}

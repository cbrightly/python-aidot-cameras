package io.netty.handler.ssl;

import io.netty.handler.ssl.ReferenceCountedOpenSslContext;
import io.netty.internal.tcnative.SSLContext;
import io.netty.internal.tcnative.SniHostNameMatcher;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

public final class ReferenceCountedOpenSslServerContext extends ReferenceCountedOpenSslContext {
    private static final byte[] ID = {110, 101, 116, 116, 121};
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) ReferenceCountedOpenSslServerContext.class);
    private final OpenSslKeyMaterialManager keyMaterialManager;
    private final OpenSslServerSessionContext sessionContext;

    ReferenceCountedOpenSslServerContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout, ClientAuth clientAuth, String[] protocols, boolean startTls, boolean enableOcsp) {
        this(trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, ReferenceCountedOpenSslContext.toNegotiator(apn), sessionCacheSize, sessionTimeout, clientAuth, protocols, startTls, enableOcsp);
    }

    private ReferenceCountedOpenSslServerContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, OpenSslApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout, ClientAuth clientAuth, String[] protocols, boolean startTls, boolean enableOcsp) {
        super(ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, 1, (Certificate[]) keyCertChain, clientAuth, protocols, startTls, enableOcsp, true);
        boolean success = false;
        try {
            ServerContext context = newSessionContext(this, this.ctx, this.engineMap, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory);
            this.sessionContext = context.sessionContext;
            this.keyMaterialManager = context.keyMaterialManager;
            success = true;
        } finally {
            if (!success) {
                release();
            }
        }
    }

    public OpenSslServerSessionContext sessionContext() {
        return this.sessionContext;
    }

    /* access modifiers changed from: package-private */
    public OpenSslKeyMaterialManager keyMaterialManager() {
        return this.keyMaterialManager;
    }

    public static final class ServerContext {
        OpenSslKeyMaterialManager keyMaterialManager;
        OpenSslServerSessionContext sessionContext;

        ServerContext() {
        }
    }

    static ServerContext newSessionContext(ReferenceCountedOpenSslContext thiz, long ctx, OpenSslEngineMap engineMap, X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory) {
        TrustManagerFactory trustManagerFactory2;
        long bio;
        KeyManagerFactory keyManagerFactory2;
        long j = ctx;
        OpenSslEngineMap openSslEngineMap = engineMap;
        X509Certificate[] x509CertificateArr = keyCertChain;
        String str = keyPassword;
        ServerContext result = new ServerContext();
        try {
            SSLContext.setVerify(j, 0, 10);
            if (OpenSsl.useKeyManagerFactory()) {
                PrivateKey privateKey = key;
                if (keyManagerFactory == null) {
                    keyManagerFactory2 = SslContext.buildKeyManagerFactory(keyCertChain, key, keyPassword, keyManagerFactory);
                } else {
                    keyManagerFactory2 = keyManagerFactory;
                }
                try {
                    X509KeyManager keyManager = ReferenceCountedOpenSslContext.chooseX509KeyManager(keyManagerFactory2.getKeyManagers());
                    result.keyMaterialManager = ReferenceCountedOpenSslContext.useExtendedKeyManager(keyManager) ? new OpenSslExtendedKeyMaterialManager((X509ExtendedKeyManager) keyManager, str) : new OpenSslKeyMaterialManager(keyManager, str);
                } catch (Exception e) {
                    e = e;
                    ReferenceCountedOpenSslContext referenceCountedOpenSslContext = thiz;
                    throw new SSLException("failed to set certificate and key", e);
                }
            } else if (keyManagerFactory == null) {
                try {
                    ObjectUtil.checkNotNull(x509CertificateArr, "keyCertChain");
                } catch (Exception e2) {
                    e = e2;
                    PrivateKey privateKey2 = key;
                    ReferenceCountedOpenSslContext referenceCountedOpenSslContext2 = thiz;
                    KeyManagerFactory keyManagerFactory3 = keyManagerFactory;
                    throw new SSLException("failed to set certificate and key", e);
                }
                try {
                    ReferenceCountedOpenSslContext.setKeyMaterial(j, x509CertificateArr, key, str);
                    KeyManagerFactory keyManagerFactory4 = keyManagerFactory;
                } catch (Exception e3) {
                    e = e3;
                    ReferenceCountedOpenSslContext referenceCountedOpenSslContext22 = thiz;
                    KeyManagerFactory keyManagerFactory32 = keyManagerFactory;
                    throw new SSLException("failed to set certificate and key", e);
                }
            } else {
                PrivateKey privateKey3 = key;
                throw new IllegalArgumentException("KeyManagerFactory not supported");
            }
            if (trustCertCollection != null) {
                try {
                    trustManagerFactory2 = SslContext.buildTrustManagerFactory(trustCertCollection, trustManagerFactory);
                } catch (SSLException e4) {
                    e = e4;
                    ReferenceCountedOpenSslContext referenceCountedOpenSslContext3 = thiz;
                    TrustManagerFactory trustManagerFactory3 = trustManagerFactory;
                    throw e;
                } catch (Exception e5) {
                    e = e5;
                    ReferenceCountedOpenSslContext referenceCountedOpenSslContext4 = thiz;
                    TrustManagerFactory trustManagerFactory4 = trustManagerFactory;
                    throw new SSLException("unable to setup trustmanager", e);
                }
            } else if (trustManagerFactory == null) {
                trustManagerFactory2 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                try {
                    trustManagerFactory2.init((KeyStore) null);
                } catch (SSLException e6) {
                    e = e6;
                    ReferenceCountedOpenSslContext referenceCountedOpenSslContext5 = thiz;
                } catch (Exception e7) {
                    e = e7;
                    ReferenceCountedOpenSslContext referenceCountedOpenSslContext6 = thiz;
                    throw new SSLException("unable to setup trustmanager", e);
                } catch (Throwable th) {
                    ReferenceCountedOpenSslContext.freeBio(bio);
                    throw th;
                }
            } else {
                trustManagerFactory2 = trustManagerFactory;
            }
            X509TrustManager manager = ReferenceCountedOpenSslContext.chooseTrustManager(trustManagerFactory2.getTrustManagers());
            if (ReferenceCountedOpenSslContext.useExtendedTrustManager(manager)) {
                SSLContext.setCertVerifyCallback(j, new ExtendedTrustManagerVerifyCallback(openSslEngineMap, (X509ExtendedTrustManager) manager));
            } else {
                SSLContext.setCertVerifyCallback(j, new TrustManagerVerifyCallback(openSslEngineMap, manager));
            }
            X509Certificate[] issuers = manager.getAcceptedIssuers();
            if (issuers != null && issuers.length > 0) {
                bio = 0;
                bio = ReferenceCountedOpenSslContext.toBIO(issuers);
                if (SSLContext.setCACertificateBio(j, bio)) {
                    ReferenceCountedOpenSslContext.freeBio(bio);
                } else {
                    throw new SSLException("unable to setup accepted issuers for trustmanager " + manager);
                }
            }
            if (PlatformDependent.javaVersion() >= 8) {
                SSLContext.setSniHostnameMatcher(j, new OpenSslSniHostnameMatcher(openSslEngineMap));
            }
            OpenSslServerSessionContext openSslServerSessionContext = new OpenSslServerSessionContext(thiz);
            result.sessionContext = openSslServerSessionContext;
            openSslServerSessionContext.setSessionIdContext(ID);
            return result;
        } catch (Exception e8) {
            e = e8;
            ReferenceCountedOpenSslContext referenceCountedOpenSslContext7 = thiz;
            PrivateKey privateKey4 = key;
            KeyManagerFactory keyManagerFactory322 = keyManagerFactory;
            throw new SSLException("failed to set certificate and key", e);
        }
    }

    public static final class TrustManagerVerifyCallback extends ReferenceCountedOpenSslContext.AbstractCertificateVerifier {
        private final X509TrustManager manager;

        TrustManagerVerifyCallback(OpenSslEngineMap engineMap, X509TrustManager manager2) {
            super(engineMap);
            this.manager = manager2;
        }

        /* access modifiers changed from: package-private */
        public void verify(ReferenceCountedOpenSslEngine engine, X509Certificate[] peerCerts, String auth) {
            this.manager.checkClientTrusted(peerCerts, auth);
        }
    }

    public static final class ExtendedTrustManagerVerifyCallback extends ReferenceCountedOpenSslContext.AbstractCertificateVerifier {
        private final X509ExtendedTrustManager manager;

        ExtendedTrustManagerVerifyCallback(OpenSslEngineMap engineMap, X509ExtendedTrustManager manager2) {
            super(engineMap);
            this.manager = manager2;
        }

        /* access modifiers changed from: package-private */
        public void verify(ReferenceCountedOpenSslEngine engine, X509Certificate[] peerCerts, String auth) {
            this.manager.checkClientTrusted(peerCerts, auth, engine);
        }
    }

    public static final class OpenSslSniHostnameMatcher implements SniHostNameMatcher {
        private final OpenSslEngineMap engineMap;

        OpenSslSniHostnameMatcher(OpenSslEngineMap engineMap2) {
            this.engineMap = engineMap2;
        }

        public boolean match(long ssl, String hostname) {
            ReferenceCountedOpenSslEngine engine = this.engineMap.get(ssl);
            if (engine != null) {
                return engine.checkSniHostnameMatch(hostname);
            }
            ReferenceCountedOpenSslServerContext.logger.warn("No ReferenceCountedOpenSslEngine found for SSL pointer: {}", (Object) Long.valueOf(ssl));
            return false;
        }
    }
}

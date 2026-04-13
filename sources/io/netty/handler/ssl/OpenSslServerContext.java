package io.netty.handler.ssl;

import io.netty.handler.ssl.ReferenceCountedOpenSslServerContext;
import java.io.File;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

public final class OpenSslServerContext extends OpenSslContext {
    private final OpenSslKeyMaterialManager keyMaterialManager;
    private final OpenSslServerSessionContext sessionContext;

    @Deprecated
    public OpenSslServerContext(File certChainFile, File keyFile) {
        this(certChainFile, keyFile, (String) null);
    }

    @Deprecated
    public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword) {
        this(certChainFile, keyFile, keyPassword, (Iterable<String>) null, (CipherSuiteFilter) IdentityCipherSuiteFilter.INSTANCE, ApplicationProtocolConfig.DISABLED, 0, 0);
    }

    @Deprecated
    public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) {
        this(certChainFile, keyFile, keyPassword, ciphers, (CipherSuiteFilter) IdentityCipherSuiteFilter.INSTANCE, apn, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) {
        this(certChainFile, keyFile, keyPassword, ciphers, SslContext.toApplicationProtocolConfig(nextProtocols), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, ApplicationProtocolConfig config, long sessionCacheSize, long sessionTimeout) {
        this(certChainFile, keyFile, keyPassword, trustManagerFactory, ciphers, ReferenceCountedOpenSslContext.toNegotiator(config), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, OpenSslApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) {
        this((File) null, trustManagerFactory, certChainFile, keyFile, keyPassword, (KeyManagerFactory) null, ciphers, (CipherSuiteFilter) null, apn, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) {
        this((File) null, (TrustManagerFactory) null, certChainFile, keyFile, keyPassword, (KeyManagerFactory) null, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public OpenSslServerContext(File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig config, long sessionCacheSize, long sessionTimeout) {
        this(trustCertCollectionFile, trustManagerFactory, keyCertChainFile, keyFile, keyPassword, keyManagerFactory, ciphers, cipherFilter, ReferenceCountedOpenSslContext.toNegotiator(config), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig config, long sessionCacheSize, long sessionTimeout) {
        this((File) null, trustManagerFactory, certChainFile, keyFile, keyPassword, (KeyManagerFactory) null, ciphers, cipherFilter, ReferenceCountedOpenSslContext.toNegotiator(config), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, OpenSslApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) {
        this((File) null, trustManagerFactory, certChainFile, keyFile, keyPassword, (KeyManagerFactory) null, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public OpenSslServerContext(File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, OpenSslApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) {
        this(SslContext.toX509CertificatesInternal(trustCertCollectionFile), trustManagerFactory, SslContext.toX509CertificatesInternal(keyCertChainFile), SslContext.toPrivateKeyInternal(keyFile, keyPassword), keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, ClientAuth.NONE, (String[]) null, false, false);
    }

    OpenSslServerContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout, ClientAuth clientAuth, String[] protocols, boolean startTls, boolean enableOcsp) {
        this(trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, ReferenceCountedOpenSslContext.toNegotiator(apn), sessionCacheSize, sessionTimeout, clientAuth, protocols, startTls, enableOcsp);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private OpenSslServerContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, OpenSslApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout, ClientAuth clientAuth, String[] protocols, boolean startTls, boolean enableOcsp) {
        super(ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, 1, (Certificate[]) keyCertChain, clientAuth, protocols, startTls, enableOcsp);
        boolean success = false;
        try {
            ReferenceCountedOpenSslServerContext.ServerContext context = ReferenceCountedOpenSslServerContext.newSessionContext(this, this.ctx, this.engineMap, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory);
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
}

package io.netty.handler.ssl;

import java.io.File;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

public final class OpenSslClientContext extends OpenSslContext {
    private final OpenSslSessionContext sessionContext;

    @Deprecated
    public OpenSslClientContext() {
        this((File) null, (TrustManagerFactory) null, (File) null, (File) null, (String) null, (KeyManagerFactory) null, (Iterable<String>) null, IdentityCipherSuiteFilter.INSTANCE, (ApplicationProtocolConfig) null, 0, 0);
    }

    @Deprecated
    public OpenSslClientContext(File certChainFile) {
        this(certChainFile, (TrustManagerFactory) null);
    }

    @Deprecated
    public OpenSslClientContext(TrustManagerFactory trustManagerFactory) {
        this((File) null, trustManagerFactory);
    }

    @Deprecated
    public OpenSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory) {
        this(certChainFile, trustManagerFactory, (File) null, (File) null, (String) null, (KeyManagerFactory) null, (Iterable<String>) null, IdentityCipherSuiteFilter.INSTANCE, (ApplicationProtocolConfig) null, 0, 0);
    }

    @Deprecated
    public OpenSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) {
        this(certChainFile, trustManagerFactory, (File) null, (File) null, (String) null, (KeyManagerFactory) null, ciphers, IdentityCipherSuiteFilter.INSTANCE, apn, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public OpenSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) {
        this(certChainFile, trustManagerFactory, (File) null, (File) null, (String) null, (KeyManagerFactory) null, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public OpenSslClientContext(File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) {
        this(SslContext.toX509CertificatesInternal(trustCertCollectionFile), trustManagerFactory, SslContext.toX509CertificatesInternal(keyCertChainFile), SslContext.toPrivateKeyInternal(keyFile, keyPassword), keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, (String[]) null, sessionCacheSize, sessionTimeout, false);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    OpenSslClientContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, String[] protocols, long sessionCacheSize, long sessionTimeout, boolean enableOcsp) {
        super(ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, 0, (Certificate[]) keyCertChain, ClientAuth.NONE, protocols, false, enableOcsp);
        boolean success = false;
        try {
            this.sessionContext = ReferenceCountedOpenSslClientContext.newSessionContext(this, this.ctx, this.engineMap, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory);
            success = true;
        } finally {
            if (!success) {
                release();
            }
        }
    }

    public OpenSslSessionContext sessionContext() {
        return this.sessionContext;
    }

    /* access modifiers changed from: package-private */
    public OpenSslKeyMaterialManager keyMaterialManager() {
        return null;
    }
}

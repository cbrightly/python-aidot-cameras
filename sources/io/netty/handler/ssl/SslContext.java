package io.netty.handler.ssl;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.util.internal.EmptyArrays;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManagerFactory;

public abstract class SslContext {
    static final CertificateFactory X509_CERT_FACTORY;
    private final boolean startTls;

    public abstract ApplicationProtocolNegotiator applicationProtocolNegotiator();

    public abstract List<String> cipherSuites();

    public abstract boolean isClient();

    public abstract SSLEngine newEngine(ByteBufAllocator byteBufAllocator);

    public abstract SSLEngine newEngine(ByteBufAllocator byteBufAllocator, String str, int i);

    public abstract long sessionCacheSize();

    public abstract SSLSessionContext sessionContext();

    public abstract long sessionTimeout();

    static {
        try {
            X509_CERT_FACTORY = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            throw new IllegalStateException("unable to instance X.509 CertificateFactory", e);
        }
    }

    public static SslProvider defaultServerProvider() {
        return defaultProvider();
    }

    public static SslProvider defaultClientProvider() {
        return defaultProvider();
    }

    private static SslProvider defaultProvider() {
        if (OpenSsl.isAvailable()) {
            return SslProvider.OPENSSL;
        }
        return SslProvider.JDK;
    }

    @Deprecated
    public static SslContext newServerContext(File certChainFile, File keyFile) {
        return newServerContext(certChainFile, keyFile, (String) null);
    }

    @Deprecated
    public static SslContext newServerContext(File certChainFile, File keyFile, String keyPassword) {
        return newServerContext((SslProvider) null, certChainFile, keyFile, keyPassword);
    }

    @Deprecated
    public static SslContext newServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) {
        return newServerContext((SslProvider) null, certChainFile, keyFile, keyPassword, ciphers, nextProtocols, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) {
        return newServerContext((SslProvider) null, certChainFile, keyFile, keyPassword, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile) {
        return newServerContext(provider, certChainFile, keyFile, (String) null);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile, String keyPassword) {
        return newServerContext(provider, certChainFile, keyFile, keyPassword, (Iterable<String>) null, (CipherSuiteFilter) IdentityCipherSuiteFilter.INSTANCE, (ApplicationProtocolConfig) null, 0, 0);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) {
        return newServerContext(provider, certChainFile, keyFile, keyPassword, ciphers, (CipherSuiteFilter) IdentityCipherSuiteFilter.INSTANCE, toApplicationProtocolConfig(nextProtocols), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile, String keyPassword, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) {
        return newServerContext(provider, (File) null, trustManagerFactory, certChainFile, keyFile, keyPassword, (KeyManagerFactory) null, ciphers, IdentityCipherSuiteFilter.INSTANCE, toApplicationProtocolConfig(nextProtocols), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) {
        return newServerContext(provider, (File) null, (TrustManagerFactory) null, certChainFile, keyFile, keyPassword, (KeyManagerFactory) null, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider provider, File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) {
        try {
            return newServerContextInternal(provider, (Provider) null, toX509Certificates(trustCertCollectionFile), trustManagerFactory, toX509Certificates(keyCertChainFile), toPrivateKey(keyFile, keyPassword), keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, ClientAuth.NONE, (String[]) null, false, false);
        } catch (Exception e) {
            if (e instanceof SSLException) {
                throw ((SSLException) e);
            }
            throw new SSLException("failed to initialize the server-side SSL context", e);
        }
    }

    static SslContext newServerContextInternal(SslProvider provider, Provider sslContextProvider, X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout, ClientAuth clientAuth, String[] protocols, boolean startTls2, boolean enableOcsp) {
        SslProvider provider2;
        Provider provider3 = sslContextProvider;
        if (provider == null) {
            provider2 = defaultServerProvider();
        } else {
            provider2 = provider;
        }
        switch (AnonymousClass1.$SwitchMap$io$netty$handler$ssl$SslProvider[provider2.ordinal()]) {
            case 1:
                if (!enableOcsp) {
                    SslProvider sslProvider = provider2;
                    return new JdkSslServerContext(sslContextProvider, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, clientAuth, protocols, startTls2);
                }
                throw new IllegalArgumentException("OCSP is not supported with this SslProvider: " + provider2);
            case 2:
                verifyNullSslContextProvider(provider2, provider3);
                return new OpenSslServerContext(trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, clientAuth, protocols, startTls2, enableOcsp);
            case 3:
                verifyNullSslContextProvider(provider2, provider3);
                return new ReferenceCountedOpenSslServerContext(trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, clientAuth, protocols, startTls2, enableOcsp);
            default:
                throw new Error(provider2.toString());
        }
    }

    /* renamed from: io.netty.handler.ssl.SslContext$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$SslProvider;

        static {
            int[] iArr = new int[SslProvider.values().length];
            $SwitchMap$io$netty$handler$ssl$SslProvider = iArr;
            try {
                iArr[SslProvider.JDK.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$SslProvider[SslProvider.OPENSSL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$io$netty$handler$ssl$SslProvider[SslProvider.OPENSSL_REFCNT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private static void verifyNullSslContextProvider(SslProvider provider, Provider sslContextProvider) {
        if (sslContextProvider != null) {
            throw new IllegalArgumentException("Java Security Provider unsupported for SslProvider: " + provider);
        }
    }

    @Deprecated
    public static SslContext newClientContext() {
        return newClientContext((SslProvider) null, (File) null, (TrustManagerFactory) null);
    }

    @Deprecated
    public static SslContext newClientContext(File certChainFile) {
        return newClientContext((SslProvider) null, certChainFile);
    }

    @Deprecated
    public static SslContext newClientContext(TrustManagerFactory trustManagerFactory) {
        return newClientContext((SslProvider) null, (File) null, trustManagerFactory);
    }

    @Deprecated
    public static SslContext newClientContext(File certChainFile, TrustManagerFactory trustManagerFactory) {
        return newClientContext((SslProvider) null, certChainFile, trustManagerFactory);
    }

    @Deprecated
    public static SslContext newClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) {
        return newClientContext((SslProvider) null, certChainFile, trustManagerFactory, ciphers, nextProtocols, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) {
        return newClientContext((SslProvider) null, certChainFile, trustManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider provider) {
        return newClientContext(provider, (File) null, (TrustManagerFactory) null);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider provider, File certChainFile) {
        return newClientContext(provider, certChainFile, (TrustManagerFactory) null);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider provider, TrustManagerFactory trustManagerFactory) {
        return newClientContext(provider, (File) null, trustManagerFactory);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider provider, File certChainFile, TrustManagerFactory trustManagerFactory) {
        return newClientContext(provider, certChainFile, trustManagerFactory, (Iterable<String>) null, IdentityCipherSuiteFilter.INSTANCE, (ApplicationProtocolConfig) null, 0, 0);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider provider, File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) {
        return newClientContext(provider, certChainFile, trustManagerFactory, (File) null, (File) null, (String) null, (KeyManagerFactory) null, ciphers, IdentityCipherSuiteFilter.INSTANCE, toApplicationProtocolConfig(nextProtocols), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider provider, File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) {
        return newClientContext(provider, certChainFile, trustManagerFactory, (File) null, (File) null, (String) null, (KeyManagerFactory) null, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider provider, File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) {
        try {
            return newClientContextInternal(provider, (Provider) null, toX509Certificates(trustCertCollectionFile), trustManagerFactory, toX509Certificates(keyCertChainFile), toPrivateKey(keyFile, keyPassword), keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, (String[]) null, sessionCacheSize, sessionTimeout, false);
        } catch (Exception e) {
            if (e instanceof SSLException) {
                throw ((SSLException) e);
            }
            throw new SSLException("failed to initialize the client-side SSL context", e);
        }
    }

    static SslContext newClientContextInternal(SslProvider provider, Provider sslContextProvider, X509Certificate[] trustCert, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, String[] protocols, long sessionCacheSize, long sessionTimeout, boolean enableOcsp) {
        SslProvider provider2;
        Provider provider3 = sslContextProvider;
        if (provider == null) {
            provider2 = defaultClientProvider();
        } else {
            provider2 = provider;
        }
        switch (AnonymousClass1.$SwitchMap$io$netty$handler$ssl$SslProvider[provider2.ordinal()]) {
            case 1:
                if (!enableOcsp) {
                    SslProvider sslProvider = provider2;
                    return new JdkSslClientContext(sslContextProvider, trustCert, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, protocols, sessionCacheSize, sessionTimeout);
                }
                throw new IllegalArgumentException("OCSP is not supported with this SslProvider: " + provider2);
            case 2:
                verifyNullSslContextProvider(provider2, provider3);
                return new OpenSslClientContext(trustCert, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, protocols, sessionCacheSize, sessionTimeout, enableOcsp);
            case 3:
                verifyNullSslContextProvider(provider2, provider3);
                return new ReferenceCountedOpenSslClientContext(trustCert, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, protocols, sessionCacheSize, sessionTimeout, enableOcsp);
            default:
                throw new Error(provider2.toString());
        }
    }

    static ApplicationProtocolConfig toApplicationProtocolConfig(Iterable<String> nextProtocols) {
        if (nextProtocols == null) {
            return ApplicationProtocolConfig.DISABLED;
        }
        return new ApplicationProtocolConfig(ApplicationProtocolConfig.Protocol.NPN_AND_ALPN, ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL, ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT, nextProtocols);
    }

    protected SslContext() {
        this(false);
    }

    protected SslContext(boolean startTls2) {
        this.startTls = startTls2;
    }

    public final boolean isServer() {
        return !isClient();
    }

    @Deprecated
    public final List<String> nextProtocols() {
        return applicationProtocolNegotiator().protocols();
    }

    public final SslHandler newHandler(ByteBufAllocator alloc) {
        return newHandler(alloc, this.startTls);
    }

    /* access modifiers changed from: protected */
    public SslHandler newHandler(ByteBufAllocator alloc, boolean startTls2) {
        return new SslHandler(newEngine(alloc), startTls2);
    }

    public final SslHandler newHandler(ByteBufAllocator alloc, String peerHost, int peerPort) {
        return newHandler(alloc, peerHost, peerPort, this.startTls);
    }

    /* access modifiers changed from: protected */
    public SslHandler newHandler(ByteBufAllocator alloc, String peerHost, int peerPort, boolean startTls2) {
        return new SslHandler(newEngine(alloc, peerHost, peerPort), startTls2);
    }

    protected static PKCS8EncodedKeySpec generateKeySpec(char[] password, byte[] key) {
        if (password == null) {
            return new PKCS8EncodedKeySpec(key);
        }
        EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(key);
        SecretKey pbeKey = SecretKeyFactory.getInstance(encryptedPrivateKeyInfo.getAlgName()).generateSecret(new PBEKeySpec(password));
        Cipher cipher = Cipher.getInstance(encryptedPrivateKeyInfo.getAlgName());
        cipher.init(2, pbeKey, encryptedPrivateKeyInfo.getAlgParameters());
        return encryptedPrivateKeyInfo.getKeySpec(cipher);
    }

    static KeyStore buildKeyStore(X509Certificate[] certChain, PrivateKey key, char[] keyPasswordChars) {
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load((InputStream) null, (char[]) null);
        ks.setKeyEntry(CacheEntity.KEY, key, keyPasswordChars, certChain);
        return ks;
    }

    static PrivateKey toPrivateKey(File keyFile, String keyPassword) {
        if (keyFile == null) {
            return null;
        }
        return getPrivateKeyFromByteBuffer(PemReader.readPrivateKey(keyFile), keyPassword);
    }

    static PrivateKey toPrivateKey(InputStream keyInputStream, String keyPassword) {
        if (keyInputStream == null) {
            return null;
        }
        return getPrivateKeyFromByteBuffer(PemReader.readPrivateKey(keyInputStream), keyPassword);
    }

    private static PrivateKey getPrivateKeyFromByteBuffer(ByteBuf encodedKeyBuf, String keyPassword) {
        char[] cArr;
        byte[] encodedKey = new byte[encodedKeyBuf.readableBytes()];
        encodedKeyBuf.readBytes(encodedKey).release();
        if (keyPassword == null) {
            cArr = null;
        } else {
            cArr = keyPassword.toCharArray();
        }
        PKCS8EncodedKeySpec encodedKeySpec = generateKeySpec(cArr, encodedKey);
        try {
            return KeyFactory.getInstance("RSA").generatePrivate(encodedKeySpec);
        } catch (InvalidKeySpecException e) {
            try {
                return KeyFactory.getInstance("DSA").generatePrivate(encodedKeySpec);
            } catch (InvalidKeySpecException e2) {
                try {
                    return KeyFactory.getInstance("EC").generatePrivate(encodedKeySpec);
                } catch (InvalidKeySpecException e3) {
                    throw new InvalidKeySpecException("Neither RSA, DSA nor EC worked", e3);
                }
            }
        }
    }

    @Deprecated
    protected static TrustManagerFactory buildTrustManagerFactory(File certChainFile, TrustManagerFactory trustManagerFactory) {
        return buildTrustManagerFactory(toX509Certificates(certChainFile), trustManagerFactory);
    }

    static X509Certificate[] toX509Certificates(File file) {
        if (file == null) {
            return null;
        }
        return getCertificatesFromBuffers(PemReader.readCertificates(file));
    }

    static X509Certificate[] toX509Certificates(InputStream in) {
        if (in == null) {
            return null;
        }
        return getCertificatesFromBuffers(PemReader.readCertificates(in));
    }

    private static X509Certificate[] getCertificatesFromBuffers(ByteBuf[] certs) {
        InputStream is;
        RuntimeException runtimeException;
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate[] x509Certs = new X509Certificate[certs.length];
        int i = 0;
        while (i < certs.length) {
            try {
                is = new ByteBufInputStream(certs[i], true);
                x509Certs[i] = (X509Certificate) cf.generateCertificate(is);
                is.close();
                i++;
            } catch (IOException e) {
                runtimeException = new RuntimeException(e);
            } catch (Throwable th) {
                while (i < certs.length) {
                    certs[i].release();
                    i++;
                }
                throw th;
            }
        }
        while (i < certs.length) {
            certs[i].release();
            i++;
        }
        return x509Certs;
        throw runtimeException;
    }

    static TrustManagerFactory buildTrustManagerFactory(X509Certificate[] certCollection, TrustManagerFactory trustManagerFactory) {
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load((InputStream) null, (char[]) null);
        int i = 1;
        for (X509Certificate cert : certCollection) {
            ks.setCertificateEntry(Integer.toString(i), cert);
            i++;
        }
        if (trustManagerFactory == null) {
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        }
        trustManagerFactory.init(ks);
        return trustManagerFactory;
    }

    static PrivateKey toPrivateKeyInternal(File keyFile, String keyPassword) {
        try {
            return toPrivateKey(keyFile, keyPassword);
        } catch (Exception e) {
            throw new SSLException(e);
        }
    }

    static X509Certificate[] toX509CertificatesInternal(File file) {
        try {
            return toX509Certificates(file);
        } catch (CertificateException e) {
            throw new SSLException(e);
        }
    }

    static KeyManagerFactory buildKeyManagerFactory(X509Certificate[] certChain, PrivateKey key, String keyPassword, KeyManagerFactory kmf) {
        return buildKeyManagerFactory(certChain, KeyManagerFactory.getDefaultAlgorithm(), key, keyPassword, kmf);
    }

    static KeyManagerFactory buildKeyManagerFactory(X509Certificate[] certChainFile, String keyAlgorithm, PrivateKey key, String keyPassword, KeyManagerFactory kmf) {
        char[] keyPasswordChars = keyPassword == null ? EmptyArrays.EMPTY_CHARS : keyPassword.toCharArray();
        KeyStore ks = buildKeyStore(certChainFile, key, keyPasswordChars);
        if (kmf == null) {
            kmf = KeyManagerFactory.getInstance(keyAlgorithm);
        }
        kmf.init(ks, keyPasswordChars);
        return kmf;
    }
}

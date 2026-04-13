package io.netty.handler.ssl;

import io.netty.util.internal.ObjectUtil;
import java.io.File;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

public final class SslContextBuilder {
    private ApplicationProtocolConfig apn;
    private CipherSuiteFilter cipherFilter = IdentityCipherSuiteFilter.INSTANCE;
    private Iterable<String> ciphers;
    private ClientAuth clientAuth = ClientAuth.NONE;
    private boolean enableOcsp;
    private final boolean forServer;
    private PrivateKey key;
    private X509Certificate[] keyCertChain;
    private KeyManagerFactory keyManagerFactory;
    private String keyPassword;
    private String[] protocols;
    private SslProvider provider;
    private long sessionCacheSize;
    private long sessionTimeout;
    private Provider sslContextProvider;
    private boolean startTls;
    private X509Certificate[] trustCertCollection;
    private TrustManagerFactory trustManagerFactory;

    public static SslContextBuilder forClient() {
        return new SslContextBuilder(false);
    }

    public static SslContextBuilder forServer(File keyCertChainFile, File keyFile) {
        return new SslContextBuilder(true).keyManager(keyCertChainFile, keyFile);
    }

    public static SslContextBuilder forServer(InputStream keyCertChainInputStream, InputStream keyInputStream) {
        return new SslContextBuilder(true).keyManager(keyCertChainInputStream, keyInputStream);
    }

    public static SslContextBuilder forServer(PrivateKey key2, X509Certificate... keyCertChain2) {
        return new SslContextBuilder(true).keyManager(key2, keyCertChain2);
    }

    public static SslContextBuilder forServer(File keyCertChainFile, File keyFile, String keyPassword2) {
        return new SslContextBuilder(true).keyManager(keyCertChainFile, keyFile, keyPassword2);
    }

    public static SslContextBuilder forServer(InputStream keyCertChainInputStream, InputStream keyInputStream, String keyPassword2) {
        return new SslContextBuilder(true).keyManager(keyCertChainInputStream, keyInputStream, keyPassword2);
    }

    public static SslContextBuilder forServer(PrivateKey key2, String keyPassword2, X509Certificate... keyCertChain2) {
        return new SslContextBuilder(true).keyManager(key2, keyPassword2, keyCertChain2);
    }

    public static SslContextBuilder forServer(KeyManagerFactory keyManagerFactory2) {
        return new SslContextBuilder(true).keyManager(keyManagerFactory2);
    }

    private SslContextBuilder(boolean forServer2) {
        this.forServer = forServer2;
    }

    public SslContextBuilder sslProvider(SslProvider provider2) {
        this.provider = provider2;
        return this;
    }

    public SslContextBuilder sslContextProvider(Provider sslContextProvider2) {
        this.sslContextProvider = sslContextProvider2;
        return this;
    }

    public SslContextBuilder trustManager(File trustCertCollectionFile) {
        try {
            return trustManager(SslContext.toX509Certificates(trustCertCollectionFile));
        } catch (Exception e) {
            throw new IllegalArgumentException("File does not contain valid certificates: " + trustCertCollectionFile, e);
        }
    }

    public SslContextBuilder trustManager(InputStream trustCertCollectionInputStream) {
        try {
            return trustManager(SslContext.toX509Certificates(trustCertCollectionInputStream));
        } catch (Exception e) {
            throw new IllegalArgumentException("Input stream does not contain valid certificates.", e);
        }
    }

    public SslContextBuilder trustManager(X509Certificate... trustCertCollection2) {
        this.trustCertCollection = trustCertCollection2 != null ? (X509Certificate[]) trustCertCollection2.clone() : null;
        this.trustManagerFactory = null;
        return this;
    }

    public SslContextBuilder trustManager(TrustManagerFactory trustManagerFactory2) {
        this.trustCertCollection = null;
        this.trustManagerFactory = trustManagerFactory2;
        return this;
    }

    public SslContextBuilder keyManager(File keyCertChainFile, File keyFile) {
        return keyManager(keyCertChainFile, keyFile, (String) null);
    }

    public SslContextBuilder keyManager(InputStream keyCertChainInputStream, InputStream keyInputStream) {
        return keyManager(keyCertChainInputStream, keyInputStream, (String) null);
    }

    public SslContextBuilder keyManager(PrivateKey key2, X509Certificate... keyCertChain2) {
        return keyManager(key2, (String) null, keyCertChain2);
    }

    public SslContextBuilder keyManager(File keyCertChainFile, File keyFile, String keyPassword2) {
        try {
            try {
                return keyManager(SslContext.toPrivateKey(keyFile, keyPassword2), keyPassword2, SslContext.toX509Certificates(keyCertChainFile));
            } catch (Exception e) {
                throw new IllegalArgumentException("File does not contain valid private key: " + keyFile, e);
            }
        } catch (Exception e2) {
            throw new IllegalArgumentException("File does not contain valid certificates: " + keyCertChainFile, e2);
        }
    }

    public SslContextBuilder keyManager(InputStream keyCertChainInputStream, InputStream keyInputStream, String keyPassword2) {
        try {
            try {
                return keyManager(SslContext.toPrivateKey(keyInputStream, keyPassword2), keyPassword2, SslContext.toX509Certificates(keyCertChainInputStream));
            } catch (Exception e) {
                throw new IllegalArgumentException("Input stream does not contain valid private key.", e);
            }
        } catch (Exception e2) {
            throw new IllegalArgumentException("Input stream not contain valid certificates.", e2);
        }
    }

    public SslContextBuilder keyManager(PrivateKey key2, String keyPassword2, X509Certificate... keyCertChain2) {
        if (this.forServer) {
            ObjectUtil.checkNotNull(keyCertChain2, "keyCertChain required for servers");
            if (keyCertChain2.length != 0) {
                ObjectUtil.checkNotNull(key2, "key required for servers");
            } else {
                throw new IllegalArgumentException("keyCertChain must be non-empty");
            }
        }
        if (keyCertChain2 == null || keyCertChain2.length == 0) {
            this.keyCertChain = null;
        } else {
            int length = keyCertChain2.length;
            int i = 0;
            while (i < length) {
                if (keyCertChain2[i] != null) {
                    i++;
                } else {
                    throw new IllegalArgumentException("keyCertChain contains null entry");
                }
            }
            this.keyCertChain = (X509Certificate[]) keyCertChain2.clone();
        }
        this.key = key2;
        this.keyPassword = keyPassword2;
        this.keyManagerFactory = null;
        return this;
    }

    public SslContextBuilder keyManager(KeyManagerFactory keyManagerFactory2) {
        if (this.forServer) {
            ObjectUtil.checkNotNull(keyManagerFactory2, "keyManagerFactory required for servers");
        }
        this.keyCertChain = null;
        this.key = null;
        this.keyPassword = null;
        this.keyManagerFactory = keyManagerFactory2;
        return this;
    }

    public SslContextBuilder ciphers(Iterable<String> ciphers2) {
        return ciphers(ciphers2, IdentityCipherSuiteFilter.INSTANCE);
    }

    public SslContextBuilder ciphers(Iterable<String> ciphers2, CipherSuiteFilter cipherFilter2) {
        ObjectUtil.checkNotNull(cipherFilter2, "cipherFilter");
        this.ciphers = ciphers2;
        this.cipherFilter = cipherFilter2;
        return this;
    }

    public SslContextBuilder applicationProtocolConfig(ApplicationProtocolConfig apn2) {
        this.apn = apn2;
        return this;
    }

    public SslContextBuilder sessionCacheSize(long sessionCacheSize2) {
        this.sessionCacheSize = sessionCacheSize2;
        return this;
    }

    public SslContextBuilder sessionTimeout(long sessionTimeout2) {
        this.sessionTimeout = sessionTimeout2;
        return this;
    }

    public SslContextBuilder clientAuth(ClientAuth clientAuth2) {
        this.clientAuth = (ClientAuth) ObjectUtil.checkNotNull(clientAuth2, "clientAuth");
        return this;
    }

    public SslContextBuilder protocols(String... protocols2) {
        this.protocols = protocols2 == null ? null : (String[]) protocols2.clone();
        return this;
    }

    public SslContextBuilder startTls(boolean startTls2) {
        this.startTls = startTls2;
        return this;
    }

    public SslContextBuilder enableOcsp(boolean enableOcsp2) {
        this.enableOcsp = enableOcsp2;
        return this;
    }

    public SslContext build() {
        if (this.forServer) {
            return SslContext.newServerContextInternal(this.provider, this.sslContextProvider, this.trustCertCollection, this.trustManagerFactory, this.keyCertChain, this.key, this.keyPassword, this.keyManagerFactory, this.ciphers, this.cipherFilter, this.apn, this.sessionCacheSize, this.sessionTimeout, this.clientAuth, this.protocols, this.startTls, this.enableOcsp);
        }
        return SslContext.newClientContextInternal(this.provider, this.sslContextProvider, this.trustCertCollection, this.trustManagerFactory, this.keyCertChain, this.key, this.keyPassword, this.keyManagerFactory, this.ciphers, this.cipherFilter, this.apn, this.protocols, this.sessionCacheSize, this.sessionTimeout, this.enableOcsp);
    }
}

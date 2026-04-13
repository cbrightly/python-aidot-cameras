package io.netty.handler.ssl;

import java.io.File;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

@Deprecated
public final class JdkSslClientContext extends JdkSslContext {
    @Deprecated
    public JdkSslClientContext() {
        this((File) null, (TrustManagerFactory) null);
    }

    @Deprecated
    public JdkSslClientContext(File certChainFile) {
        this(certChainFile, (TrustManagerFactory) null);
    }

    @Deprecated
    public JdkSslClientContext(TrustManagerFactory trustManagerFactory) {
        this((File) null, trustManagerFactory);
    }

    @Deprecated
    public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory) {
        this(certChainFile, trustManagerFactory, (Iterable<String>) null, (CipherSuiteFilter) IdentityCipherSuiteFilter.INSTANCE, (JdkApplicationProtocolNegotiator) JdkDefaultApplicationProtocolNegotiator.INSTANCE, 0, 0);
    }

    @Deprecated
    public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) {
        this(certChainFile, trustManagerFactory, ciphers, (CipherSuiteFilter) IdentityCipherSuiteFilter.INSTANCE, JdkSslContext.toNegotiator(SslContext.toApplicationProtocolConfig(nextProtocols), false), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) {
        this(certChainFile, trustManagerFactory, ciphers, cipherFilter, JdkSslContext.toNegotiator(apn, false), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, JdkApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) {
        this((Provider) null, certChainFile, trustManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    JdkSslClientContext(java.security.Provider r17, java.io.File r18, javax.net.ssl.TrustManagerFactory r19, java.lang.Iterable<java.lang.String> r20, io.netty.handler.ssl.CipherSuiteFilter r21, io.netty.handler.ssl.JdkApplicationProtocolNegotiator r22, long r23, long r25) {
        /*
            r16 = this;
            java.security.cert.X509Certificate[] r1 = io.netty.handler.ssl.SslContext.toX509CertificatesInternal(r18)
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r0 = r17
            r2 = r19
            r7 = r23
            r9 = r25
            javax.net.ssl.SSLContext r8 = newSSLContext(r0, r1, r2, r3, r4, r5, r6, r7, r9)
            io.netty.handler.ssl.ClientAuth r13 = io.netty.handler.ssl.ClientAuth.NONE
            r9 = 1
            r14 = 0
            r15 = 0
            r7 = r16
            r10 = r20
            r11 = r21
            r12 = r22
            r7.<init>(r8, r9, r10, r11, r12, r13, r14, r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.JdkSslClientContext.<init>(java.security.Provider, java.io.File, javax.net.ssl.TrustManagerFactory, java.lang.Iterable, io.netty.handler.ssl.CipherSuiteFilter, io.netty.handler.ssl.JdkApplicationProtocolNegotiator, long, long):void");
    }

    @Deprecated
    public JdkSslClientContext(File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) {
        this(trustCertCollectionFile, trustManagerFactory, keyCertChainFile, keyFile, keyPassword, keyManagerFactory, ciphers, cipherFilter, JdkSslContext.toNegotiator(apn, false), sessionCacheSize, sessionTimeout);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public JdkSslClientContext(java.io.File r15, javax.net.ssl.TrustManagerFactory r16, java.io.File r17, java.io.File r18, java.lang.String r19, javax.net.ssl.KeyManagerFactory r20, java.lang.Iterable<java.lang.String> r21, io.netty.handler.ssl.CipherSuiteFilter r22, io.netty.handler.ssl.JdkApplicationProtocolNegotiator r23, long r24, long r26) {
        /*
            r14 = this;
            java.security.cert.X509Certificate[] r1 = io.netty.handler.ssl.SslContext.toX509CertificatesInternal(r15)
            java.security.cert.X509Certificate[] r3 = io.netty.handler.ssl.SslContext.toX509CertificatesInternal(r17)
            java.security.PrivateKey r4 = io.netty.handler.ssl.SslContext.toPrivateKeyInternal(r18, r19)
            r0 = 0
            r2 = r16
            r5 = r19
            r6 = r20
            r7 = r24
            r9 = r26
            javax.net.ssl.SSLContext r6 = newSSLContext(r0, r1, r2, r3, r4, r5, r6, r7, r9)
            io.netty.handler.ssl.ClientAuth r11 = io.netty.handler.ssl.ClientAuth.NONE
            r7 = 1
            r12 = 0
            r13 = 0
            r5 = r14
            r8 = r21
            r9 = r22
            r10 = r23
            r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.JdkSslClientContext.<init>(java.io.File, javax.net.ssl.TrustManagerFactory, java.io.File, java.io.File, java.lang.String, javax.net.ssl.KeyManagerFactory, java.lang.Iterable, io.netty.handler.ssl.CipherSuiteFilter, io.netty.handler.ssl.JdkApplicationProtocolNegotiator, long, long):void");
    }

    JdkSslClientContext(Provider sslContextProvider, X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, String[] protocols, long sessionCacheSize, long sessionTimeout) {
        super(newSSLContext(sslContextProvider, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, sessionCacheSize, sessionTimeout), true, ciphers, cipherFilter, JdkSslContext.toNegotiator(apn, false), ClientAuth.NONE, protocols, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x006e  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0072  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static javax.net.ssl.SSLContext newSSLContext(java.security.Provider r16, java.security.cert.X509Certificate[] r17, javax.net.ssl.TrustManagerFactory r18, java.security.cert.X509Certificate[] r19, java.security.PrivateKey r20, java.lang.String r21, javax.net.ssl.KeyManagerFactory r22, long r23, long r25) {
        /*
            r1 = r16
            r2 = r23
            r4 = r25
            if (r17 == 0) goto L_0x0014
            javax.net.ssl.TrustManagerFactory r0 = io.netty.handler.ssl.SslContext.buildTrustManagerFactory((java.security.cert.X509Certificate[]) r17, (javax.net.ssl.TrustManagerFactory) r18)     // Catch:{ Exception -> 0x000e }
            r6 = r0
            goto L_0x0016
        L_0x000e:
            r0 = move-exception
            r6 = r18
            r7 = r22
            goto L_0x006a
        L_0x0014:
            r6 = r18
        L_0x0016:
            if (r19 == 0) goto L_0x0022
            javax.net.ssl.KeyManagerFactory r0 = io.netty.handler.ssl.SslContext.buildKeyManagerFactory(r19, r20, r21, r22)     // Catch:{ Exception -> 0x001e }
            r7 = r0
            goto L_0x0024
        L_0x001e:
            r0 = move-exception
            r7 = r22
            goto L_0x006a
        L_0x0022:
            r7 = r22
        L_0x0024:
            java.lang.String r0 = "TLS"
            if (r1 != 0) goto L_0x002d
            javax.net.ssl.SSLContext r0 = javax.net.ssl.SSLContext.getInstance(r0)     // Catch:{ Exception -> 0x0069 }
            goto L_0x0031
        L_0x002d:
            javax.net.ssl.SSLContext r0 = javax.net.ssl.SSLContext.getInstance(r0, r1)     // Catch:{ Exception -> 0x0069 }
        L_0x0031:
            r8 = 0
            if (r7 != 0) goto L_0x0037
            r9 = r8
            goto L_0x003b
        L_0x0037:
            javax.net.ssl.KeyManager[] r9 = r7.getKeyManagers()     // Catch:{ Exception -> 0x0069 }
        L_0x003b:
            if (r6 != 0) goto L_0x003f
            r10 = r8
            goto L_0x0043
        L_0x003f:
            javax.net.ssl.TrustManager[] r10 = r6.getTrustManagers()     // Catch:{ Exception -> 0x0069 }
        L_0x0043:
            r0.init(r9, r10, r8)     // Catch:{ Exception -> 0x0069 }
            javax.net.ssl.SSLSessionContext r8 = r0.getClientSessionContext()     // Catch:{ Exception -> 0x0069 }
            r9 = 0
            int r11 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            r12 = 2147483647(0x7fffffff, double:1.060997895E-314)
            if (r11 <= 0) goto L_0x005c
            long r14 = java.lang.Math.min(r2, r12)     // Catch:{ Exception -> 0x0069 }
            int r11 = (int) r14     // Catch:{ Exception -> 0x0069 }
            r8.setSessionCacheSize(r11)     // Catch:{ Exception -> 0x0069 }
        L_0x005c:
            int r9 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
            if (r9 <= 0) goto L_0x0068
            long r9 = java.lang.Math.min(r4, r12)     // Catch:{ Exception -> 0x0069 }
            int r9 = (int) r9     // Catch:{ Exception -> 0x0069 }
            r8.setSessionTimeout(r9)     // Catch:{ Exception -> 0x0069 }
        L_0x0068:
            return r0
        L_0x0069:
            r0 = move-exception
        L_0x006a:
            boolean r8 = r0 instanceof javax.net.ssl.SSLException
            if (r8 == 0) goto L_0x0072
            r8 = r0
            javax.net.ssl.SSLException r8 = (javax.net.ssl.SSLException) r8
            throw r8
        L_0x0072:
            javax.net.ssl.SSLException r8 = new javax.net.ssl.SSLException
            java.lang.String r9 = "failed to initialize the client-side SSL context"
            r8.<init>(r9, r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.JdkSslClientContext.newSSLContext(java.security.Provider, java.security.cert.X509Certificate[], javax.net.ssl.TrustManagerFactory, java.security.cert.X509Certificate[], java.security.PrivateKey, java.lang.String, javax.net.ssl.KeyManagerFactory, long, long):javax.net.ssl.SSLContext");
    }
}

package io.netty.handler.ssl;

import java.io.File;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

@Deprecated
public final class JdkSslServerContext extends JdkSslContext {
    @Deprecated
    public JdkSslServerContext(File certChainFile, File keyFile) {
        this(certChainFile, keyFile, (String) null);
    }

    @Deprecated
    public JdkSslServerContext(File certChainFile, File keyFile, String keyPassword) {
        this(certChainFile, keyFile, keyPassword, (Iterable<String>) null, (CipherSuiteFilter) IdentityCipherSuiteFilter.INSTANCE, (JdkApplicationProtocolNegotiator) JdkDefaultApplicationProtocolNegotiator.INSTANCE, 0, 0);
    }

    @Deprecated
    public JdkSslServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) {
        this(certChainFile, keyFile, keyPassword, ciphers, (CipherSuiteFilter) IdentityCipherSuiteFilter.INSTANCE, JdkSslContext.toNegotiator(SslContext.toApplicationProtocolConfig(nextProtocols), true), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public JdkSslServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) {
        this(certChainFile, keyFile, keyPassword, ciphers, cipherFilter, JdkSslContext.toNegotiator(apn, true), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public JdkSslServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, JdkApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) {
        this((Provider) null, certChainFile, keyFile, keyPassword, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    JdkSslServerContext(java.security.Provider r17, java.io.File r18, java.io.File r19, java.lang.String r20, java.lang.Iterable<java.lang.String> r21, io.netty.handler.ssl.CipherSuiteFilter r22, io.netty.handler.ssl.JdkApplicationProtocolNegotiator r23, long r24, long r26) {
        /*
            r16 = this;
            java.security.cert.X509Certificate[] r3 = io.netty.handler.ssl.SslContext.toX509CertificatesInternal(r18)
            java.security.PrivateKey r4 = io.netty.handler.ssl.SslContext.toPrivateKeyInternal(r19, r20)
            r1 = 0
            r2 = 0
            r6 = 0
            r0 = r17
            r5 = r20
            r7 = r24
            r9 = r26
            javax.net.ssl.SSLContext r8 = newSSLContext(r0, r1, r2, r3, r4, r5, r6, r7, r9)
            io.netty.handler.ssl.ClientAuth r13 = io.netty.handler.ssl.ClientAuth.NONE
            r9 = 0
            r14 = 0
            r15 = 0
            r7 = r16
            r10 = r21
            r11 = r22
            r12 = r23
            r7.<init>(r8, r9, r10, r11, r12, r13, r14, r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.JdkSslServerContext.<init>(java.security.Provider, java.io.File, java.io.File, java.lang.String, java.lang.Iterable, io.netty.handler.ssl.CipherSuiteFilter, io.netty.handler.ssl.JdkApplicationProtocolNegotiator, long, long):void");
    }

    @Deprecated
    public JdkSslServerContext(File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) {
        this(trustCertCollectionFile, trustManagerFactory, keyCertChainFile, keyFile, keyPassword, keyManagerFactory, ciphers, cipherFilter, JdkSslContext.toNegotiator(apn, true), sessionCacheSize, sessionTimeout);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public JdkSslServerContext(java.io.File r15, javax.net.ssl.TrustManagerFactory r16, java.io.File r17, java.io.File r18, java.lang.String r19, javax.net.ssl.KeyManagerFactory r20, java.lang.Iterable<java.lang.String> r21, io.netty.handler.ssl.CipherSuiteFilter r22, io.netty.handler.ssl.JdkApplicationProtocolNegotiator r23, long r24, long r26) {
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
            r7 = 0
            r12 = 0
            r13 = 0
            r5 = r14
            r8 = r21
            r9 = r22
            r10 = r23
            r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.JdkSslServerContext.<init>(java.io.File, javax.net.ssl.TrustManagerFactory, java.io.File, java.io.File, java.lang.String, javax.net.ssl.KeyManagerFactory, java.lang.Iterable, io.netty.handler.ssl.CipherSuiteFilter, io.netty.handler.ssl.JdkApplicationProtocolNegotiator, long, long):void");
    }

    JdkSslServerContext(Provider provider, X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout, ClientAuth clientAuth, String[] protocols, boolean startTls) {
        super(newSSLContext(provider, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, sessionCacheSize, sessionTimeout), false, ciphers, cipherFilter, JdkSslContext.toNegotiator(apn, true), clientAuth, protocols, startTls);
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x007b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static javax.net.ssl.SSLContext newSSLContext(java.security.Provider r16, java.security.cert.X509Certificate[] r17, javax.net.ssl.TrustManagerFactory r18, java.security.cert.X509Certificate[] r19, java.security.PrivateKey r20, java.lang.String r21, javax.net.ssl.KeyManagerFactory r22, long r23, long r25) {
        /*
            r1 = r16
            r2 = r23
            r4 = r25
            if (r20 != 0) goto L_0x0013
            if (r22 == 0) goto L_0x000b
            goto L_0x0013
        L_0x000b:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r6 = "key, keyManagerFactory"
            r0.<init>(r6)
            throw r0
        L_0x0013:
            if (r17 == 0) goto L_0x0021
            javax.net.ssl.TrustManagerFactory r0 = io.netty.handler.ssl.SslContext.buildTrustManagerFactory((java.security.cert.X509Certificate[]) r17, (javax.net.ssl.TrustManagerFactory) r18)     // Catch:{ Exception -> 0x001b }
            r6 = r0
            goto L_0x0023
        L_0x001b:
            r0 = move-exception
            r6 = r18
            r7 = r22
            goto L_0x0073
        L_0x0021:
            r6 = r18
        L_0x0023:
            if (r20 == 0) goto L_0x002f
            javax.net.ssl.KeyManagerFactory r0 = io.netty.handler.ssl.SslContext.buildKeyManagerFactory(r19, r20, r21, r22)     // Catch:{ Exception -> 0x002b }
            r7 = r0
            goto L_0x0031
        L_0x002b:
            r0 = move-exception
            r7 = r22
            goto L_0x0073
        L_0x002f:
            r7 = r22
        L_0x0031:
            java.lang.String r0 = "TLS"
            if (r1 != 0) goto L_0x003a
            javax.net.ssl.SSLContext r0 = javax.net.ssl.SSLContext.getInstance(r0)     // Catch:{ Exception -> 0x0072 }
            goto L_0x003e
        L_0x003a:
            javax.net.ssl.SSLContext r0 = javax.net.ssl.SSLContext.getInstance(r0, r1)     // Catch:{ Exception -> 0x0072 }
        L_0x003e:
            javax.net.ssl.KeyManager[] r8 = r7.getKeyManagers()     // Catch:{ Exception -> 0x0072 }
            r9 = 0
            if (r6 != 0) goto L_0x0048
            r10 = r9
            goto L_0x004c
        L_0x0048:
            javax.net.ssl.TrustManager[] r10 = r6.getTrustManagers()     // Catch:{ Exception -> 0x0072 }
        L_0x004c:
            r0.init(r8, r10, r9)     // Catch:{ Exception -> 0x0072 }
            javax.net.ssl.SSLSessionContext r8 = r0.getServerSessionContext()     // Catch:{ Exception -> 0x0072 }
            r9 = 0
            int r11 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            r12 = 2147483647(0x7fffffff, double:1.060997895E-314)
            if (r11 <= 0) goto L_0x0065
            long r14 = java.lang.Math.min(r2, r12)     // Catch:{ Exception -> 0x0072 }
            int r11 = (int) r14     // Catch:{ Exception -> 0x0072 }
            r8.setSessionCacheSize(r11)     // Catch:{ Exception -> 0x0072 }
        L_0x0065:
            int r9 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
            if (r9 <= 0) goto L_0x0071
            long r9 = java.lang.Math.min(r4, r12)     // Catch:{ Exception -> 0x0072 }
            int r9 = (int) r9     // Catch:{ Exception -> 0x0072 }
            r8.setSessionTimeout(r9)     // Catch:{ Exception -> 0x0072 }
        L_0x0071:
            return r0
        L_0x0072:
            r0 = move-exception
        L_0x0073:
            boolean r8 = r0 instanceof javax.net.ssl.SSLException
            if (r8 == 0) goto L_0x007b
            r8 = r0
            javax.net.ssl.SSLException r8 = (javax.net.ssl.SSLException) r8
            throw r8
        L_0x007b:
            javax.net.ssl.SSLException r8 = new javax.net.ssl.SSLException
            java.lang.String r9 = "failed to initialize the server-side SSL context"
            r8.<init>(r9, r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.JdkSslServerContext.newSSLContext(java.security.Provider, java.security.cert.X509Certificate[], javax.net.ssl.TrustManagerFactory, java.security.cert.X509Certificate[], java.security.PrivateKey, java.lang.String, javax.net.ssl.KeyManagerFactory, long, long):javax.net.ssl.SSLContext");
    }
}

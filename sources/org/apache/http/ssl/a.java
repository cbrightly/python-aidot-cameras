package org.apache.http.ssl;

import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* compiled from: SSLContextBuilder */
public class a {
    private String a;
    private final Set<KeyManager> b = new LinkedHashSet();
    private final Set<TrustManager> c = new LinkedHashSet();
    private SecureRandom d;

    public a c(KeyStore truststore, c trustStrategy) {
        TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmfactory.init(truststore);
        TrustManager[] tms = tmfactory.getTrustManagers();
        if (tms != null) {
            if (trustStrategy != null) {
                for (int i = 0; i < tms.length; i++) {
                    TrustManager tm = tms[i];
                    if (tm instanceof X509TrustManager) {
                        tms[i] = new C0146a((X509TrustManager) tm, trustStrategy);
                    }
                }
            }
            for (TrustManager tm2 : tms) {
                this.c.add(tm2);
            }
        }
        return this;
    }

    public a d(c trustStrategy) {
        return c((KeyStore) null, trustStrategy);
    }

    /* access modifiers changed from: protected */
    public void b(SSLContext sslcontext, Collection<KeyManager> keyManagers, Collection<TrustManager> trustManagers, SecureRandom secureRandom) {
        TrustManager[] trustManagerArr = null;
        KeyManager[] keyManagerArr = !keyManagers.isEmpty() ? (KeyManager[]) keyManagers.toArray(new KeyManager[keyManagers.size()]) : null;
        if (!trustManagers.isEmpty()) {
            trustManagerArr = (TrustManager[]) trustManagers.toArray(new TrustManager[trustManagers.size()]);
        }
        sslcontext.init(keyManagerArr, trustManagerArr, secureRandom);
    }

    public SSLContext a() {
        String str = this.a;
        if (str == null) {
            str = "TLS";
        }
        SSLContext sslcontext = SSLContext.getInstance(str);
        b(sslcontext, this.b, this.c, this.d);
        return sslcontext;
    }

    /* renamed from: org.apache.http.ssl.a$a  reason: collision with other inner class name */
    /* compiled from: SSLContextBuilder */
    public static class C0146a implements X509TrustManager {
        private final X509TrustManager a;
        private final c b;

        C0146a(X509TrustManager trustManager, c trustStrategy) {
            this.a = trustManager;
            this.b = trustStrategy;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) {
            this.a.checkClientTrusted(chain, authType);
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
            if (!this.b.isTrusted(chain, authType)) {
                this.a.checkServerTrusted(chain, authType);
            }
        }

        public X509Certificate[] getAcceptedIssuers() {
            return this.a.getAcceptedIssuers();
        }
    }
}

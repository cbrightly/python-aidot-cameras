package com.squareup.okhttp;

import com.squareup.okhttp.internal.j;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLSocket;

/* compiled from: ConnectionSpec */
public final class k {
    private static final h[] a;
    public static final k b;
    public static final k c;
    public static final k d = new b(false).e();
    /* access modifiers changed from: private */
    public final boolean e;
    /* access modifiers changed from: private */
    public final boolean f;
    /* access modifiers changed from: private */
    public final String[] g;
    /* access modifiers changed from: private */
    public final String[] h;

    static {
        h[] hVarArr = {h.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, h.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, h.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, h.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA, h.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, h.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, h.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, h.TLS_DHE_RSA_WITH_AES_128_CBC_SHA, h.TLS_DHE_RSA_WITH_AES_256_CBC_SHA, h.TLS_RSA_WITH_AES_128_GCM_SHA256, h.TLS_RSA_WITH_AES_128_CBC_SHA, h.TLS_RSA_WITH_AES_256_CBC_SHA, h.TLS_RSA_WITH_3DES_EDE_CBC_SHA};
        a = hVarArr;
        b f2 = new b(true).f(hVarArr);
        a0 a0Var = a0.TLS_1_0;
        k e2 = f2.i(a0.TLS_1_2, a0.TLS_1_1, a0Var).h(true).e();
        b = e2;
        c = new b(e2).i(a0Var).h(true).e();
    }

    private k(b builder) {
        this.e = builder.a;
        this.g = builder.b;
        this.h = builder.c;
        this.f = builder.d;
    }

    public List<h> f() {
        String[] strArr = this.g;
        if (strArr == null) {
            return null;
        }
        h[] result = new h[strArr.length];
        int i = 0;
        while (true) {
            String[] strArr2 = this.g;
            if (i >= strArr2.length) {
                return j.k(result);
            }
            result[i] = h.forJavaName(strArr2[i]);
            i++;
        }
    }

    public List<a0> k() {
        String[] strArr = this.h;
        if (strArr == null) {
            return null;
        }
        a0[] result = new a0[strArr.length];
        int i = 0;
        while (true) {
            String[] strArr2 = this.h;
            if (i >= strArr2.length) {
                return j.k(result);
            }
            result[i] = a0.forJavaName(strArr2[i]);
            i++;
        }
    }

    public boolean j() {
        return this.f;
    }

    /* access modifiers changed from: package-private */
    public void e(SSLSocket sslSocket, boolean isFallback) {
        k specToApply = i(sslSocket, isFallback);
        String[] strArr = specToApply.h;
        if (strArr != null) {
            sslSocket.setEnabledProtocols(strArr);
        }
        String[] strArr2 = specToApply.g;
        if (strArr2 != null) {
            sslSocket.setEnabledCipherSuites(strArr2);
        }
    }

    private k i(SSLSocket sslSocket, boolean isFallback) {
        String[] cipherSuitesIntersection;
        String[] tlsVersionsIntersection;
        Class<String> cls = String.class;
        String[] strArr = this.g;
        if (strArr != null) {
            cipherSuitesIntersection = (String[]) j.n(cls, strArr, sslSocket.getEnabledCipherSuites());
        } else {
            cipherSuitesIntersection = sslSocket.getEnabledCipherSuites();
        }
        String[] strArr2 = this.h;
        if (strArr2 != null) {
            tlsVersionsIntersection = (String[]) j.n(cls, strArr2, sslSocket.getEnabledProtocols());
        } else {
            tlsVersionsIntersection = sslSocket.getEnabledProtocols();
        }
        if (isFallback && j.f(sslSocket.getSupportedCipherSuites(), "TLS_FALLBACK_SCSV")) {
            cipherSuitesIntersection = j.e(cipherSuitesIntersection, "TLS_FALLBACK_SCSV");
        }
        return new b(this).g(cipherSuitesIntersection).j(tlsVersionsIntersection).e();
    }

    public boolean g(SSLSocket socket) {
        if (!this.e) {
            return false;
        }
        String[] strArr = this.h;
        if (strArr != null && !h(strArr, socket.getEnabledProtocols())) {
            return false;
        }
        String[] strArr2 = this.g;
        if (strArr2 == null || h(strArr2, socket.getEnabledCipherSuites())) {
            return true;
        }
        return false;
    }

    private static boolean h(String[] a2, String[] b2) {
        if (a2 == null || b2 == null || a2.length == 0 || b2.length == 0) {
            return false;
        }
        for (String toFind : a2) {
            if (j.f(b2, toFind)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object other) {
        if (!(other instanceof k)) {
            return false;
        }
        if (other == this) {
            return true;
        }
        k that = (k) other;
        boolean z = this.e;
        if (z != that.e) {
            return false;
        }
        if (!z || (Arrays.equals(this.g, that.g) && Arrays.equals(this.h, that.h) && this.f == that.f)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.e) {
            return (((((17 * 31) + Arrays.hashCode(this.g)) * 31) + Arrays.hashCode(this.h)) * 31) + (this.f ^ true ? 1 : 0);
        }
        return 17;
    }

    public String toString() {
        if (!this.e) {
            return "ConnectionSpec()";
        }
        String tlsVersionsString = "[all enabled]";
        String cipherSuitesString = this.g != null ? f().toString() : tlsVersionsString;
        if (this.h != null) {
            tlsVersionsString = k().toString();
        }
        return "ConnectionSpec(cipherSuites=" + cipherSuitesString + ", tlsVersions=" + tlsVersionsString + ", supportsTlsExtensions=" + this.f + ")";
    }

    /* compiled from: ConnectionSpec */
    public static final class b {
        /* access modifiers changed from: private */
        public boolean a;
        /* access modifiers changed from: private */
        public String[] b;
        /* access modifiers changed from: private */
        public String[] c;
        /* access modifiers changed from: private */
        public boolean d;

        b(boolean tls) {
            this.a = tls;
        }

        public b(k connectionSpec) {
            this.a = connectionSpec.e;
            this.b = connectionSpec.g;
            this.c = connectionSpec.h;
            this.d = connectionSpec.f;
        }

        public b f(h... cipherSuites) {
            if (this.a) {
                String[] strings = new String[cipherSuites.length];
                for (int i = 0; i < cipherSuites.length; i++) {
                    strings[i] = cipherSuites[i].javaName;
                }
                return g(strings);
            }
            throw new IllegalStateException("no cipher suites for cleartext connections");
        }

        public b g(String... cipherSuites) {
            if (!this.a) {
                throw new IllegalStateException("no cipher suites for cleartext connections");
            } else if (cipherSuites.length != 0) {
                this.b = (String[]) cipherSuites.clone();
                return this;
            } else {
                throw new IllegalArgumentException("At least one cipher suite is required");
            }
        }

        public b i(a0... tlsVersions) {
            if (this.a) {
                String[] strings = new String[tlsVersions.length];
                for (int i = 0; i < tlsVersions.length; i++) {
                    strings[i] = tlsVersions[i].javaName;
                }
                return j(strings);
            }
            throw new IllegalStateException("no TLS versions for cleartext connections");
        }

        public b j(String... tlsVersions) {
            if (!this.a) {
                throw new IllegalStateException("no TLS versions for cleartext connections");
            } else if (tlsVersions.length != 0) {
                this.c = (String[]) tlsVersions.clone();
                return this;
            } else {
                throw new IllegalArgumentException("At least one TLS version is required");
            }
        }

        public b h(boolean supportsTlsExtensions) {
            if (this.a) {
                this.d = supportsTlsExtensions;
                return this;
            }
            throw new IllegalStateException("no TLS extensions for cleartext connections");
        }

        public k e() {
            return new k(this);
        }
    }
}

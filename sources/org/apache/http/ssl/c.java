package org.apache.http.ssl;

import java.security.cert.X509Certificate;

/* compiled from: TrustStrategy */
public interface c {
    boolean isTrusted(X509Certificate[] x509CertificateArr, String str);
}

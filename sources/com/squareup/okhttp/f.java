package com.squareup.okhttp;

import com.squareup.okhttp.internal.j;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.SSLPeerUnverifiedException;
import okio.ByteString;

/* compiled from: CertificatePinner */
public final class f {
    public static final f a = new b().b();
    private final Map<String, Set<okio.f>> b;

    private f(b builder) {
        this.b = j.l(builder.a);
    }

    public void a(String hostname, List<Certificate> peerCertificates) {
        Set<okio.f> b2 = b(hostname);
        if (b2 != null) {
            int i = 0;
            int size = peerCertificates.size();
            while (i < size) {
                if (!b2.contains(d((X509Certificate) peerCertificates.get(i)))) {
                    i++;
                } else {
                    return;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Certificate pinning failure!");
            StringBuilder message = sb.append("\n  Peer certificate chain:");
            int size2 = peerCertificates.size();
            for (int i2 = 0; i2 < size2; i2++) {
                X509Certificate x509Certificate = (X509Certificate) peerCertificates.get(i2);
                message.append("\n    ");
                message.append(c(x509Certificate));
                message.append(": ");
                message.append(x509Certificate.getSubjectDN().getName());
            }
            message.append("\n  Pinned certificates for ");
            message.append(hostname);
            message.append(":");
            for (okio.f pin : b2) {
                message.append("\n    sha1/");
                message.append(pin.base64());
            }
            throw new SSLPeerUnverifiedException(message.toString());
        }
    }

    /* access modifiers changed from: package-private */
    public Set<okio.f> b(String hostname) {
        Set<ByteString> directPins = this.b.get(hostname);
        Set<ByteString> wildcardPins = null;
        int indexOfFirstDot = hostname.indexOf(46);
        if (indexOfFirstDot != hostname.lastIndexOf(46)) {
            Map<String, Set<okio.f>> map = this.b;
            wildcardPins = map.get("*." + hostname.substring(indexOfFirstDot + 1));
        }
        if (directPins == null && wildcardPins == null) {
            return null;
        }
        if (directPins != null && wildcardPins != null) {
            Set<ByteString> pins = new LinkedHashSet<>();
            pins.addAll(directPins);
            pins.addAll(wildcardPins);
            return pins;
        } else if (directPins != null) {
            return directPins;
        } else {
            return wildcardPins;
        }
    }

    public static String c(Certificate certificate) {
        if (certificate instanceof X509Certificate) {
            return "sha1/" + d((X509Certificate) certificate).base64();
        }
        throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
    }

    private static okio.f d(X509Certificate x509Certificate) {
        return j.q(okio.f.of(x509Certificate.getPublicKey().getEncoded()));
    }

    /* compiled from: CertificatePinner */
    public static final class b {
        /* access modifiers changed from: private */
        public final Map<String, Set<okio.f>> a = new LinkedHashMap();

        public f b() {
            return new f(this);
        }
    }
}

package com.squareup.okhttp;

import com.squareup.okhttp.internal.j;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

/* compiled from: Handshake */
public final class o {
    private final String a;
    private final List<Certificate> b;
    private final List<Certificate> c;

    private o(String cipherSuite, List<Certificate> peerCertificates, List<Certificate> localCertificates) {
        this.a = cipherSuite;
        this.b = peerCertificates;
        this.c = localCertificates;
    }

    public static o c(SSLSession session) {
        Certificate[] peerCertificates;
        List<Certificate> peerCertificatesList;
        List<Certificate> localCertificatesList;
        String cipherSuite = session.getCipherSuite();
        if (cipherSuite != null) {
            try {
                peerCertificates = session.getPeerCertificates();
            } catch (SSLPeerUnverifiedException e) {
                peerCertificates = null;
            }
            if (peerCertificates != null) {
                peerCertificatesList = j.k(peerCertificates);
            } else {
                peerCertificatesList = Collections.emptyList();
            }
            Certificate[] localCertificates = session.getLocalCertificates();
            if (localCertificates != null) {
                localCertificatesList = j.k(localCertificates);
            } else {
                localCertificatesList = Collections.emptyList();
            }
            return new o(cipherSuite, peerCertificatesList, localCertificatesList);
        }
        throw new IllegalStateException("cipherSuite == null");
    }

    public static o b(String cipherSuite, List<Certificate> peerCertificates, List<Certificate> localCertificates) {
        if (cipherSuite != null) {
            return new o(cipherSuite, j.j(peerCertificates), j.j(localCertificates));
        }
        throw new IllegalArgumentException("cipherSuite == null");
    }

    public String a() {
        return this.a;
    }

    public List<Certificate> e() {
        return this.b;
    }

    public List<Certificate> d() {
        return this.c;
    }

    public boolean equals(Object other) {
        if (!(other instanceof o)) {
            return false;
        }
        o that = (o) other;
        if (!this.a.equals(that.a) || !this.b.equals(that.b) || !this.c.equals(that.c)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((17 * 31) + this.a.hashCode()) * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
    }
}

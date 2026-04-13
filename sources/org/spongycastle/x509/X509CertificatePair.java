package org.spongycastle.x509;

import java.security.cert.X509Certificate;
import org.spongycastle.asn1.x509.CertificatePair;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.provider.X509CertificateObject;

public class X509CertificatePair {
    private final JcaJceHelper a = new BCJcaJceHelper();
    private X509Certificate b;
    private X509Certificate c;

    public X509CertificatePair(CertificatePair pair) {
        if (pair.e() != null) {
            this.b = new X509CertificateObject(pair.e());
        }
        if (pair.g() != null) {
            this.c = new X509CertificateObject(pair.g());
        }
    }

    public X509Certificate a() {
        return this.b;
    }

    public X509Certificate b() {
        return this.c;
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof X509CertificatePair)) {
            return false;
        }
        X509CertificatePair pair = (X509CertificatePair) o;
        boolean equalReverse = true;
        boolean equalForward = true;
        X509Certificate x509Certificate = this.b;
        if (x509Certificate != null) {
            equalForward = x509Certificate.equals(pair.b);
        } else if (pair.b != null) {
            equalForward = false;
        }
        X509Certificate x509Certificate2 = this.c;
        if (x509Certificate2 != null) {
            equalReverse = x509Certificate2.equals(pair.c);
        } else if (pair.c != null) {
            equalReverse = false;
        }
        if (!equalForward || !equalReverse) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int hash = -1;
        X509Certificate x509Certificate = this.b;
        if (x509Certificate != null) {
            hash = -1 ^ x509Certificate.hashCode();
        }
        X509Certificate x509Certificate2 = this.c;
        if (x509Certificate2 != null) {
            return (hash * 17) ^ x509Certificate2.hashCode();
        }
        return hash;
    }
}

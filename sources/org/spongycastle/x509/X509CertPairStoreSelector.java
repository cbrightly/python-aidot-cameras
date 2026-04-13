package org.spongycastle.x509;

import org.spongycastle.util.Selector;

public class X509CertPairStoreSelector implements Selector {
    private X509CertStoreSelector c;
    private X509CertStoreSelector d;
    private X509CertificatePair f;

    public X509CertificatePair a() {
        return this.f;
    }

    public void c(X509CertStoreSelector forwardSelector) {
        this.c = forwardSelector;
    }

    public void d(X509CertStoreSelector reverseSelector) {
        this.d = reverseSelector;
    }

    public Object clone() {
        X509CertPairStoreSelector cln = new X509CertPairStoreSelector();
        cln.f = this.f;
        X509CertStoreSelector x509CertStoreSelector = this.c;
        if (x509CertStoreSelector != null) {
            cln.c((X509CertStoreSelector) x509CertStoreSelector.clone());
        }
        X509CertStoreSelector x509CertStoreSelector2 = this.d;
        if (x509CertStoreSelector2 != null) {
            cln.d((X509CertStoreSelector) x509CertStoreSelector2.clone());
        }
        return cln;
    }

    public boolean P0(Object obj) {
        try {
            if (!(obj instanceof X509CertificatePair)) {
                return false;
            }
            X509CertificatePair pair = (X509CertificatePair) obj;
            X509CertStoreSelector x509CertStoreSelector = this.c;
            if (x509CertStoreSelector != null && !x509CertStoreSelector.P0(pair.a())) {
                return false;
            }
            X509CertStoreSelector x509CertStoreSelector2 = this.d;
            if (x509CertStoreSelector2 != null && !x509CertStoreSelector2.P0(pair.b())) {
                return false;
            }
            X509CertificatePair x509CertificatePair = this.f;
            if (x509CertificatePair != null) {
                return x509CertificatePair.equals(obj);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public X509CertStoreSelector b() {
        return this.c;
    }
}

package org.spongycastle.jcajce;

import java.io.IOException;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.X509CertSelector;
import java.util.Collection;
import org.spongycastle.util.Selector;

public class PKIXCertStoreSelector<T extends Certificate> implements Selector<T> {
    /* access modifiers changed from: private */
    public final CertSelector c;

    public static class Builder {
        private final CertSelector a;

        public Builder(CertSelector certSelector) {
            this.a = (CertSelector) certSelector.clone();
        }

        public PKIXCertStoreSelector<? extends Certificate> a() {
            return new PKIXCertStoreSelector<>(this.a);
        }
    }

    private PKIXCertStoreSelector(CertSelector baseSelector) {
        this.c = baseSelector;
    }

    /* renamed from: match */
    public boolean P0(Certificate cert) {
        return this.c.match(cert);
    }

    public Object clone() {
        return new PKIXCertStoreSelector(this.c);
    }

    public static Collection<? extends Certificate> b(PKIXCertStoreSelector selector, CertStore certStore) {
        return certStore.getCertificates(new SelectorClone(selector));
    }

    public static class SelectorClone extends X509CertSelector {
        private final PKIXCertStoreSelector c;

        SelectorClone(PKIXCertStoreSelector selector) {
            this.c = selector;
            if (selector.c instanceof X509CertSelector) {
                X509CertSelector baseSelector = (X509CertSelector) selector.c;
                setAuthorityKeyIdentifier(baseSelector.getAuthorityKeyIdentifier());
                setBasicConstraints(baseSelector.getBasicConstraints());
                setCertificate(baseSelector.getCertificate());
                setCertificateValid(baseSelector.getCertificateValid());
                setKeyUsage(baseSelector.getKeyUsage());
                setMatchAllSubjectAltNames(baseSelector.getMatchAllSubjectAltNames());
                setPrivateKeyValid(baseSelector.getPrivateKeyValid());
                setSerialNumber(baseSelector.getSerialNumber());
                setSubjectKeyIdentifier(baseSelector.getSubjectKeyIdentifier());
                setSubjectPublicKey(baseSelector.getSubjectPublicKey());
                try {
                    setExtendedKeyUsage(baseSelector.getExtendedKeyUsage());
                    setIssuer(baseSelector.getIssuerAsBytes());
                    setNameConstraints(baseSelector.getNameConstraints());
                    setPathToNames(baseSelector.getPathToNames());
                    setPolicy(baseSelector.getPolicy());
                    setSubject(baseSelector.getSubjectAsBytes());
                    setSubjectAlternativeNames(baseSelector.getSubjectAlternativeNames());
                    setSubjectPublicKeyAlgID(baseSelector.getSubjectPublicKeyAlgID());
                } catch (IOException e) {
                    throw new IllegalStateException("base selector invalid: " + e.getMessage(), e);
                }
            }
        }

        public boolean match(Certificate certificate) {
            PKIXCertStoreSelector pKIXCertStoreSelector = this.c;
            if (pKIXCertStoreSelector == null) {
                return certificate != null;
            }
            return pKIXCertStoreSelector.P0(certificate);
        }
    }
}

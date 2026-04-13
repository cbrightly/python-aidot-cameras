package org.spongycastle.jcajce;

import java.math.BigInteger;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertStore;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509Certificate;
import java.util.Collection;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Selector;

public class PKIXCRLStoreSelector<T extends CRL> implements Selector<T> {
    /* access modifiers changed from: private */
    public final CRLSelector c;
    private final boolean d;
    private final boolean f;
    private final BigInteger q;
    private final byte[] x;
    private final boolean y;

    public static class Builder {
        /* access modifiers changed from: private */
        public final CRLSelector a;
        /* access modifiers changed from: private */
        public boolean b = false;
        /* access modifiers changed from: private */
        public boolean c = false;
        /* access modifiers changed from: private */
        public BigInteger d = null;
        /* access modifiers changed from: private */
        public byte[] e = null;
        /* access modifiers changed from: private */
        public boolean f = false;

        public Builder(CRLSelector crlSelector) {
            this.a = (CRLSelector) crlSelector.clone();
        }

        public Builder h(boolean completeCRLEnabled) {
            this.c = completeCRLEnabled;
            return this;
        }

        public void k(BigInteger maxBaseCRLNumber) {
            this.d = maxBaseCRLNumber;
        }

        public void j(boolean issuingDistributionPointEnabled) {
            this.f = issuingDistributionPointEnabled;
        }

        public void i(byte[] issuingDistributionPoint) {
            this.e = Arrays.h(issuingDistributionPoint);
        }

        public PKIXCRLStoreSelector<? extends CRL> g() {
            return new PKIXCRLStoreSelector<>(this);
        }
    }

    private PKIXCRLStoreSelector(Builder baseBuilder) {
        this.c = baseBuilder.a;
        this.d = baseBuilder.b;
        this.f = baseBuilder.c;
        this.q = baseBuilder.d;
        this.x = baseBuilder.e;
        this.y = baseBuilder.f;
    }

    /* renamed from: match */
    public boolean P0(CRL obj) {
        if (!(obj instanceof X509CRL)) {
            return this.c.match(obj);
        }
        X509CRL crl = (X509CRL) obj;
        ASN1Integer dci = null;
        try {
            byte[] bytes = crl.getExtensionValue(Extension.p2.s());
            if (bytes != null) {
                dci = ASN1Integer.o(ASN1OctetString.o(bytes).q());
            }
            if (e() && dci == null) {
                return false;
            }
            if (d() && dci != null) {
                return false;
            }
            if (dci != null && this.q != null && dci.q().compareTo(this.q) == 1) {
                return false;
            }
            if (this.y) {
                byte[] idp = crl.getExtensionValue(Extension.p3.s());
                byte[] bArr = this.x;
                if (bArr == null) {
                    if (idp != null) {
                        return false;
                    }
                } else if (!Arrays.b(idp, bArr)) {
                    return false;
                }
            }
            return this.c.match(obj);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean e() {
        return this.d;
    }

    public Object clone() {
        return this;
    }

    public boolean d() {
        return this.f;
    }

    public X509Certificate c() {
        CRLSelector cRLSelector = this.c;
        if (cRLSelector instanceof X509CRLSelector) {
            return ((X509CRLSelector) cRLSelector).getCertificateChecking();
        }
        return null;
    }

    public static Collection<? extends CRL> b(PKIXCRLStoreSelector selector, CertStore certStore) {
        return certStore.getCRLs(new SelectorClone(selector));
    }

    public static class SelectorClone extends X509CRLSelector {
        private final PKIXCRLStoreSelector c;

        SelectorClone(PKIXCRLStoreSelector selector) {
            this.c = selector;
            if (selector.c instanceof X509CRLSelector) {
                X509CRLSelector baseSelector = (X509CRLSelector) selector.c;
                setCertificateChecking(baseSelector.getCertificateChecking());
                setDateAndTime(baseSelector.getDateAndTime());
                setIssuers(baseSelector.getIssuers());
                setMinCRLNumber(baseSelector.getMinCRL());
                setMaxCRLNumber(baseSelector.getMaxCRL());
            }
        }

        public boolean match(CRL crl) {
            PKIXCRLStoreSelector pKIXCRLStoreSelector = this.c;
            if (pKIXCRLStoreSelector == null) {
                return crl != null;
            }
            return pKIXCRLStoreSelector.P0(crl);
        }
    }
}

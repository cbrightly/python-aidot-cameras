package org.spongycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CRL;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Selector;
import org.spongycastle.x509.extension.X509ExtensionUtil;

public class X509CRLStoreSelector extends X509CRLSelector implements Selector {
    private boolean c = false;
    private boolean d = false;
    private BigInteger f = null;
    private byte[] q = null;
    private boolean x = false;
    private X509AttributeCertificate y;

    public X509AttributeCertificate a() {
        return this.y;
    }

    public boolean P0(Object obj) {
        if (!(obj instanceof X509CRL)) {
            return false;
        }
        X509CRL crl = (X509CRL) obj;
        ASN1Integer dci = null;
        try {
            byte[] bytes = crl.getExtensionValue(X509Extensions.p2.s());
            if (bytes != null) {
                dci = ASN1Integer.o(X509ExtensionUtil.a(bytes));
            }
            if (d() && dci == null) {
                return false;
            }
            if (c() && dci != null) {
                return false;
            }
            if (dci != null && this.f != null && dci.q().compareTo(this.f) == 1) {
                return false;
            }
            if (this.x) {
                byte[] idp = crl.getExtensionValue(X509Extensions.p3.s());
                byte[] bArr = this.q;
                if (bArr == null) {
                    if (idp != null) {
                        return false;
                    }
                } else if (!Arrays.b(idp, bArr)) {
                    return false;
                }
            }
            return super.match((X509CRL) obj);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean match(CRL crl) {
        return P0(crl);
    }

    public boolean d() {
        return this.c;
    }

    public static X509CRLStoreSelector b(X509CRLSelector selector) {
        if (selector != null) {
            X509CRLStoreSelector cs = new X509CRLStoreSelector();
            cs.setCertificateChecking(selector.getCertificateChecking());
            cs.setDateAndTime(selector.getDateAndTime());
            try {
                cs.setIssuerNames(selector.getIssuerNames());
                cs.setIssuers(selector.getIssuers());
                cs.setMaxCRLNumber(selector.getMaxCRL());
                cs.setMinCRLNumber(selector.getMinCRL());
                return cs;
            } catch (IOException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("cannot create from null selector");
        }
    }

    public Object clone() {
        X509CRLStoreSelector sel = b(this);
        sel.c = this.c;
        sel.d = this.d;
        sel.f = this.f;
        sel.y = this.y;
        sel.x = this.x;
        sel.q = Arrays.h(this.q);
        return sel;
    }

    public boolean c() {
        return this.d;
    }
}

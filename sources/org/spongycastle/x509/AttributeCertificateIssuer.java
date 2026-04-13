package org.spongycastle.x509;

import java.io.IOException;
import java.security.Principal;
import java.security.cert.CertSelector;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.x509.AttCertIssuer;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.V2Form;
import org.spongycastle.util.Selector;

public class AttributeCertificateIssuer implements CertSelector, Selector {
    final ASN1Encodable c;

    public AttributeCertificateIssuer(AttCertIssuer issuer) {
        this.c = issuer.f();
    }

    private Object[] a() {
        GeneralNames name;
        ASN1Encodable aSN1Encodable = this.c;
        if (aSN1Encodable instanceof V2Form) {
            name = ((V2Form) aSN1Encodable).h();
        } else {
            name = (GeneralNames) aSN1Encodable;
        }
        GeneralName[] names = name.g();
        List l = new ArrayList(names.length);
        for (int i = 0; i != names.length; i++) {
            if (names[i].i() == 4) {
                try {
                    l.add(new X500Principal(names[i].h().toASN1Primitive().getEncoded()));
                } catch (IOException e) {
                    throw new RuntimeException("badly formed Name object");
                }
            }
        }
        return l.toArray(new Object[l.size()]);
    }

    public Principal[] b() {
        Object[] p = a();
        List l = new ArrayList();
        for (int i = 0; i != p.length; i++) {
            if (p[i] instanceof Principal) {
                l.add(p[i]);
            }
        }
        return (Principal[]) l.toArray(new Principal[l.size()]);
    }

    private boolean c(X500Principal subject, GeneralNames targets) {
        GeneralName[] names = targets.g();
        for (int i = 0; i != names.length; i++) {
            GeneralName gn = names[i];
            if (gn.i() == 4) {
                try {
                    if (new X500Principal(gn.h().toASN1Primitive().getEncoded()).equals(subject)) {
                        return true;
                    }
                } catch (IOException e) {
                }
            }
        }
        return false;
    }

    public Object clone() {
        return new AttributeCertificateIssuer(AttCertIssuer.e(this.c));
    }

    public boolean match(Certificate cert) {
        if (!(cert instanceof X509Certificate)) {
            return false;
        }
        X509Certificate x509Cert = (X509Certificate) cert;
        ASN1Encodable aSN1Encodable = this.c;
        if (aSN1Encodable instanceof V2Form) {
            V2Form issuer = (V2Form) aSN1Encodable;
            if (issuer.e() == null) {
                if (c(x509Cert.getSubjectX500Principal(), issuer.h())) {
                    return true;
                }
            } else if (!issuer.e().h().r().equals(x509Cert.getSerialNumber()) || !c(x509Cert.getIssuerX500Principal(), issuer.e().g())) {
                return false;
            } else {
                return true;
            }
        } else {
            if (c(x509Cert.getSubjectX500Principal(), (GeneralNames) aSN1Encodable)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AttributeCertificateIssuer)) {
            return false;
        }
        return this.c.equals(((AttributeCertificateIssuer) obj).c);
    }

    public int hashCode() {
        return this.c.hashCode();
    }

    public boolean P0(Object obj) {
        if (!(obj instanceof X509Certificate)) {
            return false;
        }
        return match((Certificate) obj);
    }
}

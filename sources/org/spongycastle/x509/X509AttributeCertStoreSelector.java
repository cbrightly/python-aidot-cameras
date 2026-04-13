package org.spongycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.Target;
import org.spongycastle.asn1.x509.TargetInformation;
import org.spongycastle.asn1.x509.Targets;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.util.Selector;

public class X509AttributeCertStoreSelector implements Selector {
    private AttributeCertificateHolder c;
    private AttributeCertificateIssuer d;
    private BigInteger f;
    private Date q;
    private X509AttributeCertificate x;
    private Collection y = new HashSet();
    private Collection z = new HashSet();

    public boolean P0(Object obj) {
        byte[] targetInfoExt;
        if (!(obj instanceof X509AttributeCertificate)) {
            return false;
        }
        X509AttributeCertificate attrCert = (X509AttributeCertificate) obj;
        X509AttributeCertificate x509AttributeCertificate = this.x;
        if (x509AttributeCertificate != null && !x509AttributeCertificate.equals(attrCert)) {
            return false;
        }
        if (this.f != null && !attrCert.getSerialNumber().equals(this.f)) {
            return false;
        }
        if (this.c != null && !attrCert.a().equals(this.c)) {
            return false;
        }
        if (this.d != null && !attrCert.c().equals(this.d)) {
            return false;
        }
        Date date = this.q;
        if (date != null) {
            try {
                attrCert.checkValidity(date);
            } catch (CertificateExpiredException e) {
                return false;
            } catch (CertificateNotYetValidException e2) {
                return false;
            }
        }
        if ((this.y.isEmpty() && this.z.isEmpty()) || (targetInfoExt = attrCert.getExtensionValue(X509Extensions.P4.s())) == null) {
            return true;
        }
        try {
            Targets[] targetss = TargetInformation.e(new ASN1InputStream(((DEROctetString) ASN1Primitive.h(targetInfoExt)).q()).r()).f();
            if (!this.y.isEmpty()) {
                boolean found = false;
                for (Targets t : targetss) {
                    Target[] targets = t.f();
                    int j = 0;
                    while (true) {
                        if (j >= targets.length) {
                            break;
                        } else if (this.y.contains(GeneralName.f(targets[j].g()))) {
                            found = true;
                            break;
                        } else {
                            j++;
                        }
                    }
                }
                if (!found) {
                    return false;
                }
            }
            if (this.z.isEmpty()) {
                return true;
            }
            boolean found2 = false;
            for (Targets t2 : targetss) {
                Target[] targets2 = t2.f();
                int j2 = 0;
                while (true) {
                    if (j2 >= targets2.length) {
                        break;
                    } else if (this.z.contains(GeneralName.f(targets2[j2].f()))) {
                        found2 = true;
                        break;
                    } else {
                        j2++;
                    }
                }
            }
            if (!found2) {
                return false;
            }
            return true;
        } catch (IOException e3) {
            return false;
        } catch (IllegalArgumentException e4) {
            return false;
        }
    }

    public Object clone() {
        X509AttributeCertStoreSelector sel = new X509AttributeCertStoreSelector();
        sel.x = this.x;
        sel.q = b();
        sel.c = this.c;
        sel.d = this.d;
        sel.f = this.f;
        sel.z = e();
        sel.y = f();
        return sel;
    }

    public X509AttributeCertificate a() {
        return this.x;
    }

    public Date b() {
        if (this.q != null) {
            return new Date(this.q.getTime());
        }
        return null;
    }

    public AttributeCertificateHolder c() {
        return this.c;
    }

    public BigInteger d() {
        return this.f;
    }

    public Collection f() {
        return Collections.unmodifiableCollection(this.y);
    }

    public Collection e() {
        return Collections.unmodifiableCollection(this.z);
    }
}

package org.spongycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.Principal;
import java.security.cert.CertSelector;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.Holder;
import org.spongycastle.jce.PrincipalUtil;
import org.spongycastle.jce.X509Principal;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Selector;

public class AttributeCertificateHolder implements CertSelector, Selector {
    final Holder c;

    AttributeCertificateHolder(ASN1Sequence seq) {
        this.c = Holder.g(seq);
    }

    public int b() {
        if (this.c.h() != null) {
            return this.c.h().f().q().intValue();
        }
        return -1;
    }

    public String a() {
        if (this.c.h() != null) {
            return this.c.h().e().e().s();
        }
        return null;
    }

    public byte[] f() {
        if (this.c.h() != null) {
            return this.c.h().i().q();
        }
        return null;
    }

    private boolean j(X509Principal subject, GeneralNames targets) {
        GeneralName[] names = targets.g();
        for (int i = 0; i != names.length; i++) {
            GeneralName gn = names[i];
            if (gn.i() == 4) {
                try {
                    if (new X509Principal(gn.h().toASN1Primitive().getEncoded()).equals(subject)) {
                        return true;
                    }
                } catch (IOException e) {
                }
            }
        }
        return false;
    }

    private Object[] e(GeneralName[] names) {
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

    private Principal[] h(GeneralNames names) {
        Object[] p = e(names.g());
        List l = new ArrayList();
        for (int i = 0; i != p.length; i++) {
            if (p[i] instanceof Principal) {
                l.add(p[i]);
            }
        }
        return (Principal[]) l.toArray(new Principal[l.size()]);
    }

    public Principal[] c() {
        if (this.c.f() != null) {
            return h(this.c.f());
        }
        return null;
    }

    public Principal[] d() {
        if (this.c.e() != null) {
            return h(this.c.e().g());
        }
        return null;
    }

    public BigInteger i() {
        if (this.c.e() != null) {
            return this.c.e().h().r();
        }
        return null;
    }

    public Object clone() {
        return new AttributeCertificateHolder((ASN1Sequence) this.c.toASN1Primitive());
    }

    public boolean match(Certificate cert) {
        if (!(cert instanceof X509Certificate)) {
            return false;
        }
        X509Certificate x509Cert = (X509Certificate) cert;
        try {
            if (this.c.e() != null) {
                if (!this.c.e().h().r().equals(x509Cert.getSerialNumber()) || !j(PrincipalUtil.a(x509Cert), this.c.e().g())) {
                    return false;
                }
                return true;
            } else if (this.c.f() != null && j(PrincipalUtil.b(x509Cert), this.c.f())) {
                return true;
            } else {
                if (this.c.h() != null) {
                    try {
                        MessageDigest md = MessageDigest.getInstance(a(), BouncyCastleProvider.PROVIDER_NAME);
                        switch (b()) {
                            case 0:
                                md.update(cert.getPublicKey().getEncoded());
                                break;
                            case 1:
                                md.update(cert.getEncoded());
                                break;
                        }
                        if (!Arrays.b(md.digest(), f())) {
                            return false;
                        }
                    } catch (Exception e) {
                        return false;
                    }
                }
                return false;
            }
        } catch (CertificateEncodingException e2) {
            return false;
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AttributeCertificateHolder)) {
            return false;
        }
        return this.c.equals(((AttributeCertificateHolder) obj).c);
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

package org.spongycastle.x509;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.x509.AttributeCertificate;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.util.Arrays;

public class X509V2AttributeCertificate implements X509AttributeCertificate {
    private AttributeCertificate c;
    private Date d;
    private Date f;

    private static AttributeCertificate f(InputStream in) {
        try {
            return AttributeCertificate.f(new ASN1InputStream(in).r());
        } catch (IOException e) {
            throw e;
        } catch (Exception e2) {
            throw new IOException("exception decoding certificate structure: " + e2.toString());
        }
    }

    public X509V2AttributeCertificate(InputStream encIn) {
        this(f(encIn));
    }

    public X509V2AttributeCertificate(byte[] encoded) {
        this((InputStream) new ByteArrayInputStream(encoded));
    }

    X509V2AttributeCertificate(AttributeCertificate cert) {
        this.c = cert;
        try {
            this.f = cert.e().e().f().q();
            this.d = cert.e().e().g().q();
        } catch (ParseException e) {
            throw new IOException("invalid data structure in certificate!");
        }
    }

    public BigInteger getSerialNumber() {
        return this.c.e().n().r();
    }

    public AttributeCertificateHolder a() {
        return new AttributeCertificateHolder((ASN1Sequence) this.c.e().h().toASN1Primitive());
    }

    public AttributeCertificateIssuer c() {
        return new AttributeCertificateIssuer(this.c.e().k());
    }

    public Date e() {
        return this.d;
    }

    public Date getNotAfter() {
        return this.f;
    }

    public void checkValidity(Date date) {
        if (date.after(getNotAfter())) {
            throw new CertificateExpiredException("certificate expired on " + getNotAfter());
        } else if (date.before(e())) {
            throw new CertificateNotYetValidException("certificate not valid till " + e());
        }
    }

    public byte[] getEncoded() {
        return this.c.getEncoded();
    }

    public byte[] getExtensionValue(String oid) {
        Extension ext;
        Extensions extensions = this.c.e().g();
        if (extensions == null || (ext = extensions.e(new ASN1ObjectIdentifier(oid))) == null) {
            return null;
        }
        try {
            return ext.g().getEncoded("DER");
        } catch (Exception e) {
            throw new RuntimeException("error encoding " + e.toString());
        }
    }

    private Set d(boolean critical) {
        Extensions extensions = this.c.e().g();
        if (extensions == null) {
            return null;
        }
        Set set = new HashSet();
        Enumeration e = extensions.h();
        while (e.hasMoreElements()) {
            ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) e.nextElement();
            if (extensions.e(oid).k() == critical) {
                set.add(oid.s());
            }
        }
        return set;
    }

    public Set getNonCriticalExtensionOIDs() {
        return d(false);
    }

    public Set getCriticalExtensionOIDs() {
        return d(true);
    }

    public boolean hasUnsupportedCriticalExtension() {
        Set extensions = getCriticalExtensionOIDs();
        return extensions != null && !extensions.isEmpty();
    }

    public X509Attribute[] b(String oid) {
        ASN1Sequence seq = this.c.e().f();
        List list = new ArrayList();
        for (int i = 0; i != seq.size(); i++) {
            X509Attribute attr = new X509Attribute(seq.r(i));
            if (attr.e().equals(oid)) {
                list.add(attr);
            }
        }
        if (list.size() == 0) {
            return null;
        }
        return (X509Attribute[]) list.toArray(new X509Attribute[list.size()]);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof X509AttributeCertificate)) {
            return false;
        }
        try {
            return Arrays.b(getEncoded(), ((X509AttributeCertificate) o).getEncoded());
        } catch (IOException e) {
            return false;
        }
    }

    public int hashCode() {
        try {
            return Arrays.J(getEncoded());
        } catch (IOException e) {
            return 0;
        }
    }
}

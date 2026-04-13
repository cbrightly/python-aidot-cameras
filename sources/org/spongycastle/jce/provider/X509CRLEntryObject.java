package org.spongycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CRLException;
import java.security.cert.X509CRLEntry;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Enumerated;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.util.ASN1Dump;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.CRLReason;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.TBSCertList;
import org.spongycastle.asn1.x509.X509Extension;
import org.spongycastle.util.Strings;

public class X509CRLEntryObject extends X509CRLEntry {
    private TBSCertList.CRLEntry c;
    private X500Name d;
    private int f;
    private boolean q;

    public X509CRLEntryObject(TBSCertList.CRLEntry c2, boolean isIndirect, X500Name previousCertificateIssuer) {
        this.c = c2;
        this.d = f(isIndirect, previousCertificateIssuer);
    }

    public boolean hasUnsupportedCriticalExtension() {
        Set extns = getCriticalExtensionOIDs();
        return extns != null && !extns.isEmpty();
    }

    private X500Name f(boolean isIndirect, X500Name previousCertificateIssuer) {
        if (!isIndirect) {
            return null;
        }
        Extension ext = d(Extension.p4);
        if (ext == null) {
            return previousCertificateIssuer;
        }
        try {
            GeneralName[] names = GeneralNames.e(ext.i()).g();
            for (int i = 0; i < names.length; i++) {
                if (names[i].i() == 4) {
                    return X500Name.e(names[i].h());
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public X500Principal getCertificateIssuer() {
        if (this.d == null) {
            return null;
        }
        try {
            return new X500Principal(this.d.getEncoded());
        } catch (IOException e) {
            return null;
        }
    }

    private Set e(boolean critical) {
        Extensions extensions = this.c.e();
        if (extensions == null) {
            return null;
        }
        Set set = new HashSet();
        Enumeration e = extensions.h();
        while (e.hasMoreElements()) {
            ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) e.nextElement();
            if (critical == extensions.e(oid).k()) {
                set.add(oid.s());
            }
        }
        return set;
    }

    public Set getCriticalExtensionOIDs() {
        return e(true);
    }

    public Set getNonCriticalExtensionOIDs() {
        return e(false);
    }

    private Extension d(ASN1ObjectIdentifier oid) {
        Extensions exts = this.c.e();
        if (exts != null) {
            return exts.e(oid);
        }
        return null;
    }

    public byte[] getExtensionValue(String oid) {
        Extension ext = d(new ASN1ObjectIdentifier(oid));
        if (ext == null) {
            return null;
        }
        try {
            return ext.g().getEncoded();
        } catch (Exception e) {
            throw new RuntimeException("error encoding " + e.toString());
        }
    }

    public int hashCode() {
        if (!this.q) {
            this.f = super.hashCode();
            this.q = true;
        }
        return this.f;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof X509CRLEntryObject) {
            return this.c.equals(((X509CRLEntryObject) o).c);
        }
        return super.equals(this);
    }

    public byte[] getEncoded() {
        try {
            return this.c.getEncoded("DER");
        } catch (IOException e) {
            throw new CRLException(e.toString());
        }
    }

    public BigInteger getSerialNumber() {
        return this.c.h().r();
    }

    public Date getRevocationDate() {
        return this.c.g().e();
    }

    public boolean hasExtensions() {
        return this.c.e() != null;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        String nl = Strings.d();
        buf.append("      userCertificate: ");
        buf.append(getSerialNumber());
        buf.append(nl);
        buf.append("       revocationDate: ");
        buf.append(getRevocationDate());
        buf.append(nl);
        buf.append("       certificateIssuer: ");
        buf.append(getCertificateIssuer());
        buf.append(nl);
        Extensions extensions = this.c.e();
        if (extensions != null) {
            Enumeration e = extensions.h();
            if (e.hasMoreElements()) {
                buf.append("   crlEntryExtensions:");
                buf.append(nl);
                while (e.hasMoreElements()) {
                    ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) e.nextElement();
                    Extension ext = extensions.e(oid);
                    if (ext.g() != null) {
                        ASN1InputStream dIn = new ASN1InputStream(ext.g().q());
                        buf.append("                       critical(");
                        buf.append(ext.k());
                        buf.append(") ");
                        try {
                            if (oid.equals(X509Extension.i)) {
                                buf.append(CRLReason.e(ASN1Enumerated.p(dIn.r())));
                                buf.append(nl);
                            } else if (oid.equals(X509Extension.n)) {
                                buf.append("Certificate issuer: ");
                                buf.append(GeneralNames.e(dIn.r()));
                                buf.append(nl);
                            } else {
                                buf.append(oid.s());
                                buf.append(" value = ");
                                buf.append(ASN1Dump.c(dIn.r()));
                                buf.append(nl);
                            }
                        } catch (Exception e2) {
                            buf.append(oid.s());
                            buf.append(" value = ");
                            buf.append("*****");
                            buf.append(nl);
                        }
                    } else {
                        buf.append(nl);
                    }
                }
            }
        }
        return buf.toString();
    }
}

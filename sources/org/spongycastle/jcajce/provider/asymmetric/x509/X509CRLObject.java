package org.spongycastle.jcajce.provider.asymmetric.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.util.ASN1Dump;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.CRLDistPoint;
import org.spongycastle.asn1.x509.CRLNumber;
import org.spongycastle.asn1.x509.CertificateList;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.IssuingDistributionPoint;
import org.spongycastle.asn1.x509.TBSCertList;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.X509Principal;
import org.spongycastle.util.Strings;
import org.spongycastle.util.encoders.Hex;

public class X509CRLObject extends X509CRL {
    private JcaJceHelper c;
    private CertificateList d;
    private String f;
    private byte[] q;
    private boolean x;
    private boolean y = false;
    private int z;

    static boolean f(X509CRL crl) {
        try {
            byte[] idp = crl.getExtensionValue(Extension.p3.s());
            return idp != null && IssuingDistributionPoint.h(ASN1OctetString.o(idp).q()).k();
        } catch (Exception e) {
            throw new ExtCRLException("Exception reading IssuingDistributionPoint", e);
        }
    }

    protected X509CRLObject(JcaJceHelper bcHelper, CertificateList c2) {
        this.c = bcHelper;
        this.d = c2;
        try {
            this.f = X509SignatureUtil.b(c2.k());
            if (c2.k().h() != null) {
                this.q = c2.k().h().toASN1Primitive().getEncoded("DER");
            } else {
                this.q = null;
            }
            this.x = f(this);
        } catch (Exception e) {
            throw new CRLException("CRL contents invalid: " + e);
        }
    }

    public boolean hasUnsupportedCriticalExtension() {
        Set extns = getCriticalExtensionOIDs();
        if (extns == null) {
            return false;
        }
        extns.remove(Extension.p3.s());
        extns.remove(Extension.p2.s());
        return !extns.isEmpty();
    }

    private Set e(boolean critical) {
        Extensions extensions;
        if (getVersion() != 2 || (extensions = this.d.n().e()) == null) {
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

    public byte[] getExtensionValue(String oid) {
        Extension ext;
        Extensions exts = this.d.n().e();
        if (exts == null || (ext = exts.e(new ASN1ObjectIdentifier(oid))) == null) {
            return null;
        }
        try {
            return ext.g().getEncoded();
        } catch (Exception e) {
            throw new IllegalStateException("error parsing " + e.toString());
        }
    }

    public byte[] getEncoded() {
        try {
            return this.d.getEncoded("DER");
        } catch (IOException e) {
            throw new CRLException(e.toString());
        }
    }

    public void verify(PublicKey key) {
        Signature sig;
        try {
            sig = this.c.d(getSigAlgName());
        } catch (Exception e) {
            sig = Signature.getInstance(getSigAlgName());
        }
        d(key, sig);
    }

    public void verify(PublicKey key, String sigProvider) {
        Signature sig;
        if (sigProvider != null) {
            sig = Signature.getInstance(getSigAlgName(), sigProvider);
        } else {
            sig = Signature.getInstance(getSigAlgName());
        }
        d(key, sig);
    }

    public void verify(PublicKey key, Provider sigProvider) {
        Signature sig;
        if (sigProvider != null) {
            sig = Signature.getInstance(getSigAlgName(), sigProvider);
        } else {
            sig = Signature.getInstance(getSigAlgName());
        }
        d(key, sig);
    }

    private void d(PublicKey key, Signature sig) {
        if (this.d.k().equals(this.d.n().k())) {
            sig.initVerify(key);
            sig.update(getTBSCertList());
            if (!sig.verify(getSignature())) {
                throw new SignatureException("CRL does not verify with supplied public key.");
            }
            return;
        }
        throw new CRLException("Signature algorithm on CertificateList does not match TBSCertList.");
    }

    public int getVersion() {
        return this.d.p();
    }

    public Principal getIssuerDN() {
        return new X509Principal(X500Name.e(this.d.f().toASN1Primitive()));
    }

    public X500Principal getIssuerX500Principal() {
        try {
            return new X500Principal(this.d.f().getEncoded());
        } catch (IOException e) {
            throw new IllegalStateException("can't encode issuer DN");
        }
    }

    public Date getThisUpdate() {
        return this.d.o().e();
    }

    public Date getNextUpdate() {
        if (this.d.g() != null) {
            return this.d.g().e();
        }
        return null;
    }

    private Set g() {
        Extension currentCaName;
        Set entrySet = new HashSet();
        Enumeration certs = this.d.h();
        X500Name previousCertificateIssuer = null;
        while (certs.hasMoreElements()) {
            TBSCertList.CRLEntry entry = (TBSCertList.CRLEntry) certs.nextElement();
            entrySet.add(new X509CRLEntryObject(entry, this.x, previousCertificateIssuer));
            if (this.x && entry.i() && (currentCaName = entry.e().e(Extension.p4)) != null) {
                previousCertificateIssuer = X500Name.e(GeneralNames.e(currentCaName.i()).g()[0].h());
            }
        }
        return entrySet;
    }

    public X509CRLEntry getRevokedCertificate(BigInteger serialNumber) {
        Extension currentCaName;
        Enumeration certs = this.d.h();
        X500Name previousCertificateIssuer = null;
        while (certs.hasMoreElements()) {
            TBSCertList.CRLEntry entry = (TBSCertList.CRLEntry) certs.nextElement();
            if (serialNumber.equals(entry.h().r())) {
                return new X509CRLEntryObject(entry, this.x, previousCertificateIssuer);
            }
            if (this.x && entry.i() && (currentCaName = entry.e().e(Extension.p4)) != null) {
                previousCertificateIssuer = X500Name.e(GeneralNames.e(currentCaName.i()).g()[0].h());
            }
        }
        return null;
    }

    public Set getRevokedCertificates() {
        Set entrySet = g();
        if (!entrySet.isEmpty()) {
            return Collections.unmodifiableSet(entrySet);
        }
        return null;
    }

    public byte[] getTBSCertList() {
        try {
            return this.d.n().getEncoded("DER");
        } catch (IOException e) {
            throw new CRLException(e.toString());
        }
    }

    public byte[] getSignature() {
        return this.d.i().s();
    }

    public String getSigAlgName() {
        return this.f;
    }

    public String getSigAlgOID() {
        return this.d.k().e().s();
    }

    public byte[] getSigAlgParams() {
        byte[] bArr = this.q;
        if (bArr == null) {
            return null;
        }
        byte[] tmp = new byte[bArr.length];
        System.arraycopy(bArr, 0, tmp, 0, tmp.length);
        return tmp;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        String nl = Strings.d();
        buf.append("              Version: ");
        buf.append(getVersion());
        buf.append(nl);
        buf.append("             IssuerDN: ");
        buf.append(getIssuerDN());
        buf.append(nl);
        buf.append("          This update: ");
        buf.append(getThisUpdate());
        buf.append(nl);
        buf.append("          Next update: ");
        buf.append(getNextUpdate());
        buf.append(nl);
        buf.append("  Signature Algorithm: ");
        buf.append(getSigAlgName());
        buf.append(nl);
        byte[] sig = getSignature();
        buf.append("            Signature: ");
        buf.append(new String(Hex.c(sig, 0, 20)));
        buf.append(nl);
        for (int i = 20; i < sig.length; i += 20) {
            if (i < sig.length - 20) {
                buf.append("                       ");
                buf.append(new String(Hex.c(sig, i, 20)));
                buf.append(nl);
            } else {
                buf.append("                       ");
                buf.append(new String(Hex.c(sig, i, sig.length - i)));
                buf.append(nl);
            }
        }
        Extensions extensions = this.d.n().e();
        if (extensions != null) {
            Enumeration e = extensions.h();
            if (e.hasMoreElements()) {
                buf.append("           Extensions: ");
                buf.append(nl);
            }
            while (e.hasMoreElements()) {
                ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) e.nextElement();
                Extension ext = extensions.e(oid);
                if (ext.g() != null) {
                    ASN1InputStream dIn = new ASN1InputStream(ext.g().q());
                    buf.append("                       critical(");
                    buf.append(ext.k());
                    buf.append(") ");
                    try {
                        if (oid.equals(Extension.p0)) {
                            buf.append(new CRLNumber(ASN1Integer.o(dIn.r()).q()));
                            buf.append(nl);
                        } else if (oid.equals(Extension.p2)) {
                            buf.append("Base CRL: " + new CRLNumber(ASN1Integer.o(dIn.r()).q()));
                            buf.append(nl);
                        } else if (oid.equals(Extension.p3)) {
                            buf.append(IssuingDistributionPoint.h(dIn.r()));
                            buf.append(nl);
                        } else if (oid.equals(Extension.A4)) {
                            buf.append(CRLDistPoint.f(dIn.r()));
                            buf.append(nl);
                        } else if (oid.equals(Extension.G4)) {
                            buf.append(CRLDistPoint.f(dIn.r()));
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
        Set<Object> set = getRevokedCertificates();
        if (set != null) {
            for (Object append : set) {
                buf.append(append);
                buf.append(nl);
            }
        }
        return buf.toString();
    }

    public boolean isRevoked(Certificate cert) {
        X500Name issuer;
        Extension currentCaName;
        if (cert.getType().equals("X.509")) {
            Enumeration certs = this.d.h();
            X500Name caName = this.d.f();
            if (certs.hasMoreElements()) {
                BigInteger serial = ((X509Certificate) cert).getSerialNumber();
                while (certs.hasMoreElements()) {
                    TBSCertList.CRLEntry entry = TBSCertList.CRLEntry.f(certs.nextElement());
                    if (this.x && entry.i() && (currentCaName = entry.e().e(Extension.p4)) != null) {
                        caName = X500Name.e(GeneralNames.e(currentCaName.i()).g()[0].h());
                    }
                    if (entry.h().r().equals(serial)) {
                        if (cert instanceof X509Certificate) {
                            issuer = X500Name.e(((X509Certificate) cert).getIssuerX500Principal().getEncoded());
                        } else {
                            try {
                                issuer = org.spongycastle.asn1.x509.Certificate.f(cert.getEncoded()).h();
                            } catch (CertificateEncodingException e) {
                                throw new IllegalArgumentException("Cannot process certificate: " + e.getMessage());
                            }
                        }
                        if (!caName.equals(issuer)) {
                            return false;
                        }
                        return true;
                    }
                }
            }
            return false;
        }
        throw new IllegalArgumentException("X.509 CRL used with non X.509 Cert");
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof X509CRL)) {
            return false;
        }
        if (!(other instanceof X509CRLObject)) {
            return super.equals(other);
        }
        X509CRLObject crlObject = (X509CRLObject) other;
        if (!this.y || !crlObject.y || crlObject.z == this.z) {
            return this.d.equals(crlObject.d);
        }
        return false;
    }

    public int hashCode() {
        if (!this.y) {
            this.y = true;
            this.z = super.hashCode();
        }
        return this.z;
    }
}

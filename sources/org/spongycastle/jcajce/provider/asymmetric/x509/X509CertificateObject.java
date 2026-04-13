package org.spongycastle.jcajce.provider.asymmetric.x509;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1BitString;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1OutputStream;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1String;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.misc.MiscObjectIdentifiers;
import org.spongycastle.asn1.misc.NetscapeCertType;
import org.spongycastle.asn1.misc.NetscapeRevocationURL;
import org.spongycastle.asn1.misc.VerisignCzagExtension;
import org.spongycastle.asn1.util.ASN1Dump;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x500.style.RFC4519Style;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.BasicConstraints;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.KeyUsage;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.X509Principal;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.util.Integers;
import org.spongycastle.util.Strings;
import org.spongycastle.util.encoders.Hex;

public class X509CertificateObject extends X509Certificate implements PKCS12BagAttributeCarrier {
    private PKCS12BagAttributeCarrier attrCarrier = new PKCS12BagAttributeCarrierImpl();
    private BasicConstraints basicConstraints;
    private JcaJceHelper bcHelper;
    private Certificate c;
    private int hashValue;
    private boolean hashValueSet;
    private boolean[] keyUsage;

    public X509CertificateObject(JcaJceHelper bcHelper2, Certificate c2) {
        this.bcHelper = bcHelper2;
        this.c = c2;
        try {
            byte[] bytes = f("2.5.29.19");
            if (bytes != null) {
                this.basicConstraints = BasicConstraints.e(ASN1Primitive.h(bytes));
            }
            try {
                byte[] bytes2 = f("2.5.29.15");
                if (bytes2 != null) {
                    ASN1BitString bits = DERBitString.x(ASN1Primitive.h(bytes2));
                    byte[] bytes3 = bits.q();
                    int length = (bytes3.length * 8) - bits.t();
                    int i = 9;
                    if (length >= 9) {
                        i = length;
                    }
                    this.keyUsage = new boolean[i];
                    for (int i2 = 0; i2 != length; i2++) {
                        this.keyUsage[i2] = (bytes3[i2 / 8] & (128 >>> (i2 % 8))) != 0;
                    }
                    return;
                }
                this.keyUsage = null;
            } catch (Exception e) {
                throw new CertificateParsingException("cannot construct KeyUsage: " + e);
            }
        } catch (Exception e2) {
            throw new CertificateParsingException("cannot construct BasicConstraints: " + e2);
        }
    }

    public void checkValidity() {
        checkValidity(new Date());
    }

    public void checkValidity(Date date) {
        if (date.getTime() > getNotAfter().getTime()) {
            throw new CertificateExpiredException("certificate expired on " + this.c.e().g());
        } else if (date.getTime() < getNotBefore().getTime()) {
            throw new CertificateNotYetValidException("certificate not valid till " + this.c.o().g());
        }
    }

    public int getVersion() {
        return this.c.s();
    }

    public BigInteger getSerialNumber() {
        return this.c.i().r();
    }

    public Principal getIssuerDN() {
        try {
            return new X509Principal(X500Name.e(this.c.h().getEncoded()));
        } catch (IOException e) {
            return null;
        }
    }

    public X500Principal getIssuerX500Principal() {
        try {
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            new ASN1OutputStream(bOut).j(this.c.h());
            return new X500Principal(bOut.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException("can't encode issuer DN");
        }
    }

    public Principal getSubjectDN() {
        return new X509Principal(X500Name.e(this.c.p().toASN1Primitive()));
    }

    public X500Principal getSubjectX500Principal() {
        try {
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            new ASN1OutputStream(bOut).j(this.c.p());
            return new X500Principal(bOut.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException("can't encode issuer DN");
        }
    }

    public Date getNotBefore() {
        return this.c.o().e();
    }

    public Date getNotAfter() {
        return this.c.e().e();
    }

    public byte[] getTBSCertificate() {
        try {
            return this.c.r().getEncoded("DER");
        } catch (IOException e) {
            throw new CertificateEncodingException(e.toString());
        }
    }

    public byte[] getSignature() {
        return this.c.k().s();
    }

    public String getSigAlgName() {
        return X509SignatureUtil.b(this.c.n());
    }

    public String getSigAlgOID() {
        return this.c.n().e().s();
    }

    public byte[] getSigAlgParams() {
        if (this.c.n().h() == null) {
            return null;
        }
        try {
            return this.c.n().h().toASN1Primitive().getEncoded("DER");
        } catch (IOException e) {
            return null;
        }
    }

    public boolean[] getIssuerUniqueID() {
        DERBitString id = this.c.r().i();
        if (id == null) {
            return null;
        }
        byte[] bytes = id.q();
        boolean[] boolId = new boolean[((bytes.length * 8) - id.t())];
        for (int i = 0; i != boolId.length; i++) {
            boolId[i] = (bytes[i / 8] & (128 >>> (i % 8))) != 0;
        }
        return boolId;
    }

    public boolean[] getSubjectUniqueID() {
        DERBitString id = this.c.r().r();
        if (id == null) {
            return null;
        }
        byte[] bytes = id.q();
        boolean[] boolId = new boolean[((bytes.length * 8) - id.t())];
        for (int i = 0; i != boolId.length; i++) {
            boolId[i] = (bytes[i / 8] & (128 >>> (i % 8))) != 0;
        }
        return boolId;
    }

    public boolean[] getKeyUsage() {
        return this.keyUsage;
    }

    public List getExtendedKeyUsage() {
        byte[] bytes = f("2.5.29.37");
        if (bytes == null) {
            return null;
        }
        try {
            ASN1Sequence seq = (ASN1Sequence) new ASN1InputStream(bytes).r();
            List list = new ArrayList();
            for (int i = 0; i != seq.size(); i++) {
                list.add(((ASN1ObjectIdentifier) seq.r(i)).s());
            }
            return Collections.unmodifiableList(list);
        } catch (Exception e) {
            throw new CertificateParsingException("error processing extended key usage extension");
        }
    }

    public int getBasicConstraints() {
        BasicConstraints basicConstraints2 = this.basicConstraints;
        if (basicConstraints2 == null || !basicConstraints2.g()) {
            return -1;
        }
        if (this.basicConstraints.f() == null) {
            return Integer.MAX_VALUE;
        }
        return this.basicConstraints.f().intValue();
    }

    public Collection getSubjectAlternativeNames() {
        return e(f(Extension.x.s()));
    }

    public Collection getIssuerAlternativeNames() {
        return e(f(Extension.y.s()));
    }

    public Set getCriticalExtensionOIDs() {
        if (getVersion() != 3) {
            return null;
        }
        Set set = new HashSet();
        Extensions extensions = this.c.r().f();
        if (extensions == null) {
            return null;
        }
        Enumeration e = extensions.h();
        while (e.hasMoreElements()) {
            ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) e.nextElement();
            if (extensions.e(oid).k()) {
                set.add(oid.s());
            }
        }
        return set;
    }

    private byte[] f(String oid) {
        Extension ext;
        Extensions exts = this.c.r().f();
        if (exts == null || (ext = exts.e(new ASN1ObjectIdentifier(oid))) == null) {
            return null;
        }
        return ext.g().q();
    }

    public byte[] getExtensionValue(String oid) {
        Extension ext;
        Extensions exts = this.c.r().f();
        if (exts == null || (ext = exts.e(new ASN1ObjectIdentifier(oid))) == null) {
            return null;
        }
        try {
            return ext.g().getEncoded();
        } catch (Exception e) {
            throw new IllegalStateException("error parsing " + e.toString());
        }
    }

    public Set getNonCriticalExtensionOIDs() {
        if (getVersion() != 3) {
            return null;
        }
        Set set = new HashSet();
        Extensions extensions = this.c.r().f();
        if (extensions == null) {
            return null;
        }
        Enumeration e = extensions.h();
        while (e.hasMoreElements()) {
            ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) e.nextElement();
            if (!extensions.e(oid).k()) {
                set.add(oid.s());
            }
        }
        return set;
    }

    public boolean hasUnsupportedCriticalExtension() {
        Extensions extensions;
        if (getVersion() != 3 || (extensions = this.c.r().f()) == null) {
            return false;
        }
        Enumeration e = extensions.h();
        while (e.hasMoreElements()) {
            ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) e.nextElement();
            if (!oid.equals(Extension.f) && !oid.equals(Extension.B4) && !oid.equals(Extension.C4) && !oid.equals(Extension.H4) && !oid.equals(Extension.A4) && !oid.equals(Extension.p3) && !oid.equals(Extension.p2) && !oid.equals(Extension.E4) && !oid.equals(Extension.z) && !oid.equals(Extension.x) && !oid.equals(Extension.z4) && extensions.e(oid).k()) {
                return true;
            }
        }
        return false;
    }

    public PublicKey getPublicKey() {
        try {
            return BouncyCastleProvider.getPublicKey(this.c.q());
        } catch (IOException e) {
            return null;
        }
    }

    public byte[] getEncoded() {
        try {
            return this.c.getEncoded("DER");
        } catch (IOException e) {
            throw new CertificateEncodingException(e.toString());
        }
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof X509CertificateObject)) {
            return super.equals(o);
        }
        X509CertificateObject other = (X509CertificateObject) o;
        if (!this.hashValueSet || !other.hashValueSet || this.hashValue == other.hashValue) {
            return this.c.equals(other.c);
        }
        return false;
    }

    public synchronized int hashCode() {
        if (!this.hashValueSet) {
            this.hashValue = super.hashCode();
            this.hashValueSet = true;
        }
        return this.hashValue;
    }

    public int originalHashCode() {
        int hashCode = 0;
        try {
            byte[] certData = getEncoded();
            for (int i = 1; i < certData.length; i++) {
                hashCode += certData[i] * i;
            }
            return hashCode;
        } catch (CertificateEncodingException e) {
            return 0;
        }
    }

    public void setBagAttribute(ASN1ObjectIdentifier oid, ASN1Encodable attribute) {
        this.attrCarrier.setBagAttribute(oid, attribute);
    }

    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier oid) {
        return this.attrCarrier.getBagAttribute(oid);
    }

    public Enumeration getBagAttributeKeys() {
        return this.attrCarrier.getBagAttributeKeys();
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        String nl = Strings.d();
        buf.append("  [0]         Version: ");
        buf.append(getVersion());
        buf.append(nl);
        buf.append("         SerialNumber: ");
        buf.append(getSerialNumber());
        buf.append(nl);
        buf.append("             IssuerDN: ");
        buf.append(getIssuerDN());
        buf.append(nl);
        buf.append("           Start Date: ");
        buf.append(getNotBefore());
        buf.append(nl);
        buf.append("           Final Date: ");
        buf.append(getNotAfter());
        buf.append(nl);
        buf.append("            SubjectDN: ");
        buf.append(getSubjectDN());
        buf.append(nl);
        buf.append("           Public Key: ");
        buf.append(getPublicKey());
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
        Extensions extensions = this.c.r().f();
        if (extensions != null) {
            Enumeration e = extensions.h();
            if (e.hasMoreElements()) {
                buf.append("       Extensions: \n");
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
                        if (oid.equals(Extension.z)) {
                            buf.append(BasicConstraints.e(dIn.r()));
                            buf.append(nl);
                        } else if (oid.equals(Extension.f)) {
                            buf.append(KeyUsage.g(dIn.r()));
                            buf.append(nl);
                        } else if (oid.equals(MiscObjectIdentifiers.b)) {
                            buf.append(new NetscapeCertType((DERBitString) dIn.r()));
                            buf.append(nl);
                        } else if (oid.equals(MiscObjectIdentifiers.d)) {
                            buf.append(new NetscapeRevocationURL((DERIA5String) dIn.r()));
                            buf.append(nl);
                        } else if (oid.equals(MiscObjectIdentifiers.k)) {
                            buf.append(new VerisignCzagExtension((DERIA5String) dIn.r()));
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
        return buf.toString();
    }

    public final void verify(PublicKey key) {
        Signature signature;
        String sigName = X509SignatureUtil.b(this.c.n());
        try {
            signature = this.bcHelper.d(sigName);
        } catch (Exception e) {
            signature = Signature.getInstance(sigName);
        }
        d(key, signature);
    }

    public final void verify(PublicKey key, String sigProvider) {
        Signature signature;
        String sigName = X509SignatureUtil.b(this.c.n());
        if (sigProvider != null) {
            signature = Signature.getInstance(sigName, sigProvider);
        } else {
            signature = Signature.getInstance(sigName);
        }
        d(key, signature);
    }

    public final void verify(PublicKey key, Provider sigProvider) {
        Signature signature;
        String sigName = X509SignatureUtil.b(this.c.n());
        if (sigProvider != null) {
            signature = Signature.getInstance(sigName, sigProvider);
        } else {
            signature = Signature.getInstance(sigName);
        }
        d(key, signature);
    }

    private void d(PublicKey key, Signature signature) {
        if (g(this.c.n(), this.c.r().n())) {
            X509SignatureUtil.c(signature, this.c.n().h());
            signature.initVerify(key);
            signature.update(getTBSCertificate());
            if (!signature.verify(getSignature())) {
                throw new SignatureException("certificate does not verify with supplied key");
            }
            return;
        }
        throw new CertificateException("signature algorithm in TBS cert not same as outer cert");
    }

    private boolean g(AlgorithmIdentifier id1, AlgorithmIdentifier id2) {
        if (!id1.e().equals(id2.e())) {
            return false;
        }
        if (id1.h() == null) {
            if (id2.h() == null || id2.h().equals(DERNull.c)) {
                return true;
            }
            return false;
        } else if (id2.h() != null) {
            return id1.h().equals(id2.h());
        } else {
            if (id1.h() == null || id1.h().equals(DERNull.c)) {
                return true;
            }
            return false;
        }
    }

    private static Collection e(byte[] extVal) {
        if (extVal == null) {
            return null;
        }
        try {
            Collection temp = new ArrayList();
            Enumeration it = ASN1Sequence.o(extVal).s();
            while (it.hasMoreElements()) {
                GeneralName genName = GeneralName.f(it.nextElement());
                List list = new ArrayList();
                list.add(Integers.b(genName.i()));
                switch (genName.i()) {
                    case 0:
                    case 3:
                    case 5:
                        list.add(genName.getEncoded());
                        break;
                    case 1:
                    case 2:
                    case 6:
                        list.add(((ASN1String) genName.h()).a());
                        break;
                    case 4:
                        list.add(X500Name.g(RFC4519Style.T, genName.h()).toString());
                        break;
                    case 7:
                        try {
                            list.add(InetAddress.getByAddress(ASN1OctetString.o(genName.h()).q()).getHostAddress());
                            break;
                        } catch (UnknownHostException e) {
                            break;
                        }
                    case 8:
                        list.add(ASN1ObjectIdentifier.t(genName.h()).s());
                        break;
                    default:
                        throw new IOException("Bad tag number: " + genName.i());
                }
                temp.add(Collections.unmodifiableList(list));
            }
            if (temp.size() == 0) {
                return null;
            }
            return Collections.unmodifiableCollection(temp);
        } catch (Exception e2) {
            throw new CertificateParsingException(e2.getMessage());
        }
    }
}

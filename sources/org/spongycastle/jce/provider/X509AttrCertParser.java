package org.spongycastle.jce.provider;

import java.io.BufferedInputStream;
import java.io.InputStream;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.SignedData;
import org.spongycastle.x509.X509AttributeCertificate;
import org.spongycastle.x509.X509StreamParserSpi;
import org.spongycastle.x509.X509V2AttributeCertificate;
import org.spongycastle.x509.util.StreamParsingException;

public class X509AttrCertParser extends X509StreamParserSpi {
    private static final PEMUtil a = new PEMUtil("ATTRIBUTE CERTIFICATE");
    private ASN1Set b = null;
    private int c = 0;
    private InputStream d = null;

    private X509AttributeCertificate d(InputStream in) {
        ASN1Sequence seq = (ASN1Sequence) new ASN1InputStream(in).r();
        if (seq.size() <= 1 || !(seq.r(0) instanceof ASN1ObjectIdentifier) || !seq.r(0).equals(PKCSObjectIdentifiers.B0)) {
            return new X509V2AttributeCertificate(seq.getEncoded());
        }
        this.b = new SignedData(ASN1Sequence.p((ASN1TaggedObject) seq.r(1), true)).f();
        return c();
    }

    private X509AttributeCertificate c() {
        if (this.b == null) {
            return null;
        }
        while (this.c < this.b.size()) {
            ASN1Set aSN1Set = this.b;
            int i = this.c;
            this.c = i + 1;
            ASN1Encodable s = aSN1Set.s(i);
            if ((s instanceof ASN1TaggedObject) && ((ASN1TaggedObject) s).r() == 2) {
                return new X509V2AttributeCertificate(ASN1Sequence.p((ASN1TaggedObject) s, false).getEncoded());
            }
        }
        return null;
    }

    private X509AttributeCertificate e(InputStream in) {
        ASN1Sequence seq = a.b(in);
        if (seq != null) {
            return new X509V2AttributeCertificate(seq.getEncoded());
        }
        return null;
    }

    public void a(InputStream in) {
        this.d = in;
        this.b = null;
        this.c = 0;
        if (!in.markSupported()) {
            this.d = new BufferedInputStream(this.d);
        }
    }

    public Object b() {
        try {
            ASN1Set aSN1Set = this.b;
            if (aSN1Set == null) {
                this.d.mark(10);
                int tag = this.d.read();
                if (tag == -1) {
                    return null;
                }
                if (tag != 48) {
                    this.d.reset();
                    return e(this.d);
                }
                this.d.reset();
                return d(this.d);
            } else if (this.c != aSN1Set.size()) {
                return c();
            } else {
                this.b = null;
                this.c = 0;
                return null;
            }
        } catch (Exception e) {
            throw new StreamParsingException(e.toString(), e);
        }
    }
}

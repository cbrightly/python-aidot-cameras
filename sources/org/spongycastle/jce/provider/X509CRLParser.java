package org.spongycastle.jce.provider;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.cert.CRL;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.SignedData;
import org.spongycastle.asn1.x509.CertificateList;
import org.spongycastle.x509.X509StreamParserSpi;
import org.spongycastle.x509.util.StreamParsingException;

public class X509CRLParser extends X509StreamParserSpi {
    private static final PEMUtil a = new PEMUtil("CRL");
    private ASN1Set b = null;
    private int c = 0;
    private InputStream d = null;

    private CRL d(InputStream in) {
        ASN1Sequence seq = (ASN1Sequence) new ASN1InputStream(in).r();
        if (seq.size() <= 1 || !(seq.r(0) instanceof ASN1ObjectIdentifier) || !seq.r(0).equals(PKCSObjectIdentifiers.B0)) {
            return new X509CRLObject(CertificateList.e(seq));
        }
        this.b = new SignedData(ASN1Sequence.p((ASN1TaggedObject) seq.r(1), true)).e();
        return c();
    }

    private CRL c() {
        ASN1Set aSN1Set = this.b;
        if (aSN1Set == null || this.c >= aSN1Set.size()) {
            return null;
        }
        ASN1Set aSN1Set2 = this.b;
        int i = this.c;
        this.c = i + 1;
        return new X509CRLObject(CertificateList.e(aSN1Set2.s(i)));
    }

    private CRL e(InputStream in) {
        ASN1Sequence seq = a.b(in);
        if (seq != null) {
            return new X509CRLObject(CertificateList.e(seq));
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

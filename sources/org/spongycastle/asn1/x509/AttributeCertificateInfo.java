package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;

public class AttributeCertificateInfo extends ASN1Object {
    private Extensions a1;
    private ASN1Integer c;
    private Holder d;
    private AttCertIssuer f;
    private DERBitString p0;
    private AlgorithmIdentifier q;
    private ASN1Integer x;
    private AttCertValidityPeriod y;
    private ASN1Sequence z;

    public static AttributeCertificateInfo i(Object obj) {
        if (obj instanceof AttributeCertificateInfo) {
            return (AttributeCertificateInfo) obj;
        }
        if (obj != null) {
            return new AttributeCertificateInfo(ASN1Sequence.o(obj));
        }
        return null;
    }

    private AttributeCertificateInfo(ASN1Sequence seq) {
        int start;
        if (seq.size() < 6 || seq.size() > 9) {
            throw new IllegalArgumentException("Bad sequence size: " + seq.size());
        }
        if (seq.r(0) instanceof ASN1Integer) {
            this.c = ASN1Integer.o(seq.r(0));
            start = 1;
        } else {
            this.c = new ASN1Integer(0);
            start = 0;
        }
        this.d = Holder.g(seq.r(start));
        this.f = AttCertIssuer.e(seq.r(start + 1));
        this.q = AlgorithmIdentifier.f(seq.r(start + 2));
        this.x = ASN1Integer.o(seq.r(start + 3));
        this.y = AttCertValidityPeriod.e(seq.r(start + 4));
        this.z = ASN1Sequence.o(seq.r(start + 5));
        for (int i = start + 6; i < seq.size(); i++) {
            ASN1Encodable obj = seq.r(i);
            if (obj instanceof DERBitString) {
                this.p0 = DERBitString.x(seq.r(i));
            } else if ((obj instanceof ASN1Sequence) || (obj instanceof Extensions)) {
                this.a1 = Extensions.g(seq.r(i));
            }
        }
    }

    public Holder h() {
        return this.d;
    }

    public AttCertIssuer k() {
        return this.f;
    }

    public ASN1Integer n() {
        return this.x;
    }

    public AttCertValidityPeriod e() {
        return this.y;
    }

    public ASN1Sequence f() {
        return this.z;
    }

    public Extensions g() {
        return this.a1;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (this.c.r().intValue() != 0) {
            v.a(this.c);
        }
        v.a(this.d);
        v.a(this.f);
        v.a(this.q);
        v.a(this.x);
        v.a(this.y);
        v.a(this.z);
        DERBitString dERBitString = this.p0;
        if (dERBitString != null) {
            v.a(dERBitString);
        }
        Extensions extensions = this.a1;
        if (extensions != null) {
            v.a(extensions);
        }
        return new DERSequence(v);
    }
}

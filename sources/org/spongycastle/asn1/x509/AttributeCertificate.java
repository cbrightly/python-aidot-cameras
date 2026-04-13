package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;

public class AttributeCertificate extends ASN1Object {
    AttributeCertificateInfo c;
    AlgorithmIdentifier d;
    DERBitString f;

    public static AttributeCertificate f(Object obj) {
        if (obj instanceof AttributeCertificate) {
            return (AttributeCertificate) obj;
        }
        if (obj != null) {
            return new AttributeCertificate(ASN1Sequence.o(obj));
        }
        return null;
    }

    public AttributeCertificate(ASN1Sequence seq) {
        if (seq.size() == 3) {
            this.c = AttributeCertificateInfo.i(seq.r(0));
            this.d = AlgorithmIdentifier.f(seq.r(1));
            this.f = DERBitString.x(seq.r(2));
            return;
        }
        throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }

    public AttributeCertificateInfo e() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        v.a(this.f);
        return new DERSequence(v);
    }
}

package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class AttCertValidityPeriod extends ASN1Object {
    ASN1GeneralizedTime c;
    ASN1GeneralizedTime d;

    public static AttCertValidityPeriod e(Object obj) {
        if (obj instanceof AttCertValidityPeriod) {
            return (AttCertValidityPeriod) obj;
        }
        if (obj != null) {
            return new AttCertValidityPeriod(ASN1Sequence.o(obj));
        }
        return null;
    }

    private AttCertValidityPeriod(ASN1Sequence seq) {
        if (seq.size() == 2) {
            this.c = ASN1GeneralizedTime.r(seq.r(0));
            this.d = ASN1GeneralizedTime.r(seq.r(1));
            return;
        }
        throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }

    public ASN1GeneralizedTime g() {
        return this.c;
    }

    public ASN1GeneralizedTime f() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        return new DERSequence(v);
    }
}

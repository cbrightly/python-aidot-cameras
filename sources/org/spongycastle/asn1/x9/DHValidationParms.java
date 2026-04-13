package org.spongycastle.asn1.x9;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;

public class DHValidationParms extends ASN1Object {
    private DERBitString c;
    private ASN1Integer d;

    public static DHValidationParms e(Object obj) {
        if (obj instanceof DHValidationParms) {
            return (DHValidationParms) obj;
        }
        if (obj != null) {
            return new DHValidationParms(ASN1Sequence.o(obj));
        }
        return null;
    }

    private DHValidationParms(ASN1Sequence seq) {
        if (seq.size() == 2) {
            this.c = DERBitString.x(seq.r(0));
            this.d = ASN1Integer.o(seq.r(1));
            return;
        }
        throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        return new DERSequence(v);
    }
}

package org.spongycastle.asn1.ess;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class SigningCertificateV2 extends ASN1Object {
    ASN1Sequence c;
    ASN1Sequence d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        ASN1Sequence aSN1Sequence = this.d;
        if (aSN1Sequence != null) {
            v.a(aSN1Sequence);
        }
        return new DERSequence(v);
    }
}

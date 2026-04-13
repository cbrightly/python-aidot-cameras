package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class OcspResponsesID extends ASN1Object {
    private OcspIdentifier c;
    private OtherHash d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        OtherHash otherHash = this.d;
        if (otherHash != null) {
            v.a(otherHash);
        }
        return new DERSequence(v);
    }
}

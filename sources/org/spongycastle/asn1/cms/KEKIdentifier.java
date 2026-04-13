package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class KEKIdentifier extends ASN1Object {
    private ASN1OctetString c;
    private ASN1GeneralizedTime d;
    private OtherKeyAttribute f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        ASN1GeneralizedTime aSN1GeneralizedTime = this.d;
        if (aSN1GeneralizedTime != null) {
            v.a(aSN1GeneralizedTime);
        }
        OtherKeyAttribute otherKeyAttribute = this.f;
        if (otherKeyAttribute != null) {
            v.a(otherKeyAttribute);
        }
        return new DERSequence(v);
    }
}

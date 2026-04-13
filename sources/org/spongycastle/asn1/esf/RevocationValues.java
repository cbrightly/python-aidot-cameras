package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class RevocationValues extends ASN1Object {
    private ASN1Sequence c;
    private ASN1Sequence d;
    private OtherRevVals f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (this.c != null) {
            v.a(new DERTaggedObject(true, 0, this.c));
        }
        if (this.d != null) {
            v.a(new DERTaggedObject(true, 1, this.d));
        }
        if (this.f != null) {
            v.a(new DERTaggedObject(true, 2, this.f.toASN1Primitive()));
        }
        return new DERSequence(v);
    }
}

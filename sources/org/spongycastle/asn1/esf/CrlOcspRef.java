package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class CrlOcspRef extends ASN1Object {
    private CrlListID c;
    private OcspListID d;
    private OtherRevRefs f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (this.c != null) {
            v.a(new DERTaggedObject(true, 0, this.c.toASN1Primitive()));
        }
        if (this.d != null) {
            v.a(new DERTaggedObject(true, 1, this.d.toASN1Primitive()));
        }
        if (this.f != null) {
            v.a(new DERTaggedObject(true, 2, this.f.toASN1Primitive()));
        }
        return new DERSequence(v);
    }
}

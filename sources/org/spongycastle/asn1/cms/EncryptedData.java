package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.BERTaggedObject;

public class EncryptedData extends ASN1Object {
    private ASN1Integer c;
    private EncryptedContentInfo d;
    private ASN1Set f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        ASN1Set aSN1Set = this.f;
        if (aSN1Set != null) {
            v.a(new BERTaggedObject(false, 1, aSN1Set));
        }
        return new BERSequence(v);
    }
}

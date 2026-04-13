package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class EnvelopedData extends ASN1Object {
    private ASN1Integer c;
    private OriginatorInfo d;
    private ASN1Set f;
    private EncryptedContentInfo q;
    private ASN1Set x;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        if (this.d != null) {
            v.a(new DERTaggedObject(false, 0, this.d));
        }
        v.a(this.f);
        v.a(this.q);
        if (this.x != null) {
            v.a(new DERTaggedObject(false, 1, this.x));
        }
        return new BERSequence(v);
    }
}

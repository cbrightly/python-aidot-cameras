package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class KeyRecRepContent extends ASN1Object {
    private PKIStatusInfo c;
    private CMPCertificate d;
    private ASN1Sequence f;
    private ASN1Sequence q;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        e(v, 0, this.d);
        e(v, 1, this.f);
        e(v, 2, this.q);
        return new DERSequence(v);
    }

    private void e(ASN1EncodableVector v, int tagNo, ASN1Encodable obj) {
        if (obj != null) {
            v.a(new DERTaggedObject(true, tagNo, obj));
        }
    }
}

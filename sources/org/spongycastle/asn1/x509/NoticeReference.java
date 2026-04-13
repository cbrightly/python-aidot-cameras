package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class NoticeReference extends ASN1Object {
    private DisplayText c;
    private ASN1Sequence d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector av = new ASN1EncodableVector();
        av.a(this.c);
        av.a(this.d);
        return new DERSequence(av);
    }
}

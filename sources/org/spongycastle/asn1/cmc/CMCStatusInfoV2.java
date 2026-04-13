package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERUTF8String;

public class CMCStatusInfoV2 extends ASN1Object {
    private final CMCStatus c;
    private final ASN1Sequence d;
    private final DERUTF8String f;
    private final OtherStatusInfo q;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        DERUTF8String dERUTF8String = this.f;
        if (dERUTF8String != null) {
            v.a(dERUTF8String);
        }
        OtherStatusInfo otherStatusInfo = this.q;
        if (otherStatusInfo != null) {
            v.a(otherStatusInfo);
        }
        return new DERSequence(v);
    }
}

package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERUTF8String;

public class CMCStatusInfo extends ASN1Object {
    private final CMCStatus c;
    private final ASN1Sequence d;
    private final DERUTF8String f;
    private final OtherInfo q;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        DERUTF8String dERUTF8String = this.f;
        if (dERUTF8String != null) {
            v.a(dERUTF8String);
        }
        OtherInfo otherInfo = this.q;
        if (otherInfo != null) {
            v.a(otherInfo);
        }
        return new DERSequence(v);
    }

    public static class OtherInfo extends ASN1Object implements ASN1Choice {
        private final CMCFailInfo c;
        private final PendInfo d;

        public ASN1Primitive toASN1Primitive() {
            PendInfo pendInfo = this.d;
            if (pendInfo != null) {
                return pendInfo.toASN1Primitive();
            }
            return this.c.toASN1Primitive();
        }
    }
}

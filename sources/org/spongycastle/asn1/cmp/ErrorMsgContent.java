package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class ErrorMsgContent extends ASN1Object {
    private PKIStatusInfo c;
    private ASN1Integer d;
    private PKIFreeText f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        e(v, this.d);
        e(v, this.f);
        return new DERSequence(v);
    }

    private void e(ASN1EncodableVector v, ASN1Encodable obj) {
        if (obj != null) {
            v.a(obj);
        }
    }
}

package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class CertStatus extends ASN1Object {
    private ASN1OctetString c;
    private ASN1Integer d;
    private PKIStatusInfo f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        PKIStatusInfo pKIStatusInfo = this.f;
        if (pKIStatusInfo != null) {
            v.a(pKIStatusInfo);
        }
        return new DERSequence(v);
    }
}

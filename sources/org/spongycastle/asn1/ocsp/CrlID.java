package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class CrlID extends ASN1Object {
    private DERIA5String c;
    private ASN1Integer d;
    private ASN1GeneralizedTime f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (this.c != null) {
            v.a(new DERTaggedObject(true, 0, this.c));
        }
        if (this.d != null) {
            v.a(new DERTaggedObject(true, 1, this.d));
        }
        if (this.f != null) {
            v.a(new DERTaggedObject(true, 2, this.f));
        }
        return new DERSequence(v);
    }
}

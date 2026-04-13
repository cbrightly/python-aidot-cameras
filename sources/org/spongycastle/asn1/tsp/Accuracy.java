package org.spongycastle.asn1.tsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class Accuracy extends ASN1Object {
    ASN1Integer c;
    ASN1Integer d;
    ASN1Integer f;

    protected Accuracy() {
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        ASN1Integer aSN1Integer = this.c;
        if (aSN1Integer != null) {
            v.a(aSN1Integer);
        }
        if (this.d != null) {
            v.a(new DERTaggedObject(false, 0, this.d));
        }
        if (this.f != null) {
            v.a(new DERTaggedObject(false, 1, this.f));
        }
        return new DERSequence(v);
    }
}

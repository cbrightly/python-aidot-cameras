package org.spongycastle.asn1.cryptopro;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class ECGOST3410ParamSetParameters extends ASN1Object {
    ASN1Integer c;
    ASN1Integer d;
    ASN1Integer f;
    ASN1Integer q;
    ASN1Integer x;
    ASN1Integer y;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.f);
        v.a(this.q);
        v.a(this.c);
        v.a(this.d);
        v.a(this.x);
        v.a(this.y);
        return new DERSequence(v);
    }
}

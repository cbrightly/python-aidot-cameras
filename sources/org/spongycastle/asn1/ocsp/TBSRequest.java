package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralName;

public class TBSRequest extends ASN1Object {
    private static final ASN1Integer c = new ASN1Integer(0);
    ASN1Integer d;
    GeneralName f;
    ASN1Sequence q;
    Extensions x;
    boolean y;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (!this.d.equals(c) || this.y) {
            v.a(new DERTaggedObject(true, 0, this.d));
        }
        if (this.f != null) {
            v.a(new DERTaggedObject(true, 1, this.f));
        }
        v.a(this.q);
        if (this.x != null) {
            v.a(new DERTaggedObject(true, 2, this.x));
        }
        return new DERSequence(v);
    }
}

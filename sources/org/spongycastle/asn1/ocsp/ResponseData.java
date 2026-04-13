package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extensions;

public class ResponseData extends ASN1Object {
    private static final ASN1Integer c = new ASN1Integer(0);
    private boolean d;
    private ASN1Integer f;
    private ResponderID q;
    private ASN1GeneralizedTime x;
    private ASN1Sequence y;
    private Extensions z;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (this.d || !this.f.equals(c)) {
            v.a(new DERTaggedObject(true, 0, this.f));
        }
        v.a(this.q);
        v.a(this.x);
        v.a(this.y);
        if (this.z != null) {
            v.a(new DERTaggedObject(true, 1, this.z));
        }
        return new DERSequence(v);
    }
}

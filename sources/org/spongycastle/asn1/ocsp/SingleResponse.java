package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extensions;

public class SingleResponse extends ASN1Object {
    private CertID c;
    private CertStatus d;
    private ASN1GeneralizedTime f;
    private ASN1GeneralizedTime q;
    private Extensions x;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        v.a(this.f);
        if (this.q != null) {
            v.a(new DERTaggedObject(true, 0, this.q));
        }
        if (this.x != null) {
            v.a(new DERTaggedObject(true, 1, this.x));
        }
        return new DERSequence(v);
    }
}

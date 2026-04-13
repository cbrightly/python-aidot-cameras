package org.spongycastle.asn1.tsp;

import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extensions;

public class TimeStampReq extends ASN1Object {
    ASN1Integer c;
    MessageImprint d;
    ASN1ObjectIdentifier f;
    ASN1Integer q;
    ASN1Boolean x;
    Extensions y;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        ASN1ObjectIdentifier aSN1ObjectIdentifier = this.f;
        if (aSN1ObjectIdentifier != null) {
            v.a(aSN1ObjectIdentifier);
        }
        ASN1Integer aSN1Integer = this.q;
        if (aSN1Integer != null) {
            v.a(aSN1Integer);
        }
        ASN1Boolean aSN1Boolean = this.x;
        if (aSN1Boolean != null && aSN1Boolean.s()) {
            v.a(this.x);
        }
        if (this.y != null) {
            v.a(new DERTaggedObject(false, 0, this.y));
        }
        return new DERSequence(v);
    }
}

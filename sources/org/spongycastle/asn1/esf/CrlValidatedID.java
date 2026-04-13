package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class CrlValidatedID extends ASN1Object {
    private OtherHash c;
    private CrlIdentifier d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c.toASN1Primitive());
        CrlIdentifier crlIdentifier = this.d;
        if (crlIdentifier != null) {
            v.a(crlIdentifier.toASN1Primitive());
        }
        return new DERSequence(v);
    }
}

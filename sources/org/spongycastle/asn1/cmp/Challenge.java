package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class Challenge extends ASN1Object {
    private AlgorithmIdentifier c;
    private ASN1OctetString d;
    private ASN1OctetString f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        e(v, this.c);
        v.a(this.d);
        v.a(this.f);
        return new DERSequence(v);
    }

    private void e(ASN1EncodableVector v, ASN1Encodable obj) {
        if (obj != null) {
            v.a(obj);
        }
    }
}

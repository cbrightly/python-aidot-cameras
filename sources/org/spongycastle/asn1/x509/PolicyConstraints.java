package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class PolicyConstraints extends ASN1Object {
    private BigInteger c;
    private BigInteger d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (this.c != null) {
            v.a(new DERTaggedObject(false, 0, new ASN1Integer(this.c)));
        }
        if (this.d != null) {
            v.a(new DERTaggedObject(false, 1, new ASN1Integer(this.d)));
        }
        return new DERSequence(v);
    }
}

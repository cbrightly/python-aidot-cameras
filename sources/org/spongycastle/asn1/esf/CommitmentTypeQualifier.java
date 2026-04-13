package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class CommitmentTypeQualifier extends ASN1Object {
    private ASN1ObjectIdentifier c;
    private ASN1Encodable d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector dev = new ASN1EncodableVector();
        dev.a(this.c);
        ASN1Encodable aSN1Encodable = this.d;
        if (aSN1Encodable != null) {
            dev.a(aSN1Encodable);
        }
        return new DERSequence(dev);
    }
}

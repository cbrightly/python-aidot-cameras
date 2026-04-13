package org.spongycastle.asn1.x509.qualified;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class QCStatement extends ASN1Object implements ETSIQCObjectIdentifiers, RFC3739QCObjectIdentifiers {
    ASN1ObjectIdentifier c;
    ASN1Encodable d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector seq = new ASN1EncodableVector();
        seq.a(this.c);
        ASN1Encodable aSN1Encodable = this.d;
        if (aSN1Encodable != null) {
            seq.a(aSN1Encodable);
        }
        return new DERSequence(seq);
    }
}

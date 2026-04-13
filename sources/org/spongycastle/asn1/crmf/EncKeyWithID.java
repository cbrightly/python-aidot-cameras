package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;

public class EncKeyWithID extends ASN1Object {
    private final PrivateKeyInfo c;
    private final ASN1Encodable d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        ASN1Encodable aSN1Encodable = this.d;
        if (aSN1Encodable != null) {
            v.a(aSN1Encodable);
        }
        return new DERSequence(v);
    }
}

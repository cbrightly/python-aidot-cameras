package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class CMSAlgorithmProtection extends ASN1Object {
    private final AlgorithmIdentifier c;
    private final AlgorithmIdentifier d;
    private final AlgorithmIdentifier f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        if (this.d != null) {
            v.a(new DERTaggedObject(false, 1, this.d));
        }
        if (this.f != null) {
            v.a(new DERTaggedObject(false, 2, this.f));
        }
        return new DERSequence(v);
    }
}

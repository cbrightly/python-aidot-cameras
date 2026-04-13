package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class SignaturePolicyId extends ASN1Object {
    private ASN1ObjectIdentifier c;
    private OtherHashAlgAndValue d;
    private SigPolicyQualifiers f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        SigPolicyQualifiers sigPolicyQualifiers = this.f;
        if (sigPolicyQualifiers != null) {
            v.a(sigPolicyQualifiers);
        }
        return new DERSequence(v);
    }
}

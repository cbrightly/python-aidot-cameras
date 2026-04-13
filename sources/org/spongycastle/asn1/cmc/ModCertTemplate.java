package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.crmf.CertTemplate;

public class ModCertTemplate extends ASN1Object {
    private final BodyPartPath c;
    private final BodyPartList d;
    private final boolean f;
    private final CertTemplate q;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        boolean z = this.f;
        if (!z) {
            v.a(ASN1Boolean.r(z));
        }
        v.a(this.q);
        return new DERSequence(v);
    }
}

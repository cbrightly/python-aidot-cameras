package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.ReasonFlags;

public class GetCRL extends ASN1Object {
    private final X500Name c;
    private GeneralName d;
    private ASN1GeneralizedTime f;
    private ReasonFlags q;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        GeneralName generalName = this.d;
        if (generalName != null) {
            v.a(generalName);
        }
        ASN1GeneralizedTime aSN1GeneralizedTime = this.f;
        if (aSN1GeneralizedTime != null) {
            v.a(aSN1GeneralizedTime);
        }
        ReasonFlags reasonFlags = this.q;
        if (reasonFlags != null) {
            v.a(reasonFlags);
        }
        return new DERSequence(v);
    }
}

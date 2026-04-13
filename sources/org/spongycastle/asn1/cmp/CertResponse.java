package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class CertResponse extends ASN1Object {
    private ASN1Integer c;
    private PKIStatusInfo d;
    private CertifiedKeyPair f;
    private ASN1OctetString q;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        CertifiedKeyPair certifiedKeyPair = this.f;
        if (certifiedKeyPair != null) {
            v.a(certifiedKeyPair);
        }
        ASN1OctetString aSN1OctetString = this.q;
        if (aSN1OctetString != null) {
            v.a(aSN1OctetString);
        }
        return new DERSequence(v);
    }
}

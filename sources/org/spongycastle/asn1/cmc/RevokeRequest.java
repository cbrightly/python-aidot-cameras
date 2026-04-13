package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERUTF8String;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.CRLReason;

public class RevokeRequest extends ASN1Object {
    private final X500Name c;
    private final ASN1Integer d;
    private final CRLReason f;
    private ASN1GeneralizedTime q;
    private ASN1OctetString x;
    private DERUTF8String y;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        v.a(this.f);
        ASN1GeneralizedTime aSN1GeneralizedTime = this.q;
        if (aSN1GeneralizedTime != null) {
            v.a(aSN1GeneralizedTime);
        }
        ASN1OctetString aSN1OctetString = this.x;
        if (aSN1OctetString != null) {
            v.a(aSN1OctetString);
        }
        DERUTF8String dERUTF8String = this.y;
        if (dERUTF8String != null) {
            v.a(dERUTF8String);
        }
        return new DERSequence(v);
    }
}

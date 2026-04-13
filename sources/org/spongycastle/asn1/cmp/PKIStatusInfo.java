package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;

public class PKIStatusInfo extends ASN1Object {
    ASN1Integer c;
    PKIFreeText d;
    DERBitString f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        PKIFreeText pKIFreeText = this.d;
        if (pKIFreeText != null) {
            v.a(pKIFreeText);
        }
        DERBitString dERBitString = this.f;
        if (dERBitString != null) {
            v.a(dERBitString);
        }
        return new DERSequence(v);
    }
}

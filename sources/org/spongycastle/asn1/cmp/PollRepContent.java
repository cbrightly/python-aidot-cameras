package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class PollRepContent extends ASN1Object {
    private ASN1Integer[] c;
    private ASN1Integer[] d;
    private PKIFreeText[] f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector outer = new ASN1EncodableVector();
        for (int i = 0; i != this.c.length; i++) {
            ASN1EncodableVector v = new ASN1EncodableVector();
            v.a(this.c[i]);
            v.a(this.d[i]);
            PKIFreeText[] pKIFreeTextArr = this.f;
            if (pKIFreeTextArr[i] != null) {
                v.a(pKIFreeTextArr[i]);
            }
            outer.a(new DERSequence(v));
        }
        return new DERSequence(outer);
    }
}

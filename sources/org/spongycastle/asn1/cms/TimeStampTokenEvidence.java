package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class TimeStampTokenEvidence extends ASN1Object {
    private TimeStampAndCRL[] c;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        int i = 0;
        while (true) {
            TimeStampAndCRL[] timeStampAndCRLArr = this.c;
            if (i == timeStampAndCRLArr.length) {
                return new DERSequence(v);
            }
            v.a(timeStampAndCRLArr[i]);
            i++;
        }
    }
}

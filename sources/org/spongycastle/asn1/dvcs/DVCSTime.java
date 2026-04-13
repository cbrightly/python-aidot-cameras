package org.spongycastle.asn1.dvcs;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.cms.ContentInfo;

public class DVCSTime extends ASN1Object implements ASN1Choice {
    private final ASN1GeneralizedTime c;
    private final ContentInfo d;

    public ASN1Primitive toASN1Primitive() {
        ASN1GeneralizedTime aSN1GeneralizedTime = this.c;
        if (aSN1GeneralizedTime != null) {
            return aSN1GeneralizedTime;
        }
        return this.d.toASN1Primitive();
    }

    public String toString() {
        ASN1GeneralizedTime aSN1GeneralizedTime = this.c;
        if (aSN1GeneralizedTime != null) {
            return aSN1GeneralizedTime.toString();
        }
        return this.d.toString();
    }
}

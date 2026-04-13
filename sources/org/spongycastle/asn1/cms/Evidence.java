package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERTaggedObject;

public class Evidence extends ASN1Object implements ASN1Choice {
    private TimeStampTokenEvidence c;

    public ASN1Primitive toASN1Primitive() {
        if (this.c != null) {
            return new DERTaggedObject(false, 0, this.c);
        }
        return null;
    }
}

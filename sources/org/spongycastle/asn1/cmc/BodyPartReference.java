package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;

public class BodyPartReference extends ASN1Object implements ASN1Choice {
    private final BodyPartID c;
    private final BodyPartPath d;

    public ASN1Primitive toASN1Primitive() {
        BodyPartID bodyPartID = this.c;
        if (bodyPartID != null) {
            return bodyPartID.toASN1Primitive();
        }
        return this.d.toASN1Primitive();
    }
}

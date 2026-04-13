package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;

public class BodyPartID extends ASN1Object {
    private final long c;

    public ASN1Primitive toASN1Primitive() {
        return new ASN1Integer(this.c);
    }
}

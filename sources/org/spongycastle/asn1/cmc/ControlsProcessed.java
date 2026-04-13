package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class ControlsProcessed extends ASN1Object {
    private final ASN1Sequence c;

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence((ASN1Encodable) this.c);
    }
}

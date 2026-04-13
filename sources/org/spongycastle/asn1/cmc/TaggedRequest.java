package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERTaggedObject;

public class TaggedRequest extends ASN1Object implements ASN1Choice {
    private final int c;
    private final ASN1Encodable d;

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(false, this.c, this.d);
    }
}

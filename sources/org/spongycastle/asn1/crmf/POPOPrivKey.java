package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERTaggedObject;

public class POPOPrivKey extends ASN1Object implements ASN1Choice {
    private int c;
    private ASN1Encodable d;

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(false, this.c, this.d);
    }
}

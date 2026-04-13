package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;

public class RecipientInfo extends ASN1Object implements ASN1Choice {
    ASN1Encodable c;

    public ASN1Primitive toASN1Primitive() {
        return this.c.toASN1Primitive();
    }
}

package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;

public class Attributes extends ASN1Object {
    private ASN1Set c;

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}

package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Null;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERNull;

public class PKIConfirmContent extends ASN1Object {
    private ASN1Null c = DERNull.c;

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}

package org.spongycastle.asn1.ua;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;

public class DSTU4145PublicKey extends ASN1Object {
    private ASN1OctetString c;

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}

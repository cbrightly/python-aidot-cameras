package org.spongycastle.asn1.est;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class CsrAttrs extends ASN1Object {
    private final AttrOrOID[] c;

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence((ASN1Encodable[]) this.c);
    }
}

package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.Extension;

public class ExtensionReq extends ASN1Object {
    private final Extension[] c;

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence((ASN1Encodable[]) this.c);
    }
}

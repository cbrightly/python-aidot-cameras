package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;

public class CertTemplate extends ASN1Object {
    private ASN1Sequence c;

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}

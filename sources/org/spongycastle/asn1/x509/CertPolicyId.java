package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;

public class CertPolicyId extends ASN1Object {
    private ASN1ObjectIdentifier c;

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}

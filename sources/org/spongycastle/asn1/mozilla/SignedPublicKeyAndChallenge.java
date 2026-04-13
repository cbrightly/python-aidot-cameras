package org.spongycastle.asn1.mozilla;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;

public class SignedPublicKeyAndChallenge extends ASN1Object {
    private final ASN1Sequence c;

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}

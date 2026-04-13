package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERNull;

public class SignaturePolicyIdentifier extends ASN1Object {
    private SignaturePolicyId c;
    private boolean d = true;

    public ASN1Primitive toASN1Primitive() {
        if (this.d) {
            return DERNull.c;
        }
        return this.c.toASN1Primitive();
    }
}

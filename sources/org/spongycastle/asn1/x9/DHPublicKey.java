package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;

public class DHPublicKey extends ASN1Object {
    private ASN1Integer c;

    public static DHPublicKey e(Object obj) {
        if (obj == null || (obj instanceof DHPublicKey)) {
            return (DHPublicKey) obj;
        }
        if (obj instanceof ASN1Integer) {
            return new DHPublicKey((ASN1Integer) obj);
        }
        throw new IllegalArgumentException("Invalid DHPublicKey: " + obj.getClass().getName());
    }

    private DHPublicKey(ASN1Integer y) {
        if (y != null) {
            this.c = y;
            return;
        }
        throw new IllegalArgumentException("'y' cannot be null");
    }

    public BigInteger f() {
        return this.c.q();
    }

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}
